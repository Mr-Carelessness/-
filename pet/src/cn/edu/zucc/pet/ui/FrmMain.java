package cn.edu.zucc.pet.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cn.edu.zucc.pet.control.OperatorManager;
import cn.edu.zucc.pet.control.SwingOperation;

public class FrmMain extends JFrame implements ActionListener {
	private JButton btn_dm = null;
	private JButton btn_pm = null;
	private JButton btn_ym = null;
	private JButton btn_rm = null;
	private JMenuBar menuBar = null;
	String text = null;
	JMenu mnNewMenu = new JMenu("\u7CFB\u7EDF\u64CD\u4F5C");//【系统操作】
	JMenuItem menuItem_vstmt = new JMenuItem("开发团队");//--版本说明
	JMenuItem menuItem_chgpwd = new JMenuItem("\u4FEE\u6539\u5BC6\u7801");//--修改密码
	JMenuItem menuItem_optmg = new JMenuItem("员工管理");//--员工管理
	JMenuItem menuItem_exit = new JMenuItem("\u9000\u51FA\u7CFB\u7EDF");//--退出系统
	JMenu menu = new JMenu("基本信息管理");//【宠物服务管理】
	JMenuItem menuItem_usrmg = new JMenuItem("用户管理");//--用户管理
	JMenuItem menuItem_catmg = new JMenuItem("\u7C7B\u522B\u7BA1\u7406");//--类别管理
	JMenuItem menuItem_comtmg = new JMenuItem("商品管理");//--商品管理
	JMenuItem menuItem_petmg = new JMenuItem("宠物管理");//--宠物管理
	private final JMenuItem menuItem_sermg = new JMenuItem("服务管理");
	JMenu mnNewMenu_1 = new JMenu("基本信息查询");//【基本信息查询】
	JMenuItem menuItem_usersch = new JMenuItem("用户信息查询");//--根据用户查询
	JMenuItem menuItem_rankList = new JMenuItem("用户排行榜");
	JMenuItem menuItem_comsch = new JMenuItem("商品/服务信息查询");//--根据商品/服务信息查询
	private final JMenu menu_1 = new JMenu("订单/预约服务");//【订单状态修改】
	private final JMenuItem menuItem_ordadd = new JMenuItem("新增订单");
	private final JMenuItem menuItem_subadd = new JMenuItem("新增预约");	
	private final JMenuItem menuItem_ordmodify = new JMenuItem("订单状态修改");
	private final JMenuItem menuItem_submodify = new JMenuItem("预约完成修改");	
	int level = 0;
	private final JLabel label_rank = new JLabel("New label");
	private final JLabel label_hint = new JLabel("New ");
	
	public FrmMain(){
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));//路径引用有/会调到jar包库中，文件夹直接说路径
		setFont(null);
		setTitle("宠物服务系统");
		setForeground(SystemColor.inactiveCaption);
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("欢迎来到宠物服务系统！");
		label_1.setFont(new Font("宋体", Font.BOLD, 36));
		label_1.setForeground(Color.BLACK);
		label_1.setBounds(427, 156, 480, 67);
//		text = ">> 欢迎"+OperatorManager.currentOperator.getName()+"（LV"+OperatorManager.currentOperator.getRank()+"）用户进入宠物服务系统";
//		try {
//			SwingOperation.JlabelSetText(label_1,text);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		getContentPane().add(label_1);
		
		ImageIcon pic1 = new ImageIcon("‪‪E:\\作业\\暑假短学期2018\\timg.jpg");
		
		JLabel label_2 = new JLabel("当前用户：");
		label_2.setFont(new Font("宋体", Font.BOLD, 24));
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(273, 337, 140, 60);
//		text = ">> 该系统主要针对宠物服务有关信息进行管理，您可以通过菜单栏上功能进行相关操作";
//		try {
//			SwingOperation.JlabelSetText(label_2,text);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		getContentPane().add(label_2);
		
		JLabel label_usrname = new JLabel("New label");
		label_usrname.setForeground(Color.WHITE);
		label_usrname.setFont(new Font("宋体", Font.PLAIN, 24));
		label_usrname.setBounds(405, 350, 140, 35);
		label_usrname.setText(OperatorManager.currentOperator.getName());
		getContentPane().add(label_usrname);
		
		JLabel label_3 = new JLabel("用户等级：");
		label_3.setFont(new Font("宋体", Font.BOLD, 24));
		label_3.setBounds(287, 378, 140, 60);
