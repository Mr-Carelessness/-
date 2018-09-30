package cn.edu.zucc.pet.model;

public class SubscribeDetail {
	String commodity_id;//商品编号
	String subscribe_id;//服务编号
	int number;//服务数量，一般来说是1
	double discount;//服务折扣
	
	//外键但是很重要的信息
	String commodity_name;//服务名称
	double commodity_price;//服务价格（显示在订购界面上的是  商品价格*折扣*数量 价格）
	double commodity_itsprice;//服务单价
	
	public String getCommodity_name() {
		return commodity_name;
	}
	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}
	public double getCommodity_price() {
		return commodity_price;
	}
	public void setCommodity_price(double commodity_price) {
		this.commodity_price = commodity_price;
	}
	public double getCommodity_itsprice() {
		return commodity_itsprice;
	}
	public void setCommodity_itsprice(double commodity_itsprice) {
		this.commodity_itsprice = commodity_itsprice;
	}
	public String getCommodity_id() {
		return commodity_id;
	}
	public void setCommodity_id(String commodity_id) {
		this.commodity_id = commodity_id;
	}
	public String getSubscribe_id() {
		return subscribe_id;
	}
	public void setSubscribe_id(String subscribe_id) {
		this.subscribe_id = subscribe_id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
}
