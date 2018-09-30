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
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.OperatorManager;
import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.model.Operator;
import cn.edu.zucc.pet.model.User;
import cn.edu.zucc.pet.util.BaseException;

public class FrmUsermg_modify extends JFrame implements ActionListener {
	private JTextField textField_name;
	private JTextField textField_phnum;
	private JTextField textField_email;
	private FrmUsermg super_Jrame = null; 
	JButton button_modify = new JButton("\u4FEE\u6539\u7528\u6237");
	JButton button_exit = new JButton("\u9000\u51FA");
	User user = null;
	int selectedRow = 0;
	private JTextField textField_qq;
	private JTextField textField_wxid;
	
	public FrmUsermg_modify(FrmUsermg frmUsermg,int i) {
		this.super_Jrame = frmUsermg;
		this.selectedRow = i;
		this.user = frmUsermg.users.get(this.selectedRow);//��ȡ��Ӧһ�е�brand
		this.setSize(450, 290);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon2.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("\u7528\u6237\u59D3\u540D");
		label_1.setFont(new Font("����", Font.BOLD, 15));
		label_1.setBounds(31, 27, 100, 18);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u624B\u673A\u53F7\u7801");
		label_2.setFont(new Font("����", Font.BOLD, 15));
		label_2.setBounds(31, 61, 100, 18);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u7528\u6237\u90AE\u7BB1");
		label_3.setFont(new Font("����", Font.BOLD, 15));
		label_3.setBounds(31, 95, 100, 18);
		getContentPane().add(label_3);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(126, 24, 269, 24);
		//��һ��
		if(user.getName() == null || user.getName().equals("") == true) {
			textField_name.setText("");
		}else {
			textField_name.setText(user.getName());
		}
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
				if(textField_name.getText().length() == 0 || textField_name.getText() == null) {    //����û����
					textField_name.setText("����");
					textField_name.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_name);
		
		textField_phnum = new JTextField();
		textField_phnum.setColumns(10);
		textField_phnum.setBounds(126, 58, 269, 24);
		if(user.getPhonenumber() == null || user.getPhonenumber().equals("") == true) {
			textField_phnum.setText("����");
			textField_phnum.setForeground(Color.GRAY);
		}else {
			textField_phnum.setText(user.getPhonenumber());
			textField_phnum.setForeground(Color.BLACK);	
		}
		textField_phnum.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_phnum.getForeground() == Color.GRAY) {
					textField_phnum.setText("");
					textField_phnum.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_phnum.getText().length() == 0 || textField_phnum.getText() == null ) {    //����û����
					textField_phnum.setText("���Ϊ���ܹ�������˿�ȡ����ϵ");
					textField_phnum.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_phnum);
		
		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(126, 92, 269, 24);
		if(user.getEmail() == null || user.getEmail().equals("") == true) {
			textField_email.setText("ѡ��");
			textField_email.setForeground(Color.GRAY);
		}else {
			textField_email.setText(user.getEmail());
			textField_email.setForeground(Color.BLACK);
		}
		textField_email.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_email.getForeground() == Color.GRAY) {
					textField_email.setText("");
					textField_email.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_email.getText().length() == 0 || textField_email.getText() == null) {    //����û����
					textField_email.setText("ѡ��");
					textField_email.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_email);
		
		button_modify.setBackground(Color.WHITE);
		button_modify.setBounds(216, 200, 93, 27);
		button_modify.addActionListener(this);
		getContentPane().add(button_modify);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(323, 200, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		
		JLabel lblqq = new JLabel("\u7528\u6237qq");
		lblqq.setFont(new Font("����", Font.BOLD, 15));
		lblqq.setBounds(31, 129, 100, 18);
		getContentPane().add(lblqq);
		
		textField_qq = new JTextField();
		textField_qq.setColumns(10);
		textField_qq.setBounds(126, 126, 269, 24);
		if(user.getQq() == null || user.getQq().equals("") == true) {
			textField_qq.setText("ѡ��");
			textField_qq.setForeground(Color.GRAY);
		}else {
			textField_qq.setText(user.getQq());
			textField_qq.setForeground(Color.BLACK);	
		}
		textField_qq.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_qq.getForeground() == Color.GRAY) {
					textField_qq.setText("");
					textField_qq.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_qq.getText().length() == 0 || textField_qq.getText() == null) {    //����û����
					textField_qq.setText("ѡ��");
					textField_qq.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_qq);
		
		JLabel label_4 = new JLabel("\u5FAE\u4FE1\u53F7");
		label_4.setFont(new Font("����", Font.BOLD, 15));
		label_4.setBounds(31, 163, 100, 18);
		getContentPane().add(label_4);
		
		textField_wxid = new JTextField();
		textField_wxid.setColumns(10);
		textField_wxid.setBounds(126, 160, 269, 24);
		if(user.getQq() == null || user.getQq().equals("") == true) {
			textField_wxid.setText("ѡ��");
			textField_wxid.setForeground(Color.GRAY);
		}else {
			textField_wxid.setText(user.getWx_id());
			textField_wxid.setForeground(Color.BLACK);	
		}
		textField_wxid.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_wxid.getForeground() == Color.GRAY) {
					textField_wxid.setText("");
					textField_wxid.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(!(textField_wxid.getText().length() > 0)) {    //����û����
					textField_wxid.setText("ѡ��");
					textField_wxid.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_wxid);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon2.jpg"));
		setTitle("\u4FEE\u6539\u7528\u6237");
		
		//��ֹ�Ŵ�
		this.setResizable(false);
		//����رհ�ť����ֱ�ӹر���
		this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		dispose();
            }
		});
		//���ھ�����ʾ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();    //��Ļ�Ĵ�С
		Dimension frameSize = this.getSize();                                   //�˴��ڵĴ�С
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
			User u = new User();
			UserManager um = new UserManager();
			//�û�����
			if(this.textField_name.getForeground() != Color.GRAY) {
				u.setName(this.textField_name.getText());//��ɫ����
			}else {
				u.setName("");
			}
			//�ֻ�����
			if(this.textField_phnum.getForeground() != Color.GRAY) {
				u.setPhonenumber(this.textField_phnum.getText());//��ɫ����
			}else {
				u.setPhonenumber("");
			}
			//����
			if(this.textField_email.getForeground() != Color.GRAY) {
				u.setEmail(this.textField_email.getText());//��ɫ����
			}else {
				u.setEmail("");
			}
			//qq
			if(this.textField_qq.getForeground() != Color.GRAY) {
				u.setQq(this.textField_qq.getText());//��ɫ����
			}else {
				u.setQq("");
			}
			//΢�ź�
			if(this.textField_wxid.getForeground() != Color.GRAY) {
				u.setWx_id(this.textField_wxid.getText());//��ɫ����
			}else {
				u.setWx_id("");
			}
			//�û����
			u.setId(user.getId());
			
			try {
				um.modifyUser(u);
				List<User> list = um.searchUser(u.getId());
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(u.getId()) == true) {
						this.user = list.get(i);break;//����׼ȷ��id��ֻ���ҵ�һ���������search�ҵ���Like�ģ����Ծ�����׼ȷ��
					}
				}
				u = this.user;
				JOptionPane.showMessageDialog(null, "�޸�Ա���ɹ�", "��ȷ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				//���еĶ�Ҫ�޸�һ��
				super_Jrame.users.get(this.selectedRow).setName(u.getName());
				super_Jrame.users.get(this.selectedRow).setEmail(u.getEmail());
				super_Jrame.users.get(this.selectedRow).setPhonenumber(u.getPhonenumber());
				super_Jrame.users.get(this.selectedRow).setQq(u.getQq());
				super_Jrame.users.get(this.selectedRow).setWx_id(u.getWx_id());
				super_Jrame.reloadTable(true);
				this.setVisible(false);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
			
		}else if(e.getSource() == this.button_exit) {
			this.dispose();
		}
	}

}
