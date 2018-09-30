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
	
	//设置JlabelText自动换行
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
	
	//将文本框的时间换成TimeStamp时间
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
			throw new BaseException("日期类型必须是yyyy-MM-dd HH:mm:ss");
		}
		return ts;
	}
	
	//将当前时间换成TimeStamp时间
	public static Timestamp getCurrentTime(long time){
		Timestamp ts = new Timestamp(time);
		return ts;	
	}
	
	//按输入的任意宽高改变图片的大小
	//@param filePath,@param width,@param height,@return,@throws Exception
	public static ImageIcon getFixedIcon(String filePath, int width, int height) throws Exception{
		File f = new File(filePath); 
		BufferedImage bi = ImageIO.read(f);
		double wRatio = (new Integer(width)).doubleValue() / bi.getWidth(); //宽度的比例
		double hRatio = (new Integer(height)).doubleValue() / bi.getHeight(); //高度的比例
		Image image = bi.getScaledInstance(width,height,Image.SCALE_SMOOTH); //设置图像的缩放大小
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio,hRatio),null);   //设置图像的缩放比例
		image = op.filter(bi,null);
		int lastLength = filePath.lastIndexOf(".");   
		String subFilePath = filePath.substring(0,lastLength);  //得到图片输出路径
	    String fileType = filePath.substring(lastLength);  //图片类型
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
