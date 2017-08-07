package com.example.project.limolive.tencentlive.views.customviews;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.tencentlive.model.GiftVo;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.UIUtils;
import com.example.project.limolive.tencentlive.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import static com.example.project.limolive.R.id.imageView;
import static com.example.project.limolive.R.id.rl_anim;
import static com.example.project.limolive.R.id.rl_main;

/**
 * @author zhongxf
 * @Description 礼物显示的管理类
 * @Date 2016/6/6.
 * 主要礼物逻辑：利用一个LinkedBlockingQueue来存储所有的礼物的实体类。然后利用Handler的消息机制，每隔一段时间从队列中取一次礼物出来
 * 如果取得礼物为空（队列中没有礼物），那么就延迟一段时间之后再次从队列中取出礼物
 * 如果从队列中取出的礼物不为空，则根据送礼物的人的UserId去寻找这个礼物是否正在显示，如果不在显示，则新建一个，如果正在显示，则直接修改数量
 * <p/>
 * 这个礼物View的管理类中一直存在一个定时器在沦陷礼物的容器下面的所有的礼物的View，当有礼物的View上次的更新时间超过最长显示时间，那么久就移除这个View
 * <p/>
 * 6/7实现：礼物容器中显示的礼物达到两条，并且新获取的礼物和他们两个不一样，那么需要移除一个来显示新的礼物
 * 判断所有的里面的出现的时间，然后把显示最久的先移除掉（需要考虑到线程安全）
 *
 * 6/7实现：定时器的线程会更新View，在获取礼物的时候也会更新View（增加线程安全控制）
 */
public class GiftShowManager {

    private LinkedBlockingQueue<GiftVo> queue;//礼物的队列
    private LinearLayout giftCon;//礼物的容器
    private Context cxt;//上下文
    private RelativeLayout rl_anim;
    private List<ImageView> imageLists;

    private TranslateAnimation inAnim;//礼物View出现的动画
    private Animation huaAnim;//礼物View出现的动画
    private TranslateAnimation outAnim;//礼物View消失的动画
    private ScaleAnimation giftNumAnim;//修改礼物数量的动画

    private final static int SHOW_GIFT_FLAG = 1;//显示礼物
    private final static int GET_QUEUE_GIFT = 0;//从队列中获取礼物
    private final static int REMOVE_GIFT_VIEW = 2;//当礼物的View显示超时，删除礼物View
    private final static int GET_HUA = 111;
    private final static int REMOVE_GIFT = 3;
    private ImageView im;

    private Timer timer;//轮询礼物容器的所有的子View判断是否超过显示的最长时间


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REMOVE_GIFT:
                    ImageView img= (ImageView) msg.obj;
                    rl_anim.removeView(img);
                    break;
                case SHOW_GIFT_FLAG://如果是处理显示礼物的消息
                    Log.i("handler收到的","msg..."+msg.toString());
                    GiftVo showVo = (GiftVo) msg.obj;
                    if (showVo.getType().equals("20")){

                    }
                    String userId = showVo.getUserId();
                    int num = showVo.getNum();

