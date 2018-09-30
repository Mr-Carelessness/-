package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.OrderManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.model.Order;
import cn.edu.zucc.pet.model.OrderDetail;
import cn.edu.zucc.pet.model.Pet;
import cn.edu.zucc.pet.model.Subscribe;
import cn.edu.zucc.pet.model.SubscribeDetail;
import cn.edu.zucc.pet.model.User;
import cn.edu.zucc.pet.util.BaseException;

public class FrmUserInformationsch extends JFrame implements ActionListener {
	private JTextField textField_user;
	//表格
	private JTable table_user;
	private JTable table_information;
	private JTable table_userhas;
	//按钮
	JButton button_schuser = new JButton("    \u67E5\u8BE2    ");//用户查找按钮
	JButton button_schpet = new JButton("  \u67E5\u8BE2\u5BA0\u7269\u4FE1\u606F  ");//查找用户宠物按钮
	JButton button_schorder = new JButton("  \u67E5\u8BE2\u8BA2\u5355\u4FE1\u606F  ");//查找用户订单按钮
	JButton button_schsubscribe = new JButton("  \u67E5\u8BE2\u9884\u7EA6\u4FE1\u606F  ");//查找用户预约按钮
	
	//用户信息表格
	private Object tblData_user[][];
	private Object tblTitle_user[] = {"用户编号","用户姓名","手机号码","邮箱","QQ","微信号","积分","创建时间"};
	List<User> users = null;
	DefaultTableModel tablmod_user = new DefaultTableModel();
	//用户拥有宠物订单预约信息表格
	private Object tblData_userhas[][];
	private Object tblTitle_pet[] = {"宠物编号","宠物昵称","宠物别名"};
	private Object tblTitle_order[] = {"订单编号","成交价格","订购时间","配送状态"};
	private Object tblTitle_subscribe[] = {"预约编号","宠物编号","宠物昵称","成交价格","订购时间","预约时间","服务完成时间","服务状态"};
	List<Pet> pets = null;
	List<Order> orders = null;
	List<Subscribe> subscribes = null;
	DefaultTableModel tablmod_userhas = new DefaultTableModel();
	//订单或预约具体信息表格
	private Object tblData_information[][];
	private Object tblTitle_orderDetail[] = {"商品编号","商品名称","商品数量","商品折扣","商品价格"};
	private Object tblTitle_subscribeDetail[] = {"服务编号","服务名称","服务数量","服务折扣","服务价格"};
	List<OrderDetail> orderDetails = null;
	List<SubscribeDetail> subscribeDetails = null;
	DefaultTableModel tablmod_information = new DefaultTableModel();
	
	
	public FrmUserInformationsch() {
		getContentPane().setBackground(SystemColor.inactiveCaption);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setHgap(3);
		panel.add(panel_2, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.text);
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.inactiveCaption);
		panel_3.add(toolBar, BorderLayout.NORTH);
		
