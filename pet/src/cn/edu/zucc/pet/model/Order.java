package cn.edu.zucc.pet.model;

import java.sql.Timestamp;

public class Order {
	String id;//订单编号
	String user_id;//用户编号
	double price;//订单商品总的价格
	String state;//配送状态(未发货/送货中/已收货)
	Timestamp time;//订单成功后打印小票的时间
	
	//外键但是需要用到的信息
	String user_name;//用户名称
	
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}
