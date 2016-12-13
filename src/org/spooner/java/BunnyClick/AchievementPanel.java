package org.spooner.java.BunnyClick;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class AchievementPanel extends JLabel{
	//members
	private int index;
	//constructors
	public AchievementPanel(Achievement ach, int number){
		index=number;
		setToolTipText(getImageToolTipText(ach));
		setIcon(BunnyIO.getIcon(ach.getImagePath()));
		setEnabled(ach.isUnlocked());
	}
	//methods
	private String getImageToolTipText(Achievement ach){
		StringBuilder sb=new StringBuilder();
		sb.append("<html><b>");
		sb.append(BunnyConstants.PURPLE);
		sb.append(ach.getName());
		sb.append("</b></font><br><i>");
		sb.append(ach.getDesc());
		sb.append("</i><br><br>Multiplier: ");
		sb.append(BunnyConstants.RED);
		sb.append(BunnyClick.format(ach.getMultiplier()));
		sb.append("</font><br><b>");
		sb.append(getUnlockedString(ach.isUnlocked()));
		sb.append("</b></font></html>");
		return sb.toString();
	}
	private String getUnlockedString(boolean isUnlocked){
		return isUnlocked ? BunnyConstants.GREEN+"Unlocked!" : BunnyConstants.RED+"Locked";
	}
	public void update(){
		Achievement ach=Achievement.getAchievementAt(index);
		setEnabled(ach.isUnlocked());
		setToolTipText(getImageToolTipText(ach));
	}
}
