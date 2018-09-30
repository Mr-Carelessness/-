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
	//新增用户
	public void addUser(User user)throws BaseException{
		String content = user.getId();
		//用户编号长度检查（在gui中根据最大编号递增的形式增加）
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("用户编号必须是1-15个字符");
		}
		else {
			String pattern = "usr(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("用户编号必须为：字母cus和若干数字的组合");
			}
		}
		//用户姓名长度检查
		content = user.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("用户姓名必须是1-45个字符");
		}
		//用户电话号码长度检查
		content = user.getPhonenumber();
		if(content==null || "".equals(content) || content.length() > 15) {
			throw new BusinessException("电话号码必须是1-15个字符");
		}
		//用户邮箱长度检查
		content = user.getEmail();
		if(content.length() > 20) {
			throw new BusinessException("用户邮箱长度不能超过20个字符");
		}
		//qq号长度检查
		content = user.getQq();
		if(content.length() > 15) {
			throw new BusinessException("QQ号长度不能超过15个字符");
		}
		//微信号长度检查
		content = user.getWx_id();
		if(content.length() > 20) {
			throw new BusinessException("微信号长度不能超过20个字符");
		}
		//用户积分检查
		if(user.getPoint() < 0) {
			throw new BusinessException("用户积分不能<0");//实现时新增一个用户默认为0，这里防止误操作
		}	
		//用户注册时间不能为空，这里通过创建User传入当前时间
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找用户编号是否已经存在
			String sql = "select * from User where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("用户编号已经存在");
			rs.close();
			pst.close();
			//若不存在，则增加该客户
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
	
	//修改客户
	public void modifyUser(User user)throws BaseException{
		//用户编号不作修改
		String content = null;
		//用户姓名长度检查
		content = user.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("用户姓名必须是1-45个字符");
		}
		//用户电话号码长度检查
		content = user.getPhonenumber();
		if(content==null || "".equals(content) || content.length() > 15) {
			throw new BusinessException("用户电话号码必须是1-15个字符");
		}
		//用户邮箱长度检查
		content = user.getEmail();
		if(content.length() > 20) {
			throw new BusinessException("用户邮箱长度不能超过20个字符");
		}
		//qq号长度检查
		content = user.getQq();
		if(content.length() > 15) {
			throw new BusinessException("QQ号长度不能超过15个字符");
		}
		//微信号长度检查
		content = user.getWx_id();
		if(content.length() > 20) {
			throw new BusinessException("微信号长度不能超过20个字符");
		}
		//用户积分，用户注册时间不和其余信息一起修改
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//查找用户编号是否已经存在
			String sql = "select * from User where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("用户编号不存在");
			rs.close();
			pst.close();
			//若对应用户存在，则更新该用户，用户之间互相区别的关键性是id区别，所以这里不对id重复性进行检查
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
	
	//添加用户积分
	public void modifyUserPoint(String userId, double price)throws BaseException{
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//查找用户编号是否已经存在
			String sql = "select * from User where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userId);
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("用户编号不存在");
			rs.close();
			pst.close();
			//若对应用户存在，则更新该用户，用户之间互相区别的关键性是id区别，所以这里不对id重复性进行检查
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
	
	
	//删除用户
	public void deleteUser(User user)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找用户是否已经存在
			String sql = "select * from user where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该用户不存在");
			rs.close();
			pst.close();
			//检查用户对应的宠物是否存在
			sql = "select * from pet where user_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("该用户已经拥有宠物，请先删除相关宠物");
			rs.close();
			pst.close();
			//检查用户对应的订单是否存在
			sql = "select * from pet.order where user_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("该用户已经拥有订单，请先删除相关订单");
			rs.close();
			pst.close();
			//删除客户
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
	
	//查找客户
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
	
	//查找最大用户ID
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
	
	//根据用户查找宠物信息（先通过模糊查询查找List用户，再对每一个符合条件的用户进行遍历（生成多个集合就放到一个另外一个更大的集合当中），即多次执行该函数，查找符合条件的宠物）
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
	
	//根据用户编号查找订单信息（精确查找）
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
	
	//根据积分前十条取用户
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
	
	//根据注册最早时间前十条取用户
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
