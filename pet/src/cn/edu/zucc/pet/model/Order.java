package cn.edu.zucc.pet.model;

import java.sql.Timestamp;

public class Order {
	String id;//�������
	String user_id;//�û����
	double price;//������Ʒ�ܵļ۸�
	String state;//����״̬(δ����/�ͻ���/���ջ�)
	Timestamp time;//�����ɹ����ӡСƱ��ʱ��
	
	//���������Ҫ�õ�����Ϣ
	String user_name;//�û�����
	
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
