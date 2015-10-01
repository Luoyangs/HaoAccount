package com.haoxue.haoaccount.bean;

/**
 * 说明:父类别
 * 作者:Luoyangs
 * 时间:2015-10-1
 */
public class Ptype {

	private int id;//ID
	private String name;//名称
	private int type;//总类别
	private int state;//状态
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
