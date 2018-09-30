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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cn.edu.zucc.pet.control.OperatorManager;
import cn.edu.zucc.pet.model.Operator;
import cn.edu.zucc.pet.util.BaseException;

public class FrmReset extends JFrame implements ActionListener {
	private JTextField textField_id;
	private JTextField textField_name;
	private JButton button_reset;
	private JTextField textField_identify;
	private JLabel label_2;
	private JLabel lbl_identify;
	private JButton button_exit;
	public FrmReset() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\\u4F5C\u4E1A\\\u6691\u5047\u77ED\u5B66\u671F2018\\Java\u9879\u76EE\\program\\src\\timg.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		setTitle("\u5FD8\u8BB0\u5BC6\u7801");
		this.setSize(400, 220);
		this.setResizable(false);//禁止放大
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u5458\u5DE5\u8D26\u53F7");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(30, 27, 72, 18);
		getContentPane().add(label);
		
		textField_id = new JTextField();
		textField_id.setColumns(10);
		textField_id.setBounds(111, 24, 235, 24);
		getContentPane().add(textField_id);
		
		JLabel label_1 = new JLabel("\u5458\u5DE5\u59D3\u540D");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(30, 61, 72, 18);
		getContentPane().add(label_1);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(111, 58, 235, 24);
		getContentPane().add(textField_name);
		
		button_reset = new JButton("\u91CD\u7F6E\u5BC6\u7801");
		button_reset.setBackground(SystemColor.window);
		button_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_reset.setBounds(169, 129, 93, 27);
		button_reset.addActionListener(this);
		getContentPane().add(button_reset);
		
		textField_identify = new JTextField();
		textField_identify.setColumns(10);
		textField_identify.setBounds(111, 92, 151, 24);
		getContentPane().add(textField_identify);
		
		label_2 = new JLabel("\u9A8C\u8BC1\u7801");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(30, 95, 72, 18);
		getContentPane().add(label_2);
		
		lbl_identify = new JLabel(String.valueOf((int)(Math.random()*9000+1000)));
		lbl_identify.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_identify.setFont(new Font("宋体", Font.BOLD, 20));
		lbl_identify.setForeground(SystemColor.window);
		lbl_identify.setBounds(276, 95, 70, 18);
		getContentPane().add(lbl_identify);
		
		button_exit = new JButton("\u5173\u95ED");
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(274, 129, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		
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
		if(e.getSource() == this.button_reset) {
			String str = textField_identify.getText();
			if(lbl_identify.getText().equals(str) == false) {
				JOptionPane.showMessageDialog(null,  "验证码错误","错误提示",JOptionPane.ERROR_MESSAGE);
			}else {
				OperatorManager om = new OperatorManager();
				String id = this.textField_id.getText();
				String name = this.textField_name.getText();
				try {
					Operator operator = om.loadOperator(id);
					if(name.equals(operator.getName())){
						om.resetOperatorPwd(id);
						JOptionPane.showMessageDialog(null,  "重置密码成功，当前密码和员工账号一致","正确提示",JOptionPane.INFORMATION_MESSAGE);
						this.setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,  "员工姓名错误","错误提示",JOptionPane.ERROR_MESSAGE);
					}
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}else if(e.getSource() == this.button_exit) {
			this.dispose();
		}
	}

}
