package org.spooner.java.BunnyClick;

public class BunnyStats {
	//members
	public enum ToolType{BUY, SELL};
	public static long totalMade;
	public static int goldenClicks;
	public static long totalClicked;
	public volatile static boolean isMusic=true;
	public static boolean isSound=true;
	//methods
	public static void init(String[] sa){
		if(sa==null) return;
		//sa[0] is numBunnies
		totalMade=Long.parseLong(sa[1]);
		goldenClicks=Integer.parseInt(sa[2]);
		totalClicked=Long.parseLong(sa[3]);
	}
	public static String[] generateArrayWithOthers() {
		return new String[]{
				Long.toString(BunnyClick.getBunnies()), 
				Long.toString(totalMade), 
				Integer.toString(goldenClicks),
				Long.toString(totalClicked)};
	}
}
