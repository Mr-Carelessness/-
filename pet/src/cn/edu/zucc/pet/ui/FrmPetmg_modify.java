package cn.edu.zucc.pet.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.SwingOperation;
import cn.edu.zucc.pet.model.Pet;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.DbException;

public class FrmPetmg_modify extends JFrame implements ActionListener {
	private JTextField textField_name;
	private JTextField textField_othername;
	private JTextField textField_userid;
	private FrmPetmg super_Jrame = null; 
	BackgroundPanel panel_img = null;
	JButton button_modify = new JButton("\u4FEE\u6539\u5BA0\u7269");
	JButton button_exit = new JButton("\u9000\u51FA");
	Pet pet = null;
	int selectedRow = 0;
	private JTextField textField_photo;
	String filepath1 = ("temp_img/");//���ص����·������
	String filepath2 = ("temp_img/");//���ص����·������
	
	public FrmPetmg_modify(FrmPetmg frmPetmg,int i) {
		setTitle("\u4FEE\u6539\u5BA0\u7269");
		this.super_Jrame = frmPetmg;
		this.selectedRow = i;
		this.pet = frmPetmg.pets.get(this.selectedRow);//��ȡ��Ӧһ�е�brand
		this.setSize(580, 260);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon.jpg"));
		getContentPane().setBackground(SystemColor.inactiveCaption);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("\u5BA0\u7269\u6635\u79F0");
		label_1.setFont(new Font("����", Font.BOLD, 15));
		label_1.setBounds(31, 27, 100, 18);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u5BA0\u7269\u522B\u540D");
		label_2.setFont(new Font("����", Font.BOLD, 15));
		label_2.setBounds(31, 61, 100, 18);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u4E3B\u4EBA\u7F16\u53F7");
		label_3.setFont(new Font("����", Font.BOLD, 15));
		label_3.setBounds(31, 95, 100, 18);
		getContentPane().add(label_3);
		
		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(126, 24, 269, 24);
		//��һ��
		if(pet.getName() == null || pet.getName().equals("") == true) {
			textField_name.setText("");
		}else {
			textField_name.setText(pet.getName());
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
		
		textField_othername = new JTextField();
		textField_othername.setColumns(10);
		textField_othername.setBounds(126, 58, 269, 24);
		if(pet.getOthername() == null || pet.getOthername().equals("") == true) {
			textField_othername.setText("ѡ��");
			textField_othername.setForeground(Color.GRAY);
		}else {
			textField_othername.setText(pet.getOthername());
			textField_othername.setForeground(Color.BLACK);	
		}
		textField_othername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_othername.getForeground() == Color.GRAY) {
					textField_othername.setText("");
					textField_othername.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_othername.getText().length() == 0 || textField_othername.getText() == null ) {    //����û����
					textField_othername.setText("ѡ��");
					textField_othername.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_othername);
		
		textField_userid = new JTextField();
		textField_userid.setColumns(10);
		textField_userid.setBounds(126, 92, 269, 24);
		textField_userid.setText(pet.getUser_id());
		textField_userid.setEditable(false);
		textField_userid.setForeground(Color.GRAY);
		getContentPane().add(textField_userid);
		
		button_modify.setBackground(Color.WHITE);
		button_modify.setBounds(366, 174, 93, 27);
		button_modify.addActionListener(this);
		getContentPane().add(button_modify);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(473, 174, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		
		JLabel lblqq = new JLabel("\u5BA0\u7269\u7167\u7247");
		lblqq.setFont(new Font("����", Font.BOLD, 15));
		lblqq.setBounds(31, 129, 100, 18);
		getContentPane().add(lblqq);
		
		textField_photo = new JTextField();
		textField_photo.setColumns(10);
		textField_photo.setBounds(126, 126, 269, 24);
		textField_photo.setText("���ݿ���·������������");
		textField_photo.setForeground(Color.GRAY);
		textField_photo.setEditable(false);
		getContentPane().add(textField_photo);
		
		panel_img = new BackgroundPanel();
		panel_img.setBorder(new LineBorder(Color.GRAY, 1, true));
		panel_img.setBackground(SystemColor.inactiveCaption);
		panel_img.setBounds(420, 27, 125, 125);
		//�ȴ����ݿ�����ͼƬ����ͼƬ
		filepath1 = filepath1 + (pet.getId() + ".img");
		filepath2 = filepath2 + (pet.getId() + "_125_125.img");
		PetManager pm = new PetManager();
		try {
			pm.getPetPhoto(pet.getId(), filepath1);
		} catch (DbException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//ע��Ҫ��ʾͼƬ
		Image image = null;
		try {
			image = SwingOperation.getFixedIcon(filepath1,125,125).getImage();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getContentPane().add(panel_img);
		panel_img.setImage(image);
		//ͼƬ��ʾһ������ͼ�ν������֮�������ʾ�����򷵻ؿ�ָ���쳣
		
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon2.jpg"));
		
		//��ֹ�Ŵ�
		this.setResizable(false);
		//����رհ�ť����ֱ�ӹر���
		this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		//ע������������ͼƬҲҪ����
	    		File file = new File(filepath1);
	    		if(file.exists() && file.isFile() && file.isDirectory() == false) {   //��ɾ��Ŀ¼��ֻɾ���ļ�
	    			file.delete();
	    		}
	    		file = new File(filepath2);
	    		if(file.exists() && file.isFile() && file.isDirectory() == false) {   //��ɾ��Ŀ¼��ֻɾ���ļ�
	    			file.delete();
	    		}
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
			Pet p = new Pet();
			PetManager pm = new PetManager();
			//�����ǳ�
			if(this.textField_name.getForeground() != Color.GRAY) {
				p.setName(this.textField_name.getText());//��ɫ����
			}else {
				p.setName("");
			}
			//�������
			if(this.textField_othername.getForeground() != Color.GRAY) {
				p.setOthername(this.textField_othername.getText());//��ɫ����
			}else {
				p.setOthername("");
			}
			//������
			p.setId(pet.getId());
			//���˱��
			p.setUser_id(pet.getUser_id());
			//������Ƭ
			p.setPhoto(pet.getPhoto());
			
			try {
				pm.modifyPet(p);
				List<Pet> list = pm.searchPet(p.getId());
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(p.getId()) == true) {
						this.pet = list.get(i);break;//����׼ȷ��id��ֻ���ҵ�һ���������search�ҵ���Like�ģ����Ծ�����׼ȷ��
					}
				}
				p = this.pet;
				JOptionPane.showMessageDialog(null, "�޸ĳ���ɹ�", "��ȷ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				//���еĶ�Ҫ�޸�һ�飨�����˱�ź���Ƭ�����޸�֮�⣩
				super_Jrame.pets.get(this.selectedRow).setName(p.getName());
				super_Jrame.pets.get(this.selectedRow).setId(p.getId());
				super_Jrame.pets.get(this.selectedRow).setOthername(p.getOthername());
				super_Jrame.reloadTable(true);
				this.setVisible(false);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
			
		}else if(e.getSource() == this.button_exit) {
			File file = new File(filepath1);
    		if(file.exists() && file.isFile() && file.isDirectory() == false) {   //��ɾ��Ŀ¼��ֻɾ���ļ�
    			file.delete();
    		}
    		System.out.println(filepath2);
    		if(file.exists() && file.isFile() && file.isDirectory() == false) {   //��ɾ��Ŀ¼��ֻɾ���ļ�
    			file.delete();
    		}
			this.dispose();
		}
	}
	

}
