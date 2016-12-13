package org.spooner.java.BunnyClick;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class BunnySidePanel extends JTabbedPane implements ChangeListener{
	//members
	private BuildingTab buildTab;
	private AchievementTab achTab;
	private UpgradeTab upTab;
	private StatsTab statsTab;
	//constructors
	public BunnySidePanel(){
		setFont(BunnyConstants.SIDE_FONT);
		setBackground(BunnyConstants.SCHEME_1);
		
		buildTab=new BuildingTab();
		achTab=new AchievementTab();
		upTab=new UpgradeTab();
		statsTab=new StatsTab();
		addTab("Buildings", buildTab);
		addTab("Achievements", achTab);
		addTab("Upgrades", upTab);
		addTab("Stats", statsTab);
	}
	//methods
	public void update(){
		switch(getSelectedIndex()){
			case 0: buildTab.update(); break;
			case 1: achTab.update(); break;
			case 2: upTab.update(); break;
			case 3: statsTab.update(); break;
		}
	}
	@Override
	public void stateChanged(ChangeEvent e) {update();}
}
