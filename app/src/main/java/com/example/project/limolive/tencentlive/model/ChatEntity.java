package com.example.project.limolive.tencentlive.model;

/**
 * 消息体类
 */
public class ChatEntity {
	
	private String grpSendName;
	private String context;
	private int  type;

	//自定义消息类型
	private String message_type;
	private String send_phone;
	private String present_name;
	private String presenr_type;






	public ChatEntity() {
		// TODO Auto-generated constructor stub
	}

	public String getGrpSendName() {
		return grpSendName;
	}

	public void setGrpSendName(String grpSendName) {
		this.grpSendName = grpSendName;
	}

	public String getSend_phone() {
		return send_phone;
	}

	public void setSend_phone(String send_phone) {
		this.send_phone = send_phone;
	}
	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getPresent_name() {
		return present_name;
	}

	public void setPresent_name(String present_name) {
		this.present_name = present_name;
	}

	public String getPresenr_type() {
		return presenr_type;
	}

	public void setPresenr_type(String presenr_type) {
		this.presenr_type = presenr_type;
	}

	public String getSenderName() {
		return grpSendName;
	}

	public void setSenderName(String grpSendName) {
		this.grpSendName = grpSendName;
	}



	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ChatEntity{" +
				"grpSendName='" + grpSendName + '\'' +
				", context='" + context + '\'' +
				", type=" + type +
				", message_type='" + message_type + '\'' +
				", send_phone='" + send_phone + '\'' +
				", present_name='" + present_name + '\'' +
				", presenr_type='" + presenr_type + '\'' +
				'}';
	}
}
