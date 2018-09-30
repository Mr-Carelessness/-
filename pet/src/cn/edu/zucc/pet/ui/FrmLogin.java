package cn.edu.zucc.pet.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.OperatorManager;
import cn.edu.zucc.pet.model.Operator;
import cn.edu.zucc.pet.util.BaseException;

public class FrmLogin extends JFrame implements ActionListener {
	private JTextField textField_id;
	private JPasswordField textField_pwd;
	private JButton button_login = new JButton("\u767B\u9646"); //登陆按钮
	private JButton button_exit = new JButton("\u9000\u51FA"); //退出按钮
	private JButton button_reset = new JButton("\u5FD8\u8BB0\u5BC6\u7801");//重置密码按钮
	
	public FrmLogin() {
		getContentPane().setBackground(SystemColor.inactiveCaption);
		setTitle("\u767B\u9646");
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		this.setSize(400, 180);
		getContentPane().setLayout(null);
		
		//禁止放大
		this.setResizable(false);
		//点击关闭按钮，就直接关闭了
		this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
            }
		});
		
		JLabel label = new JLabel("\u5458\u5DE5\u8D26\u53F7");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(30, 25, 72, 18);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u767B\u9646\u5BC6\u7801");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(30, 56, 72, 18);
		getContentPane().add(label_1);
		
		textField_id = new JTextField();
		textField_id.setBounds(111, 22, 235, 24);
		getContentPane().add(textField_id);
		textField_id.setColumns(10);
		
		textField_pwd = new JPasswordField();
		textField_pwd.setColumns(10);
		textField_pwd.setBounds(111, 53, 235, 24);
		getContentPane().add(textField_pwd);	
		
		button_login.setBackground(SystemColor.window);
		button_login.setBounds(81, 90, 72, 27);
		getContentPane().add(button_login);
		button_login.addActionListener(this);
		
		button_exit.setBackground(SystemColor.window);
		button_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_exit.setBounds(274, 90, 72, 27);
		button_exit.addActionListener(this);		
		getContentPane().add(button_exit);
		
		button_reset.setBackground(Color.WHITE);
		button_reset.setBounds(167, 90, 93, 27);
		button_reset.addActionListener(this);
		getContentPane().add(button_reset);
		
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
		if(e.getSource() == this.button_login) {
			OperatorManager om = new OperatorManager();
			String id = this.textField_id.getText();
			String pwd = new String(this.textField_pwd.getPassword());
			try {
				Operator operator = om.loadOperator(id);
				if(pwd.equals(operator.getPassword())){
					OperatorManager.currentOperator = operator;
					setVisible(false);
					FrmMain fm = new FrmMain();
					fm.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null,  "密码错误","错误提示",JOptionPane.ERROR_MESSAGE);
				}
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示",JOptionPane.ERROR_MESSAGE);
			} 
		}else if(e.getSource() == this.button_exit) {
			System.exit(0);
		}else if(e.getSource() == this.button_reset) {
			FrmReset fr = new FrmReset();
			fr.setVisible(true);
		}
		
	}
}
