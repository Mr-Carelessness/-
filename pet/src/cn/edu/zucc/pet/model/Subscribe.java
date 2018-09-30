package cn.edu.zucc.pet.model;

import java.sql.Timestamp;

public class Subscribe {
	String id;//预约编号
	String pet_id;//预约宠物编号
	Timestamp time;//预约成功后打印小票的时间
	Timestamp pretime;//预约时间
	Timestamp realfinishedtime;//实际完成时间
	String finishedstate;//完成情况(已完成/未完成)
	double price;//预约所有服务总的价格
	
	//外键但是需要用到的信息
	String pet_name;//宠物名称
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getPet_name() {
		return pet_name;
	}
	public void setPet_name(String pet_name) {
		this.pet_name = pet_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPet_id() {
		return pet_id;
	}
	public void setPet_id(String pet_id) {
		this.pet_id = pet_id;
	}
	public Timestamp getPretime() {
		return pretime;
	}
	public void setPretime(Timestamp pretime) {
		this.pretime = pretime;
	}
	public Timestamp getRealfinishedtime() {
		return realfinishedtime;
	}
	public void setRealfinishedtime(Timestamp realfinishedtime) {
		this.realfinishedtime = realfinishedtime;
	}
	public String getFinishedstate() {
		return finishedstate;
	}
	public void setFinishedstate(String finishedstate) {
		this.finishedstate = finishedstate;
	}
	
}
