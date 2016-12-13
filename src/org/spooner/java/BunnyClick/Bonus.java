package org.spooner.java.BunnyClick;

import java.util.Calendar;

public class Bonus implements Runnable{
	//static members
	private static Thread changer;
	private static int currentHour;
	private static Bonus[] bonuses;
	//members
	private String name;
	private String desc;
	private String TODString;
	private float multiplier;
	//constructors
	public Bonus(String[] fields){
		name=fields[0];
		desc=fields[1];
		TODString=fields[2];
		multiplier=Float.parseFloat(fields[3]);
		if(changer==null){
			currentHour=getTOD();
			changer=new Thread(this);
			changer.start();
		}
	}
	//methods
	public String getName(){return name;}
	public String getDesc(){return desc;}
	public float getMultiplier(){return multiplier;}
	public String getTODString(){return TODString;}
	
	public static int getTOD(){
		Calendar c=Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}
	public static void initBonuses(String[][] array){
		bonuses=new Bonus[array.length];
		for(int i=0;i<bonuses.length;i++){
			bonuses[i]=new Bonus(array[i]);
		}
	}
	public static Bonus getBonus(){
		//if it's midnight, return 24th index, not 0-1 -> -1
		if(currentHour==0) return bonuses[23];
		//-1, as 24 is not a valid index
		return bonuses[currentHour-1];
	}
	public static String getToolTipText(){
		Bonus b=getBonus();
		StringBuilder sb=new StringBuilder();
		sb.append("<html><b>Current Time of Day Bonus: </b>");
		sb.append(BunnyConstants.PURPLE);
		sb.append(b.getName());
		sb.append("</font><br><i>");
		sb.append(b.getDesc());
		sb.append("</i><br><br>");
		sb.append("Bonus Multiplier: ");
		sb.append(BunnyConstants.RED);
		sb.append(BunnyClick.format(b.getMultiplier()));
		sb.append("</font><br>Time of Day: ");
		sb.append(BunnyConstants.RED);
		sb.append(b.getTODString());
		sb.append("</font>");
		return sb.toString();
	}
	@Override
	public void run() {
		while(true){
			try {
				if(currentHour!=getTOD()){
					//switch bonus
					currentHour=getTOD();
					//recalculate BPS
					BunnyClick.doRecalculation();
				}
				Thread.sleep(BunnyConstants.TOD_DELAY);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(16);
			}
		}
	}
}
