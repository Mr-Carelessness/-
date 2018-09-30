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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.edu.zucc.pet.model.Commodity;
import cn.edu.zucc.pet.model.OrderDetail;
import cn.edu.zucc.pet.util.BaseException;

public class FrmOrderDetailmg_add extends JFrame implements ActionListener {
	private FrmOrdermg super_Jrame = null; 
	Commodity commodity = null;
	private JTextField textField_name;
	private JTextField textField_id;
	private JTextField textField_itsprice;
	JButton button_add = new JButton("\u6DFB\u52A0\u5546\u54C1");
	JButton button_exit = new JButton("\u9000\u51FA");
	private JTextField textField_num;
	private JTextField textField_discount;
	
	
	public FrmOrderDetailmg_add(FrmOrdermg frmOrdermg, Commodity commodity) {
		// TODO Auto-generated constructor stub
		super_Jrame = frmOrdermg;
		this.commodity = commodity;
		
		this.setSize(450, 290);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("\u5546\u54C1\u540D\u79F0");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(31, 61, 100, 18);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u5546\u54C1\u7F16\u53F7");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(31, 27, 100, 18);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u5546\u54C1\u5355\u4EF7");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(31, 95, 100, 18);
		getContentPane().add(label_3);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(126, 58, 269, 24);
		textField_name.setText(commodity.getName());
		textField_name.setEditable(false);
		getContentPane().add(textField_name);
		
		textField_id = new JTextField();
		textField_id.setColumns(10);
		textField_id.setBounds(126, 24, 269, 24);
		textField_id.setText(commodity.getCategory_id());
		textField_id.setEditable(false);
		getContentPane().add(textField_id);
		
		textField_itsprice = new JTextField();
		textField_itsprice.setColumns(10);
		textField_itsprice.setBounds(126, 92, 269, 24);
		textField_itsprice.setText(String.valueOf(commodity.getPrice()));
		textField_itsprice.setEditable(false);
		getContentPane().add(textField_itsprice);
		
		button_add.setBackground(Color.WHITE);
		button_add.setBounds(216, 200, 93, 27);
		button_add.addActionListener(this);
		getContentPane().add(button_add);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(323, 200, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		
		JLabel lblqq = new JLabel("\u5546\u54C1\u6570\u91CF");
		lblqq.setFont(new Font("宋体", Font.BOLD, 15));
		lblqq.setBounds(31, 129, 100, 18);
		getContentPane().add(lblqq);
		
		textField_num = new JTextField();
		textField_num.setColumns(10);
		textField_num.setBounds(126, 126, 269, 24);
		textField_num.setText("1");
		textField_num.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_num.getForeground() == Color.GRAY) {
					textField_num.setText("");
					textField_num.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_num.getText().length() == 0 || textField_num.getText() == null) {    //里面没字了
					textField_num.setText("必填，必须是大于0的整数");
					textField_num.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_num);
		
		JLabel label_4 = new JLabel("\u5546\u54C1\u6298\u6263");
		label_4.setFont(new Font("宋体", Font.BOLD, 15));
		label_4.setBounds(31, 163, 100, 18);
		getContentPane().add(label_4);
		
		textField_discount = new JTextField();
		textField_discount.setColumns(10);
		textField_discount.setBounds(126, 160, 269, 24);
		textField_discount.setText("1.00");		
		textField_discount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_discount.getForeground() == Color.GRAY) {
					textField_discount.setText("");
					textField_discount.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(!(textField_discount.getText().length() > 0)) {    //里面没字了
					textField_discount.setText("必填，必须是大于0小于1小数或整数");
					textField_discount.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_discount);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		setTitle("\u6DFB\u52A0\u8BA2\u5355\u5546\u54C1");
		
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
		if(e.getSource() == button_add) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setCommodity_id(commodity.getId());//商品编号
			orderDetail.setCommodity_itsprice(commodity.getPrice());//商品单价
			orderDetail.setCommodity_name(commodity.getName());//商品名称
			try{
				int number = Integer.parseInt(this.textField_num.getText());
				orderDetail.setNumber(number);
			}catch(NumberFormatException | BaseException e1) {
				JOptionPane.showMessageDialog(null, "商品数量必须是大于0的整数","错误",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				return;
			}
			try {
				double discount = Double.parseDouble(this.textField_discount.getText());
				orderDetail.setDiscount(discount);
			} catch (NumberFormatException | BaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "商品折扣必须是0到1之间的整数或小数","错误",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				return;
			}
			orderDetail.setCommodity_price(orderDetail.getNumber()*orderDetail.getCommodity_itsprice()*orderDetail.getDiscount());
			//符合条件，则直接添加商品
			super_Jrame.orderDetails.add(orderDetail);
			super_Jrame.sumprice = super_Jrame.sumprice + orderDetail.getCommodity_price();
			super_Jrame.count = super_Jrame.count + 1;
			super_Jrame.label_count.setText(String.valueOf(super_Jrame.count));
			super_Jrame.label_sumprice.setText(String.valueOf(String.format("%.2f", super_Jrame.sumprice)));
			//System.out.println(super_Jrame.orderDetails.size());
			super_Jrame.reloadTable_orderDetail(true);
			this.dispose();
		}else if(e.getSource() == button_exit) {
			dispose();
		}
		
		
	}

}
