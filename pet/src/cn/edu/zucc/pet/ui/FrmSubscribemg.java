package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.CommodityManager;
import cn.edu.zucc.pet.control.OrderManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.SwingOperation;
import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.model.Commodity;
import cn.edu.zucc.pet.model.Pet;
import cn.edu.zucc.pet.model.Subscribe;
import cn.edu.zucc.pet.model.SubscribeDetail;
import cn.edu.zucc.pet.util.BaseException;

public class FrmSubscribemg extends JFrame implements ActionListener {
	private JTextField textField_commodity;
	private JTextField textField;
	private JTable table_commodity;
	private JTable table_subscribeDetail;
	private JTextField textField_1;
	Subscribe subscribe = null;
	DefaultTableModel tablmod = new DefaultTableModel();
	DefaultTableModel tablmod2 = new DefaultTableModel();
	double sumprice = 0f;
	int count = 0;
	JLabel label_count = new JLabel("0");
	JLabel label_sumprice = new JLabel("0.0");
	//按钮
	JButton button_schcommodity = new JButton("    \u67E5\u8BE2    ");//查找商品
	JButton button_submitSubscribe = new JButton("\u63D0\u4EA4\u9884\u7EA6");//提交订单
	JButton button_deleteCommodity = new JButton(" \u5220\u9664\u670D\u52A1 ");//删除订单商品
	JButton button_addCommodity = new JButton(" \u6DFB\u52A0\u670D\u52A1 ");//添加订单商品
	//商品的表
	private Object tblData_commodity[][];
	private Object tblTitle_commodity[] = {"服务编号","服务名称","类别编号","类别名称","商品(服务)品牌","服务价格","条形码"};
	List<Commodity> commoditys = null;
	//详细信息的表
	private Object tblData_subscribeDetail[][];
	private Object tblTitle_subscribeDetail[] = {"服务编号","服务名称","服务单价","服务数量","服务折扣","服务总价"};
	List<SubscribeDetail> subscribeDetails = null;//添加商品的时候这个subscribe号先不要，等到真正添加商品的时候再统一设置subscribe号
	private JTextField textField_2;
	//private JLabel label_sumprice;
	
