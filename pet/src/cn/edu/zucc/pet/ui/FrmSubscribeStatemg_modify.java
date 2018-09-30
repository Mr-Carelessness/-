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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.OrderManager;
import cn.edu.zucc.pet.control.SwingOperation;
import cn.edu.zucc.pet.model.Order;
import cn.edu.zucc.pet.model.Subscribe;
import cn.edu.zucc.pet.util.BaseException;

public class FrmSubscribeStatemg_modify extends JFrame implements ActionListener{
	private JTextField textField_id;
	private FrmSubscribeStatemg super_Jrame = null; 
	JButton button_modify = new JButton("\u4FEE\u6539\u9884\u7EA6\u72B6\u6001");
	JButton button_exit = new JButton("\u9000\u51FA");
	Subscribe subscribe = null;
	int selectedRow = 0;
	private JTextField textField_petId;
	private JTextField textField_price;
	private JTextField textField_time;
	JComboBox comboBox = new JComboBox(new String[] {"��ԤԼ", "׼������", "������", "��ɷ���", "���˿�", "���˿�"});
	private JTextField textField_pretime;
	
	public FrmSubscribeStatemg_modify(FrmSubscribeStatemg frmSubscribeStatemg,int i) {
		this.super_Jrame = frmSubscribeStatemg;
		this.selectedRow = i;
		this.subscribe = frmSubscribeStatemg.subscribes.get(this.selectedRow);//��ȡ��Ӧһ�е�brand
		this.setSize(450, 320);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon2.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("\u670D\u52A1\u7F16\u53F7");
		label_1.setFont(new Font("����", Font.BOLD, 15));
		label_1.setBounds(31, 27, 100, 18);
		getContentPane().add(label_1);
		
		textField_id = new JTextField();
		textField_id.setColumns(10);
		textField_id.setBounds(126, 24, 269, 24);
		//��һ��
		textField_id.setText(subscribe.getId());
		textField_id.setEditable(false);
		getContentPane().add(textField_id);
		
		button_modify.setBackground(Color.WHITE);
		button_modify.setBounds(186, 233, 123, 27);
		button_modify.addActionListener(this);
		getContentPane().add(button_modify);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(323, 233, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		
		JLabel label = new JLabel("\u5BA0\u7269\u7F16\u53F7");
		label.setFont(new Font("����", Font.BOLD, 15));
		label.setBounds(31, 61, 100, 18);
		getContentPane().add(label);
		
		textField_petId = new JTextField();
		textField_petId.setColumns(10);
		textField_petId.setBounds(126, 58, 269, 24);
		textField_petId.setText(subscribe.getPet_id());
		textField_petId.setEditable(false);
		getContentPane().add(textField_petId);
		
		JLabel label_2 = new JLabel("\u670D\u52A1\u4EF7\u683C");
		label_2.setFont(new Font("����", Font.BOLD, 15));
		label_2.setBounds(31, 95, 100, 18);
		getContentPane().add(label_2);
		
		textField_price = new JTextField();
		textField_price.setColumns(10);
		textField_price.setBounds(126, 92, 269, 24);
		textField_price.setText(String.valueOf(subscribe.getPrice()));
		textField_price.setEditable(false);
		getContentPane().add(textField_price);
		
		JLabel label_3 = new JLabel("\u8BA2\u8D2D\u65F6\u95F4");
		label_3.setFont(new Font("����", Font.BOLD, 15));
		label_3.setBounds(31, 128, 100, 18);
		getContentPane().add(label_3);
		
		textField_time = new JTextField();
		textField_time.setColumns(10);
		textField_time.setBounds(126, 125, 269, 24);
		textField_time.setText(subscribe.getTime().toString());
		textField_time.setEditable(false);
		getContentPane().add(textField_time);
		
		JLabel label_4 = new JLabel("\u9884\u7EA6/\u5B8C\u6210\u72B6\u6001");
		label_4.setFont(new Font("����", Font.BOLD, 15));
		label_4.setBounds(31, 199, 129, 18);
		getContentPane().add(label_4);
		
		//���ݲ�ͬ״̬ѡ��ͬ�Ķ���
		if(subscribe.getFinishedstate().equals("���˿�") == true) {
			comboBox = new JComboBox(new String[] {"���˿�"});
			comboBox.setEditable(false);
		}else if(subscribe.getFinishedstate().equals("��ɷ���") == true) {
			comboBox = new JComboBox(new String[] {"��ɷ���", "���˿�", "���˿�"});
		}else if(subscribe.getFinishedstate().equals("������") == true) {
			comboBox = new JComboBox(new String[] {"������", "��ɷ���", "���˿�", "���˿�"});
		}else if(subscribe.getFinishedstate().equals("��ԤԼ") == true) {
			comboBox = new JComboBox(new String[] {"��ԤԼ", "׼������", "�ͻ���", "���˿�", "���˿�"});
		}
		comboBox.setBackground(SystemColor.text);
		comboBox.setBounds(156, 196, 100, 24);
		getContentPane().add(comboBox);
		
		JLabel label_5 = new JLabel("\u9884\u7EA6\u65F6\u95F4");
		label_5.setFont(new Font("����", Font.BOLD, 15));
		label_5.setBounds(31, 162, 100, 18);
		getContentPane().add(label_5);
		
		textField_pretime = new JTextField();
		textField_pretime.setText(subscribe.getPretime().toString());
		textField_pretime.setEditable(false);
		textField_pretime.setColumns(10);
		textField_pretime.setBounds(126, 159, 269, 24);
		getContentPane().add(textField_pretime);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon2.jpg"));
		setTitle("\u4FEE\u6539\u9884\u7EA6\u72B6\u6001");
		
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
			Subscribe s = new Subscribe();
			OrderManager om = new OrderManager();
			//ԤԼ���
			s.setId(subscribe.getId());
			//�û����
			s.setPet_id(subscribe.getPet_id());
			//�����۸�
			s.setPrice(subscribe.getPrice());;
			//����ʱ��
			s.setTime(subscribe.getTime());
			//ԤԼʱ��
			s.setPretime(subscribe.getPretime());
			//����״̬
			s.setFinishedstate(comboBox.getSelectedItem().toString());
			//��ɷ�����������ʱ��
			if(s.getFinishedstate().equals("��ɷ���") == true) {
				s.setRealfinishedtime(SwingOperation.getCurrentTime(System.currentTimeMillis()));
			}
			
			try {
				om.modifySubscribe(s);
				List<Subscribe> list = om.searchAcSubscribe(s.getId(), s.getFinishedstate());
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(s.getId()) == true) {
						this.subscribe = list.get(i);break;//����׼ȷ��id��ֻ���ҵ�һ���������search�ҵ���Like�ģ����Ծ�����׼ȷ��
					}
				}
				s = this.subscribe;
				if(comboBox.getSelectedItem().toString().equals("���˿�") == true) {
					JOptionPane.showMessageDialog(null, "�ɹ��˿�˿���"+String.valueOf(s.getPrice())+"Ԫ", "��ȷ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "�޸�ԤԼ״̬�ɹ�", "��ȷ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
				//���еĶ�Ҫ�޸�һ��
				super_Jrame.subscribes.get(this.selectedRow).setFinishedstate(s.getFinishedstate());
				super_Jrame.subscribes.get(this.selectedRow).setRealfinishedtime(s.getRealfinishedtime());
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
