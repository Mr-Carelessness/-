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

import cn.edu.zucc.pet.control.CommodityManager;
import cn.edu.zucc.pet.model.Commodity;
import cn.edu.zucc.pet.util.BaseException;

public class FrmServicemg_modify extends JFrame implements ActionListener {
	private JTextField textField_name;
	private JTextField textField_catId;
	private JTextField textField_brand;
	private FrmServicemg super_Jrame = null; 
	JButton button_modify = new JButton("\u4FEE\u6539\u670D\u52A1");
	JButton button_exit = new JButton("\u9000\u51FA");
	Commodity commodity = null;
	int selectedRow = 0;
	private JTextField textField_price;
	private JTextField textField_barcode;
	
	public FrmServicemg_modify(FrmServicemg frmServicemg,int i) {
		this.super_Jrame = frmServicemg;
		this.selectedRow = i;
		this.commodity = frmServicemg.commoditys.get(this.selectedRow);//��ȡ��Ӧһ�е�brand
		this.setSize(450, 290);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon2.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("\u670D\u52A1\u540D\u79F0");
		label_1.setFont(new Font("����", Font.BOLD, 15));
		label_1.setBounds(31, 27, 100, 18);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u7C7B\u522B\u7F16\u53F7");
		label_2.setFont(new Font("����", Font.BOLD, 15));
		label_2.setBounds(31, 61, 100, 18);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u670D\u52A1\u54C1\u724C");
		label_3.setFont(new Font("����", Font.BOLD, 15));
		label_3.setBounds(31, 95, 100, 18);
		getContentPane().add(label_3);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(126, 24, 269, 24);
		//��һ��
		if(commodity.getName() == null || commodity.getName().equals("") == true) {
			textField_name.setText("����");
		}else {
			textField_name.setText(commodity.getName());
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
		
		textField_catId = new JTextField();
		textField_catId.setColumns(10);
		textField_catId.setBounds(126, 58, 269, 24);
		if(commodity.getCategory_id() == null || commodity.getCategory_id().equals("") == true) {
			textField_catId.setText("���������ϵͳ���Ѿ����ڵ����");
			textField_catId.setForeground(Color.GRAY);
		}else {
			textField_catId.setText(commodity.getCategory_id());
			textField_catId.setForeground(Color.BLACK);	
		}
		textField_catId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_catId.getForeground() == Color.GRAY) {
					textField_catId.setText("");
					textField_catId.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_catId.getText().length() == 0 || textField_catId.getText() == null ) {    //����û����
					textField_catId.setText("���������ϵͳ���Ѿ����ڵ����");
					textField_catId.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_catId);
		
		textField_brand = new JTextField();
		textField_brand.setColumns(10);
		textField_brand.setBounds(126, 92, 269, 24);
		if(commodity.getBrand() == null || commodity.getBrand().equals("") == true) {
			textField_brand.setText("�����дƷ�ƾ�������");
			textField_brand.setForeground(Color.GRAY);
		}else {
			textField_brand.setText(commodity.getBrand());
			textField_brand.setForeground(Color.BLACK);
		}
		textField_brand.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_brand.getForeground() == Color.GRAY) {
					textField_brand.setText("");
					textField_brand.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_brand.getText().length() == 0 || textField_brand.getText() == null) {    //����û����
					textField_brand.setText("�����дƷ�ƾ�������");
					textField_brand.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_brand);
		
		button_modify.setBackground(Color.WHITE);
		button_modify.setBounds(216, 200, 93, 27);
		button_modify.addActionListener(this);
		getContentPane().add(button_modify);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(323, 200, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		
		JLabel lblqq = new JLabel("\u670D\u52A1\u4EF7\u683C");
		lblqq.setFont(new Font("����", Font.BOLD, 15));
		lblqq.setBounds(31, 129, 100, 18);
		getContentPane().add(lblqq);
		
		textField_price = new JTextField();
		textField_price.setColumns(10);
		textField_price.setBounds(126, 126, 269, 24);
		if(commodity.getPrice() < 0) {
			textField_price.setText("��������Ǵ���0��������С��");
			textField_price.setForeground(Color.GRAY);
		}else {
			textField_price.setText(String.valueOf(commodity.getPrice()));
			textField_price.setForeground(Color.BLACK);	
		}
		textField_price.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_price.getForeground() == Color.GRAY) {
					textField_price.setText("");
					textField_price.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_price.getText().length() == 0 || textField_price.getText() == null) {    //����û����
					textField_price.setText("��������Ǵ���0��������С��");
					textField_price.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_price);
		
