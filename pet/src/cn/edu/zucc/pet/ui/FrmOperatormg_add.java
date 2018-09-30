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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.OperatorManager;
import cn.edu.zucc.pet.model.Operator;
import cn.edu.zucc.pet.util.BaseException;

public class FrmOperatormg_add extends JFrame implements ActionListener {
	private JTextField textField_id;
	private JTextField textField_name;
	private JTextField textField_rank;
	private JTextField textField_pwd;
	private FrmOperatormg super_Jrame = null; 
	JButton button_add = new JButton("\u6DFB\u52A0\u5458\u5DE5");
	JButton button_exit = new JButton("\u9000\u51FA");
	Operator operator = null;
	
	public FrmOperatormg_add(FrmOperatormg f) {
		this.super_Jrame = f;
		this.setSize(450, 250);
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u5458\u5DE5\u7F16\u53F7");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(31, 27, 100, 18);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u5458\u5DE5\u540D\u79F0");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(31, 61, 100, 18);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u5458\u5DE5\u7B49\u7EA7");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(31, 95, 100, 18);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u5458\u5DE5\u5BC6\u7801");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(31, 129, 100, 18);
		getContentPane().add(label_3);
		
		textField_id = new JTextField();
		textField_id.setBounds(126, 24, 269, 24);
		getContentPane().add(textField_id);
		textField_id.setColumns(10);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(126, 58, 269, 24);
		getContentPane().add(textField_name);
		
		textField_rank = new JTextField();
		textField_rank.setColumns(10);
		textField_rank.setBounds(126, 92, 269, 24);
		getContentPane().add(textField_rank);
		
		textField_pwd = new JTextField();
		textField_pwd.setColumns(10);
		textField_pwd.setBounds(126, 126, 269, 24);
		getContentPane().add(textField_pwd);
		
		button_add.setBackground(Color.WHITE);
		button_add.setBounds(216, 163, 93, 27);
		button_add.addActionListener(this);
		getContentPane().add(button_add);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(323, 163, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\\u4F5C\u4E1A\\\u6691\u5047\u77ED\u5B66\u671F2018\\Java\u9879\u76EE\\program\\src\\timg.jpg"));
		setTitle("\u6DFB\u52A0\u5458\u5DE5");
		
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
		if(e.getSource() == this.button_add) {
			Operator o = new Operator();
			o.setId(this.textField_id.getText());
			o.setName(this.textField_name.getText());
			o.setPassword(this.textField_pwd.getText());
			//对于整数进行校验
			int it = 0;
			try{
				if(this.textField_rank.getText() != null && this.textField_rank.getText().equals("") == false) {
					it = new Integer(this.textField_rank.getText());//捕捉异常
				}
				if(it <= 0 || it >= 10) {
					throw new NumberFormatException();
				}
			}catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "员工等级必须是1-9的整数","错误",JOptionPane.ERROR_MESSAGE);
			}
			o.setRank(it);
			OperatorManager om = new OperatorManager();
			try {
				om.createOperator(o);
				List<Operator> list = om.searchOperator(o.getId());
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(o.getId()) == true) {
						this.operator = list.get(i);break;//根据准确的id找只能找到一个，这里的search找的是Like的，所以就找最准确的
					}
				}
				JOptionPane.showMessageDialog(null, "新增员工成功", "正确提示",JOptionPane.INFORMATION_MESSAGE);
				super_Jrame.addOperator(this.operator);
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
