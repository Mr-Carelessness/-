package cn.edu.zucc.pet.model;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
	String id;//�û����
	String name;//�û�����
	String phonenumber;//�绰����
	String email;//����
	String qq;//qq��
	String wx_id;//΢�ź�
	int point;//�û�����
	Timestamp credittime;//ע��ʱ��(sql)
	public Timestamp getCredittime() {
		return credittime;
	}
	public void setCredittime(Timestamp credittime) {
		this.credittime = credittime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWx_id() {
		return wx_id;
	}
	public void setWx_id(String wx_id) {
		this.wx_id = wx_id;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
}	
