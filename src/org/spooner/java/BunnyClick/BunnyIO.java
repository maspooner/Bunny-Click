package org.spooner.java.BunnyClick;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class BunnyIO{
	//members
	private static final File LOCATION=new File("BCsave.txt");
	private static final String SEPARATOR_REGEX="[\\^&\\^]+";
	private static final String TYPE_REGEX="[%*%]+";
	private static final String SEPARATOR_SYM="^&^";
	private static final String TYPE_SYM="%*%";
	
	//methods
	public static Image readPicture(String fileName){
		try {
			if(BunnyClick.IS_TEST){
				return ImageIO.read(new File(fileName));
			}
			else{
				return ImageIO.read(ClassLoader.class.getResourceAsStream("/" + fileName));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(5);
		}
		return null;
	}
	
	public static Icon getIcon(String fileName){
		return new ImageIcon(readPicture(fileName));
	}
	public static void save(String[][] data){
		String dataStr=toString(data);
		try{
			FileOutputStream fos=new FileOutputStream(LOCATION);
			fos.write(dataStr.getBytes());
			fos.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(6);
		}
	}
	public static String[][] load(){
		byte[] data=null;
		try{
			FileInputStream fis=new FileInputStream(LOCATION);
			data=new byte[fis.available()];
			fis.read(data);
			fis.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(8);
		}
		//return an array, split by regex
		String dataStr=new String(data);
		String[] typeArray=dataStr.split(TYPE_REGEX);
		String[] other=typeArray[0].split(SEPARATOR_REGEX);
		String[] bui=typeArray[1].split(SEPARATOR_REGEX);
		String[] ach=typeArray[2].split(SEPARATOR_REGEX);
		String[] up=typeArray[3].split(SEPARATOR_REGEX);
		return new String[][]{other, bui, ach, up};
	}
	private static String toString(String[][] array){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<array.length;i++){
			for(int j=0; j<array[i].length;j++){
				sb.append(array[i][j]);
				//do except for last element in array
				if(j<array[i].length-1)
					sb.append(SEPARATOR_SYM);
			}
			//do except for last element in array
			if(i<array.length-1)
				sb.append(TYPE_SYM);
		}
		return sb.toString();
	}
	public synchronized static boolean isFile(){
		return LOCATION.exists();
	}
	
	public static Font getFont(String loc, int size, int style){
		try {
			Font f;
			if(BunnyClick.IS_TEST){
				f = Font.createFont(Font.TRUETYPE_FONT, new File(loc));
			}
			else{
				f = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.class.getResourceAsStream("/" + loc));
			}
			return f.deriveFont(style, size);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(11);
		}
		return null;
	}
}
