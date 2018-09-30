package cn.edu.zucc.pet.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class FrmState extends JFrame implements ActionListener {
	public FrmState() {
		setTitle("\u5F00\u53D1\u56E2\u961F");
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		this.setSize(480, 250);
		
		JLabel label = new JLabel("\u9879\u76EE\u540D\u79F0\uFF1A");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(70, 40, 86, 18);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u8F6F\u4EF6\u7248\u672C\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 16));
		label_1.setBounds(70, 76, 86, 18);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u8F6F\u4EF6\u5F00\u53D1\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 16));
		label_2.setBounds(70, 113, 86, 18);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u8054\u7CFB\u65B9\u5F0F\uFF1A");
		label_3.setFont(new Font("宋体", Font.PLAIN, 16));
		label_3.setBounds(70, 152, 86, 18);
		getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("\u9879\u76EE\u540D\u79F0\uFF1A");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("宋体", Font.BOLD, 16));
		label_4.setBounds(162, 40, 218, 18);
		label_4.setText(cn.edu.zucc.pet.model.Statement.programName);//项目名称
		getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("\u9879\u76EE\u540D\u79F0\uFF1A");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("宋体", Font.BOLD, 16));
		label_5.setBounds(162, 76, 218, 18);
		label_5.setText(cn.edu.zucc.pet.model.Statement.sftVersion);//软件版本
		getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("\u9879\u76EE\u540D\u79F0\uFF1A");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("宋体", Font.BOLD, 16));
		label_6.setBounds(162, 113, 218, 18);
		label_6.setText(cn.edu.zucc.pet.model.Statement.sftAuthor);//软件开发作者
		getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel("\u9879\u76EE\u540D\u79F0\uFF1A");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("宋体", Font.BOLD, 16));
		label_7.setBounds(162, 152, 218, 18);
		label_7.setText(cn.edu.zucc.pet.model.Statement.contactInformation);//联系方式
		getContentPane().add(label_7);
		
		JLabel label_img = new JLabel("");
		label_img.setIcon(new ImageIcon("E:\\\u4F5C\u4E1A\\\u6691\u5047\u77ED\u5B66\u671F2018\\Java\u9879\u76EE\\pet\\resource\\\u84DD\u8272\u517D-\u80CC\u666F\u548C\u5BA0\u7269\u526A\u5F71-1.jpg"));
		label_img.setBounds(0, 0, 474, 215);
		getContentPane().add(label_img);
		
		//禁止放大
		this.setResizable(false);
		//点击关闭按钮，关闭窗口
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		
		
	}
}
