package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.plaf.synth.SynthSpinnerUI;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.OrderManager;
import cn.edu.zucc.pet.model.Order;
import cn.edu.zucc.pet.model.Subscribe;
import cn.edu.zucc.pet.util.BaseException;

public class FrmSubscribeStatemg extends JFrame implements ActionListener {
	private JTextField textField;
	private JTable table;
	private Object tblData[][];
	private Object tblTitle[] = {"�������","������","�����ǳ�","����۸�","����ʱ��","ԤԼʱ��","���״̬","���ʱ��"};
	List<Subscribe> subscribes = null;
	DefaultTableModel tablmod = new DefaultTableModel();
	JScrollPane scrollPane = new JScrollPane();
	JButton button_modify = new JButton(" \u4FEE\u6539\u9884\u7EA6\u72B6\u6001 ");//�޸İ�ť
	JButton button_search = new JButton("    \u67E5\u8BE2    ");//���Ұ�ť
	JComboBox comboBox = new JComboBox(new String[] {"","��ԤԼ", "׼������", "������", "��ɷ���", "���˿�", "���˿�"});
	public FrmSubscribeStatemg() {
		setTitle("\u9884\u7EA6\u72B6\u6001\u4FEE\u6539");
		this.setSize(1000, 660);
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.inactiveCaption);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		button_modify.addActionListener(this);
		toolBar.add(button_modify);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(300);
		toolBar.add(horizontalStrut_2);
		
		JLabel label = new JLabel("\u9884\u7EA6\u7F16\u53F7\uFF1A");
		label.setFont(new Font("����", Font.BOLD, 15));
		toolBar.add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		toolBar.add(textField);
		
		Component horizontalStrut = Box.createHorizontalStrut(10);
		toolBar.add(horizontalStrut);
		
		JLabel label_1 = new JLabel("\u9884\u7EA6\u72B6\u6001\uFF1A");
		label_1.setFont(new Font("����", Font.BOLD, 15));
		toolBar.add(label_1);
		
		toolBar.add(comboBox);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(10);
		toolBar.add(horizontalStrut_3);
		button_search.addActionListener(this);
		toolBar.add(button_search);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(5);
		toolBar.add(horizontalStrut_4);
		
		table = new JTable(tablmod);//��
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);		
		scrollPane.getViewport().setBackground(SystemColor.inactiveCaption);
		this.reloadTable(false);//��ȡ��������
		getContentPane().add(scrollPane, BorderLayout.CENTER);		
		//getContentPane().add(table, BorderLayout.CENTER);
		
		//��ֹ�Ŵ�
		this.setResizable(true);
		//����رհ�ť
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
	
	public void reloadTable(boolean isUpdated){
		try {
			if(isUpdated == false) {
				subscribes = (new OrderManager()).searchAcSubscribe(this.textField.getText(), comboBox.getSelectedItem().toString());
			}
			tblData = new Object[subscribes.size()][tblTitle.length];
			for(int i = 0;i < subscribes.size();i++){
				tblData[i][0] = subscribes.get(i).getId();
				tblData[i][1] = subscribes.get(i).getPet_id();
				tblData[i][2] = subscribes.get(i).getPet_name();
				tblData[i][3] = subscribes.get(i).getPrice();
				tblData[i][4] = subscribes.get(i).getTime();
				tblData[i][5] = subscribes.get(i).getPretime();
				tblData[i][6] = subscribes.get(i).getFinishedstate();
				tblData[i][7] = subscribes.get(i).getRealfinishedtime();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.table.validate();
			this.table.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.button_search) {
			this.reloadTable(false);
		}else if(e.getSource() == this.button_modify) {
			if(this.table.getSelectedRow() >= 0) {
				FrmSubscribeStatemg_modify fm = new FrmSubscribeStatemg_modify(this,this.table.getSelectedRow());//��ȡ��Ӧһ�е�brand���ٸ���brandֵ�޸�
				fm.setVisible(true);				
			}else {
				JOptionPane.showMessageDialog(null, "��ѡ��ԤԼ","����",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void addSubscribe(Subscribe subscribe) {
		this.subscribes.add(subscribe);
	}
}
