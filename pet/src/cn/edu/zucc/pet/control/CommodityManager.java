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
	//增加类别
	public void addCategory(Category category)throws BaseException{
		String content = category.getId();
		//类别编号长度检查
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("类别编号必须是1-15个字符");
		}
		else {
			String pattern = "cat(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("类别编号的编号必须为：字母cat和若干数字的组合");
			}
		}
		//类别名称长度检查
		content = category.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("类别名称必须是1-45个字符");
		}
		//类别描述长度检查
		content = category.getDescription();
		if(content.length()>200) {
			throw new BusinessException("类别描述不能超过200个字符");
		}
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找类别编号是否已经存在
			String sql = "select * from Category where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("类别编号已经存在");
			rs.close();
			pst.close();
			//若不存在，则增加该该类别
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
	
	//修改类别
	public void modifyCategory(Category category)throws BaseException{
		//类别编号不作修改
		String content = null;
		//类别名称长度检查
		content = category.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("类别名称必须是1-45个字符");
		}
		//类别描述长度检查
		content = category.getDescription();
		if(content.length()>200) {
			throw new BusinessException("类别名称不能超过200个字符");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//查找所在类别是否不存在
			String sql = "select * from Category where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("类别不存在");
			rs.close();
			pst.close();
			//若对应类别存在，则更新该类别，类别之间互相区别的关键性是id区别，所以这里不对重复性进行检查
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
	
	//删除类别
	public void deleteCategory(Category category)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找类别是否已经存在
			String sql = "select * from Category where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该类别不存在");
			rs.close();
			pst.close();
			//查找该类别是否存在对应的商品
			sql = "select * from Commodity where category_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, category.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("该类别对应相关商品存在，请先删除相关商品");
			rs.close();
			pst.close();
			//删除类别
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
	
	//查找类别
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
	
	
	//寻找最大类别Id
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
	
	//增加商品
	public void addCommodity(Commodity commodity)throws BaseException{
		String content = commodity.getId();
		//商品编号长度检查
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("商品编号必须是1-15个字符");
		}
		else {
			String pattern = "com(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("商品编号必须为：字母com和若干数字的组合");
			}				
		}
		//商品名称长度检查
		content = commodity.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("商品名称必须是1-45个字符");
		}
		//商品类别编号长度检查
		content = commodity.getCategory_id();
		if(content==null || "".equals(content) || content.length()>20) {
			throw new BusinessException("商品类别编号必须为1-15个字符");
		}
		//商品品牌长度检查
		content = commodity.getBrand();
		if(content==null || "".equals(content) || content.length()>100) {
			throw new BusinessException("商品品牌必须为1-45个字符");
		}
		//商品价格检查
		if(commodity.getPrice() < 0) {
			throw new BusinessException("商品总价格不能小于0");
		}
		//商品条形码长度检查
		content = commodity.getBarcode();
		if(content==null || "".equals(content) || content.length() > 30) {
			throw new BusinessException("商品条形码必须为1-30个字符");
		}
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找商品编号是否已经存在
			String sql = "select * from commodity where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("商品编号已经存在");
			rs.close();
			pst.close();
			//检查商品的类别编号是否为空或者是否已经在所有类别中存在
			content = commodity.getCategory_id();
			if(content != null && content.equals("") == false) {
				sql = "select * from Category where id = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, commodity.getCategory_id());
				rs = pst.executeQuery();
				if(!rs.next()) throw new BusinessException("不存在该类别编号");
				rs.close();
				pst.close();				
			}
			//若商品编号存在，则增加该商品
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
	
	//修改商品
	public void modifyCommodity(Commodity commodity)throws BaseException{
		//商品编号不能作修改
		String content = null;
		//商品名称长度检查
		content = commodity.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("商品名称必须是1-45个字符");
		}
		//商品类别编号长度检查
		content = commodity.getCategory_id();
		if(content==null || "".equals(content) || content.length()>20) {
			throw new BusinessException("商品类别编号必须为1-15个字符");
		}
		//商品品牌长度检查
		content = commodity.getBrand();
		if(content==null || "".equals(content) || content.length()>100) {
			throw new BusinessException("商品品牌必须为1-45个字符");
		}
		//商品价格检查
		if(commodity.getPrice() < 0) {
			throw new BusinessException("商品总价格不能小于0");
		}
		//商品条形码长度检查
		content = commodity.getBarcode();
		if(content==null || "".equals(content) || content.length() > 30) {
			throw new BusinessException("商品条形码必须为1-30个字符");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//查找商品编号是否已经存在
			String sql = "select * from commodity where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("商品编号不存在");
			rs.close();
			pst.close();
			//检查商品的类别编号是否为空或者是否已经在所有类别中存在
			content = commodity.getCategory_id();
			if(content != null && content.equals("") == false) {
				sql = "select * from Category where id = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, commodity.getCategory_id());
				rs = pst.executeQuery();
				if(!rs.next()) throw new BusinessException("不存在该类别编号");
				rs.close();
				pst.close();				
			}
			//若对应类别存在，则更新该商品，商品之间互相区别的关键性是id区别，所以这里不对重复性进行检查
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
		
	//删除商品
	public void deleteCommodity(Commodity commodity)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找商品是否已经存在
			String sql = "select * from commodity where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该商品不存在");
			rs.close();
			pst.close();
			//查找商品对应的订单是否存在
			sql = "select * from pet.orderDetail where commodity_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("该商品存在相关订单，请先删除相关订单");
			rs.close();
			pst.close();
			//查找商品对应的预约是否存在
			sql = "select * from subscribeDetail where commodity_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, commodity.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("该商品存在相关预约，请先删除相关预约");
			rs.close();
			pst.close();
			//删除商品
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
	
	//查找(商品/服务)
	public List<Commodity> searchCommodity(String keyword, int choice)throws BaseException{
		List<Commodity> result = new ArrayList<Commodity>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select co.id,co.name,co.category_id,co.brand,co.price,co.barcode,ca.name from commodity co left outer join category ca on co.category_id = ca.id ";
			//商品
			if(choice == 1) {
				sql+= "where (co.type = '商品')";
			}
			//服务
			else if(choice == 2) {
				sql+= "where (co.type = '服务')";
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
	
	//（根据条形码或名称）查找(商品/服务)
	public List<Commodity> searchCommodityByBarcode(String keyword, int choice)throws BaseException{
		List<Commodity> result = new ArrayList<Commodity>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select co.id,co.name,co.category_id,co.brand,co.price,co.barcode,ca.name from commodity co left outer join category ca on co.category_id = ca.id ";
			//商品
			if(choice == 1) {
				sql+= "where (co.type = '商品')";
			}
			//服务
			else if(choice == 2) {
				sql+= "where (co.type = '服务')";
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
	
	//寻找最大商品Id
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
	
	//根据商品编号/名称或者商品类别编号/名称查找符合条件的所有商品
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
