package cn.edu.zucc.pet.model;

import cn.edu.zucc.pet.util.BaseException;

public class OrderDetail {
	String order_id;//预约编号
	String commodity_id;//商品编号
	int number;//商品数量
	double discount;//商品折扣
	
	//外键但是重要的信息
	String commodity_name;//商品名称
	double commodity_price;//商品价格（显示在订购界面上的是  商品价格*折扣*数量 价格）
	double commodity_itsprice;//商品单价
	
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCommodity_id() {
		return commodity_id;
	}
	public void setCommodity_id(String commodity_id) {
		this.commodity_id = commodity_id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) throws BaseException {
		if(number <= 0)
			throw new BaseException("");
		this.number = number;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) throws BaseException {
		if(discount < 0)
			throw new BaseException("");
		this.discount = discount;
	}
	
}
