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
		this.commodity = frmServicemg.commoditys.get(this.selectedRow);//获取对应一行的brand
		this.setSize(450, 290);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("\u670D\u52A1\u540D\u79F0");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(31, 27, 100, 18);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u7C7B\u522B\u7F16\u53F7");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(31, 61, 100, 18);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u670D\u52A1\u54C1\u724C");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(31, 95, 100, 18);
		getContentPane().add(label_3);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(126, 24, 269, 24);
		//第一个
		if(commodity.getName() == null || commodity.getName().equals("") == true) {
			textField_name.setText("必填");
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
				if(textField_name.getText().length() == 0 || textField_name.getText() == null) {    //里面没字了
					textField_name.setText("必填");
					textField_name.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_name);
		
		textField_catId = new JTextField();
		textField_catId.setColumns(10);
		textField_catId.setBounds(126, 58, 269, 24);
		if(commodity.getCategory_id() == null || commodity.getCategory_id().equals("") == true) {
			textField_catId.setText("必填，必须是系统中已经存在的类别");
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
				if(textField_catId.getText().length() == 0 || textField_catId.getText() == null ) {    //里面没字了
					textField_catId.setText("必填，必须是系统中已经存在的类别");
					textField_catId.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_catId);
		
		textField_brand = new JTextField();
		textField_brand.setColumns(10);
		textField_brand.setBounds(126, 92, 269, 24);
		if(commodity.getBrand() == null || commodity.getBrand().equals("") == true) {
			textField_brand.setText("必填，填写品牌具体名称");
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
				if(textField_brand.getText().length() == 0 || textField_brand.getText() == null) {    //里面没字了
					textField_brand.setText("必填，填写品牌具体名称");
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
		lblqq.setFont(new Font("宋体", Font.BOLD, 15));
		lblqq.setBounds(31, 129, 100, 18);
		getContentPane().add(lblqq);
		
		textField_price = new JTextField();
		textField_price.setColumns(10);
		textField_price.setBounds(126, 126, 269, 24);
		if(commodity.getPrice() < 0) {
			textField_price.setText("必填，必须是大于0的整数或小数");
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
				if(textField_price.getText().length() == 0 || textField_price.getText() == null) {    //里面没字了
					textField_price.setText("必填，必须是大于0的整数或小数");
					textField_price.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_price);
		
		JLabel label_4 = new JLabel("\u670D\u52A1\u6761\u7801");
		label_4.setFont(new Font("宋体", Font.BOLD, 15));
		label_4.setBounds(31, 163, 100, 18);
		getContentPane().add(label_4);
		
		textField_barcode = new JTextField();
		textField_barcode.setColumns(10);
		textField_barcode.setBounds(126, 160, 269, 24);
		if(commodity.getBarcode() == null || commodity.getBarcode().equals("") == true) {
			textField_barcode.setText("必填，条码为该商品的工厂码也可自定");
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
				if(!(textField_barcode.getText().length() > 0)) {    //里面没字了
					textField_barcode.setText("必填，条码为该商品的工厂码也可自定");
					textField_barcode.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_barcode);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		setTitle("\u4FEE\u6539\u670D\u52A1");
		
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
		if(e.getSource() == this.button_modify) {
			Commodity c = new Commodity();
			CommodityManager cm = new CommodityManager();
			//商品名称
			if(this.textField_name.getForeground() != Color.GRAY) {
				c.setName(this.textField_name.getText());//黑色有字
			}else {
				c.setName("");
			}
			//类别编号
			if(this.textField_catId.getForeground() != Color.GRAY) {
				c.setCategory_id(this.textField_catId.getText());//黑色有字
			}else {
				c.setCategory_id("");
			}
			//商品品牌
			if(this.textField_brand.getForeground() != Color.GRAY) {
				c.setBrand(this.textField_brand.getText());//黑色有字
			}else {
				c.setBrand("");
			}
			//商品价格
			if(this.textField_price.getForeground() != Color.GRAY) {
				c.setPrice(Double.valueOf(this.textField_price.getText()));//黑色有字
			}else {
				c.setPrice(0f);
			}
			//商品条码
			if(this.textField_barcode.getForeground() != Color.GRAY) {
				c.setBarcode(this.textField_barcode.getText());//黑色有字
			}else {
				c.setBarcode("");
			}
			//商品编号
			c.setId(commodity.getId());
			
			try {
				cm.modifyCommodity(c);
				int choice_service = 2;//服务
				List<Commodity> list = cm.searchCommodity(c.getId(),choice_service);
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(c.getId()) == true) {
						this.commodity = list.get(i);break;//根据准确的id找只能找到一个，这里的search找的是Like的，所以就找最准确的
					}
				}
				c = this.commodity;
				JOptionPane.showMessageDialog(null, "修改商品(服务)成功", "正确提示",JOptionPane.INFORMATION_MESSAGE);
				//所有的都要修改一遍
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
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
			
		}else if(e.getSource() == this.button_exit) {
			this.dispose();
		}
	}
}
