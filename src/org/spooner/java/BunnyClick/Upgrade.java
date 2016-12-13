package org.spooner.java.BunnyClick;

import java.util.ArrayList;

public class Upgrade {
	//static members
	private static ArrayList<Upgrade> miscUpgrades=new ArrayList<Upgrade>();
	//members
	public enum ApplyTo{BUILD, CUS, GOL, BPS, ACH};
	/*current types:
	 * BUILD (building) _/
	 * CUS (cursor) _/
	 * GOL (golden bunny) _/
	 * BPS (BPS) _/
	 * ACH (achievement) _/
	 */
	private String name;
	private String desc;
	private String imagePath;
	private long cost;
	private boolean isBase; // if true: BASE (modifies the base produced) else: MULTI (doubles/triples etc. production)
	private int value;
	private ApplyTo applyTo;
	private int meta;
	private boolean isBought;
	
	//constructors
	public Upgrade(String[] fields, boolean isBought){
		name=fields[0];
		desc=fields[1];
		imagePath=BunnyConstants.UPGRADES_PATH+fields[2];
		cost=Long.parseLong(fields[3]);
		isBase=Boolean.parseBoolean(fields[4]);
		value=Integer.parseInt(fields[5]);
		applyTo=ApplyTo.valueOf(fields[6]);
		meta=Integer.parseInt(fields[7]);
		this.isBought=isBought;
	}
	public Upgrade(String[] fields){
		this(fields, false);
	}
	//methods
	public String getName(){return name;}
	public String getDesc(){return desc;}
	public String getImagePath() {return imagePath;}
	public long getCost(){return cost;}
	public boolean isBase(){return isBase;}
	public int getValue(){return value;}
	public ApplyTo getApplyTo(){return applyTo;}
	public int getMeta(){return meta;}
	public boolean isBought(){return isBought;}
	public void buy(long numberBunnies){
		//if have enough to buy the next one
		if(numberBunnies-getCost()>=0 && !isBought){
			BunnyClick.buyUpgrade(getCost());
			isBought=true;
			BunnyClick.playSound("buy.wav");
		}
		else{
			BunnyClick.playSound("buzz.wav");
		}
	}
	
	public static void initUpgrades(String[][] array, String[] data){
		for(int i=0;i<array.length;i++){
			//if no save file, do not pass data (ie isBought)
			Upgrade u=data!=null ?
					new Upgrade(array[i], Boolean.parseBoolean(data[i])) : new Upgrade(array[i]);
			if(u.getApplyTo().equals(ApplyTo.BUILD)){
				Building.getBuildingAt(u.getMeta()).addUpgrade(u);
			}
			else{
				miscUpgrades.add(u);
			}
		}
	}
	public static String[] toStringArray(){
		ArrayList<String> array=new ArrayList<String>();
		//building type
		for(int i=0; i<Building.getNumBuildings();i++){
			Building b=Building.getBuildingAt(i);
			for(Upgrade u : b.getUpgrades()){
				array.add(Boolean.toString(u.isBought()));
			}
		}
		//other types
		for(Upgrade u : miscUpgrades){
			//array holds isBought
			array.add(Boolean.toString(u.isBought()));
		}
		String[] strings=new String[array.size()];
		array.toArray(strings);
		return strings;
	}
	public static int getNumUpgrades(){return miscUpgrades.size();}
	public static Upgrade getUpgradeAt(int i){return miscUpgrades.get(i);}
	public static float getPercentageUpgrade(ApplyTo app){
		float per=0.0f;
		for(Upgrade u : miscUpgrades){
			if(u.getApplyTo().equals(app) && u.isBought() && !u.isBase()){
				per+=((float) u.getValue()/100);
			}
		}
		return per;
	}
	public static int getAddUpgrade(ApplyTo app, int base){
		int add=0;
		for(Upgrade u : miscUpgrades){
			if(u.isBought() && u.getApplyTo().equals(app)){
				if(u.isBase()){
					add+=u.getValue();
				}
			}
		}
		return base+add;
	}
}
