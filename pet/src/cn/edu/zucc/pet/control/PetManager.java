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
	//��������
	public void addPet(Pet pet, String filepath)throws BaseException, FileNotFoundException{
		String content = pet.getId();
		//�����ų��ȼ��
		if(content==null || "".equals(content) || content.length()>10) {
			throw new BusinessException("�����ű�����1-15���ַ�");
		}
		else {
			String pattern = "pet(\\d+)";
			boolean isMatch = Pattern.matches(pattern, content);
			if(isMatch == false) {
				throw new BusinessException("�����ű���Ϊ����ĸpet���������ֵ����");
			}
		}
		//�������Ƴ��ȼ��
		content = pet.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("�������Ʊ�����1-45���ַ�");
		}
		//�����Ƴ��ȼ��
		content = pet.getOthername();
		if(content.length()>45) {
			throw new BusinessException("�����Ʋ��ܳ���45���ַ�");
		}
		//�������˱�ų��ȼ��
		content = pet.getUser_id();
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("�������˱���Ϊ1-15���ַ�");
		}
		//������Ƭ·�����
		if(filepath == null || filepath.equals("") == true) {
			throw new BusinessException("�����ϴ�������Ƭ");
		}
		
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//���ҳ������Ƿ��Ѿ�����
			String sql = "select * from pet where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("�������Ѿ�����");
			rs.close();
			pst.close();
			//����������˱���Ƿ�Ϊ�ջ����Ƿ��Ѿ�����������д���
			sql = "select * from user where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getUser_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�����ڸ����˱��");
			rs.close();
			pst.close();				
			//�����˱�Ŵ��ڣ������Ӹó���
			sql="insert into pet values(?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			pst.setString(2, pet.getName());
			pst.setString(3, pet.getOthername());			
			pst.setString(4, pet.getUser_id());
			//���ó�����Ƭ
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
	
	//�޸ĳ�����棨�����һ�������¼�ٶԳ����¼�ĳ�����Ƭ�����޸�,GUIҪ�Ⱥ���е��ã�
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
	
	//�޸ĳ���
	public void modifyPet(Pet pet)throws BaseException{
		//�����Ų������޸ģ�������Ƭ������
		String content = null;
		//�������Ƴ��ȼ��
		content = pet.getName();
		if(content==null || "".equals(content) || content.length()>45) {
			throw new BusinessException("�������Ʊ�����1-45���ַ�");
		}
		//�����Ƴ��ȼ��
		content = pet.getOthername();
		if(content.length()>45) {
			throw new BusinessException("�����Ʋ��ܳ���45���ַ�");
		}
		//�������˱�ų��ȼ��
		content = pet.getUser_id();
		if(content==null || "".equals(content) || content.length()>15) {
			throw new BusinessException("�������˱���Ϊ1-15���ַ�");
		}
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			//���ҳ������Ƿ��Ѿ�����
			String sql = "select * from pet where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�������Ѿ�������");
			rs.close();
			pst.close();
			//����������˱���Ƿ�Ϊ�ջ����Ƿ��Ѿ�����������д���
			sql = "select * from user where id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getUser_id());
			rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�����ڸ����˱��");
			rs.close();
			pst.close();		
			//����Ӧ���˴��ڣ�����¸ó������֮�以������Ĺؼ�����id�����������ﲻ���ظ��Խ��м��
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
		
	//ɾ������
	public void deletePet(Pet pet)throws BaseException{
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			//���ҳ����Ƿ��Ѿ�����
			String sql = "select * from pet where id = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			java.sql.ResultSet rs = pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�ó��ﲻ����");
			rs.close();
			pst.close();
			//���ҳ����Ӧ��ԤԼ�Ƿ����
			sql = "select * from subscribe where pet_id = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, pet.getId());
			rs = pst.executeQuery();
			if(rs.next()) throw new BusinessException("�ó����Ѿ�����ԤԼ������ɾ��ԤԼ");
			rs.close();
			pst.close();
			//ɾ������
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
	
	//���ݳ����Ż�ó������
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
	
	//����������ID
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
	
	//���ҳ���
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
	
	//���ݳ����Ų���ԤԼ��Ϣ
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
