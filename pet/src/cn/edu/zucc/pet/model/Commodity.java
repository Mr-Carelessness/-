package cn.edu.zucc.pet.model;

public class Commodity {
	String id;//��Ʒ���
	String name;//��Ʒ����
	String category_id;//��Ʒ�������
	String brand;//��ƷƷ��
	double price;//��Ʒ�۸�
	String barcode;//������(������һ������)
	String type;//��Ʒ����(��Ʒ/����)
	
	//�����������Ҫ����Ϣ
	String category_name;//�������
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
}
