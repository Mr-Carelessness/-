package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.model.Subscribe;
import cn.edu.zucc.pet.model.SubscribeDetail;

public class FrmSubscribeshow extends JFrame implements ActionListener {
	
	private FrmSubscribemg superFrame;
	private Subscribe subscribe;
	private JTable table_subscribeDetail;
	DefaultTableModel tablmod = new DefaultTableModel();
	JButton btnNewButton = new JButton("    \u786E\u5B9A    ");//确定
	//详细信息的表
	private List<SubscribeDetail> subscribeDetails = null;
	private Object tblData_subscribeDetail[][];
	private Object tblTitle_subscribeDetail[] = {"服务编号","服务名称","服务单价","服务数量","服务折扣","服务总价"};
	
	public FrmSubscribeshow(FrmSubscribemg frmSubscribemg) {
		this.superFrame = frmSubscribemg;
		this.subscribe = superFrame.subscribe;
		this.setSize(480,420);
		setTitle("\u8BA2\u8D2D\u6210\u529F");
		getContentPane().setBackground(SystemColor.inactiveCaption);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(10);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("\u670D \u52A1 \u5C0F \u7968");
		label.setFont(new Font("黑体", Font.PLAIN, 24));
		panel.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.inactiveCaption);
		panel_2.setPreferredSize(new Dimension(getWidth(),85));
		panel_1.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_4.setBackground(SystemColor.text);
		panel_2.add(panel_4, BorderLayout.NORTH);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_4.add(horizontalStrut_2);
		
		JLabel label_10 = new JLabel("\u606D\u559C\u60A8\u8BA2\u8D2D\u6210\u529F\uFF01\u4EE5\u4E0B\u662F\u60A8\u672C\u6B21\u9884\u7EA6\u6709\u5173\u4FE1\u606F");
		label_10.setText("恭喜您订购成功！获得"+(int)(subscribe.getPrice()/10)+"积分，以下是您本次订购有关信息");
		panel_4.add(label_10);
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_5.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		panel_5.setBackground(SystemColor.text);
		panel_2.add(panel_5, BorderLayout.SOUTH);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(40);
		panel_5.add(horizontalStrut_6);
		
		JLabel label_3 = new JLabel("\u7528\u6237\u7F16\u53F7\uFF1A");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(label_3);
		
		JLabel label_5 = new JLabel("New label");
		if(subscribe!=null) {
			label_5.setText(subscribe.getPet_id());
		}
		panel_5.add(label_5);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(30);
		panel_5.add(horizontalStrut_1);
		
		JLabel label_6 = new JLabel("\u6210\u4EA4\u91D1\u989D\uFF1A");
		panel_5.add(label_6);
		
		JLabel label_7 = new JLabel("New label");
		if(subscribe!=null) {
			label_7.setText(String.valueOf(subscribe.getPrice()));
		}
		panel_5.add(label_7);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_6.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		panel_6.setBackground(SystemColor.inactiveCaption);
		panel_2.add(panel_6, BorderLayout.CENTER);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(40);
		panel_6.add(horizontalStrut_5);
		
		JLabel label_1 = new JLabel("\u670D\u52A1\u7F16\u53F7\uFF1A");
		panel_6.add(label_1);
		
		JLabel label_2 = new JLabel("New label");
		if(subscribe!=null) {
			label_2.setText(subscribe.getId());
		}
		panel_6.add(label_2);
		
		Component horizontalStrut = Box.createHorizontalStrut(30);
		panel_6.add(horizontalStrut);
		
		JLabel label_8 = new JLabel("\u8BA2\u8D2D\u65F6\u95F4\uFF1A");
		panel_6.add(label_8);
		
		JLabel label_9 = new JLabel("New label");
		if(subscribe!=null) {
			label_9.setText(subscribe.getTime().toString());
		}
		panel_6.add(label_9);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.inactiveCaption);
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_9 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_9.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel_9.setBackground(SystemColor.inactiveCaption);
		panel_3.add(panel_9, BorderLayout.NORTH);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_9.add(horizontalStrut_3);
		
		JLabel label_4 = new JLabel("\u8BA2\u8D2D\u670D\u52A1\uFF1A");
		panel_9.add(label_4);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(128);
		panel_9.add(horizontalStrut_4);
		
		JLabel label_11 = new JLabel("\u9884\u7EA6\u65F6\u95F4\uFF1A");
		panel_9.add(label_11);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setText(subscribe.getPretime().toString());
		panel_9.add(lblNewLabel);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(SystemColor.inactiveCaption);
		panel_3.add(panel_7, BorderLayout.SOUTH);
		
		btnNewButton.addActionListener(this);
		panel_7.add(btnNewButton);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(SystemColor.inactiveCaption);
		panel_3.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(SystemColor.inactiveCaption);
		panel_8.add(scrollPane);
		
		table_subscribeDetail = new JTable(tablmod);
		this.subscribeDetails = superFrame.subscribeDetails;
		table_subscribeDetail.setBackground(Color.WHITE);
		scrollPane.setViewportView(table_subscribeDetail);
		
		
		
		//禁止放大
		this.setResizable(false);
		//点击关闭按钮，就直接关闭了
		this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		superFrame.dispose();
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
	
	public void reloadTable_subscribeDetail() {
		tblData_subscribeDetail = new Object[subscribeDetails.size()][tblTitle_subscribeDetail.length];
		if(subscribeDetails.size() > 0) {
			for(int i = 0;i < subscribeDetails.size();i++){
				tblData_subscribeDetail[i][0] = subscribeDetails.get(i).getCommodity_id();
				tblData_subscribeDetail[i][1] = subscribeDetails.get(i).getCommodity_name();
				tblData_subscribeDetail[i][2] = subscribeDetails.get(i).getCommodity_itsprice();
				tblData_subscribeDetail[i][3] = subscribeDetails.get(i).getNumber();
				tblData_subscribeDetail[i][4] = subscribeDetails.get(i).getDiscount();
				tblData_subscribeDetail[i][5] = subscribeDetails.get(i).getCommodity_price();
			}			
		}
		tablmod.setDataVector(tblData_subscribeDetail,tblTitle_subscribeDetail);
		this.table_subscribeDetail.validate();
		this.table_subscribeDetail.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.btnNewButton) {
			superFrame.dispose();
			this.dispose();
		}
	}
}
