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
	public static Operator currentOperator = null;//ϵͳ��½�û�
	
	//��1�������û�
	public void createOperator(Operator operator)throws BaseException{
		if(operator.getId()==null || "".equals(operator.getId()) || operator.getId().length()>15){
			throw new BusinessException("Ա���˺ű�����1-15���ַ�");
		}
		if(operator.getId()==null || "".equals(operator.getId()) || operator.getName().length() >= 45) {
			throw new BusinessException("Ա������������1-45���ַ�");
		}
		if(operator.getRank() <= 0 || operator.getRank() >= 10) {
			throw new BusinessException("Ա���ȼ�������1-9֮��");
		}
		if(operator.getPassword()==null || "".equals(operator.getPassword()) || operator.getPassword().length() >= 45) {
			throw new BusinessException("Ա�����������1-20���ַ�");
		}
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			//���ҵ�½�˺��Ƿ��Ѿ�����
			String sql = "select * from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,operator.getId());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("��½�˺��Ѿ�����");
			rs.close();
			pst.close();
			//�������ڣ������Ӹ��û�
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
	
	//��2���޸��û����루��½֮���������ʹ�ã�
	public void changeOperatorPwd(String userid,String oldPwd,String newPwd)throws BaseException{
		if(oldPwd==null) throw new BusinessException("ԭʼ���벻��Ϊ��");
		if(newPwd==null || "".equals(newPwd) || newPwd.length()>20) throw new BusinessException("�������Ϊ1-20���ַ�");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select password from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų�����");
			if(!oldPwd.equals(rs.getString(1))) throw new BusinessException("ԭʼ�������");
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
	
	//��3�������û����루��������˺�һ�£���
	public void resetOperatorPwd(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų�����");
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
	
	//��4��ɾ���û�
	public void deleteOperator(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			//�����û��Ƿ��Ѿ�����
			String sql="select * from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų� ����");
			rs.close();
			pst.close();
			//ɾ���û�
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
	
	//��5����½�û�
	public Operator loadOperator(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select id,name,rank,password from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų� ����");
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
	
	//��6���޸�Ա����Ϣ
	public void changeOperator(Operator operator)throws BaseException{
		Connection conn=null;
		if(operator.getId()==null || "".equals(operator.getId()) || operator.getName().length() >= 45) {
			throw new BusinessException("Ա������������1-45���ַ�");
		}
//		if(operator.getRank() <= 0 || operator.getRank() >= 10) {
//			throw new BusinessException("Ա���ȼ�������1-9֮��");
//		}
		if(operator.getPassword()==null || "".equals(operator.getPassword()) || operator.getPassword().length() >= 45) {
			throw new BusinessException("Ա�����������1-20���ַ�");
		}
		try {
			conn=DBUtil.getConnection();
			String sql="select * from Operator where id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,operator.getId());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��Ա��������");
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
	
	//��7�����ҷ���������Ա����Ϣ
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
	
	//��main������Ҫ�������Ϻ����Ĳ���
	public static void main(String[] args) {
		OperatorManager o = new OperatorManager();
		/*Operator operator = new Operator();
		operator.setId("root");
		operator.setName("���û�");
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
