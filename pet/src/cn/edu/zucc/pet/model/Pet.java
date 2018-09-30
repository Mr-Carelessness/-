package cn.edu.zucc.pet.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;

import org.hibernate.Hibernate;

public class Pet {
	String id;//宠物编号
	String name;//宠物姓名
	String othername;//宠物别名
	String user_id;//宠物主人
	Blob photo;//宠物照片
	
	//除外键之外的比较重要的信息
	String user_name;//主人姓名
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	public String getOthername() {
		return othername;
	}
	public void setOthername(String othername) {
		this.othername = othername;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob blob) {
		this.photo = blob;
	}
	
}
