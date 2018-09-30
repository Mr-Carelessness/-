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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.OperatorManager;
import cn.edu.zucc.pet.model.Operator;
import cn.edu.zucc.pet.util.BaseException;

public class FrmOperatormg extends JFrame implements ActionListener {
	private JTextField textField;
	private JTable table;
	private Object tblData[][];
	private Object tblTitle[] = {"员工编号","员工姓名","员工密码","员工等级"};
	List<Operator> operators = null;
	DefaultTableModel tablmod = new DefaultTableModel();
	JScrollPane scrollPane = new JScrollPane();
	
	JButton button_add = new JButton(" \u65B0\u589E\u5458\u5DE5 ");//新增按钮
	JButton button_modify = new JButton(" \u4FEE\u6539\u5458\u5DE5 ");//修改按钮
	JButton button_delete = new JButton(" \u5220\u9664\u5458\u5DE5 ");//删除按钮
	JButton button_search = new JButton("    \u67E5\u8BE2    ");//查找按钮
	public FrmOperatormg() {
		setTitle("\u5458\u5DE5\u7BA1\u7406");
		this.setSize(1000, 660);
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(SystemColor.inactiveCaption);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		button_add.addActionListener(this);
		toolBar.add(button_add);
		Component horizontalStrut = Box.createHorizontalStrut(10);
		toolBar.add(horizontalStrut);
		button_modify.addActionListener(this);
		toolBar.add(button_modify);
		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		toolBar.add(horizontalStrut_1);
		button_delete.setForeground(Color.GRAY);
		button_delete.addActionListener(this);
		toolBar.add(button_delete);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(200);
		toolBar.add(horizontalStrut_2);
		
		JLabel label = new JLabel("\u5173\u952E\u540D/\u7F16\u53F7\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		toolBar.add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		toolBar.add(textField);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(5);
		toolBar.add(horizontalStrut_3);
		button_search.addActionListener(this);
		toolBar.add(button_search);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(5);
		toolBar.add(horizontalStrut_4);
		
		table = new JTable(tablmod);//★
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);		
		scrollPane.getViewport().setBackground(SystemColor.inactiveCaption);
		this.reloadTable(false);//提取现有数据
		getContentPane().add(scrollPane, BorderLayout.CENTER);		
		//getContentPane().add(table, BorderLayout.CENTER);
		
		//禁止放大
		this.setResizable(true);
		//点击关闭按钮
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
	
	public void reloadTable(boolean isUpdated){
		try {
			if(isUpdated == false) {
				operators = (new OperatorManager()).searchOperator(this.textField.getText());
			}
			tblData = new Object[operators.size()][4];
			for(int i = 0;i < operators.size();i++){
				tblData[i][0] = operators.get(i).getId();
				tblData[i][1] = operators.get(i).getName();
				tblData[i][2] = operators.get(i).getPassword();
				tblData[i][3] = operators.get(i).getRank();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.table.validate();
			this.table.repaint();
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.button_search) {
			this.reloadTable(false);
		}else if(e.getSource() == this.button_delete) {
			int i = this.table.getSelectedRow();
			//没有选择提示框直接删除
			if(i < 0) {
				JOptionPane.showMessageDialog(null, "请选择员工","错误",JOptionPane.ERROR_MESSAGE);
			}
			else {
				Operator operator = this.operators.get(i);
				OperatorManager om = new OperatorManager();
				try {
					om.deleteOperator(operator.getId());
					JOptionPane.showMessageDialog(null, "删除员工成功", "正确提示",JOptionPane.INFORMATION_MESSAGE);
					operators.remove(operator);
					this.reloadTable(true);
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}				
			}
		}else if(e.getSource() == this.button_modify) {
			if(this.table.getSelectedRow() >= 0) {
				FrmOperatormg_modify fm = new FrmOperatormg_modify(this,this.table.getSelectedRow());//获取对应一行的brand，再根据brand值修改
				fm.setVisible(true);				
			}else {
				JOptionPane.showMessageDialog(null, "请选择员工","错误",JOptionPane.ERROR_MESSAGE);
			}
		}else if(e.getSource() == this.button_add) {
			FrmOperatormg_add fm = new FrmOperatormg_add(this);
			fm.setVisible(true);			

		}
	}

	public void addOperator(Operator operator) {
		this.operators.add(operator);
	}
}
