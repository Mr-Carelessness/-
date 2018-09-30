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

//★订单和预约的管理是做出来这个系统的重点
public class OrderManager {
	//新增订单
	public void addOrder(Order order)throws BaseException{
		String content = order.getId();
		//订单编号长度检查
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("订单编号必须是1-10个字符");
		}
		else {
			String pattern = "ord(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("订单编号必须为：字母ord和若干数字的组合");
			}
		}
		//订单用户编号长度检查
		content = order.getUser_id();
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("订单用户编号必须是1-15个字符");
		}
		//订单成交总价检查
		if(order.getPrice() < 0) {
			throw new BaseException("订单成交总价必须大于0");
		}
		//订单派送情况长度检查
		content = order.getState();
		if(content.equals("") == true || content == null|| content.length()>10) {
			throw new BusinessException("订单派送情况长度必须是1-10个字符");
		}
		//订单配送时间放到GUI检查
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找订单编号是否已经存在
			String sql = "select * from pet.Order where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, order.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("订单编号已经存在");
			rs.close();
			pst.close();
			//查找订单对应的用户编号是否已经存在
			sql = "select * from user where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getUser_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("用户编号不存在");
			rs.close();
			pst.close();
			//若存在，则增加该项目
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
	
	//修改工单（由于实际生活需要，只需要对派送状态进行修改）
	public void modifyOrder(Order order)throws BaseException{
		//订单编号不作修改
		String content = null;
		//订单派送情况长度检查
		content = order.getState();
		if(content.equals("") == true || content == null|| content.length()>10) {
			throw new BusinessException("订单派送情况长度必须是1-10个字符");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//查找所在订单是否不存在
			String sql = "select * from pet.Order where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, order.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("订单不存在");
			rs.close();
			pst.close();
			//查找订单对应的用户编号是否已经存在
			sql = "select * from user where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, order.getUser_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("用户编号不存在");
			rs.close();
			pst.close();
			//若对应商品和用户存在，则更新该订单，订单之间互相区别的关键性是id区别，所以这里不对重复性进行检查
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
	
	//删除订单
	public void deleteOrder(Order order)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找订单是否已经存在
			String sql = "select * from pet.Order where order_id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, order.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该订单不存在");
			rs.close();
			pst.close();
			//删除订单
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
	
	//根据订单号和订单状态精确查找订单（两者都有取和，只有一种就查这一种）
	public List<Order> searchAcOrder(String Id, String state)throws BaseException{
		List<Order> result = new ArrayList<Order>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select o.id,o.user_id,o.price,o.time,o.state,u.name from pet.Order o left outer join pet.user u on o.user_id = u.id ";
			java.sql.PreparedStatement pst=null;
			//根据两者的存在性找
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
	
	//（根据用户编号、订购时间）模糊查找订单（两者都有取和，只有一种就查这一种）
	public List<Order> searchOrder(String userId,Timestamp time)throws BaseException{
		List<Order> result = new ArrayList<Order>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select id,user_id,price,time,state from Order";
			java.sql.PreparedStatement pst=null;
			//根据两者的存在性找
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
	
	//查找最大订单Id
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
	
	//新增订购商品信息
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
	
	//查找商品订购信息
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
	
	//新增预约
	public void addSubscribe(Subscribe subscribe)throws BaseException{
		String content = subscribe.getId();
		//预约编号长度检查
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("预约编号必须是1-10个字符");
		}
		else {
			String pattern = "sub(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("预约编号必须为：字母sub和若干数字的组合");
			}
		}
		//预约宠物编号长度检查
		content = subscribe.getPet_id();
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("预约宠物编号必须是1-15个字符");
		}
		//预约当时时间以及预约这件事发生时间以及结束时间放到gui界面检查
		//预约任务完成情况检查
		content = subscribe.getFinishedstate();
		if(content.equals("") == true || content == null|| content.length()>10) {
			throw new BusinessException("预约完成情况长度必须是1-10个字符");
		}
		//预约价格检查
		if(subscribe.getPrice() < 0) {
			throw new BusinessException("预约价格必须大于0");
		}
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找预约编号是否已经存在
			String sql = "select * from subscribe where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("预约编号已经存在");
			rs.close();
			pst.close();
			//查找预约对应的宠物编号是否已经存在
			sql = "select * from pet where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getPet_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("宠物编号不存在");
			rs.close();
			pst.close();
			//若存在，则增加该项目
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
	
	//修改预约
	public void modifySubscribe(Subscribe subscribe)throws BaseException{
		//预约编号不作修改，只对实际完成情况以及实际结束时间进行修改
		String content = null;
		//以及结束时间放到gui界面检查
		//预约任务完成情况检查
		content = subscribe.getFinishedstate();
		if(content.equals("") == true || content == null|| content.length()>10) {
			throw new BusinessException("预约完成情况长度必须是1-10个字符");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//查找所在订单是否不存在
			String sql = "select * from subscribe where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("预约不存在");
			rs.close();
			pst.close();
			//查找预约对应的宠物编号是否已经存在
			sql = "select * from pet where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getPet_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("宠物编号不存在");
			rs.close();
			pst.close();
			//若对应商品和用户存在，则更新该订单，订单之间互相区别的关键性是id区别，所以这里不对重复性进行检查
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
	
	//删除预约
	public void deleteSubscribe(Subscribe subscribe)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找预约是否已经存在
			String sql = "select * from subscribe where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, subscribe.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该预约不存在");
			rs.close();
			pst.close();
			//删除预约
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
	
	//（根据预约编号，预约状态）查找预约（两者都有去和，只有一种就查这一种）
	public List<Subscribe> searchAcSubscribe(String Id,String state)throws BaseException{
		List<Subscribe> result = new ArrayList<Subscribe>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select s.id,s.pet_id,s.time,s.pretime,s.realfinishedtime,s.finishedstate,s.price,p.name from subscribe s left outer join pet p on s.pet_id = p.id ";
			java.sql.PreparedStatement pst=null;
			//根据两者的存在性找
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
	
	//（根据宠物编号、商品编号）查找订单（两者都有去和，只有一种就差这一种）
	public List<Subscribe> searchSubscribe(String petId,Timestamp time)throws BaseException{
		List<Subscribe> result = new ArrayList<Subscribe>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select id,pet_id,time,pretime,realfinishedtime,finishedstate,price from subscribe ";
			java.sql.PreparedStatement pst=null;
			//根据两者的存在性找
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
	
	//寻找最大预约Id
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
	
	//新增订购预约信息
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
	
	//查找服务预约信息
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
