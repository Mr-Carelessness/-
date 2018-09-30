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
	JComboBox comboBox = new JComboBox(new String[] {"已预约", "准备服务", "服务中", "完成服务", "待退款", "已退款"});
	private JTextField textField_pretime;
	
	public FrmSubscribeStatemg_modify(FrmSubscribeStatemg frmSubscribeStatemg,int i) {
		this.super_Jrame = frmSubscribeStatemg;
		this.selectedRow = i;
		this.subscribe = frmSubscribeStatemg.subscribes.get(this.selectedRow);//获取对应一行的brand
		this.setSize(450, 320);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("\u670D\u52A1\u7F16\u53F7");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(31, 27, 100, 18);
		getContentPane().add(label_1);
		
		textField_id = new JTextField();
		textField_id.setColumns(10);
		textField_id.setBounds(126, 24, 269, 24);
		//第一个
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
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(31, 61, 100, 18);
		getContentPane().add(label);
		
		textField_petId = new JTextField();
		textField_petId.setColumns(10);
		textField_petId.setBounds(126, 58, 269, 24);
		textField_petId.setText(subscribe.getPet_id());
		textField_petId.setEditable(false);
		getContentPane().add(textField_petId);
		
		JLabel label_2 = new JLabel("\u670D\u52A1\u4EF7\u683C");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(31, 95, 100, 18);
		getContentPane().add(label_2);
		
		textField_price = new JTextField();
		textField_price.setColumns(10);
		textField_price.setBounds(126, 92, 269, 24);
		textField_price.setText(String.valueOf(subscribe.getPrice()));
		textField_price.setEditable(false);
		getContentPane().add(textField_price);
		
		JLabel label_3 = new JLabel("\u8BA2\u8D2D\u65F6\u95F4");
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(31, 128, 100, 18);
		getContentPane().add(label_3);
		
		textField_time = new JTextField();
		textField_time.setColumns(10);
		textField_time.setBounds(126, 125, 269, 24);
		textField_time.setText(subscribe.getTime().toString());
		textField_time.setEditable(false);
		getContentPane().add(textField_time);
		
		JLabel label_4 = new JLabel("\u9884\u7EA6/\u5B8C\u6210\u72B6\u6001");
		label_4.setFont(new Font("宋体", Font.BOLD, 15));
		label_4.setBounds(31, 199, 129, 18);
		getContentPane().add(label_4);
		
		//根据不同状态选择不同的订单
		if(subscribe.getFinishedstate().equals("已退款") == true) {
			comboBox = new JComboBox(new String[] {"已退款"});
			comboBox.setEditable(false);
		}else if(subscribe.getFinishedstate().equals("完成服务") == true) {
			comboBox = new JComboBox(new String[] {"完成服务", "待退款", "已退款"});
		}else if(subscribe.getFinishedstate().equals("服务中") == true) {
			comboBox = new JComboBox(new String[] {"服务中", "完成服务", "待退款", "已退款"});
		}else if(subscribe.getFinishedstate().equals("已预约") == true) {
			comboBox = new JComboBox(new String[] {"已预约", "准备服务", "送货中", "待退款", "已退款"});
		}
		comboBox.setBackground(SystemColor.text);
		comboBox.setBounds(156, 196, 100, 24);
		getContentPane().add(comboBox);
		
		JLabel label_5 = new JLabel("\u9884\u7EA6\u65F6\u95F4");
		label_5.setFont(new Font("宋体", Font.BOLD, 15));
		label_5.setBounds(31, 162, 100, 18);
		getContentPane().add(label_5);
		
		textField_pretime = new JTextField();
		textField_pretime.setText(subscribe.getPretime().toString());
		textField_pretime.setEditable(false);
		textField_pretime.setColumns(10);
		textField_pretime.setBounds(126, 159, 269, 24);
		getContentPane().add(textField_pretime);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/宠物图片/icon2.jpg"));
		setTitle("\u4FEE\u6539\u9884\u7EA6\u72B6\u6001");
		
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
			Subscribe s = new Subscribe();
			OrderManager om = new OrderManager();
			//预约编号
			s.setId(subscribe.getId());
			//用户编号
			s.setPet_id(subscribe.getPet_id());
			//订单价格
			s.setPrice(subscribe.getPrice());;
			//订购时间
			s.setTime(subscribe.getTime());
			//预约时间
			s.setPretime(subscribe.getPretime());
			//订购状态
			s.setFinishedstate(comboBox.getSelectedItem().toString());
			//完成服务后，设置完成时间
			if(s.getFinishedstate().equals("完成服务") == true) {
				s.setRealfinishedtime(SwingOperation.getCurrentTime(System.currentTimeMillis()));
			}
			
			try {
				om.modifySubscribe(s);
				List<Subscribe> list = om.searchAcSubscribe(s.getId(), s.getFinishedstate());
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(s.getId()) == true) {
						this.subscribe = list.get(i);break;//根据准确的id找只能找到一个，这里的search找的是Like的，所以就找最准确的
					}
				}
				s = this.subscribe;
				if(comboBox.getSelectedItem().toString().equals("已退款") == true) {
					JOptionPane.showMessageDialog(null, "成功退款，退款金额"+String.valueOf(s.getPrice())+"元", "正确提示",JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "修改预约状态成功", "正确提示",JOptionPane.INFORMATION_MESSAGE);
				}
				//所有的都要修改一遍
				super_Jrame.subscribes.get(this.selectedRow).setFinishedstate(s.getFinishedstate());
				super_Jrame.subscribes.get(this.selectedRow).setRealfinishedtime(s.getRealfinishedtime());
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
