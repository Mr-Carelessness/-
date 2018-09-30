package cn.edu.zucc.pet.control;

import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cn.edu.zucc.pet.util.BaseException;

public class SwingOperation {
	
	//����JlabelText�Զ�����
	public static void JlabelSetText(JLabel jLabel, String longString) throws InterruptedException {
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longString.toCharArray();
        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length())break;
                if (fontMetrics.charsWidth(chars, start, len) 
                      > jLabel.getWidth()) {
                    break;
                }
            }
            builder.append(chars, start, len-1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length()-start);
        builder.append("</html>");
        jLabel.setText(builder.toString());
    }
	
	//���ı����ʱ�任��TimeStampʱ��
	public static Timestamp ChangeDateTime(String dat) throws InterruptedException, BaseException{
		Date d = null;
		Timestamp ts = null;
		Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d = (Date) f.parseObject(dat);
			ts = new Timestamp(d.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BaseException("�������ͱ�����yyyy-MM-dd HH:mm:ss");
		}
		return ts;
	}
	
	//����ǰʱ�任��TimeStampʱ��
	public static Timestamp getCurrentTime(long time){
		Timestamp ts = new Timestamp(time);
		return ts;	
	}
	
	//������������߸ı�ͼƬ�Ĵ�С
	//@param filePath,@param width,@param height,@return,@throws Exception
	public static ImageIcon getFixedIcon(String filePath, int width, int height) throws Exception{
		File f = new File(filePath); 
		BufferedImage bi = ImageIO.read(f);
		double wRatio = (new Integer(width)).doubleValue() / bi.getWidth(); //��ȵı���
		double hRatio = (new Integer(height)).doubleValue() / bi.getHeight(); //�߶ȵı���
		Image image = bi.getScaledInstance(width,height,Image.SCALE_SMOOTH); //����ͼ������Ŵ�С
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio,hRatio),null);   //����ͼ������ű���
		image = op.filter(bi,null);
		int lastLength = filePath.lastIndexOf(".");   
		String subFilePath = filePath.substring(0,lastLength);  //�õ�ͼƬ���·��
	    String fileType = filePath.substring(lastLength);  //ͼƬ����
	    File zoomFile = new File(subFilePath + fileType);
	    ImageIcon ret = null;
		try
	    {
	     ImageIO.write((BufferedImage)image, "jpg", zoomFile); 
	     ret = new ImageIcon(zoomFile.getPath()); 
	    }
	    catch (Exception e) 
	    {
	     e.printStackTrace();
	    } 
	   return ret;
	}
	
}
