package com.example.project.limolive.tencentlive.model;

public class GiftInfo {

	private int gid;
	private String name;
	private int type;
	private int price;
	private int profit;
	private int tab;
	private int restype;
	private int isshow;
	private int newtab;//add 3.1.2  
	private long addtime;//add 3.1.2
	private String desc;//add 3.1.2
	private int streamer_num; //add 3.1.2
	private int streamer_level;//add 3.1.2
	private int target;
	private int usemeans;
	private int quantity;
	private int position;
	private int x;
	private int y;
	private int num;//库存数
	private String resource;
	
	public boolean isSelected = false;
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getProfit() {
		return profit;
	}
	public void setProfit(int profit) {
		this.profit = profit;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public int getUsemeans() {
		return usemeans;
	}
	public void setUsemeans(int usemeans) {
		this.usemeans = usemeans;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTab() {
		return tab;
	}
	public void setTab(int tab) {
		this.tab = tab;
	}
	public int getRestype() {
		return restype;
	}
	public void setRestype(int restype) {
		this.restype = restype;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getIsshow() {
		return isshow;
	}
	public void setIsshow(int isshow) {
		this.isshow = isshow;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getNewtab() {
		return newtab;
	}
	
	public void setNewtab(int newtab) {
		this.newtab = newtab;
	}
	
	public long getAddtime() {
		return addtime;
	}
	
	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public int getStreamer_num() {
		return streamer_num;
	}
	
	public void setStreamer_num(int streamer_num) {
		this.streamer_num = streamer_num;
	}
	
	public int getStreamer_level() {
		return streamer_level;
	}
	
	public void setStreamer_level(int streamer_level) {
		this.streamer_level = streamer_level;
	}

}
