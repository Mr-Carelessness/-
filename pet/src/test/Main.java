package test;

import cn.edu.zucc.pet.ui.FrmLogin;

public class Main {
	public static void main(String[] args) {
		/*try {
			java.sql.Connection conn = DBUtil.getConnection();
			String sql = "select * from operator";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString(1)+"---"+rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//主界面
		FrmLogin fm = new FrmLogin();
		fm.setVisible(true);
		//员工管理界面
//		FrmOperatormg fom = new FrmOperatormg();
//		fom.setVisible(true);
//		FrmUsermg fad = new FrmUsermg();
//		fad.setVisible(true);
//		String maxId = "cat100001";
//		int max = Integer.parseInt(maxId.substring(3));
//		System.out.println(max+"--"+maxId.substring(3));
//		FrmPetmg fpm = new FrmPetmg();
//		fpm.setVisible(true);
//		FrmCategorymg fcm = new FrmCategorymg();
//		fcm.setVisible(true);
//		FrmCommoditymg fcm = new FrmCommoditymg();
//		fcm.setVisible(true);
//		FrmOrdermg fom = new FrmOrdermg();
//		fom.setVisible(true);
//		FrmSubscribemg fsm = new FrmSubscribemg();
//		fsm.setVisible(true);
//		FrmOrderStatemg fosm = new FrmOrderStatemg();
//		fosm.setVisible(true);
//		FrmSubscribeStatemg fssm = new FrmSubscribeStatemg();
//		fssm.setVisible(true);
//		FrmCommoditysch fcs = new FrmCommoditysch();
//		fcs.setVisible(true);
//		FrmUserInformationsch fuis = new FrmUserInformationsch();
//		fuis.setVisible(true);
	}
}
