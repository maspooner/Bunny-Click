package org.spooner.java.BunnyClick;

import java.util.ArrayList;

public class Building {
	//static members
	private static Building[] buildings;
	//members
	private String name;
	private String desc;
	private String imagePath;
	private int BPS;
	private int number;
	private long baseCost;
	private ArrayList<Upgrade> upgrades=new ArrayList<Upgrade>();
	//constructors
	public Building(String[] fields, int number){
		name=fields[0];
		desc=fields[1];
		imagePath=BunnyConstants.BUILDING_PATH+fields[2];
		BPS=Integer.parseInt(fields[3]);
		baseCost=Long.parseLong(fields[4]);
		this.number=number;
	}
	public Building(String[] fields){
		this(fields, 0);
	}
	//methods
	public void addUpgrade(Upgrade u){
		upgrades.add(u);
	}
	public long getCost(int n){
		return Math.round((baseCost * (Math.pow(1.15, n)-1)) / 0.15);
	}
	public long getSell(){
		return (long) (getCost(number)/2);
	}
	public String getName(){return name;}
	public String getDesc(){return desc;}
	public int getBaseBPS(){return BPS;}
	public long getBPS(){
		int add=0;
		int mult=1;
		for(Upgrade u : upgrades){
			if(u.isBought()){
				if(u.isBase()){
					add+=u.getValue();
				}
				else{
					mult*=u.getValue();
				}
			}
		}
		return mult * (BPS+add);
	}
	public int getNumber(){return number;}
	public String getImagePath(){return imagePath;}
	public ArrayList<Upgrade> getUpgrades(){return upgrades;}
	public Upgrade getUpgradeAt(int i){return upgrades.get(i);}
	public void buy(){number++;}
	public void sell(){number--;}
	//static methods
	public static Building getBuildingAt(int i){return buildings[i];}
	public static int getNumBuildings(){return buildings.length;}
	public static long getTotalBPS(){
		long total=0;
		for(Building b:buildings){
			total+=b.getBPS()*b.getNumber();
		}
		return total;
	}
	public static void initBuildings(String[][] array, String[] data){
		buildings=new Building[array.length];
		for(int i=0;i<Building.getNumBuildings();i++){
			//if no save file, do not pass data (ie number)
			buildings[i]=data!=null ? 
					new Building(array[i], Integer.parseInt(data[i])) : new Building(array[i]);
		}
	}
	public static String[] toStringArray(){
		String[] arr=new String[getNumBuildings()];
		int ind=0;
		for(Building b : buildings){
			//array holds number
			arr[ind]=Integer.toString(b.getNumber());
			ind++;
		}
		return arr;
	}
	public static int getTotalUpgradeNum(){
		int num=0;
		for(Building b : buildings){
			num+=b.getUpgrades().size();
		}
		return num;
	}
}
