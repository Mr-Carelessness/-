package cn.edu.zucc.pet.model;

import java.sql.Timestamp;

public class Subscribe {
	String id;//ԤԼ���
	String pet_id;//ԤԼ������
	Timestamp time;//ԤԼ�ɹ����ӡСƱ��ʱ��
	Timestamp pretime;//ԤԼʱ��
	Timestamp realfinishedtime;//ʵ�����ʱ��
	String finishedstate;//������(�����/δ���)
	double price;//ԤԼ���з����ܵļ۸�
	
	//���������Ҫ�õ�����Ϣ
	String pet_name;//��������
	
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
