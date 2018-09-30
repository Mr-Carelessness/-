package cn.edu.zucc.pet.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cn.edu.zucc.pet.model.Category;
import cn.edu.zucc.pet.model.Commodity;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;

public class CommodityManager {
	//�������
	public void addCategory(Category category)throws BaseException{
		String content = category.getId();
		//����ų��ȼ��
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("����ű�����1-15���ַ�");
		}
		else {
			String pattern = "cat(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("����ŵı�ű���Ϊ����ĸcat���������ֵ����");
			}
		}
		//������Ƴ��ȼ��
		content = category.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("������Ʊ�����1-45���ַ�");
		}
		//����������ȼ��
		content = category.getDescription();
		if(content.length()>200) {
			throw new BusinessException("����������ܳ���200���ַ�");
		}
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//����������Ƿ��Ѿ�����
			String sql = "select * from Category where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("������Ѿ�����");
			rs.close();
			pst.close();
			//�������ڣ������Ӹø����
			sql="insert into Category values(?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			pst.setString(2, category.getName());	
			pst.setString(3, category.getDescription());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	//�޸����
	public void modifyCategory(Category category)throws BaseException{
		//����Ų����޸�
		String content = null;
		//������Ƴ��ȼ��
		content = category.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("������Ʊ�����1-45���ַ�");
		}
		//����������ȼ��
		content = category.getDescription();
		if(content.length()>200) {
			throw new BusinessException("������Ʋ��ܳ���200���ַ�");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//������������Ƿ񲻴���
			String sql = "select * from Category where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��𲻴���");
			rs.close();
			pst.close();
			//����Ӧ�����ڣ�����¸�������֮�以������Ĺؼ�����id�����������ﲻ���ظ��Խ��м��
			sql = "update Category set name=?,description=? where id=?";
			pst = conn.prepareStatement(sql); 
			pst.setString(1, category.getName());
			pst.setString(2, category.getDescription());
			pst.setString(3, category.getId());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	//ɾ�����
	public void deleteCategory(Category category)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//��������Ƿ��Ѿ�����
			String sql = "select * from Category where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("����𲻴���");
			rs.close();
			pst.close();
			//���Ҹ�����Ƿ���ڶ�Ӧ����Ʒ
			sql = "select * from Commodity where category_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("������Ӧ�����Ʒ���ڣ�����ɾ�������Ʒ");
			rs.close();
			pst.close();
			//ɾ�����
			sql="delete from Category where id = ?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	//�������
	public List<Category> searchCategory(String keyword)throws BaseException{
		List<Category> result = new ArrayList<Category>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select * from Category";
			if(keyword!=null && !"".equals(keyword))
				sql+=" where id like ? or name like ?";
			sql+=" order by id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Category c=new Category();
				c.setId(rs.getString(1));
				c.setName(rs.getString(2));
				c.setDescription(rs.getString(3));
				result.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	
	//Ѱ��������Id
	public String searchMaxCategoryId()throws BaseException{
		String result = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select max(Id) from category";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	//������Ʒ
	public void addCommodity(Commodity commodity)throws BaseException{
		String content = commodity.getId();
		//��Ʒ��ų��ȼ��
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("��Ʒ��ű�����1-15���ַ�");
		}
		else {
			String pattern = "com(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("��Ʒ��ű���Ϊ����ĸcom���������ֵ����");
			}				
		}
		//��Ʒ���Ƴ��ȼ��
		content = commodity.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("��Ʒ���Ʊ�����1-45���ַ�");
		}
		//��Ʒ����ų��ȼ��
		content = commodity.getCategory_id();
		if(content==null || "".equals(content) || content.length()>20) {
			throw new BusinessException("��Ʒ����ű���Ϊ1-15���ַ�");
		}
		//��ƷƷ�Ƴ��ȼ��
		content = commodity.getBrand();
		if(content==null || "".equals(content) || content.length()>100) {
			throw new BusinessException("��ƷƷ�Ʊ���Ϊ1-45���ַ�");
		}
		//��Ʒ�۸���
		if(commodity.getPrice() < 0) {
			throw new BusinessException("��Ʒ�ܼ۸���С��0");
		}
		//��Ʒ�����볤�ȼ��
		content = commodity.getBarcode();
		if(content==null || "".equals(content) || content.length() > 30) {
			throw new BusinessException("��Ʒ���������Ϊ1-30���ַ�");
		}
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//������Ʒ����Ƿ��Ѿ�����
			String sql = "select * from commodity where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("��Ʒ����Ѿ�����");
			rs.close();
			pst.close();
			//�����Ʒ��������Ƿ�Ϊ�ջ����Ƿ��Ѿ�����������д���
			content = commodity.getCategory_id();
			if(content != null && content.equals("") == false) {
				sql = "select * from Category where id = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, commodity.getCategory_id());
				rs = pst.executeQuery();
				if(!rs.next()) throw new BusinessException("�����ڸ������");
				rs.close();
				pst.close();				
			}
			//����Ʒ��Ŵ��ڣ������Ӹ���Ʒ
			sql="insert into Commodity values(?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			pst.setString(2, commodity.getName());
			pst.setString(3, commodity.getCategory_id());			
			pst.setString(4, commodity.getBrand());
			pst.setDouble(5, commodity.getPrice());
			pst.setString(6, commodity.getBarcode());		
			pst.setString(7, commodity.getType());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	//�޸���Ʒ
	public void modifyCommodity(Commodity commodity)throws BaseException{
		//��Ʒ��Ų������޸�
		String content = null;
		//��Ʒ���Ƴ��ȼ��
		content = commodity.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("��Ʒ���Ʊ�����1-45���ַ�");
		}
		//��Ʒ����ų��ȼ��
		content = commodity.getCategory_id();
		if(content==null || "".equals(content) || content.length()>20) {
			throw new BusinessException("��Ʒ����ű���Ϊ1-15���ַ�");
		}
		//��ƷƷ�Ƴ��ȼ��
		content = commodity.getBrand();
		if(content==null || "".equals(content) || content.length()>100) {
			throw new BusinessException("��ƷƷ�Ʊ���Ϊ1-45���ַ�");
		}
		//��Ʒ�۸���
		if(commodity.getPrice() < 0) {
			throw new BusinessException("��Ʒ�ܼ۸���С��0");
		}
		//��Ʒ�����볤�ȼ��
		content = commodity.getBarcode();
		if(content==null || "".equals(content) || content.length() > 30) {
			throw new BusinessException("��Ʒ���������Ϊ1-30���ַ�");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//������Ʒ����Ƿ��Ѿ�����
			String sql = "select * from commodity where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��Ʒ��Ų�����");
			rs.close();
			pst.close();
			//�����Ʒ��������Ƿ�Ϊ�ջ����Ƿ��Ѿ�����������д���
			content = commodity.getCategory_id();
			if(content != null && content.equals("") == false) {
				sql = "select * from Category where id = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, commodity.getCategory_id());
				rs = pst.executeQuery();
				if(!rs.next()) throw new BusinessException("�����ڸ������");
				rs.close();
				pst.close();				
			}
			//����Ӧ�����ڣ�����¸���Ʒ����Ʒ֮�以������Ĺؼ�����id�����������ﲻ���ظ��Խ��м��
			sql = "update commodity set name=?,category_id=?,brand=?,price=?,barcode=? where id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, commodity.getName());
			pst.setString(2, commodity.getCategory_id());
			pst.setString(3, commodity.getBrand() );
			pst.setDouble(4, commodity.getPrice());
			pst.setString(5, commodity.getBarcode());
			pst.setString(6, commodity.getId());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
		
	//ɾ����Ʒ
	public void deleteCommodity(Commodity commodity)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//������Ʒ�Ƿ��Ѿ�����
			String sql = "select * from commodity where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("����Ʒ������");
			rs.close();
			pst.close();
			//������Ʒ��Ӧ�Ķ����Ƿ����
			sql = "select * from pet.orderDetail where commodity_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("����Ʒ������ض���������ɾ����ض���");
			rs.close();
			pst.close();
			//������Ʒ��Ӧ��ԤԼ�Ƿ����
			sql = "select * from subscribeDetail where commodity_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("����Ʒ�������ԤԼ������ɾ�����ԤԼ");
			rs.close();
			pst.close();
			//ɾ����Ʒ
			sql="delete from commodity where id = ?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	//����(��Ʒ/����)
	public List<Commodity> searchCommodity(String keyword, int choice)throws BaseException{
		List<Commodity> result = new ArrayList<Commodity>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select co.id,co.name,co.category_id,co.brand,co.price,co.barcode,ca.name from commodity co left outer join category ca on co.category_id = ca.id ";
			//��Ʒ
			if(choice == 1) {
				sql+= "where (co.type = '��Ʒ')";
			}
			//����
			else if(choice == 2) {
				sql+= "where (co.type = '����')";
			}
			if(keyword!=null && !"".equals(keyword))
				sql+=" and (co.id like ? or co.name like ?)";
			sql+=" order by co.id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Commodity c = new Commodity();
				c.setId(rs.getString(1));
				c.setName(rs.getString(2));
				c.setCategory_id(rs.getString(3));
				c.setBrand(rs.getString(4));
				c.setPrice(rs.getDouble(5));
				c.setBarcode(rs.getString(6));
				c.setCategory_name(rs.getString(7));
				result.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	//����������������ƣ�����(��Ʒ/����)
	public List<Commodity> searchCommodityByBarcode(String keyword, int choice)throws BaseException{
		List<Commodity> result = new ArrayList<Commodity>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select co.id,co.name,co.category_id,co.brand,co.price,co.barcode,ca.name from commodity co left outer join category ca on co.category_id = ca.id ";
			//��Ʒ
			if(choice == 1) {
				sql+= "where (co.type = '��Ʒ')";
			}
			//����
			else if(choice == 2) {
				sql+= "where (co.type = '����')";
			}
			if(keyword!=null && !"".equals(keyword))
				sql+=" and (co.barcode like ? or co.name like ?)";
			sql+=" order by co.id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Commodity c = new Commodity();
				c.setId(rs.getString(1));
				c.setName(rs.getString(2));
				c.setCategory_id(rs.getString(3));
				c.setBrand(rs.getString(4));
				c.setPrice(rs.getDouble(5));
				c.setBarcode(rs.getString(6));
				c.setCategory_name(rs.getString(7));
				result.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	//Ѱ�������ƷId
	public String searchMaxCommodityId()throws BaseException{
		String result = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select max(Id) from commodity";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	//������Ʒ���/���ƻ�����Ʒ�����/���Ʋ��ҷ���������������Ʒ
	public List<Commodity> searchCommodityFromKeyWord(String keyword)throws BaseException{
		List<Commodity> result = new ArrayList<Commodity>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select co.id,co.name,co.category_id,co.brand,co.price,co.barcode,ca.name"
					+ " from commodity co left outer join category ca on co.category_id = ca.id ";
			if(keyword!=null && !"".equals(keyword))
				sql+=" where co.id like ? or co.name like ? or ca.id like ? or ca.name like ?";
			sql+=" order by co.id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
				pst.setString(3, "%"+keyword+"%");
				pst.setString(4, "%"+keyword+"%");
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Commodity c = new Commodity();
				c.setId(rs.getString(1));
				c.setName(rs.getString(2));
				c.setCategory_id(rs.getString(3));
				c.setBrand(rs.getString(4));
				c.setPrice(rs.getDouble(5));
				c.setBarcode(rs.getString(6));
				c.setCategory_name(rs.getString(7));
				result.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
}
