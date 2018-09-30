package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.OrderManager;
import cn.edu.zucc.pet.model.Order;
import cn.edu.zucc.pet.util.BaseException;

public class FrmOrderStatemg extends JFrame implements ActionListener {
	private JTextField textField;
	private JTable table;
	private Object tblData[][];
	private Object tblTitle[] = {"订单编号","用户编号","用户姓名","订单价格","订购时间","订单状态"};
	List<Order> orders = null;
	DefaultTableModel tablmod = new DefaultTableModel();
	JScrollPane scrollPane = new JScrollPane();
	JButton button_modify = new JButton(" \u4FEE\u6539\u8BA2\u5355\u72B6\u6001 ");//修改按钮
	JButton button_search = new JButton("    \u67E5\u8BE2    ");//查找按钮
	JComboBox comboBox = new JComboBox(new String[] {"","已订购", "已发货", "送货中", "已收货", "待退款", "已退款"});
	public FrmOrderStatemg() {
		setTitle("\u8BA2\u5355\u72B6\u6001\u4FEE\u6539");
		this.setSize(1000, 660);
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.inactiveCaption);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		button_modify.addActionListener(this);
		toolBar.add(button_modify);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(300);
		toolBar.add(horizontalStrut_2);
		
		JLabel label = new JLabel("\u8BA2\u5355\u7F16\u53F7\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		toolBar.add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		toolBar.add(textField);
		
		Component horizontalStrut = Box.createHorizontalStrut(10);
		toolBar.add(horizontalStrut);
		
		JLabel label_1 = new JLabel("\u8BA2\u5355\u72B6\u6001\uFF1A");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		toolBar.add(label_1);
		
		toolBar.add(comboBox);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(10);
		toolBar.add(horizontalStrut_3);
		button_search.addActionListener(this);
		toolBar.add(button_search);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(5);
		toolBar.add(horizontalStrut_4);
		
		table = new JTable(tablmod);//★
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);		
		scrollPane.getViewport().setBackground(SystemColor.inactiveCaption);
		this.reloadTable(false);//提取现有数据
		getContentPane().add(scrollPane, BorderLayout.CENTER);		
		//getContentPane().add(table, BorderLayout.CENTER);
		
		//禁止放大
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
	
	public void reloadTable(boolean isUpdated){
		try {
			if(isUpdated == false) {
				orders = (new OrderManager()).searchAcOrder(this.textField.getText(), comboBox.getSelectedItem().toString());
			}
			tblData = new Object[orders.size()][tblTitle.length];
			for(int i = 0;i < orders.size();i++){
				tblData[i][0] = orders.get(i).getId();
				tblData[i][1] = orders.get(i).getUser_id();
				tblData[i][2] = orders.get(i).getUser_name();
				tblData[i][3] = orders.get(i).getPrice();
				tblData[i][4] = orders.get(i).getTime();
				tblData[i][5] = orders.get(i).getState();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.table.validate();
			this.table.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.button_search) {
			this.reloadTable(false);
		}else if(e.getSource() == this.button_modify) {
			if(this.table.getSelectedRow() >= 0) {
				FrmOrderStatemg_modify fm = new FrmOrderStatemg_modify(this,this.table.getSelectedRow());//获取对应一行的brand，再根据brand值修改
				fm.setVisible(true);				
			}else {
				JOptionPane.showMessageDialog(null, "请选择订单","错误",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}

}