		JLabel label = new JLabel("\u7528\u6237\u4FE1\u606F\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		toolBar.add(label);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(100);
		toolBar.add(horizontalStrut_2);
		
		textField_user = new JTextField();
		toolBar.add(textField_user);
		textField_user.setColumns(18);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		toolBar.add(horizontalStrut_1);
		button_schuser.addActionListener(this);
		toolBar.add(button_schuser);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		toolBar.add(horizontalStrut);
		
		JScrollPane scrollPane_user = new JScrollPane();
		scrollPane_user.getViewport().setBackground(SystemColor.inactiveCaption);
		panel_3.add(scrollPane_user, BorderLayout.CENTER);
		
		table_user = new JTable(tablmod_user);
		reloadTable_User(false);
		scrollPane_user.setViewportView(table_user);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_information = new JScrollPane();
		scrollPane_information.setPreferredSize(new Dimension(scrollPane_information.getWidth(), 240));
		scrollPane_information.getViewport().setBackground(SystemColor.inactiveCaption);
		panel_4.add(scrollPane_information, BorderLayout.CENTER);
		
		table_information = new JTable(tablmod_information);
		scrollPane_information.setViewportView(table_information);
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setBackground(SystemColor.menu);
		panel_4.add(toolBar_1, BorderLayout.NORTH);
		
		JLabel label_1 = new JLabel("\uFF08\u8BA2\u5355/\u9884\u7EA6\uFF09\u5177\u4F53\u4FE1\u606F\uFF1A");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		toolBar_1.add(label_1);
		
		JLabel label_2 = new JLabel("\uFF08\u9700\u9009\u62E9\u4E00\u6761\u8BA2\u5355\u6216\u9884\u7EA6\u4FE1\u606F\u624D\u80FD\u663E\u793A\u51FA\u6765\uFF09");
		label_2.setForeground(SystemColor.windowBorder);
		toolBar_1.add(label_2);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(SystemColor.inactiveCaption);
		panel_1.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar_2 = new JToolBar();
		toolBar_2.setBackground(SystemColor.inactiveCaption);
		panel_5.add(toolBar_2, BorderLayout.NORTH);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(5);
		toolBar_2.add(horizontalStrut_3);
		button_schpet.addActionListener(this);
		toolBar_2.add(button_schpet);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(10);
		toolBar_2.add(horizontalStrut_4);
		button_schorder.addActionListener(this);
		toolBar_2.add(button_schorder);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(10);
		toolBar_2.add(horizontalStrut_5);
		button_schsubscribe.addActionListener(this);
		toolBar_2.add(button_schsubscribe);
		
		JScrollPane scrollPane_userhas = new JScrollPane();
		panel_5.add(scrollPane_userhas, BorderLayout.CENTER);
		table_userhas = new JTable(tablmod_userhas);
		table_userhas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table_userhas.getSelectedRow();
				//System.out.println(i);
				if(i<0) {return;}
				//System.out.println(table_userhas.getColumnModel().getColumn(0).getHeaderValue().toString());
				if(table_userhas.getColumnModel().getColumn(0).getHeaderValue().toString().equals("订单编号") == true) {
					Order o = orders.get(i);
					reloadTable_OrderDetail(o);					
				}else if(table_userhas.getColumnModel().getColumn(0).getHeaderValue().toString().equals("预约编号") == true) {
					Subscribe s = subscribes.get(i);
					reloadTable_SubscribeDetail(s);	
				}
			}
		});
		scrollPane_userhas.setViewportView(table_userhas);
		scrollPane_userhas.getViewport().setBackground(SystemColor.inactiveCaption);
		setTitle("\u7528\u6237\u4FE1\u606F\u67E5\u8BE2");
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		this.setSize(1000, 660);
		
		
		
		//允许放大
		this.setResizable(true);
		//点击关闭按钮
		this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		dispose();
            }
		});
		//窗口居中显示
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();    //屏幕的大小
		Dimension frameSize = this.getSize();                                   //此窗口的大小
		if (frameSize.height > screenSize.height){
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width){
			frameSize.width = screenSize.width;
		}
		this.setLocation((screenSize.width - frameSize.width) / 2  ,(screenSize.height - frameSize.height ) / 2);
	}
	
	//查找符合条件的用户
	public void reloadTable_User(boolean isUpdated){
		try {
			if(isUpdated == false) {
				users = (new UserManager()).searchUser(this.textField_user.getText());
			}
			tblData_user = new Object[users.size()][tblTitle_user.length];
			for(int i = 0;i < users.size();i++){
				tblData_user[i][0] = users.get(i).getId();
				tblData_user[i][1] = users.get(i).getName();
				tblData_user[i][2] = users.get(i).getPhonenumber();
				tblData_user[i][3] = users.get(i).getEmail();
				tblData_user[i][4] = users.get(i).getQq();
				tblData_user[i][5] = users.get(i).getWx_id();
				tblData_user[i][6] = users.get(i).getPoint();
				tblData_user[i][7] = users.get(i).getCredittime();
			}
			tablmod_user.setDataVector(tblData_user,tblTitle_user);
			//this.table.setShowHorizontalLines(false);
			this.table_user.validate();
			this.table_user.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	//查找符合条件的宠物
	public void reloadTable_Pet(boolean isUpdated, User u){
		try {
			if(isUpdated == false) {
				pets = (new UserManager()).searchPetFromUser(u);
			}
			//System.out.println(pets.size());
			tblData_userhas = new Object[pets.size()][tblTitle_pet.length];
			for(int i = 0;i < pets.size();i++){
				tblData_userhas[i][0] = pets.get(i).getId();
				tblData_userhas[i][1] = pets.get(i).getName();
				tblData_userhas[i][2] = pets.get(i).getOthername();
			}
			tablmod_userhas.setDataVector(tblData_userhas,tblTitle_pet);
			//this.table.setShowHorizontalLines(false);
			
			
			this.table_userhas.validate();
			this.table_userhas.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	//查找符合条件的订单
	public void reloadTable_Order(boolean isUpdated, User u){
		try {
			if(isUpdated == false) {
				orders = (new UserManager()).searchOrder(u.getId());
			}
			//System.out.println(orders);
			tblData_userhas = new Object[orders.size()][tblTitle_order.length];
			for(int i = 0;i < orders.size();i++){
				tblData_userhas[i][0] = orders.get(i).getId();
				tblData_userhas[i][1] = orders.get(i).getPrice();
				tblData_userhas[i][2] = orders.get(i).getTime();
				tblData_userhas[i][3] = orders.get(i).getState();
			}
			tablmod_userhas.setDataVector(tblData_userhas,tblTitle_order);
			//this.table.setShowHorizontalLines(false);
			this.table_userhas.validate();
			this.table_userhas.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	//查找符合条件的预约
	public void reloadTable_Subscribe(boolean isUpdated, User u){
		try {
			List<Pet> lps = new ArrayList<Pet>();
			subscribes = new ArrayList<Subscribe>();
			if(isUpdated == false) {
				lps = (new UserManager()).searchPetFromUser(u);
				List<Subscribe> tempsubscribes = null;
				for(int i=0;i<lps.size();i++) {
					tempsubscribes = (new PetManager()).searchSubscribeFromPet(lps.get(i));
					for(int j=0;j<tempsubscribes.size();j++) {
						subscribes.add(tempsubscribes.get(j));
					}
				}
			}
			//System.out.println(orders);
			tblData_userhas = new Object[subscribes.size()][tblTitle_subscribe.length];
			for(int i = 0;i < subscribes.size();i++){
				tblData_userhas[i][0] = subscribes.get(i).getId();
				tblData_userhas[i][1] = subscribes.get(i).getPet_id();
				tblData_userhas[i][2] = subscribes.get(i).getPet_name();
				tblData_userhas[i][3] = subscribes.get(i).getPrice();
				tblData_userhas[i][4] = subscribes.get(i).getTime();
				tblData_userhas[i][5] = subscribes.get(i).getPretime();
				tblData_userhas[i][6] = subscribes.get(i).getRealfinishedtime();
				tblData_userhas[i][7] = subscribes.get(i).getFinishedstate();
			}
			tablmod_userhas.setDataVector(tblData_userhas,tblTitle_subscribe);
			//this.table.setShowHorizontalLines(false);
			this.table_userhas.validate();
			this.table_userhas.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	//查找符合条件的详细订单
	public void reloadTable_OrderDetail(Order o){
		try {
			orderDetails = new OrderManager().searchOrderDetail(o);
			tblData_information = new Object[orderDetails.size()][tblTitle_orderDetail.length];
			if(orderDetails != null) {
				for(int i = 0;i < orderDetails.size();i++){
					tblData_information[i][0] = orderDetails.get(i).getCommodity_id();
					tblData_information[i][1] = orderDetails.get(i).getCommodity_name();
					tblData_information[i][2] = orderDetails.get(i).getNumber();
					tblData_information[i][3] = orderDetails.get(i).getDiscount();
					tblData_information[i][4] = orderDetails.get(i).getNumber()*orderDetails.get(i).getDiscount()*o.getPrice();
				}				
			}
			tablmod_information.setDataVector(tblData_information,tblTitle_orderDetail);
			//this.table.setShowHorizontalLines(false);
			this.table_information.validate();
			this.table_information.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	//查找符合条件的详细预约
	public void reloadTable_SubscribeDetail(Subscribe s){
		try {
			subscribeDetails = new OrderManager().searchSubscribeDetail(s);
			tblData_information = new Object[subscribeDetails.size()][tblTitle_subscribeDetail.length];
			if(subscribeDetails!=null) {
				for(int i = 0;i < subscribeDetails.size();i++){
					tblData_information[i][0] = subscribeDetails.get(i).getCommodity_id();
					tblData_information[i][1] = subscribeDetails.get(i).getCommodity_name();
					tblData_information[i][2] = subscribeDetails.get(i).getNumber();
					tblData_information[i][3] = subscribeDetails.get(i).getDiscount();
					tblData_information[i][4] = subscribeDetails.get(i).getNumber()*subscribeDetails.get(i).getDiscount()*s.getPrice();
				}				
			}
			tablmod_information.setDataVector(tblData_information,tblTitle_subscribeDetail);
			//this.table.setShowHorizontalLines(false);
			this.table_information.validate();
			this.table_information.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.button_schuser) {
			this.reloadTable_User(false);
		}else if(e.getSource() == this.button_schpet) {
			int i = this.table_user.getSelectedRow();
			//System.out.println(i);
			if(i < 0) {
				JOptionPane.showMessageDialog(null, "请选择用户","错误",JOptionPane.ERROR_MESSAGE);
			}else {
				User u = this.users.get(i);
				//System.out.println(u.getId());
				this.reloadTable_Pet(false, u);
			}
		}else if(e.getSource() == this.button_schorder) {
			int i = this.table_user.getSelectedRow();
			//System.out.println(i);
			if(i < 0) {
				JOptionPane.showMessageDialog(null, "请选择用户","错误",JOptionPane.ERROR_MESSAGE);
			}else {
				User u = this.users.get(i);
				this.reloadTable_Order(false, u);
			}
		}else if(e.getSource() == this.button_schsubscribe) {
			int i = this.table_user.getSelectedRow();
			//System.out.println(i);
			if(i < 0) {
				JOptionPane.showMessageDialog(null, "请选择用户","错误",JOptionPane.ERROR_MESSAGE);
			}else {
				User u = this.users.get(i);
				this.reloadTable_Subscribe(false, u);
			}
		}
		
		
	}

}