                    View giftView = giftCon.findViewWithTag(userId+showVo.getType());
                    giftNumAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            handler.sendEmptyMessageDelayed(GET_QUEUE_GIFT, 500);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });

                    if (giftView == null) {//获取的礼物的实体，判断送的人不在显示

                        //首先需要判断下Gift ViewGroup下面的子View是否超过两个
                        int count = giftCon.getChildCount();
                        if (count >= 2) {//如果正在显示的礼物的个数超过两个，那么就移除最后一次更新时间比较长的

                            View giftView1 = giftCon.getChildAt(0);
                            ImageView im1 = (ImageView) giftView1.findViewById(R.id.big_present_toa_2);
                            TextView nameTv1 = (TextView) giftView1.findViewById(R.id.name);
                            long lastTime1 = (long) nameTv1.getTag();

                            View giftView2 = giftCon.getChildAt(1);
                            ImageView im2 = (ImageView) giftView2.findViewById(R.id.big_present_toa_2);
                            TextView nameTv2 = (TextView) giftView2.findViewById(R.id.name);
                            long lastTime2 = (long) nameTv2.getTag();
                            Message rmMsg = new Message();
                            if (lastTime1 > lastTime2) {//如果第二个View显示的时间比较长
                                rmMsg.obj = 1; //从队列中获取

                            } else {//如果第一个View显示的时间长
                                rmMsg.obj = 0;  //显示礼物
                            }
                            rmMsg.what = REMOVE_GIFT_VIEW; // 移除view
                            handler.sendMessage(rmMsg);
                        }


                        //获取礼物的View的布局
                        giftView = LayoutInflater.from(cxt).inflate(R.layout.gift_item, null);
                        giftView.setTag(userId+showVo.getType());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.topMargin = 10;
                        giftView.setLayoutParams(lp);

                        //显示礼物的数量
                        final MagicTextView giftNum = (MagicTextView) giftView.findViewById(R.id.gift_num);
                        giftNum.setTag(num);
                        giftNum.setText("X" + num);
                        TextView tv = (TextView) giftView.findViewById(R.id.name);
                        TextView tv_n = (TextView) giftView.findViewById(R.id.tv_presentcontent_2);
                       im = (ImageView) giftView.findViewById(R.id.big_present_toa_2);
                        im.setTag(showVo.getType());
                        CircleImageView civ = (CircleImageView) giftView.findViewById(R.id.tv_chat_head_image_present_2);
                        tv.setText(showVo.getName());
                        tv.setTag(System.currentTimeMillis());
                      //  Picasso.with(cxt).load(HttpUtil.IMAGE_URL + showVo.getHeard()).into(civ);
                        showHeadIcon(civ,showVo.getHeard());
                        if (showVo.getType().equals("0")) {
                            im.setImageResource(R.drawable.present_1);
                            tv_n.setText("赠送主播");
                        } else if (showVo.getType().equals("1")) {
                            im.setImageResource(R.drawable.present_2);
                            tv_n.setText("赠送主播");
                        } else if (showVo.getType().equals("2")) {
                            im.setImageResource(R.drawable.present_3);
                            tv_n.setText("赠送主播");
                        } else if (showVo.getType().equals("3")) {
                            im.setImageResource(R.drawable.present_4);
                            tv_n.setText("赠送主播");
                        } else if (showVo.getType().equals("4")) {
                            im.setImageResource(R.drawable.present_5);
                            tv_n.setText("赠送主播");
                        } else if (showVo.getType().equals("5")) {
                            im.setImageResource(R.drawable.present_6);
                            tv_n.setText("赠送主播");
                        } else if (showVo.getType().equals("6")) {
                            im.setImageResource(R.drawable.present_7);
                            tv_n.setText("赠送主播");
                        } else if (showVo.getType().equals("7")) {
                            im.setImageResource(R.drawable.present_8);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("8")) {
                            im.setImageResource(R.drawable.present_0);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("9")) {
                            im.setImageResource(R.drawable.present_9);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("10")) {
                            im.setImageResource(R.drawable.present_10);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("11")) {
                            im.setImageResource(R.drawable.present_11);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("12")) {
                            im.setImageResource(R.drawable.present_12);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("13")) {
                            im.setImageResource(R.drawable.present_13);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("14")) {
                            im.setImageResource(R.drawable.present_14);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("15")) {
                            im.setImageResource(R.drawable.present_15);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("16")) {
                            im.setImageResource(R.drawable.present_16);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("17")) {
                            im.setImageResource(R.drawable.present_17);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("18")) {
                            im.setImageResource(R.drawable.present_18);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("19")) {
                            im.setImageResource(R.drawable.present_19);
                            final ImageView imageView = new ImageView(cxt);
                            showBigluwu(imageView,R.drawable.animation_qiche,R.anim.translate,cxt,rl_anim);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("20")) {
                            im.setImageResource(R.drawable.present_20);
                            final ImageView imageView = new ImageView(cxt);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            imageView.setLayoutParams(params);
                            imageView.setBackgroundResource(R.drawable.animation_1314);
                            Animation animation = AnimationUtils.loadAnimation(cxt, R.anim.translate3);
                            imageView.startAnimation(animation);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Message ms = Message.obtain();
                                    ms.what=REMOVE_GIFT;
                                    ms.obj=imageView;
                                    handler.sendMessage(ms);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            rl_anim.addView(imageView);
                            AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
                            anim.start();
                            imageLists.add(imageView);
                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("21")) {
                            im.setImageResource(R.drawable.present_21);
                            final ImageView imageView = new ImageView(cxt);
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                    400, 200);
                            imageView.setLayoutParams(params);
                            imageView.setBackgroundResource(R.drawable.animation_fj);
                            Animation animation = AnimationUtils.loadAnimation(cxt, R.anim.translate2);
                            imageView.startAnimation(animation);
                            animation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Message ms = Message.obtain();
                                    ms.what=REMOVE_GIFT;
                                    ms.obj=imageView;
                                    handler.sendMessage(ms);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            rl_anim.addView(imageView);
                            AnimationDrawable a = (AnimationDrawable) imageView.getBackground();
                            a.start();


                            tv_n.setText("赠送主播");
                        }else if (showVo.getType().equals("22")) {
                            im.setImageResource(R.drawable.present_22);
                            tv_n.setText("赠送主播");
                        }
                        //将礼物的View添加到礼物的ViewGroup中
                        giftCon.addView(giftView);

                        giftView.startAnimation(inAnim);//播放礼物View出现的动
                        handler.sendEmptyMessageDelayed(111,500);
                        inAnim.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                giftNum.startAnimation(giftNumAnim);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                    } else {//如果送的礼物正在显示（只是修改下数量）
                        //显示礼物的数量
                        final MagicTextView giftNum = (MagicTextView) giftView.findViewById(R.id.gift_num);
                        int showNum = (int) giftNum.getTag() + num;
                        giftNum.setText("X" + (showNum));
                        giftNum.setTag(showNum);
                        TextView tv = (TextView) giftView.findViewById(R.id.name);
                        tv.setTag(System.currentTimeMillis());

                        giftNum.startAnimation(giftNumAnim);
                    }
                    break;
                case GET_QUEUE_GIFT://如果是从队列中获取礼物实体的消息
                    GiftVo vo = queue.poll();
                    if (vo != null) {//如果从队列中获取的礼物不为空，那么就将礼物展示在界面上
                        Message giftMsg = new Message();
                        giftMsg.obj = vo;
                        giftMsg.what = SHOW_GIFT_FLAG;
                        handler.sendMessage(giftMsg);
                    } else {
                        handler.sendEmptyMessageDelayed(GET_QUEUE_GIFT, 1000);//如果这次从队列中获取的消息是礼物是空的，则一秒之后重新获取
                    }
                    break;

                case REMOVE_GIFT_VIEW:
                    final int index = (int) msg.obj;
                    View removeView = giftCon.getChildAt(index);
                    outAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            giftCon.removeViewAt(index);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    removeView.startAnimation(outAnim);

                    break;
                case GET_HUA:
                    im.setVisibility(View.VISIBLE);
                    im.startAnimation(huaAnim);
                    break;
                default:
                    break;
            }
        }
    };

    private void showBigluwu(final ImageView imageView , int drawable, int anim, Context context, final RelativeLayout relativeLayout) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                400, 200);
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(drawable);
        Animation animation = AnimationUtils.loadAnimation(context, anim);
        imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Message msg = Message.obtain();
                msg.obj=imageView;
                msg.what=REMOVE_GIFT;
                handler.sendMessage(msg);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        relativeLayout.addView(imageView);
        AnimationDrawable a = (AnimationDrawable) imageView.getBackground();
        a.start();
    }

    public GiftShowManager(Context cxt, final LinearLayout giftCon,RelativeLayout rl_anim) {
        this.cxt = cxt;
        this.giftCon = giftCon;
        this.rl_anim=rl_anim;
        imageLists=new ArrayList<>();
        queue = new LinkedBlockingQueue<GiftVo>(100);
        inAnim = (TranslateAnimation) AnimationUtils.loadAnimation(cxt, R.anim.slide_left_in);
        huaAnim = (Animation) AnimationUtils.loadAnimation(cxt, R.anim.gif_hua);
        outAnim = (TranslateAnimation) AnimationUtils.loadAnimation(cxt, R.anim.gift_out);
        giftNumAnim = (ScaleAnimation) AnimationUtils.loadAnimation(cxt, R.anim.gift_num);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int count = giftCon.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = giftCon.getChildAt(i);
                    TextView name = (TextView) view.findViewById(R.id.name);
                    long nowtime = System.currentTimeMillis();
                    long upTime = (long) name.getTag();
                    if ((nowtime - upTime) >= 5000) {
                        Message msg = new Message();
                        msg.obj = i;
                        msg.what = REMOVE_GIFT_VIEW;
                        handler.sendMessage(msg);
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(task, 2000, 2000);

    }

    private void showHeadIcon(ImageView view, String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(cxt.getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else {
            RequestManager req = Glide.with(cxt);
            if (avatar.toString().contains("http://")) {
                req.load(avatar).transform(new GlideCircleTransform(cxt)).into(view);
            } else {
                req.load(ApiHttpClient.API_PIC + avatar).transform(new GlideCircleTransform(cxt)).into(view);
            }
        }
    }

    //开始显示礼物
    public void showGift() {
        handler.sendEmptyMessageDelayed(GET_QUEUE_GIFT, 1000);//轮询队列获取礼物
    }

    //放入礼物到队列
    public boolean addGift(GiftVo vo) {
        return queue.add(vo);
    }


}
