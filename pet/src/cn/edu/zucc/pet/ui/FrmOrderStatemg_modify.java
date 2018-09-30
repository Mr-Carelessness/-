package cn.edu.zucc.pet.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.OrderManager;
import cn.edu.zucc.pet.model.Category;
import cn.edu.zucc.pet.model.Order;
import cn.edu.zucc.pet.util.BaseException;

public class FrmOrderStatemg_modify extends JFrame implements ActionListener {
	private JTextField textField_id;
	private FrmOrderStatemg super_Jrame = null; 
	JButton button_modify = new JButton("\u4FEE\u6539\u8BA2\u5355\u72B6\u6001");
	JButton button_exit = new JButton("\u9000\u51FA");
	Order order = null;
	int selectedRow = 0;
	private JTextField textField_userId;
	private JTextField textField_price;
	private JTextField textField_time;
	JComboBox comboBox = new JComboBox(new String[] {"已预约", "准备服务", "服务中", "完成服务", "待退款", "已退款"});
	
	public FrmOrderStatemg_modify(FrmOrderStatemg frmOrderStatemg,int i) {
		this.super_Jrame = frmOrderStatemg;
		this.selectedRow = i;
		this.order = frmOrderStatemg.orders.get(this.selectedRow);//获取对应一行的brand
		this.setSize(450, 290);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("\u8BA2\u5355\u7F16\u53F7");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(31, 27, 100, 18);
		getContentPane().add(label_1);
		
		textField_id = new JTextField();
		textField_id.setColumns(10);
		textField_id.setBounds(126, 24, 269, 24);
		//第一个
		textField_id.setText(order.getId());
		textField_id.setEditable(false);
		getContentPane().add(textField_id);
		
		button_modify.setBackground(Color.WHITE);
		button_modify.setBounds(186, 200, 123, 27);
		button_modify.addActionListener(this);
		getContentPane().add(button_modify);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(323, 200, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		
		JLabel label = new JLabel("\u7528\u6237\u7F16\u53F7");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(31, 61, 100, 18);
		getContentPane().add(label);
		
		textField_userId = new JTextField();
		textField_userId.setColumns(10);
		textField_userId.setBounds(126, 58, 269, 24);
		textField_userId.setText(order.getUser_id());
		textField_userId.setEditable(false);
		getContentPane().add(textField_userId);
		
		JLabel label_2 = new JLabel("\u8BA2\u5355\u4EF7\u683C");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(31, 95, 100, 18);
		getContentPane().add(label_2);
		
		textField_price = new JTextField();
		textField_price.setColumns(10);
		textField_price.setBounds(126, 92, 269, 24);
		textField_price.setText(String.valueOf(order.getPrice()));
		textField_price.setEditable(false);
		getContentPane().add(textField_price);
		
		JLabel label_3 = new JLabel("\u8BA2\u8D2D\u65F6\u95F4");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(31, 128, 100, 18);
		getContentPane().add(label_3);
		
		textField_time = new JTextField();
		textField_time.setColumns(10);
		textField_time.setBounds(126, 125, 269, 24);
		textField_time.setText(order.getTime().toString());
		textField_time.setEditable(false);
		getContentPane().add(textField_time);
		
		JLabel label_4 = new JLabel("\u8BA2\u5355\u72B6\u6001");
		label_4.setFont(new Font("宋体", Font.BOLD, 15));
		label_4.setBounds(31, 162, 100, 18);
		getContentPane().add(label_4);
		
		//根据不同状态选择不同的订单
		if(order.getState().equals("已退款") == true) {
			comboBox = new JComboBox(new String[] {"已退款"});
			comboBox.setEditable(false);
		}else if(order.getState().equals("已收货") == true) {
			comboBox = new JComboBox(new String[] {"已收货", "待退款", "已退款"});
		}else if(order.getState().equals("送货中") == true) {
			comboBox = new JComboBox(new String[] {"送货中", "已收货", "待退款", "已退款"});
		}else if(order.getState().equals("已订购") == true) {
			comboBox = new JComboBox(new String[] {"已订购", "已发货", "送货中", "待退款", "已退款"});
		}
		comboBox.setBounds(126, 159, 100, 24);
		getContentPane().add(comboBox);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		setTitle("\u4FEE\u6539\u8BA2\u5355\u72B6\u6001");
		
		//禁止放大
		this.setResizable(false);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.button_modify) {
			Order o = new Order();
			OrderManager om = new OrderManager();
			//订单编号
			o.setId(order.getId());
			//用户编号
			o.setUser_id(order.getUser_id());
			//订单价格
			o.setPrice(order.getPrice());;
			//订购时间
			o.setTime(order.getTime());
			//订购状态
			o.setState(comboBox.getSelectedItem().toString());
			
			try {
				om.modifyOrder(o);
				List<Order> list = om.searchAcOrder(o.getId(), o.getState());
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(o.getId()) == true) {
						this.order = list.get(i);break;//根据准确的id找只能找到一个，这里的search找的是Like的，所以就找最准确的
					}
				}
				o = this.order;
				if(comboBox.getSelectedItem().toString().equals("已退款") == true) {
					JOptionPane.showMessageDialog(null, "成功退款，退款金额"+String.valueOf(order.getPrice())+"元", "正确提示",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "修改订单状态成功", "正确提示",JOptionPane.INFORMATION_MESSAGE);
				}
				//所有的都要修改一遍
				super_Jrame.orders.get(this.selectedRow).setState(o.getState());
				super_Jrame.reloadTable(true);
				this.setVisible(false);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
			
		}else if(e.getSource() == this.button_exit) {
			this.dispose();
		}
	}
}
