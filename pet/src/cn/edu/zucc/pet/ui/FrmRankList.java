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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.model.User;
import cn.edu.zucc.pet.util.BaseException;

public class FrmRankList extends JFrame implements ActionListener{
	private JTable table;
	JButton button_point = new JButton(" \u7528\u6237\u79EF\u5206\u6392\u884C\u699C ");
	JButton button_credittime = new JButton(" \u7528\u6237\u6CE8\u518C\u65F6\u95F4\u6392\u884C\u699C ");
	//详细信息的表
	DefaultTableModel tablmod = new DefaultTableModel();
	private List<User> users = null;
	private Object tblData[][];
	private Object tblTitle[] = {"用户编号","用户姓名","用户积分","注册时间"};
	public FrmRankList() {
		setTitle("\u7528\u6237\u6392\u884C\u699C");
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		this.setSize(480, 320);
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.inactiveCaption);
		panel.add(toolBar, BorderLayout.NORTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		toolBar.add(horizontalStrut);
		toolBar.add(button_point);
		button_point.addActionListener(this);
		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		toolBar.add(horizontalStrut_1);
		button_credittime.addActionListener(this);
		toolBar.add(button_credittime);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		
		JLabel label = new JLabel("\u6392 \u884C \u699C");
		label.setFont(new Font("黑体", Font.PLAIN, 24));
		panel_3.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_2.setBackground(SystemColor.inactiveCaption);
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut_2);
		
		JLabel label_1 = new JLabel("\u8BE5\u6392\u884C\u699C\u524D\u5341\u540D\u5982\u4E0B\uFF1A");
		panel_2.add(label_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(SystemColor.inactiveCaption);
		scrollPane.setViewportView(table);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(tablmod);
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		//允许放大
		this.setResizable(true);
		//点击关闭按钮，就直接关闭了
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
	
	public void reloadTable(int choice) {
		if(choice == 0) {
			try {
				users = new UserManager().searchUserByPoint();
			} catch (BaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(choice == 1) {
			try {
				users = new UserManager().searchUserByCredittime();
			} catch (BaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tblData = new Object[users.size()][tblTitle.length];
		if(users.size() > 0) {
			for(int i = 0;i < users.size();i++){
				tblData[i][0] = users.get(i).getId();
				tblData[i][1] = users.get(i).getName();
				tblData[i][2] = users.get(i).getPoint();
				tblData[i][3] = users.get(i).getCredittime();
			}			
		}
		tablmod.setDataVector(tblData,tblTitle);
		this.table.validate();
		this.table.repaint();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.button_point) {
			this.reloadTable(0);
		}else if(e.getSource() == this.button_credittime) {
			this.reloadTable(1);
		}
	}
	
	
}
