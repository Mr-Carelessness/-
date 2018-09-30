package cn.edu.zucc.pet.model;

public class Operator {
	String id;//员工编号
	String name;//员工姓名
	int rank;//员工等级(最高10级，即根用户等级)
	String password;//员工密码
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
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
