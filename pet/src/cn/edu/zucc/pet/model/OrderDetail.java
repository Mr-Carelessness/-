package cn.edu.zucc.pet.model;

import cn.edu.zucc.pet.util.BaseException;

public class OrderDetail {
	String order_id;//ԤԼ���
	String commodity_id;//��Ʒ���
	int number;//��Ʒ����
	double discount;//��Ʒ�ۿ�
	
	//���������Ҫ����Ϣ
	String commodity_name;//��Ʒ����
	double commodity_price;//��Ʒ�۸���ʾ�ڶ��������ϵ���  ��Ʒ�۸�*�ۿ�*���� �۸�
	double commodity_itsprice;//��Ʒ����
	
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
