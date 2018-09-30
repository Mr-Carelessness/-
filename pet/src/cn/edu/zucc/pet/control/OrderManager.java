package cn.edu.zucc.pet.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cn.edu.zucc.pet.model.Order;
import cn.edu.zucc.pet.model.OrderDetail;
import cn.edu.zucc.pet.model.Subscribe;
import cn.edu.zucc.pet.model.SubscribeDetail;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;

//�ﶩ����ԤԼ�Ĺ��������������ϵͳ���ص�
public class OrderManager {
	//��������
	public void addOrder(Order order)throws BaseException{
		String content = order.getId();
		//������ų��ȼ��
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("������ű�����1-10���ַ�");
		}
		else {
			String pattern = "ord(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("������ű���Ϊ����ĸord���������ֵ����");
			}
		}
		//�����û���ų��ȼ��
		content = order.getUser_id();
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("�����û���ű�����1-15���ַ�");
		}
		//�����ɽ��ܼۼ��
		if(order.getPrice() < 0) {
			throw new BaseException("�����ɽ��ܼ۱������0");
		}
		//��������������ȼ��
		content = order.getState();
		if(content.equals("") == true || content == null|| content.length()>10) {
			throw new BusinessException("��������������ȱ�����1-10���ַ�");
		}
		//��������ʱ��ŵ�GUI���
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//���Ҷ�������Ƿ��Ѿ�����
			String sql = "select * from pet.Order where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, order.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("��������Ѿ�����");
			rs.close();
			pst.close();
			//���Ҷ�����Ӧ���û�����Ƿ��Ѿ�����
			sql = "select * from user where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getUser_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�û���Ų�����");
			rs.close();
			pst.close();
			//�����ڣ������Ӹ���Ŀ
			sql="insert into pet.Order(id,user_id,price,state,time) values(?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, order.getId());
			pst.setString(2, order.getUser_id());			
			pst.setDouble(3, order.getPrice());
			pst.setString(4, order.getState());
			pst.setTimestamp(5, order.getTime());
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
	
	//�޸Ĺ���������ʵ��������Ҫ��ֻ��Ҫ������״̬�����޸ģ�
	public void modifyOrder(Order order)throws BaseException{
		//������Ų����޸�
		String content = null;
		//��������������ȼ��
		content = order.getState();
		if(content.equals("") == true || content == null|| content.length()>10) {
			throw new BusinessException("��������������ȱ�����1-10���ַ�");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//�������ڶ����Ƿ񲻴���
			String sql = "select * from pet.Order where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, order.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("����������");
			rs.close();
			pst.close();
			//���Ҷ�����Ӧ���û�����Ƿ��Ѿ�����
			sql = "select * from user where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getUser_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�û���Ų�����");
			rs.close();
			pst.close();
			//����Ӧ��Ʒ���û����ڣ�����¸ö���������֮�以������Ĺؼ�����id�����������ﲻ���ظ��Խ��м��
			sql = "update pet.Order set state=? where id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, order.getState());
			pst.setString(2, order.getId());
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
	
	//ɾ������
	public void deleteOrder(Order order)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//���Ҷ����Ƿ��Ѿ�����
			String sql = "select * from pet.Order where order_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, order.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�ö���������");
			rs.close();
			pst.close();
			//ɾ������
			sql="delete from pet.Order where order_id = ?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, order.getId());
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
	
	//���ݶ����źͶ���״̬��ȷ���Ҷ��������߶���ȡ�ͣ�ֻ��һ�־Ͳ���һ�֣�
	public List<Order> searchAcOrder(String Id, String state)throws BaseException{
		List<Order> result = new ArrayList<Order>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select o.id,o.user_id,o.price,o.time,o.state,u.name from pet.Order o left outer join pet.user u on o.user_id = u.id ";
			java.sql.PreparedStatement pst=null;
			//�������ߵĴ�������
			if((Id == null || Id.equals("") == true) && (state == null || state.equals("") == true)) {
				sql = sql + "order by o.id";
				pst=conn.prepareStatement(sql);
			}else if((Id == null || Id.equals("") == true) && (state.length()>0)){
				sql = sql + "where o.state = ? order by o.id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, state);
			}else if((Id.length()>0) && (state == null || state.equals("") == true)) {
				sql = sql + "where o.Id = ? order by o.id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, Id);
			}else {
				sql = sql + "where o.id = ? and o.state = ? order by o.id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, Id);
				pst.setString(2, state);
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Order o = new Order();
				o.setId(rs.getString(1));
				o.setUser_id(rs.getString(2));
				o.setPrice(rs.getDouble(3));
				o.setTime(rs.getTimestamp(4));
				o.setState(rs.getString(5));
				o.setUser_name(rs.getString(6));
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
	
	//�������û���š�����ʱ�䣩ģ�����Ҷ��������߶���ȡ�ͣ�ֻ��һ�־Ͳ���һ�֣�
	public List<Order> searchOrder(String userId,Timestamp time)throws BaseException{
		List<Order> result = new ArrayList<Order>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select id,user_id,price,time,state from Order";
			java.sql.PreparedStatement pst=null;
			//�������ߵĴ�������
			if((userId == null || userId.equals("") == true) && (time == null)) {
				sql = sql + "order by id";
				pst=conn.prepareStatement(sql);
			}else if((userId == null || userId.equals("") == true) && (time != null)){
				sql = sql + "where time = ? order by id";
				pst=conn.prepareStatement(sql);
				pst.setTimestamp(1, time);
			}else if((userId.length()>0) && (time == null)) {
				sql = sql + "where userId = ? order by id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, userId);
			}else {
				sql = sql + "where userId = ? and time = ? order by id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, userId);
				pst.setTimestamp(2, time);
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Order o = new Order();
				o.setId(rs.getString(1));
				o.setUser_id(rs.getString(2));
				o.setPrice(rs.getDouble(3));
				o.setPrice(rs.getDouble(4));
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
	
	//������󶩵�Id
	public String searchMaxOrderId()throws BaseException{
		String result = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select max(Id) from pet.order";
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
	
	//����������Ʒ��Ϣ
	public void addOrderDetail(List<OrderDetail> orderDetail)throws BaseException{
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="insert into orderDetail(order_id,commodity_id,number,discount) values(?,?,?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			for(int i=0;i<orderDetail.size();i++) {
				pst.setString(1, orderDetail.get(i).getOrder_id());
				pst.setString(2, orderDetail.get(i).getCommodity_id());
				pst.setInt(3, orderDetail.get(i).getNumber());
				pst.setDouble(4, orderDetail.get(i).getDiscount());
				pst.addBatch();
			}
			pst.executeBatch();
			pst.clearBatch();
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
	
	//������Ʒ������Ϣ
	public List<OrderDetail> searchOrderDetail(Order o)throws BaseException{
		List<OrderDetail> result = new ArrayList<OrderDetail>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select od.commodity_id,od.number,od.discount,c.name from orderdetail od left outer join commodity c on od.commodity_id = c.id where od.order_id = ? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, o.getId());
			//System.out.println(o.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				OrderDetail od = new OrderDetail();
				od.setOrder_id(o.getId());
				od.setCommodity_id(rs.getString(1));
				od.setNumber(rs.getInt(2));
				od.setDiscount(rs.getDouble(3));
				od.setCommodity_name(rs.getString(4));
				result.add(od);
			}
			rs.close();
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
		return result;
	}
	
	//����ԤԼ
	public void addSubscribe(Subscribe subscribe)throws BaseException{
		String content = subscribe.getId();
		//ԤԼ��ų��ȼ��
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("ԤԼ��ű�����1-10���ַ�");
		}
		else {
			String pattern = "sub(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("ԤԼ��ű���Ϊ����ĸsub���������ֵ����");
			}
		}
		//ԤԼ�����ų��ȼ��
		content = subscribe.getPet_id();
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("ԤԼ�����ű�����1-15���ַ�");
		}
		//ԤԼ��ʱʱ���Լ�ԤԼ����·���ʱ���Լ�����ʱ��ŵ�gui������
		//ԤԼ�������������
		content = subscribe.getFinishedstate();
		if(content.equals("") == true || content == null|| content.length()>10) {
			throw new BusinessException("ԤԼ���������ȱ�����1-10���ַ�");
		}
		//ԤԼ�۸���
		if(subscribe.getPrice() < 0) {
			throw new BusinessException("ԤԼ�۸�������0");
		}
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//����ԤԼ����Ƿ��Ѿ�����
			String sql = "select * from subscribe where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("ԤԼ����Ѿ�����");
			rs.close();
			pst.close();
			//����ԤԼ��Ӧ�ĳ������Ƿ��Ѿ�����
			sql = "select * from pet where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getPet_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�����Ų�����");
			rs.close();
			pst.close();
			//�����ڣ������Ӹ���Ŀ
			sql="insert into pet.subscribe(id,pet_id,time,pretime,realfinishedtime,finishedstate,price) values(?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, subscribe.getId());
			pst.setString(2, subscribe.getPet_id());
			pst.setTimestamp(3, subscribe.getTime());	
			pst.setTimestamp(4, subscribe.getPretime());	
			pst.setTimestamp(5, subscribe.getRealfinishedtime());
			pst.setString(6, subscribe.getFinishedstate());
			pst.setDouble(7, subscribe.getPrice());
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
	
	//�޸�ԤԼ
	public void modifySubscribe(Subscribe subscribe)throws BaseException{
		//ԤԼ��Ų����޸ģ�ֻ��ʵ���������Լ�ʵ�ʽ���ʱ������޸�
		String content = null;
		//�Լ�����ʱ��ŵ�gui������
		//ԤԼ�������������
		content = subscribe.getFinishedstate();
		if(content.equals("") == true || content == null|| content.length()>10) {
			throw new BusinessException("ԤԼ���������ȱ�����1-10���ַ�");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//�������ڶ����Ƿ񲻴���
			String sql = "select * from subscribe where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("ԤԼ������");
			rs.close();
			pst.close();
			//����ԤԼ��Ӧ�ĳ������Ƿ��Ѿ�����
			sql = "select * from pet where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getPet_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�����Ų�����");
			rs.close();
			pst.close();
			//����Ӧ��Ʒ���û����ڣ�����¸ö���������֮�以������Ĺؼ�����id�����������ﲻ���ظ��Խ��м��
			sql = "update pet.Subscribe set realfinishedtime=?,finishedstate=? where id=?";
			pst=conn.prepareStatement(sql);
			pst.setTimestamp(1, subscribe.getRealfinishedtime());
			pst.setString(2, subscribe.getFinishedstate());
			pst.setString(3, subscribe.getId());
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
	
	//ɾ��ԤԼ
	public void deleteSubscribe(Subscribe subscribe)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//����ԤԼ�Ƿ��Ѿ�����
			String sql = "select * from subscribe where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��ԤԼ������");
			rs.close();
			pst.close();
			//ɾ��ԤԼ
			sql="delete from subscribe where id = ?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, subscribe.getId());
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
	
	//������ԤԼ��ţ�ԤԼ״̬������ԤԼ�����߶���ȥ�ͣ�ֻ��һ�־Ͳ���һ�֣�
	public List<Subscribe> searchAcSubscribe(String Id,String state)throws BaseException{
		List<Subscribe> result = new ArrayList<Subscribe>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select s.id,s.pet_id,s.time,s.pretime,s.realfinishedtime,s.finishedstate,s.price,p.name from subscribe s left outer join pet p on s.pet_id = p.id ";
			java.sql.PreparedStatement pst=null;
			//�������ߵĴ�������
			if((Id == null || Id.equals("") == true) && (state == null || state.equals("") == true)) {
				sql = sql + "order by s.id";
				pst=conn.prepareStatement(sql);
			}else if((Id == null || Id.equals("") == true) && (state.length()>0)){
				sql = sql + "where s.finishedstate = ? order by s.id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, state);
			}else if((Id.length()>0) && (state == null || state.equals("") == true)) {
				sql = sql + "where s.id = ? order by s.id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, Id);
			}else {
				sql = sql + "where s.id = ? and s.finishedstate = ? order by s.id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, Id);
				pst.setString(2, state);
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Subscribe o = new Subscribe();
				o.setId(rs.getString(1));
				o.setPet_id(rs.getString(2));
				o.setTime(rs.getTimestamp(3));
				o.setPretime(rs.getTimestamp(4));
				o.setRealfinishedtime(rs.getTimestamp(5));
				o.setFinishedstate(rs.getString(6));
				o.setPrice(rs.getDouble(7));
				o.setPet_name(rs.getString(8));
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
	
	//�����ݳ����š���Ʒ��ţ����Ҷ��������߶���ȥ�ͣ�ֻ��һ�־Ͳ���һ�֣�
	public List<Subscribe> searchSubscribe(String petId,Timestamp time)throws BaseException{
		List<Subscribe> result = new ArrayList<Subscribe>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select id,pet_id,time,pretime,realfinishedtime,finishedstate,price from subscribe ";
			java.sql.PreparedStatement pst=null;
			//�������ߵĴ�������
			if((petId == null || petId.equals("") == true) && (time == null)) {
				sql = sql + "order by id";
				pst=conn.prepareStatement(sql);
			}else if((petId == null || petId.equals("") == true) && (time != null)){
				sql = sql + "where time = ? order by id";
				pst=conn.prepareStatement(sql);
				pst.setTimestamp(1, time);
			}else if((petId.length()>0) && (time == null)) {
				sql = sql + "where pet_id = ? order by id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, petId);
			}else {
				sql = sql + "where pet_id = ? and time = ? order by id";
				pst=conn.prepareStatement(sql);
				pst.setString(1, petId);
				pst.setTimestamp(2, time);
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Subscribe o = new Subscribe();
				o.setId(rs.getString(1));
				o.setPet_id(rs.getString(2));
				o.setTime(rs.getTimestamp(3));
				o.setPretime(rs.getTimestamp(4));
				o.setRealfinishedtime(rs.getTimestamp(5));
				o.setFinishedstate(rs.getString(6));
				o.setPrice(rs.getDouble(7));
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
	
	//Ѱ�����ԤԼId
	public String searchMaxSubscribeId()throws BaseException{
		String result = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select max(Id) from subscribe";
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
	
	//��������ԤԼ��Ϣ
	public void addSubscribeDetail(List<SubscribeDetail> subscribeDetail)throws BaseException{
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="insert into subscribeDetail(commodity_id,subscribe_id,number,discount) values(?,?,?,?)";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			for(int i=0;i<subscribeDetail.size();i++) {
				pst.setString(1, subscribeDetail.get(i).getCommodity_id());				
				pst.setString(2, subscribeDetail.get(i).getSubscribe_id());
				pst.setInt(3, subscribeDetail.get(i).getNumber());
				pst.setDouble(4, subscribeDetail.get(i).getDiscount());
				pst.addBatch();
			}
			pst.executeBatch();
			pst.clearBatch();
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
	
	//���ҷ���ԤԼ��Ϣ
	public List<SubscribeDetail> searchSubscribeDetail(Subscribe s)throws BaseException{
		List<SubscribeDetail> result = new ArrayList<SubscribeDetail>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select sd.commodity_id,sd.number,sd.discount,c.name from subscribeDetail sd left outer join commodity c on sd.commodity_id = c.id where sd.subscribe_id = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, s.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				SubscribeDetail sd = new SubscribeDetail();
				sd.setSubscribe_id(s.getId());
				sd.setCommodity_id(rs.getString(1));
				sd.setNumber(rs.getInt(2));
				sd.setDiscount(rs.getDouble(3));
				sd.setCommodity_name(rs.getString(4));
				result.add(sd);
			}
			rs.close();
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
		return result;
	}
}
