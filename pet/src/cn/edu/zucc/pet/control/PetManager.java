package cn.edu.zucc.pet.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cn.edu.zucc.pet.model.Pet;
import cn.edu.zucc.pet.model.Subscribe;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;

public class PetManager {
	//新增宠物
	public void addPet(Pet pet, String filepath)throws BaseException, FileNotFoundException{
		String content = pet.getId();
		//宠物编号长度检查
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("宠物编号必须是1-15个字符");
		}
		else {
			String pattern = "pet(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("宠物编号必须为：字母pet和若干数字的组合");
			}
		}
		//宠物名称长度检查
		content = pet.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("宠物名称必须是1-45个字符");
		}
		//宠物别称长度检查
		content = pet.getOthername();
		if(content.length()>45) {
			throw new BusinessException("宠物别称不能超过45个字符");
		}
		//宠物主人编号长度检查
		content = pet.getUser_id();
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("宠物主人必须为1-15个字符");
		}
		//宠物照片路径检查
		if(filepath == null || filepath.equals("") == true) {
			throw new BusinessException("必须上传宠物照片");
		}
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找宠物编号是否已经存在
			String sql = "select * from pet where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("宠物编号已经存在");
			rs.close();
			pst.close();
			//检查宠物的主人编号是否为空或者是否已经在所有类别中存在
			sql = "select * from user where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getUser_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("不存在该主人编号");
			rs.close();
			pst.close();				
			//若主人编号存在，则增加该宠物
			sql="insert into pet values(?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			pst.setString(2, pet.getName());
			pst.setString(3, pet.getOthername());			
			pst.setString(4, pet.getUser_id());
			//设置宠物照片
			File file = new File(filepath);
			FileInputStream fis = new FileInputStream(file);
			pst.setBlob(5, fis);
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
	
	//修改宠物封面（先添加一条宠物记录再对宠物记录的宠物照片进行修改,GUI要先后进行调用）
	public void setPetPhoto(Pet pet, String filepath) throws DbException, FileNotFoundException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update pet set photo = ? where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			File file = new File(filepath);
			FileInputStream fis = new FileInputStream(file);
			pst.setString(2, pet.getId());
			pst.setBlob(1, fis);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DbException(e);
		}
	}
	
	//修改宠物
	public void modifyPet(Pet pet)throws BaseException{
		//宠物编号不能作修改，宠物照片单独改
		String content = null;
		//宠物名称长度检查
		content = pet.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("宠物名称必须是1-45个字符");
		}
		//宠物别称长度检查
		content = pet.getOthername();
		if(content.length()>45) {
			throw new BusinessException("宠物别称不能超过45个字符");
		}
		//宠物主人编号长度检查
		content = pet.getUser_id();
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("宠物主人必须为1-15个字符");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//查找宠物编号是否已经存在
			String sql = "select * from pet where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("宠物编号已经不存在");
			rs.close();
			pst.close();
			//检查宠物的主人编号是否为空或者是否已经在所有类别中存在
			sql = "select * from user where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getUser_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("不存在该主人编号");
			rs.close();
			pst.close();		
			//若对应主人存在，则更新该宠物，宠物之间互相区别的关键性是id区别，所以这里不对重复性进行检查
			sql = "update pet set name=?,othername=? where id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, pet.getName());
			pst.setString(2, pet.getOthername());
			pst.setString(3, pet.getId());
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
		
	//删除宠物
	public void deletePet(Pet pet)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//查找宠物是否已经存在
			String sql = "select * from pet where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该宠物不存在");
			rs.close();
			pst.close();
			//查找宠物对应的预约是否存在
			sql = "select * from subscribe where pet_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("该宠物已经存在预约，请先删除预约");
			rs.close();
			pst.close();
			//删除宠物
			sql="delete from pet where id = ?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
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
	
	//根据宠物编号获得宠物封面
	public void getPetPhoto(String petId,String filepath) throws DbException,IOException{
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select photo from pet where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, petId);
			File file = new File(filepath);
			InputStream fis = null;
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				fis = rs.getBinaryStream(1);
			}
			FileOutputStream fos = new FileOutputStream(file);
			byte []bys = new byte[1024];
			int len;
			while((len = fis.read(bys,0,1024))!=-1) {
				fos.write(bys,0,1024);
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
	}
	
	//查找最大宠物ID
	public String searchMaxPetId()throws BaseException{
		String result = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select max(Id) from pet";
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
	
	//查找宠物
	public List<Pet> searchPet(String keyword)throws BaseException{
		List<Pet> result = new ArrayList<Pet>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select p.id,p.name,p.othername,p.user_id,u.name from pet.pet p left outer join pet.user u on p.user_id = u.id";
			if(keyword!=null && !"".equals(keyword))
				sql+=" where p.id like ? or p.name like ?";
			sql+=" order by p.id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
			}
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Pet p = new Pet();
				p.setId(rs.getString(1));
				p.setName(rs.getString(2));
				p.setOthername(rs.getString(3));
				p.setUser_id(rs.getString(4));
				p.setUser_name(rs.getString(5));
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
	
	//根据宠物编号查找预约信息
	public List<Subscribe> searchSubscribeFromPet(Pet pet)throws BaseException{
		List<Subscribe> result = new ArrayList<Subscribe>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select s.id,s.pet_id,s.time,s.pretime,s.realfinishedtime,s.finishedstate,s.price,p.name"
					+ " from subscribe s left outer join pet.pet p on s.pet_id = p.id ";
			sql+="where p.id = ? order by p.id";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
				
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Subscribe s = new Subscribe();
				s.setId(rs.getString(1));
				s.setPet_id(rs.getString(2));
				s.setTime(rs.getTimestamp(3));
				s.setPretime(rs.getTimestamp(4));
				s.setRealfinishedtime(rs.getTimestamp(5));
				s.setFinishedstate(rs.getString(6));
				s.setPrice(rs.getDouble(7));
				s.setPet_name(rs.getString(8));
				result.add(s);
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