	public FrmSubscribemg() {
		setTitle("\u6DFB\u52A0\u9884\u7EA6");
		this.setSize(1000, 660);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_commodity = new JPanel();
		getContentPane().add(panel_commodity, BorderLayout.WEST);
		panel_commodity.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_cborder = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_cborder.getLayout();
		flowLayout.setHgap(3);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_cborder.setBackground(SystemColor.menu);
		panel_commodity.add(panel_cborder, BorderLayout.EAST);
		
		JPanel panel_ccenter = new JPanel();
		panel_ccenter.setBackground(SystemColor.inactiveCaption);
		panel_commodity.add(panel_ccenter);
		panel_ccenter.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		//toolBar.setPreferredSize(new Dimension(230,toolBar.size().height));
		
		toolBar.setBackground(SystemColor.inactiveCaption);
		panel_ccenter.add(toolBar, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("\u670D\u52A1\u67E5\u8BE2\uFF1A");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 15));
		lblNewLabel.setToolTipText("");
		toolBar.add(lblNewLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(65);
		toolBar.add(horizontalStrut);
		
		textField_commodity = new JTextField();
		toolBar.add(textField_commodity);
		textField_commodity.setColumns(22);
		textField_commodity.setText("输入商品的条码或商品名称进行查询");
		textField_commodity.setForeground(Color.GRAY);	
		textField_commodity.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_commodity.getForeground() == Color.GRAY) {
					textField_commodity.setText("");
					textField_commodity.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_commodity.getText().length() == 0 || textField_commodity.getText() == null) {    //里面没字了
					textField_commodity.setText("输入商品的条码或商品名称进行查询");
					textField_commodity.setForeground(Color.GRAY);					
				}
			}
		});
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		toolBar.add(horizontalStrut_2);
		
		button_schcommodity.addActionListener(this);
		toolBar.add(button_schcommodity);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		toolBar.add(horizontalStrut_1);
		
		JScrollPane scrollPane_commodity = new JScrollPane();
		scrollPane_commodity.getViewport().setBackground(SystemColor.inactiveCaption);
		scrollPane_commodity.setSize(230,660);
		scrollPane_commodity.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_commodity.getViewport().setBackground(SystemColor.inactiveCaption);
		panel_ccenter.add(scrollPane_commodity, BorderLayout.CENTER);
		
		table_commodity = new JTable(tablmod);
		table_commodity.setBackground(Color.WHITE);
		this.reloadTable_Commodity(false);//提取现有数据
		//getContentPane().add(scrollPane_commodity, BorderLayout.CENTER);		
		scrollPane_commodity.setViewportView(table_commodity);
		
		JPanel panel_order = new JPanel();
		panel_order.setBackground(SystemColor.inactiveCaption);
		getContentPane().add(panel_order, BorderLayout.CENTER);
		panel_order.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_oup = new JPanel();
		panel_order.add(panel_oup, BorderLayout.NORTH);
		panel_oup.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_orderinfo = new JPanel();
		panel_orderinfo.setBackground(SystemColor.inactiveCaption);
		panel_orderinfo.setPreferredSize(new Dimension(panel_orderinfo.getWidth(),130));
		panel_oup.add(panel_orderinfo, BorderLayout.NORTH);
		panel_orderinfo.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_3.getLayout();
		flowLayout_4.setVgap(10);
		panel_3.setBackground(SystemColor.inactiveCaption);
		panel_orderinfo.add(panel_3, BorderLayout.NORTH);
		
		JLabel label_2 = new JLabel("\u5BA0\u7269\u7F16\u53F7\uFF1A");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		panel_3.add(label_2);
		
		textField = new JTextField();
		textField.setColumns(8);
		panel_3.add(textField);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_7);
		
		JLabel label_1 = new JLabel("\u9884\u7EA6\u65F6\u95F4\uFF1A");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		panel_3.add(label_1);
		
		textField_2 = new JTextField();
		panel_3.add(textField_2);
		textField_2.setColumns(14);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panel_1.getLayout();
		flowLayout_5.setVgap(10);
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_orderinfo.add(panel_1, BorderLayout.CENTER);
		
		JLabel label = new JLabel("\u670D\u52A1\u6570\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		panel_1.add(label);
		
		label_count.setFont(new Font("宋体", Font.PLAIN, 16));
		panel_1.add(label_count);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(150);
		panel_1.add(horizontalStrut_3);
		
		JLabel label_3 = new JLabel("\u603B\u4EF7\u683C\uFF1A");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		panel_1.add(label_3);
		
		label_sumprice.setFont(new Font("宋体", Font.PLAIN, 16));
		panel_1.add(label_sumprice);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		flowLayout_1.setVgap(10);
		panel_2.setBackground(SystemColor.inactiveCaption);
		panel_orderinfo.add(panel_2, BorderLayout.SOUTH);
		
		button_submitSubscribe.setVerticalAlignment(SwingConstants.TOP);
		button_submitSubscribe.addActionListener(this);
		panel_2.add(button_submitSubscribe);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut_4);
		
		JPanel panel_oborder = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_oborder.getLayout();
		flowLayout_3.setVgap(3);
		panel_oup.add(panel_oborder);
		
		JPanel panel_odown = new JPanel();
		panel_odown.setBackground(SystemColor.inactiveCaption);
		panel_order.add(panel_odown, BorderLayout.CENTER);
		panel_odown.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setBackground(SystemColor.inactiveCaption);
		panel_odown.add(toolBar_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel_1 = new JLabel("\u5DF2\u8BA2\u8D2D\u670D\u52A1\uFF1A");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 15));
		toolBar_1.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBackground(SystemColor.inactiveCaption);
		textField_1.setEditable(false);
		toolBar_1.add(textField_1);
		textField_1.setColumns(10);
		
		button_addCommodity.addActionListener(this);
		toolBar_1.add(button_addCommodity);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(10);
		toolBar_1.add(horizontalStrut_5);
		
		button_deleteCommodity.addActionListener(this);
		toolBar_1.add(button_deleteCommodity);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(5);
		toolBar_1.add(horizontalStrut_6);
		
		JScrollPane scrollPane_orderDetail = new JScrollPane();
		scrollPane_orderDetail.getViewport().setBackground(SystemColor.inactiveCaption);
		panel_odown.add(scrollPane_orderDetail, BorderLayout.CENTER);
		
		table_subscribeDetail = new JTable(tablmod2);
		table_subscribeDetail.setBackground(Color.WHITE);
		this.reloadTable_subscribeDetail(false);//提取现有数据
		scrollPane_orderDetail.setViewportView(table_subscribeDetail);
		
		
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

	public void reloadTable_Commodity(boolean isUpdated) {
		// TODO Auto-generated method stub
		try {
			if(isUpdated == false) {
				int choice_service = 2;//商品
				if(this.textField_commodity.getForeground() != Color.GRAY) {
					commoditys = (new CommodityManager()).searchCommodityByBarcode(this.textField_commodity.getText(),choice_service);
				}else {
					commoditys = (new CommodityManager()).searchCommodityByBarcode("",choice_service);
				}
			}
			tblData_commodity = new Object[commoditys.size()][tblTitle_commodity.length];
			for(int i = 0;i < commoditys.size();i++){
				tblData_commodity[i][0] = commoditys.get(i).getId();
				tblData_commodity[i][1] = commoditys.get(i).getName();
				tblData_commodity[i][2] = commoditys.get(i).getCategory_id();
				tblData_commodity[i][3] = commoditys.get(i).getCategory_name();
				tblData_commodity[i][4] = commoditys.get(i).getBrand();
				tblData_commodity[i][5] = commoditys.get(i).getPrice();
				tblData_commodity[i][6] = commoditys.get(i).getBarcode();
			}
			tablmod.setDataVector(tblData_commodity,tblTitle_commodity);
			this.table_commodity.validate();
			this.table_commodity.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	public void reloadTable_subscribeDetail(boolean isUpdated) {
		if(isUpdated == false) {
			subscribeDetails = new ArrayList<SubscribeDetail>();
		}
		tblData_subscribeDetail = new Object[subscribeDetails.size()][tblTitle_subscribeDetail.length];
		if(subscribeDetails.size() > 0) {
			for(int i = 0;i < subscribeDetails.size();i++){
				tblData_subscribeDetail[i][0] = subscribeDetails.get(i).getCommodity_id();
				tblData_subscribeDetail[i][1] = subscribeDetails.get(i).getCommodity_name();
				tblData_subscribeDetail[i][2] = subscribeDetails.get(i).getCommodity_itsprice();
				tblData_subscribeDetail[i][3] = subscribeDetails.get(i).getNumber();
				tblData_subscribeDetail[i][4] = subscribeDetails.get(i).getDiscount();
				tblData_subscribeDetail[i][5] = subscribeDetails.get(i).getCommodity_price();
			}			
		}
		tablmod2.setDataVector(tblData_subscribeDetail,tblTitle_subscribeDetail);
		this.table_subscribeDetail.validate();
		this.table_subscribeDetail.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button_schcommodity) {
			//搜索商品
			this.reloadTable_Commodity(false);
		}else if(e.getSource() == button_submitSubscribe) {
			//提交订单
			subscribe = new Subscribe();
			OrderManager om = new OrderManager();
			//设置订单编号
			String maxId = null;
			try {
				maxId = om.searchMaxSubscribeId();
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int max = 100001;
			//System.out.println(maxId);
			if(maxId != null && maxId.equals("") == false) {
				max = Integer.parseInt(maxId.substring(3))+1;
			}
			String id = "sub" + String.valueOf(max);
			subscribe.setId(id);
			//设置用户编号
			if(textField.getText() == null || textField.getText().equals("") == true) {
				JOptionPane.showMessageDialog(null, "请先设置宠物编号，宠物编号必须在系统中存在","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			subscribe.setPet_id(textField.getText());
			try {
				Timestamp ts = SwingOperation.ChangeDateTime(textField_2.getText());
				subscribe.setPretime(ts);
			}catch(InterruptedException | BaseException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			subscribe.setPrice(this.sumprice);
			subscribe.setTime(SwingOperation.getCurrentTime(System.currentTimeMillis()));
			subscribe.setFinishedstate("已预约");
			
			try {
				om.addSubscribe(subscribe);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//改变订单细节的订单编号
			for(int i=0;i<subscribeDetails.size();i++) {
				subscribeDetails.get(i).setSubscribe_id(subscribe.getId());
			}
			try {
				om.addSubscribeDetail(subscribeDetails);
				List<Pet> p = new PetManager().searchPet(subscribe.getPet_id());
				String userId = "";
				for(Pet pt:p) {
					if(pt.getId().equals(subscribe.getPet_id()) == true) {
						userId = pt.getUser_id();break;
					}
				}
				new UserManager().modifyUserPoint(userId, subscribe.getPrice());
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//JOptionPane.showMessageDialog(null, "新增订单成功，该订单编号为"+order.getId(), "正确提示",JOptionPane.INFORMATION_MESSAGE);
			//弹出小票界面
			this.setVisible(false);
			FrmSubscribeshow fos = new FrmSubscribeshow(this);
			fos.setVisible(true);
			fos.reloadTable_subscribeDetail();
			
		}else if(e.getSource() == button_addCommodity) {
			//添加订单商品
			int i = this.table_commodity.getSelectedRow();
			if(i < 0) {
				JOptionPane.showMessageDialog(null, "请选择左侧商品查询里的商品进行订购","错误",JOptionPane.ERROR_MESSAGE);
			}
			else {
				Commodity commodity = this.commoditys.get(i);
				//弹出界面
				FrmSubscribeDetailmg_add fod = new FrmSubscribeDetailmg_add(this,commodity);
				fod.setVisible(true);
				
			}
			
			
		}else if(e.getSource() == button_deleteCommodity) {
			//删除订单商品
			int i = this.table_subscribeDetail.getSelectedRow();
			if(i < 0) {
				JOptionPane.showMessageDialog(null, "请选择右下侧已经订购的服务删除","错误",JOptionPane.ERROR_MESSAGE);
			}
			else {
				sumprice = sumprice - subscribeDetails.get(i).getCommodity_price();
				count = count - 1;
				label_count.setText(String.valueOf(count));
				label_sumprice.setText(String.valueOf(String.format("%.2f", sumprice)));
				subscribeDetails.remove(i);//假如i大于0，则直接删除这条记录
				this.reloadTable_subscribeDetail(true);
			}
			
			
		}
		
	}
}