		JLabel label_4 = new JLabel("\u670D\u52A1\u6761\u7801");
		label_4.setFont(new Font("����", Font.BOLD, 15));
		label_4.setBounds(31, 163, 100, 18);
		getContentPane().add(label_4);
		
		textField_barcode = new JTextField();
		textField_barcode.setColumns(10);
		textField_barcode.setBounds(126, 160, 269, 24);
		if(commodity.getBarcode() == null || commodity.getBarcode().equals("") == true) {
			textField_barcode.setText("�������Ϊ����Ʒ�Ĺ�����Ҳ���Զ�");
			textField_barcode.setForeground(Color.GRAY);
		}else {
			textField_barcode.setText(commodity.getBarcode());
			textField_barcode.setForeground(Color.BLACK);	
		}
		textField_barcode.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_barcode.getForeground() == Color.GRAY) {
					textField_barcode.setText("");
					textField_barcode.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(!(textField_barcode.getText().length() > 0)) {    //����û����
					textField_barcode.setText("�������Ϊ����Ʒ�Ĺ�����Ҳ���Զ�");
					textField_barcode.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_barcode);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon2.jpg"));
		setTitle("\u4FEE\u6539\u670D\u52A1");
		
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
			Commodity c = new Commodity();
			CommodityManager cm = new CommodityManager();
			//��Ʒ����
			if(this.textField_name.getForeground() != Color.GRAY) {
				c.setName(this.textField_name.getText());//��ɫ����
			}else {
				c.setName("");
			}
			//�����
			if(this.textField_catId.getForeground() != Color.GRAY) {
				c.setCategory_id(this.textField_catId.getText());//��ɫ����
			}else {
				c.setCategory_id("");
			}
			//��ƷƷ��
			if(this.textField_brand.getForeground() != Color.GRAY) {
				c.setBrand(this.textField_brand.getText());//��ɫ����
			}else {
				c.setBrand("");
			}
			//��Ʒ�۸�
			if(this.textField_price.getForeground() != Color.GRAY) {
				c.setPrice(Double.valueOf(this.textField_price.getText()));//��ɫ����
			}else {
				c.setPrice(0f);
			}
			//��Ʒ����
			if(this.textField_barcode.getForeground() != Color.GRAY) {
				c.setBarcode(this.textField_barcode.getText());//��ɫ����
			}else {
				c.setBarcode("");
			}
			//��Ʒ���
			c.setId(commodity.getId());
			
			try {
				cm.modifyCommodity(c);
				int choice_service = 2;//����
				List<Commodity> list = cm.searchCommodity(c.getId(),choice_service);
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(c.getId()) == true) {
						this.commodity = list.get(i);break;//����׼ȷ��id��ֻ���ҵ�һ���������search�ҵ���Like�ģ����Ծ�����׼ȷ��
					}
				}
				c = this.commodity;
				JOptionPane.showMessageDialog(null, "�޸���Ʒ(����)�ɹ�", "��ȷ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				//���еĶ�Ҫ�޸�һ��
				super_Jrame.commoditys.get(this.selectedRow).setName(c.getName());
				super_Jrame.commoditys.get(this.selectedRow).setCategory_id(c.getCategory_id());
				super_Jrame.commoditys.get(this.selectedRow).setCategory_name(c.getCategory_name());;
				super_Jrame.commoditys.get(this.selectedRow).setBrand(c.getBrand());
				super_Jrame.commoditys.get(this.selectedRow).setPrice(c.getPrice());
				super_Jrame.commoditys.get(this.selectedRow).setBarcode(c.getBarcode());
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