//		text = ">> 祝您使用愉快！";
//		try {
//			SwingOperation.JlabelSetText(label_3,text);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		getContentPane().add(label_3);
		label_rank.setForeground(Color.BLACK);
		label_rank.setFont(new Font("宋体", Font.PLAIN, 24));
		label_rank.setBounds(419, 388, 140, 35);
		label_rank.setText("LV"+OperatorManager.currentOperator.getRank());
		
		getContentPane().add(label_rank);
		label_hint.setFont(new Font("宋体", Font.PLAIN, 22));
		label_hint.setBounds(633, 455, 407, 115);
		try {
			SwingOperation.JlabelSetText(label_hint, "温馨提示：请按照工具条上方的功能对宠物服务进行一系列操作！祝您使用愉快！");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		getContentPane().add(label_hint);
		JLabel label_img = new JLabel(new ImageIcon("resource\\蓝色兽-背景和宠物剪影-35410000.jpg"));
		label_img.setBounds(0, 0, 1074, 639);
		getContentPane().add(label_img);

		this.setSize(1080, 700);
		
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		menuBar.setForeground(new Color(0, 0, 0));
		menuBar.setBackground(SystemColor.menu);
		setJMenuBar(menuBar);
		
		//系统操作
		menuBar.add(mnNewMenu);
		mnNewMenu.add(menuItem_vstmt);
		menuItem_vstmt.addActionListener(this);
		mnNewMenu.add(menuItem_chgpwd);
		menuItem_chgpwd.addActionListener(this);	
		//员工管理
		if(OperatorManager.currentOperator.getRank() >= 10) {   
			mnNewMenu.add(menuItem_optmg);
			menuItem_optmg.addActionListener(this);		
		}
		mnNewMenu.add(menuItem_exit);
		menuItem_exit.addActionListener(this);
		
		
		menuBar.add(menu);
		menu.add(menuItem_usrmg);
		menuItem_usrmg.addActionListener(this);			
		menu.add(menuItem_petmg);
		menuItem_petmg.addActionListener(this);
		menu.add(menuItem_catmg);
		menuItem_catmg.addActionListener(this);
		menu.add(menuItem_comtmg);
		menuItem_comtmg.addActionListener(this);
		menu.add(menuItem_sermg);
		menuItem_sermg.addActionListener(this);
		
		menuBar.add(menu_1);
		menu_1.add(menuItem_ordadd);
		menuItem_ordadd.addActionListener(this);
		menu_1.add(menuItem_subadd);
		menuItem_subadd.addActionListener(this);
		menu_1.add(menuItem_ordmodify);
		menuItem_ordmodify.addActionListener(this);
		menu_1.add(menuItem_submodify);
		menuItem_submodify.addActionListener(this);
		
		//项目查询
		menuBar.add(mnNewMenu_1);
		mnNewMenu_1.add(menuItem_usersch);
		menuItem_usersch.addActionListener(this);
		
		menuItem_rankList.addActionListener(this);
		mnNewMenu_1.add(menuItem_rankList);
		mnNewMenu_1.add(menuItem_comsch);
		menuItem_comsch.addActionListener(this);
		
		//禁止放大
		this.setResizable(false);
		//点击关闭按钮，就直接关闭了
		this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.menuItem_exit) {
			System.exit(0);
		}else if(e.getSource() == this.menuItem_chgpwd) {
			FrmChangePwd fcp = new FrmChangePwd();
			fcp.setVisible(true);
		}else if(e.getSource() == this.menuItem_optmg) {
			FrmOperatormg fom = new FrmOperatormg();
			fom.setVisible(true);
		}else if(e.getSource() == this.menuItem_catmg) {
			FrmCategorymg fcm = new FrmCategorymg();
			fcm.setVisible(true);
		}else if(e.getSource() == this.menuItem_comtmg) {
			FrmCommoditymg fcm = new FrmCommoditymg();
			fcm.setVisible(true);
		}else if(e.getSource() == this.menuItem_sermg) {
			FrmServicemg fsm = new FrmServicemg();
			fsm.setVisible(true);
		}else if(e.getSource() == this.menuItem_petmg) {
			FrmPetmg fpm = new FrmPetmg();
			fpm.setVisible(true);
		}else if(e.getSource() == this.menuItem_ordadd) {
			FrmOrdermg fom = new FrmOrdermg();
			fom.setVisible(true);
		}else if(e.getSource() == this.menuItem_subadd) {
			FrmSubscribemg frm = new FrmSubscribemg();
			frm.setVisible(true);
		}else if(e.getSource() == this.menuItem_usrmg) {
			FrmUsermg fum = new FrmUsermg();
			fum.setVisible(true);
		}else if(e.getSource() == this.menuItem_vstmt) {
			FrmState fs = new FrmState();
			fs.setVisible(true);
		}else if(e.getSource() == this.menuItem_ordmodify) {
			FrmOrderStatemg fosm = new FrmOrderStatemg();
			fosm.setVisible(true);
		}else if(e.getSource() == this.menuItem_submodify) {
			FrmSubscribeStatemg fssm = new FrmSubscribeStatemg();
			fssm.setVisible(true);
		}else if(e.getSource() == this.menuItem_comsch) {
			FrmCommoditysch fcs = new FrmCommoditysch();
			fcs.setVisible(true);
		}else if(e.getSource() == this.menuItem_usersch) {
			FrmUserInformationsch fus = new FrmUserInformationsch();
			fus.setVisible(true);
		}else if(e.getSource() == this.menuItem_rankList) {
			FrmRankList frl = new FrmRankList();
			frl.setVisible(true);
		}
		
		
	}
}
