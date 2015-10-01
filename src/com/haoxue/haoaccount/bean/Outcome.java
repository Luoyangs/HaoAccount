package com.haoxue.haoaccount.bean;

/**
 * 说明:支出
 * 作者:Luoyangs
 * 时间:2015-10-1
 */
public class Outcome {

	private int id;//ID
	private int userId;//用户（外键）
	private float num;//支出金额
	private int type;//类别:分别为收入，支出，预算
	private int ptype;//支出父类别（外键）
	private int ctype;//支出子类别（外键）
	private String from;//支出来源（外键）
	private String use;//支出去向
	private String info;//支出备注
	private String date;//支出时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPtype() {
		return ptype;
	}
	public void setPtype(int ptype) {
		this.ptype = ptype;
	}
	public int getCtype() {
		return ctype;
	}
	public void setCtype(int ctype) {
		this.ctype = ctype;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
