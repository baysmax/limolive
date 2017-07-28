package com.example.project.limolive.sidebar;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/10/22.
 */
public class ITask implements Runnable {

    private HashMap<String, Object> parameters;       //参数HashMap
    private int key;                                  //任务标识
    private int paramsCount;

    public ITask() {
        super();
    }

    public int getParamsCount() {

        return paramsCount;

    }

    public void setParamsCount(int paramsCount) {
        this.paramsCount = paramsCount;
    }

    public ITask(int key) {

        super();
        this.key = key;

    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    /**
     * 设置任务hashmap属性，添加到ITask的haspMap
     * @param key  key
     * @param value 对应的值
     */
    public void setTaskParameters(String key, Object value) {

        if(null == parameters)
            parameters = new HashMap<String, Object>();

        parameters.put(key,value);

    }

    public void setParameters(HashMap<String,Object> parameters1) {
        parameters = parameters1;
    }
//    public Object getTaskParameters(String key) {
//        return parameters.get(key);
//    }

    public Object removeTaskParameters(String key) {
        return parameters.remove(key);
    }

    /**
     * 获取值
     * @param key 值对应的key
     * @param <T> 获取返回的类型
     * @return 返回值
     */
    public <T> T getTaskParameters(String key) {
        return (T)parameters.get(key);
    }

    public boolean containsKey(String key) {
        return parameters.containsKey(key);
    }

    public interface OnTaskDoingListener{

        void doTaskBackGround(ITask iTask);

    }

    public OnTaskDoingListener mListener;

    public void setOnTaskDoingListener(OnTaskDoingListener listener) {

        this.mListener = listener;

    }

    public void doInBackground() {

        if(null != mListener) {
            mListener.doTaskBackGround(this);
        }

    }

    /**
     * ThreadPoolmanager线程池管理类执行excute后 执行run，然后回调doTaskBackGround
     */
    @Override
    public void run() {

        doInBackground();

    }
}
