package cn.edu.zucc.pet.model;

public class SubscribeDetail {
	String commodity_id;//��Ʒ���
	String subscribe_id;//������
	int number;//����������һ����˵��1
	double discount;//�����ۿ�
	
	//������Ǻ���Ҫ����Ϣ
	String commodity_name;//��������
	double commodity_price;//����۸���ʾ�ڶ��������ϵ���  ��Ʒ�۸�*�ۿ�*���� �۸�
	double commodity_itsprice;//���񵥼�
	
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
