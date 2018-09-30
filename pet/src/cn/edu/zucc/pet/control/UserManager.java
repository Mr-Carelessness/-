package cn.edu.zucc.pet.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cn.edu.zucc.pet.model.Order;
import cn.edu.zucc.pet.model.Pet;
import cn.edu.zucc.pet.model.User;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;

public class UserManager {
	//�����û�
	public void addUser(User user)throws BaseException{
		String content = user.getId();
		//�û���ų��ȼ�飨��gui�и�������ŵ�������ʽ���ӣ�
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("�û���ű�����1-15���ַ�");
		}
		else {
			String pattern = "usr(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("�û���ű���Ϊ����ĸcus���������ֵ����");
			}
		}
		//�û��������ȼ��
		content = user.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("�û�����������1-45���ַ�");
		}
		//�û��绰���볤�ȼ��
		content = user.getPhonenumber();
		if(content==null || "".equals(content) || content.length() > 15) {
			throw new BusinessException("�绰���������1-15���ַ�");
		}
		//�û����䳤�ȼ��
		content = user.getEmail();
		if(content.length() > 20) {
			throw new BusinessException("�û����䳤�Ȳ��ܳ���20���ַ�");
		}
		//qq�ų��ȼ��
		content = user.getQq();
		if(content.length() > 15) {
			throw new BusinessException("QQ�ų��Ȳ��ܳ���15���ַ�");
		}
		//΢�źų��ȼ��
		content = user.getWx_id();
		if(content.length() > 20) {
			throw new BusinessException("΢�źų��Ȳ��ܳ���20���ַ�");
		}
		//�û����ּ��
		if(user.getPoint() < 0) {
			throw new BusinessException("�û����ֲ���<0");//ʵ��ʱ����һ���û�Ĭ��Ϊ0�������ֹ�����
		}	
		//�û�ע��ʱ�䲻��Ϊ�գ�����ͨ������User���뵱ǰʱ��
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//�����û�����Ƿ��Ѿ�����
			String sql = "select * from User where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("�û�����Ѿ�����");
			rs.close();
			pst.close();
			//�������ڣ������Ӹÿͻ�
			sql = "insert into User values(?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			pst.setString(2, user.getName());
			pst.setString(3, user.getPhonenumber());			
			pst.setString(4, user.getEmail());
			pst.setString(5, user.getQq());
			pst.setString(6, user.getWx_id());		
			pst.setInt(7, user.getPoint());
			pst.setTimestamp(8, user.getCredittime());	
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
	
	//�޸Ŀͻ�
	public void modifyUser(User user)throws BaseException{
		//�û���Ų����޸�
		String content = null;
		//�û��������ȼ��
		content = user.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("�û�����������1-45���ַ�");
		}
		//�û��绰���볤�ȼ��
		content = user.getPhonenumber();
		if(content==null || "".equals(content) || content.length() > 15) {
			throw new BusinessException("�û��绰���������1-15���ַ�");
		}
		//�û����䳤�ȼ��
		content = user.getEmail();
		if(content.length() > 20) {
			throw new BusinessException("�û����䳤�Ȳ��ܳ���20���ַ�");
		}
		//qq�ų��ȼ��
		content = user.getQq();
		if(content.length() > 15) {
			throw new BusinessException("QQ�ų��Ȳ��ܳ���15���ַ�");
		}
		//΢�źų��ȼ��
		content = user.getWx_id();
		if(content.length() > 20) {
			throw new BusinessException("΢�źų��Ȳ��ܳ���20���ַ�");
		}
		//�û����֣��û�ע��ʱ�䲻��������Ϣһ���޸�
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//�����û�����Ƿ��Ѿ�����
			String sql = "select * from User where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�û���Ų�����");
			rs.close();
			pst.close();
			//����Ӧ�û����ڣ�����¸��û����û�֮�以������Ĺؼ�����id�����������ﲻ��id�ظ��Խ��м��
			sql = "update User set name=?,phonenumber=?,email=?,qq=?,wx_id=? where id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, user.getName());
			pst.setString(2, user.getPhonenumber());
			pst.setString(3, user.getEmail());
			pst.setString(4, user.getQq());
			pst.setString(5, user.getWx_id());
			pst.setString(6, user.getId());
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
	
	//����û�����
	public void modifyUserPoint(String userId, double price)throws BaseException{
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//�����û�����Ƿ��Ѿ�����
			String sql = "select * from User where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userId);
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�û���Ų�����");
			rs.close();
			pst.close();
			//����Ӧ�û����ڣ�����¸��û����û�֮�以������Ĺؼ�����id�����������ﲻ��id�ظ��Խ��м��
			sql = "update User set point=point + ? where id=?";
			pst=conn.prepareStatement(sql);
			int point = (int) (price / 10);
			pst.setInt(1, point);
			pst.setString(2, userId);
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
	
	
	//ɾ���û�
	public void deleteUser(User user)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//�����û��Ƿ��Ѿ�����
			String sql = "select * from user where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("���û�������");
			rs.close();
			pst.close();
			//����û���Ӧ�ĳ����Ƿ����
			sql = "select * from pet where user_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("���û��Ѿ�ӵ�г������ɾ����س���");
			rs.close();
			pst.close();
			//����û���Ӧ�Ķ����Ƿ����
			sql = "select * from pet.order where user_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("���û��Ѿ�ӵ�ж���������ɾ����ض���");
			rs.close();
			pst.close();
			//ɾ���ͻ�
			sql="delete from user where id = ?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, user.getId());
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
	
	//���ҿͻ�
	public List<User> searchUser(String keyword)throws BaseException{
		List<User> result = new ArrayList<User>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select * from user";
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
				User u = new User();
				u.setId(rs.getString(1));
				u.setName(rs.getString(2));
				u.setPhonenumber(rs.getString(3));
				u.setEmail(rs.getString(4));
				u.setQq(rs.getString(5));
				u.setWx_id(rs.getString(6));
				u.setPoint(rs.getInt(7));
				u.setCredittime(rs.getTimestamp(8));
				result.add(u);
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
	
	//��������û�ID
	public String searchMaxUserId()throws BaseException{
		String result = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select max(Id) from user";
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
	
	//�����û����ҳ�����Ϣ����ͨ��ģ����ѯ����List�û����ٶ�ÿһ�������������û����б��������ɶ�����Ͼͷŵ�һ������һ������ļ��ϵ��У��������ִ�иú��������ҷ��������ĳ��
	public List<Pet> searchPetFromUser(User user)throws BaseException{
		List<Pet> result = new ArrayList<Pet>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select id,name,othername,user_id from Pet where user_id = ? order by id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, user.getId());
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Pet p = new Pet();
				p.setId(rs.getString(1));
				p.setName(rs.getString(2));
				p.setOthername(rs.getString(3));
				p.setUser_id(rs.getString(4));
				result.add(p);
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
	
	//�����û���Ų��Ҷ�����Ϣ����ȷ���ң�
	public List<Order> searchOrder(String userId)throws BaseException{
		List<Order> result = new ArrayList<Order>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select id,user_id,price,time,state from pet.Order where user_id = ? order by id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, userId);
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Order o = new Order();
				o.setId(rs.getString(1));
				o.setUser_id(rs.getString(2));
				o.setPrice(rs.getDouble(3));
				o.setTime(rs.getTimestamp(4));
				o.setState(rs.getString(5));
				result.add(o);
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
	
	//���ݻ���ǰʮ��ȡ�û�
	public List<User> searchUserByPoint()throws BaseException{
		List<User> result = new ArrayList<User>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select id,name,point,credittime from pet.user order by point desc limit 10";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				User u = new User();
				u.setId(rs.getString(1));
				u.setName(rs.getString(2));
				u.setPoint(rs.getInt(3));
				u.setCredittime(rs.getTimestamp(4));
				result.add(u);
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
	
	//����ע������ʱ��ǰʮ��ȡ�û�
	public List<User> searchUserByCredittime()throws BaseException{
		List<User> result = new ArrayList<User>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select id,name,point,credittime from pet.user order by credittime limit 10";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				User u = new User();
				u.setId(rs.getString(1));
				u.setName(rs.getString(2));
				u.setPoint(rs.getInt(3));
				u.setCredittime(rs.getTimestamp(4));
				result.add(u);
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
