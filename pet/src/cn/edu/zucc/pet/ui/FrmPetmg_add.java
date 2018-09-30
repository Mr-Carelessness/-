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
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.SwingOperation;
import cn.edu.zucc.pet.model.Pet;
import cn.edu.zucc.pet.util.BaseException;

public class FrmPetmg_add extends JFrame implements ActionListener {
	private JTextField textField_name;
	private JTextField textField_othname;
	private JTextField textField_usrid;
	private FrmPetmg super_Jrame = null; 
	JButton button_add = new JButton("\u6DFB\u52A0\u5BA0\u7269");
	JButton button_exit = new JButton("\u9000\u51FA");
	JButton button_choose = new JButton("\u9009\u62E9\u7167\u7247");
	BackgroundPanel panel_img = new BackgroundPanel();
	Pet pet = null;
	private JTextField txtjpggif;
	
	public FrmPetmg_add(FrmPetmg frmPetmg) {
		this.super_Jrame = frmPetmg;
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
		
		textField_othname = new JTextField();
		textField_othname.setColumns(10);
		textField_othname.setBounds(126, 58, 269, 24);
		textField_othname.setText("ѡ��");
		textField_othname.setForeground(Color.GRAY);	
		textField_othname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_othname.getForeground() == Color.GRAY) {
					textField_othname.setText("");
					textField_othname.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_othname.getText().length() == 0 || textField_othname.getText() == null ) {    //����û����
					textField_othname.setText("ѡ��");
					textField_othname.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_othname);
		
		textField_usrid = new JTextField();
		textField_usrid.setColumns(10);
		textField_usrid.setBounds(126, 92, 269, 24);
		textField_usrid.setText("������˱����ϵͳ�б������");
		textField_usrid.setForeground(Color.GRAY);
		textField_usrid.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textField_usrid.getForeground() == Color.GRAY) {
					textField_usrid.setText("");
					textField_usrid.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textField_usrid.getText().length() == 0 || textField_usrid.getText() == null) {    //����û����
					textField_usrid.setText("������˱����ϵͳ�б������");
					textField_usrid.setForeground(Color.GRAY);					
				}
			}
		});
		getContentPane().add(textField_usrid);
		
		button_add.setBackground(Color.WHITE);
		button_add.setBounds(364, 174, 93, 27);
		button_add.addActionListener(this);
		getContentPane().add(button_add);
		
		button_exit.setBackground(Color.WHITE);
		button_exit.setBounds(471, 174, 72, 27);
		button_exit.addActionListener(this);
		getContentPane().add(button_exit);
		
		JLabel lblqq = new JLabel("\u5BA0\u7269\u7167\u7247");
		lblqq.setFont(new Font("����", Font.BOLD, 15));
		lblqq.setBounds(31, 129, 100, 18);
		getContentPane().add(lblqq);
		
		panel_img.setBackground(SystemColor.inactiveCaption);
		panel_img.setBorder(new LineBorder(Color.GRAY, 1, true));
		panel_img.setBounds(418, 25, 125, 125);
		getContentPane().add(panel_img);
		
		txtjpggif = new JTextField();
		txtjpggif.setText("\u5FC5\u586B\uFF0C\u4EC5\u652F\u6301jpg\u548Cgif");
		txtjpggif.setForeground(Color.GRAY);
		txtjpggif.setColumns(10);
		txtjpggif.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtjpggif.getForeground() == Color.GRAY) {
					txtjpggif.setText("");
					txtjpggif.setForeground(Color.BLACK);					
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtjpggif.getText().length() == 0 || textField_usrid.getText() == null) {    //����û����
					txtjpggif.setText("�����֧��jpg��gif");
					txtjpggif.setForeground(Color.GRAY);					
				}
			}
		});
		txtjpggif.setBounds(126, 126, 169, 24);
		
		getContentPane().add(txtjpggif);
		
		button_choose.setFont(new Font("����", Font.PLAIN, 14));
		button_choose.setBackground(new Color(255, 255, 225));
		button_choose.setBounds(302, 125, 93, 27);
		button_choose.addActionListener(this);
		getContentPane().add(button_choose);
		setBackground(SystemColor.inactiveCaption);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resource/����ͼƬ/icon2.jpg"));
		setTitle("\u6DFB\u52A0\u5BA0\u7269");
		
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
		if(e.getSource() == this.button_add) {
			Pet p = new Pet();
			PetManager pm = new PetManager();
			String maxId;
			try {
				//������
				maxId = pm.searchMaxPetId();
				String id = "pet100001";
				if(maxId != null && maxId.length() > 0)
				{
					int max = Integer.parseInt(maxId.substring(3));
					id = "pet" + String.valueOf(max+1);
				}				
				p.setId(id);
			} catch (BaseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			//�û�����
			if(this.textField_name.getForeground() != Color.GRAY) {
				p.setName(this.textField_name.getText());//��ɫ����
			}else {
				p.setName("");
			}
			//�ֻ�����
			if(this.textField_othname.getForeground() != Color.GRAY) {
				p.setOthername(this.textField_othname.getText());//��ɫ����
			}else {
				p.setOthername("");
			}
			//�û����
			if(this.textField_usrid.getForeground() != Color.GRAY) {
				p.setUser_id(this.textField_usrid.getText());//��ɫ����
			}else {
				p.setUser_id("");
			}
			//��ó�����Ƭ·��
			String filepath = "";
			if(this.txtjpggif.getForeground() != Color.GRAY) {
				filepath = this.txtjpggif.getText();
			}
			
			try {
				pm.addPet(p, filepath);
				List<Pet> list = pm.searchPet(p.getId());
				for(int i = 0;i < list.size();i++) {
					if(list.get(i).getId().equals(p.getId()) == true) {
						this.pet = list.get(i);break;//����׼ȷ��id��ֻ���ҵ�һ���������search�ҵ���Like�ģ����Ծ�����׼ȷ��
					}
				}
				JOptionPane.showMessageDialog(null, "��������ɹ���������Ϊ"+p.getId(), "��ȷ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				super_Jrame.addUser(this.pet);
				super_Jrame.reloadTable(true);
				this.setVisible(false);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(),"�ļ�·������",JOptionPane.ERROR_MESSAGE);
			}
			
		}else if(e.getSource() == this.button_exit) {
			this.dispose();
		}else if(e.getSource() == this.button_choose) {
			JFileChooser chooser = new JFileChooser();//ͨ������JFileChooser�������ļ���ѡȡ�ļ�����
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images","jpg","gif");
			//�����ļ�����
			chooser.setFileFilter(filter);
			//���ļ�ѡ�������
			int returnVal = chooser.showOpenDialog(new JPanel());
			//�����ļ��������֣���������ļ���
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				this.txtjpggif.setText(chooser.getSelectedFile().getAbsolutePath());
				this.txtjpggif.setForeground(Color.BLACK);
				//System.out.println("�ļ�·����"+chooser.getSelectedFile().getPath());
				Image image = null;
				try {
					image = SwingOperation.getFixedIcon(chooser.getSelectedFile().getPath(),125,125).getImage();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.panel_img.setImage(image);
				this.panel_img.paint(this.panel_img.getGraphics());
			}
			
		}
	}
}
