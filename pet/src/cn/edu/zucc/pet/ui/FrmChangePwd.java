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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.OperatorManager;
import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.util.BaseException;

public class FrmChangePwd extends JFrame implements ActionListener {
	private JTextField textField_id;
	private JPasswordField textField_oldPwd;
	private JPasswordField textField_newPwd;
	private JPasswordField textField_againPwd;
	JButton button_chgPwd = new JButton("\u4FEE\u6539\u5BC6\u7801");
	JButton button_exit = new JButton("\u9000\u51FA");
	
	public FrmChangePwd() {
		getContentPane().setBackground(SystemColor.inactiveCaption);
		this.setSize(400, 260);
		getContentPane().setLayout(null);
		
		JLabel label_id = new JLabel("\u5458\u5DE5\u8D26\u53F7");
		label_id.setFont(new Font("宋体", Font.BOLD, 15));
		label_id.setBounds(30, 29, 72, 18);
		getContentPane().add(label_id);
		
		textField_id = new JTextField();
		textField_id.setColumns(10);
		textField_id.setBounds(111, 26, 235, 24);
		textField_id.setText(OperatorManager.currentOperator.getId());
		textField_id.setEditable(false);
		getContentPane().add(textField_id);
		
		JLabel label_oldPwd = new JLabel("\u65E7\u5BC6\u7801");
		label_oldPwd.setFont(new Font("宋体", Font.BOLD, 15));
		label_oldPwd.setBounds(30, 63, 72, 18);
		getContentPane().add(label_oldPwd);
		
		textField_oldPwd = new JPasswordField();
		textField_oldPwd.setColumns(10);
		textField_oldPwd.setBounds(111, 60, 235, 24);
		textField_oldPwd.setText(OperatorManager.currentOperator.getPassword());
		getContentPane().add(textField_oldPwd);
		
		JLabel label_newPwd = new JLabel("\u65B0\u5BC6\u7801");
		label_newPwd.setFont(new Font("宋体", Font.BOLD, 15));
		label_newPwd.setBounds(30, 97, 72, 18);
		getContentPane().add(label_newPwd);
		
		textField_newPwd = new JPasswordField();
		textField_newPwd.setColumns(10);
		textField_newPwd.setBounds(111, 94, 235, 24);
		getContentPane().add(textField_newPwd);
		
		JLabel label_againPwd = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_againPwd.setFont(new Font("宋体", Font.BOLD, 15));
		label_againPwd.setBounds(30, 131, 72, 18);
		getContentPane().add(label_againPwd);
		
		textField_againPwd = new JPasswordField();
		textField_againPwd.setColumns(10);
		textField_againPwd.setBounds(111, 128, 235, 24);
		getContentPane().add(textField_againPwd);
		
		button_chgPwd.setBackground(Color.WHITE);
		button_chgPwd.setBounds(167, 166, 93, 27);
		getContentPane().add(button_chgPwd);
		button_chgPwd.addActionListener(this);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(274, 166, 72, 27);
		getContentPane().add(button_exit);
		button_exit.addActionListener(this);
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\\u4F5C\u4E1A\\\u6691\u5047\u77ED\u5B66\u671F2018\\Java\u9879\u76EE\\program\\src\\timg.jpg"));
		setTitle("\u4FEE\u6539\u5BC6\u7801");
		
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
		if(e.getSource() == this.button_chgPwd) {
			String userid = OperatorManager.currentOperator.getId();
			String oldPwd = new String(this.textField_oldPwd.getPassword());
			String newPwd = new String(this.textField_newPwd.getPassword());
			String againPwd = new String(this.textField_againPwd.getPassword());
			if(newPwd.equals(againPwd) == false) {
				JOptionPane.showMessageDialog(null,  "确认密码与新密码不一致","错误提示",JOptionPane.ERROR_MESSAGE);
			}else {
				OperatorManager om = new OperatorManager();
				try {
					om.changeOperatorPwd(userid, oldPwd, newPwd);
					JOptionPane.showMessageDialog(null, "修改密码成功", "正确提示",JOptionPane.INFORMATION_MESSAGE);
					
					this.dispose();
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示",JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(e.getSource() == this.button_exit) {
			this.dispose();
		}
	}

}
