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

import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.model.Pet;
import cn.edu.zucc.pet.model.User;
import cn.edu.zucc.pet.util.BaseException;

public class FrmPetmg extends JFrame implements ActionListener{
	private JTextField textField;
	private JTable table;
	private Object tblData[][];
	private Object tblTitle[] = {"������","�����ǳ�","�������","���˱��","��������"};
	List<Pet> pets = null;
	DefaultTableModel tablmod = new DefaultTableModel();
	JScrollPane scrollPane = new JScrollPane();
	
	JButton button_add = new JButton(" \u65B0\u589E\u5BA0\u7269 ");//������ť
	JButton button_modify = new JButton(" \u4FEE\u6539\u5BA0\u7269 ");//�޸İ�ť
	JButton button_delete = new JButton(" \u5220\u9664\u5BA0\u7269 ");//ɾ����ť
	JButton button_search = new JButton("    \u67E5\u8BE2    ");//���Ұ�ť
	public FrmPetmg() {
		setTitle("\u5BA0\u7269\u7BA1\u7406");
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon2.jpg"));
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
		label.setFont(new Font("����", Font.BOLD, 15));
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
		
		table = new JTable(tablmod);//��
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);		
		scrollPane.getViewport().setBackground(SystemColor.inactiveCaption);
		this.reloadTable(false);//��ȡ��������
		getContentPane().add(scrollPane, BorderLayout.CENTER);		
		//getContentPane().add(table, BorderLayout.CENTER);
		
		//����Ŵ�
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
				pets = (new PetManager()).searchPet(this.textField.getText());
			}
			tblData = new Object[pets.size()][tblTitle.length];
			for(int i = 0;i < pets.size();i++){
				tblData[i][0] = pets.get(i).getId();
				tblData[i][1] = pets.get(i).getName();
				tblData[i][2] = pets.get(i).getOthername();
				tblData[i][3] = pets.get(i).getUser_id();
				tblData[i][4] = pets.get(i).getUser_name();
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
		}else if(e.getSource() == this.button_delete) {
			int i = this.table.getSelectedRow();
			//û��ѡ����ʾ��ֱ��ɾ��
			if(i < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ�����","����",JOptionPane.ERROR_MESSAGE);
			}
			else {
				Pet pet = this.pets.get(i);
				PetManager pm = new PetManager();
				try {
					pm.deletePet(pet);
					JOptionPane.showMessageDialog(null, "ɾ������ɹ�", "��ȷ��ʾ",JOptionPane.INFORMATION_MESSAGE);
					pets.remove(pet);
					this.reloadTable(true);
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}				
			}
		}else if(e.getSource() == this.button_modify) {
			if(this.table.getSelectedRow() >= 0) {
				FrmPetmg_modify fm = new FrmPetmg_modify(this,this.table.getSelectedRow());//��ȡ��Ӧһ�е�brand���ٸ���brandֵ�޸�
				fm.setVisible(true);				
				fm.panel_img.paint(fm.panel_img.getGraphics());
			}else {
				JOptionPane.showMessageDialog(null, "��ѡ�����","����",JOptionPane.ERROR_MESSAGE);
			}
		}else if(e.getSource() == this.button_add) {
			FrmPetmg_add fm = new FrmPetmg_add(this);
			fm.setVisible(true);			

		}
	}

	public void addUser(Pet pet) {
		this.pets.add(pet);
	}

}
