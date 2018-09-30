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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.CommodityManager;
import cn.edu.zucc.pet.control.SwingOperation;
import cn.edu.zucc.pet.model.Category;
import cn.edu.zucc.pet.model.User;
import cn.edu.zucc.pet.util.BaseException;

public class FrmCategorymg_add extends JFrame implements ActionListener {
	private JTextField textField_name;
	private JTextArea textField_descp;
	private FrmCategorymg super_Jrame = null; 
	JButton button_add = new JButton("\u6DFB\u52A0\u7C7B\u522B");
	JButton button_exit = new JButton("\u9000\u51FA");
	Category category = null;
	
	public FrmCategorymg_add(FrmCategorymg frmCategorymg) {
		this.super_Jrame = frmCategorymg;
		this.setSize(450, 230);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("\u7C7B\u522B\u540D\u79F0");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(31, 27, 100, 18);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u7C7B\u522B\u63CF\u8FF0");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(31, 61, 100, 18);
		getContentPane().add(label_2);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(126, 24, 269, 24);
		textField_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_name.getForeground() == Color.GRAY) {
					textField_name.setText("");
					textField_name.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_name.getText().length() == 0 || textField_name.getText() == null) {    //里面没字了
					textField_name.setText("必填");
					textField_name.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_name);
		
		textField_descp = new JTextArea();
		textField_descp.setFont(new Font("宋体", Font.PLAIN, 13));
		textField_descp.setColumns(10);
		textField_descp.setBounds(126, 58, 269, 72);
		textField_descp.setText("选填，对商品类别进行一个简单介绍，不超过100字");
		textField_descp.setForeground(Color.GRAY);	
		textField_descp.setLineWrap(true);//激活自动换行功能
		textField_descp.setWrapStyleWord(true);//激活换行不断字功能
		textField_descp.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_descp.getForeground() == Color.GRAY) {
					textField_descp.setText("");
					textField_descp.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_descp.getText().length() == 0 || textField_descp.getText() == null ) {    //里面没字了
					textField_descp.setText("选填，对商品类别进行一个简单介绍，不超过100字");
					textField_descp.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_descp);
		
		button_add.setBackground(Color.WHITE);
		button_add.setBounds(216, 143, 93, 27);
		button_add.addActionListener(this);
		getContentPane().add(button_add);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(323, 143, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		setTitle("\u6DFB\u52A0\u7C7B\u522B");
		
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
			Category c = new Category();
			CommodityManager cm = new CommodityManager();
			String maxId;
			try {
				//类别编号
				maxId = cm.searchMaxCategoryId();
				String id = "cat100001";
				if(maxId != null && maxId.length() > 0)
				{
					int max = Integer.parseInt(maxId.substring(3));
					id = "cat" + String.valueOf(max+1);
				}				
				c.setId(id);
			} catch (BaseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			//类别名称
			if(this.textField_name.getForeground() != Color.GRAY) {
				c.setName(this.textField_name.getText());//黑色有字
			}else {
				c.setName("");
			}
			//类别描述
			if(this.textField_descp.getForeground() != Color.GRAY) {
				c.setDescription(this.textField_descp.getText());//黑色有字
			}else {
				c.setDescription("");
			}
			
			try {
				cm.addCategory(c);
				List<Category> list = cm.searchCategory(c.getId());
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(c.getId()) == true) {
						this.category = list.get(i);break;//根据准确的id找只能找到一个，这里的search找的是Like的，所以就找最准确的
					}
				}
				JOptionPane.showMessageDialog(null, "新增类别成功，该类别编号为"+category.getId(), "正确提示",JOptionPane.INFORMATION_MESSAGE);
				super_Jrame.addCategory(this.category);
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
