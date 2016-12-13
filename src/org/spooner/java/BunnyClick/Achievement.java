package org.spooner.java.BunnyClick;

public class Achievement {
	/*current types:
	 *  CLICK (bunnies generated when clicked) 
	 *  NUMBER (number of owned buildings of a cetain type)
	 *  TOTAL (total generated amount of bunnies)
	 *  BPS (bps)
	 *  GOLD (golden bunny clicks)
	 */
	//static members
	private static Achievement[] achievements;
	//members
	public enum Type{CLICK, NUMBER, TOTAL, BPS, GOLD};
	private String name;
	private String desc;
	private String imagePath;
	private long value;
	private int data;
	private float multiplier;
	private Type type;
	private boolean isUnlocked;
	//constructors
	public Achievement(String[] fields, boolean isUnlocked){
		name=fields[0];
		desc=fields[1];
		imagePath=BunnyConstants.ACHIEVEMENT_PATH+fields[2];
		value=Long.parseLong(fields[3]);
		data=Integer.parseInt(fields[4]);
		multiplier=Float.parseFloat(fields[5]);
		type=Type.valueOf(fields[6]);
		this.isUnlocked=isUnlocked;
	}
	public Achievement(String[] fields){
		this(fields, false);
	}
	//methods
	public String getName(){return name;}
	public String getDesc() {return desc;}
	public String getImagePath() {return imagePath;}
	public int getData(){return data;}
	public long getValue() {return value;}
	public float getMultiplier() {return multiplier;}
	public Type getType(){return type;}
	public boolean isUnlocked() {return isUnlocked;}
	public void unlock(){isUnlocked=true;}
	//static methods
	public static void initAchievements(String[][] array, String[] data){
		achievements=new Achievement[array.length];
		for(int i=0;i<achievements.length;i++){
			//if no save file, do not pass data (ie isUnlocked)
			achievements[i]=data!=null ?
					new Achievement(array[i], Boolean.parseBoolean(data[i])) : new Achievement(array[i]);
		}
	}
	public static void checkType(Type type, long count){
		for(Achievement ach : achievements){
			if(ach.getType()==type){
				if(!ach.isUnlocked()){
					if(ach.getValue()<=count){
						ach.unlock();
						BunnyClick.playSound("ach.wav");
						BunnyClick.consoleWrite(ach.name+" is unlocked!");
					}
				}
			}
		}
	}
	public static void checkType(Type type, int count, int data){
		for(Achievement ach : achievements){
			if(ach.getType()==type){
				if(!ach.isUnlocked()){
					if(ach.getValue()<=count && data==ach.getData()){
						ach.unlock();
						BunnyClick.playSound("ach.wav");
						BunnyClick.consoleWrite(ach.name+" is unlocked!");
					}
				}
			}
		}
	}
	public static float getTotalMultiplier(){
		float multi=1.0f;
		for(Achievement ach : achievements){
			if(ach.isUnlocked())
				multi+=ach.getMultiplier();
		}
		return multi;
	}
	public static Achievement[] getAchievements(){return achievements;}
	public static Achievement getAchievementAt(int i){return achievements[i];}
	public static int getNumAchievements(){return achievements.length;}
	public static String[] toStringArray(){
		String[] arr=new String[achievements.length];
		int ind=0;
		for(Achievement ach : achievements){
			//array holds isunlocked
			arr[ind]=Boolean.toString(ach.isUnlocked());
			ind++;
		}
		return arr;
	}
	public static String getMultiplierString(){
		int percent=(int) (getTotalMultiplier() * 100) - 100;
		return "Achievement Multiplier: "+percent+"%";
	}
}
