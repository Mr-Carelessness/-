package cn.edu.zucc.pet.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.pet.model.Operator;
import cn.edu.zucc.pet.model.User;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;

public class OperatorManager {
	public static Operator currentOperator = null;//系统登陆用户
	
	//（1）创建用户
	public void createOperator(Operator operator)throws BaseException{
		if(operator.getId()==null || "".equals(operator.getId()) || operator.getId().length()>15){
			throw new BusinessException("员工账号必须是1-15个字符");
		}
		if(operator.getId()==null || "".equals(operator.getId()) || operator.getName().length() >= 45) {
			throw new BusinessException("员工姓名必须是1-45个字符");
		}
		if(operator.getRank() <= 0 || operator.getRank() >= 10) {
			throw new BusinessException("员工等级必须在1-9之间");
		}
		if(operator.getPassword()==null || "".equals(operator.getPassword()) || operator.getPassword().length() >= 45) {
			throw new BusinessException("员工密码必须是1-20个字符");
		}
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			//查找登陆账号是否已经存在
			String sql = "select * from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,operator.getId());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("登陆账号已经存在");
			rs.close();
			pst.close();
			//若不存在，则增加该用户
			sql="insert into Operator(id,name,rank,password) values(?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, operator.getId());
			pst.setString(2, operator.getName());
			pst.setInt(3, operator.getRank());			
			pst.setString(4, operator.getPassword());
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
	
	//（2）修改用户密码（登陆之后操作界面使用）
	public void changeOperatorPwd(String userid,String oldPwd,String newPwd)throws BaseException{
		if(oldPwd==null) throw new BusinessException("原始密码不能为空");
		if(newPwd==null || "".equals(newPwd) || newPwd.length()>20) throw new BusinessException("密码必须为1-20个字符");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select password from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不存在");
			if(!oldPwd.equals(rs.getString(1))) throw new BusinessException("原始密码错误");
			rs.close();
			pst.close();
			sql="update Operator set password=? where id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, userid);
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
	
	//（3）重置用户密码（新密码和账号一致！）
	public void resetOperatorPwd(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql="update Operator set password=? where id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, userid);
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
	
	//（4）删除用户
	public void deleteOperator(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			//查找用户是否已经存在
			String sql="select * from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			rs.close();
			pst.close();
			//删除用户
			sql="delete from Operator where id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
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
	
	//（5）登陆用户
	public Operator loadOperator(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select id,name,rank,password from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			Operator o = new Operator();
			o.setId(rs.getString(1));
			o.setName(rs.getString(2));
			o.setRank(rs.getInt(3));
			o.setPassword(rs.getString(4));
			rs.close();
			pst.close();
			return o;
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
	
	//（6）修改员工信息
	public void changeOperator(Operator operator)throws BaseException{
		Connection conn=null;
		if(operator.getId()==null || "".equals(operator.getId()) || operator.getName().length() >= 45) {
			throw new BusinessException("员工姓名必须是1-45个字符");
		}
//		if(operator.getRank() <= 0 || operator.getRank() >= 10) {
//			throw new BusinessException("员工等级必须在1-9之间");
//		}
		if(operator.getPassword()==null || "".equals(operator.getPassword()) || operator.getPassword().length() >= 45) {
			throw new BusinessException("员工密码必须是1-20个字符");
		}
		try {
			conn=DBUtil.getConnection();
			String sql="select * from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,operator.getId());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该员工不存在");
			rs.close();
			pst.close();
			sql="update Operator set password=?,name=?,rank=? where id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, operator.getPassword());
			pst.setString(2, operator.getName());
			pst.setInt(3, operator.getRank());
			pst.setString(4, operator.getId());
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
	
	//（7）查找符合条件的员工信息
	public List<Operator> searchOperator(String keyword)throws BaseException{
		List<Operator> result = new ArrayList<Operator>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select * from Operator";
			sql += " where id != 'root'";
			if(keyword!=null && !"".equals(keyword))
				sql+=" and (id like ? or name like ?)";
			sql+=" order by id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Operator o = new Operator();
				o.setId(rs.getString(1));
				o.setName(rs.getString(2));
				o.setRank(rs.getInt(3));
				o.setPassword(rs.getString(4));
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
	
	//该main函数主要用于以上函数的测试
	public static void main(String[] args) {
		OperatorManager o = new OperatorManager();
		/*Operator operator = new Operator();
		operator.setId("root");
		operator.setName("根用户");
		operator.setPassword("oracle");
		operator.setRank(10);*/
		
		
		try {
			//u.createUser(user);
			o.changeOperatorPwd("root", "31601365", "oracle");
			//u.resetUserPwd("root");
			//User user2 = u.loadUser("u1001");
			//System.out.println(user2.getId());
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
