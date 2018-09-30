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

public class FrmOperatormg_modify extends JFrame implements ActionListener {
	private JTextField textField_id;
	private FrmOperatormg super_Jrame = null; 
	Operator operator = null;
	int selectedRow = 0;
	private JTextField textField_name;
	private JTextField textField_rank;
	private JTextField textField_pwd;
	JButton button_modify = new JButton("\u4FEE\u6539\u5458\u5DE5");
	JButton button_exit = new JButton("\u9000\u51FA");
	
	public FrmOperatormg_modify(FrmOperatormg f,int i) {
		this.super_Jrame = f;
		this.selectedRow = i;
		this.operator = f.operators.get(this.selectedRow);//��ȡ��Ӧһ�е�brand
		this.setSize(450, 260);
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u5BA2\u6237\u7F16\u53F7");
		label.setFont(new Font("����", Font.BOLD, 15));
		label.setBounds(31, 27, 100, 18);
		getContentPane().add(label);
		
		textField_id = new JTextField();
		textField_id.setBounds(126, 24, 269, 24);
		getContentPane().add(textField_id);
		textField_id.setColumns(10);
		textField_id.setText(operator.getId());
		textField_id.setEditable(false);
		
		JLabel label_1 = new JLabel("\u5BA2\u6237\u540D\u79F0");
		label_1.setFont(new Font("����", Font.BOLD, 15));
		label_1.setBounds(31, 61, 100, 18);
		getContentPane().add(label_1);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(126, 58, 269, 24);
		textField_name.setText(this.operator.getName());
		getContentPane().add(textField_name);
		
		JLabel label_2 = new JLabel("\u5458\u5DE5\u7B49\u7EA7");
		label_2.setFont(new Font("����", Font.BOLD, 15));
		label_2.setBounds(31, 95, 100, 18);
		getContentPane().add(label_2);
		
		textField_rank = new JTextField();
		textField_rank.setColumns(10);
		textField_rank.setBounds(126, 92, 269, 24);
		textField_rank.setText(String.valueOf(this.operator.getRank()));
		getContentPane().add(textField_rank);
		
		textField_pwd = new JTextField();
		textField_pwd.setColumns(10);
		textField_pwd.setBounds(126, 126, 269, 24);
		textField_pwd.setText(this.operator.getPassword());
		getContentPane().add(textField_pwd);
		
		JLabel label_3 = new JLabel("\u5458\u5DE5\u5BC6\u7801");
		label_3.setFont(new Font("����", Font.BOLD, 15));
		label_3.setBounds(31, 129, 100, 18);
		getContentPane().add(label_3);
		
		button_modify.setBackground(Color.WHITE);
		button_modify.setBounds(216, 167, 93, 27);
		button_modify.addActionListener(this);
		getContentPane().add(button_modify);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(323, 167, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\\u4F5C\u4E1A\\\u6691\u5047\u77ED\u5B66\u671F2018\\Java\u9879\u76EE\\program\\src\\timg.jpg"));
		setTitle("\u4FEE\u6539\u8BBE\u5907");
		
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
		if(e.getSource() == this.button_modify) {
			Operator o = new Operator();
			o.setId(this.textField_id.getText());
			o.setName(this.textField_name.getText());
			o.setPassword(this.textField_pwd.getText());
			int it = 0;
			try{
				if(this.textField_rank.getText() != null && this.textField_rank.getText().equals("") == false) {
					it = new Integer(this.textField_rank.getText());//��׽�쳣
				}
				if(it <= 0 || it >= 10) {
					throw new NumberFormatException();
				}
			}catch(NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "Ա���ȼ�������1-9������","����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			o.setRank(it);
			OperatorManager om = new OperatorManager();
			try {
				om.changeOperator(o);
				List<Operator> list = om.searchOperator(o.getId());
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(o.getId()) == true) {
						this.operator = list.get(i);break;//����׼ȷ��id��ֻ���ҵ�һ���������search�ҵ���Like�ģ����Ծ�����׼ȷ��
					}
				}
				o = this.operator;
				JOptionPane.showMessageDialog(null, "�޸�Ա���ɹ�", "��ȷ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				//���еĶ�Ҫ�޸�һ��
				super_Jrame.operators.get(this.selectedRow).setName(o.getName());
				super_Jrame.operators.get(this.selectedRow).setPassword(o.getPassword());
				super_Jrame.operators.get(this.selectedRow).setRank(o.getRank());
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
