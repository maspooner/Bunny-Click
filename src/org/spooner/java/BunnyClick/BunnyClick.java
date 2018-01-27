package org.spooner.java.BunnyClick;

import java.text.NumberFormat;

/*
 * BunnyClick
 * Made by Matt Spooner
 * 
 * Date Created: Nov 10 2013
 * Last Edited: Jan 1 2014
 * 
 * Error Codes
 * 1 - Sound Play Error
 * 2 - XML Parse error
 * 5 - Image read error
 * 6 - file write error
 * 7 - UI thread interupted error
 * 8 - file read error
 * 11 - font read error
 * 14 - BPS thread interupted error
 * 15 - Save thread interrupted error
 * 16 - TOD thread interrupted error
 * 17 - BunnyRunway thread interrupted error
 * 
 */

public class BunnyClick {
	public static final boolean IS_TEST = false;
	//members
	private static long numberBunnies;
	private static Thread incrementer;
	private static Thread saver;
	private static boolean recalculate;
	private static long BPS;
	private static BunnyPlayer player;
	private static BunnyFrame frame;
	
	//methods
	public static void main(String[] args) {
		recalculate=true;
		BPS=0L;
		boolean isFile=BunnyIO.isFile();
		String[][] data=null;
		if(isFile)
			data=BunnyIO.load();
		if(data!=null) numberBunnies=Long.parseLong(data[0][0]);
		BunnyStats.init(data==null?null:data[0]);
		initBuildings(data==null?null:data[1]);
		initAchievements(data==null?null:data[2]);
		initBonuses();
		initUpgrades(data==null?null:data[3]);
		startThreads();
		frame=new BunnyFrame();
		player=new BunnyPlayer();
		BunnyIO.save(getData());
		player.play();
	}
	public static void consoleWrite(String text){frame.consoleWrite(text);}
	public synchronized static void playSound(String fileName){if(BunnyStats.isSound) player.playSound(fileName);}
	public synchronized static void save(){BunnyIO.save(getData());}
	public synchronized static long getBunnies(){return numberBunnies;}
	public synchronized static long getBPS(){return BPS;}
	public static void click(){
		long click=getBPC();
		addBunnies(click);
		BunnyStats.totalClicked+=click;
		Achievement.checkType(Achievement.Type.CLICK, BunnyStats.totalClicked);
	}
	public static void doRecalculation(){recalculate=true;}
	private static void doBPS(){
		if(recalculate){
			recalculate=false;
			BPS=calculateTotalBPS();
		}
		addBunnies(BPS);
	}
	public synchronized static long getBPC(){
		return (long) (BunnyConstants.STARTING_CLICK + 
				BPS * Upgrade.getPercentageUpgrade(Upgrade.ApplyTo.CUS));
	}
	private synchronized static void addBunnies(long l){
		numberBunnies+=l;
		BunnyStats.totalMade+=l;
		Achievement.checkType(Achievement.Type.TOTAL, BunnyStats.totalMade);
	}
	private static long calculateTotalBPS(){
		long total=0;
		//add base BPS to total
		total+=Building.getTotalBPS();
		//get achievement multi mult by upgrades
		float ach=Achievement.getTotalMultiplier() * 
				(Upgrade.getPercentageUpgrade(Upgrade.ApplyTo.ACH) + 1);
		//add bonus and BPS upgrade to complete multiplier
		float multi=Bonus.getBonus().getMultiplier() + ach + Upgrade.getPercentageUpgrade(Upgrade.ApplyTo.BPS);
		total=(long) (total * multi);
		Achievement.checkType(Achievement.Type.BPS, total);
		return total;
	}
	
	private static void initBuildings(String[] data){
		String[][] parsedBuildings=BunnyXML.parse("buildings.xml", "building", 7);
		Building.initBuildings(parsedBuildings, data);
	}
	private static void initAchievements(String[] data) {
		String[][] parsedAchievements=BunnyXML.parse("achievements.xml", "achievement", 7);
		Achievement.initAchievements(parsedAchievements, data);
	}
	private static void initBonuses() {
		String[][] parsedBonuses=BunnyXML.parse("bonuses.xml", "bonus", 4);
		Bonus.initBonuses(parsedBonuses);
	}
	private static void initUpgrades(String[] data) {
		String[][] parsedUpgrades=BunnyXML.parse("upgrades.xml", "upgrade", 8);
		Upgrade.initUpgrades(parsedUpgrades, data);
	}
	private static void startThreads(){
		incrementer=new Thread(new Runnable() {
			public void run() {
				while(true){
					try{
						Thread.sleep(BunnyConstants.BPS_DELAY);
						doBPS();
					}
					catch (Exception e) {
						e.printStackTrace();
						System.exit(14);
					}
				}
			}
		});
		incrementer.start();
		saver=new Thread(new Runnable(){
			public void run() {
				while(true){
					try{
						Thread.sleep(BunnyConstants.SAVE_DELAY);
						BunnyIO.save(BunnyClick.getData());
						consoleWrite("Game Saved!");
					}
					catch(Exception e){
						e.printStackTrace();
						System.exit(15);
					}
				}
			}
		});
		saver.start();
	}
	public static void buy(int i){
		Building b=Building.getBuildingAt(i);
		//if have enough to buy the next one
		if(numberBunnies-b.getCost(b.getNumber()+1)>=0){
			numberBunnies-=b.getCost(b.getNumber()+1);
			b.buy();
			playSound("buy.wav");
		}
		else{
			playSound("buzz.wav");
		}
	}
	public static void sell(int i){
		Building b=Building.getBuildingAt(i);
		if(b.getNumber()>0){
			numberBunnies+=b.getSell();
			b.sell();
			playSound("sell.wav");
		}
		else{
			playSound("buzz.wav");
		}
	}
	public static String[][] getData(){
		String[] other=BunnyStats.generateArrayWithOthers();
		String[] bui=Building.toStringArray();
		String[] ach=Achievement.toStringArray();
		String[] up=Upgrade.toStringArray();
		return new String[][]{other, bui, ach, up};
	}
	public static String format(long num){
		return NumberFormat.getInstance().format(num);
	}
	public static String format(float num){
		int percent=(int) (num * 100);
		return percent+"%";
	}
	public static int getGoldenChance(){
		//gets just chance (not multiply)
		return Upgrade.getAddUpgrade(Upgrade.ApplyTo.GOL, BunnyConstants.STARTING_CHANCE);
	}
	public static void doBonus(){
		float per=BunnyConstants.STARTING_GOLD_BUNNIES 
				+ Upgrade.getPercentageUpgrade(Upgrade.ApplyTo.GOL);
		//add a percent of BPS to bunnies
		long gain=(long) (per * getBPS() + 777);
		consoleWrite("Lucky! Got "+format(gain)+" bunnies!");
		addBunnies(gain);
		BunnyStats.goldenClicks++;
		Achievement.checkType(Achievement.Type.GOLD, BunnyStats.goldenClicks);
	}
	public static void buyUpgrade(long cost){
		numberBunnies-=cost;
		doRecalculation();
	}
	public static String getBunnyString(){return "Bunnies: "+format(numberBunnies);}
	public static String getBPSString(){return format(BPS)+" BPS";}
}
