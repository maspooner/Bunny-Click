package org.spooner.java.BunnyClick;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class AchievementTab extends JScrollPane{
	//members
	private JPanel panel;
	private AchievementPanel[] panels;
	//constructors
	public AchievementTab(){
		panel=new JPanel();
		panel.setLayout(new GridLayout(0, 3, 10, 10));
		panel.setBackground(BunnyConstants.SCHEME_5);
		panels=new AchievementPanel[Achievement.getNumAchievements()];
		for(int i=0;i<Achievement.getNumAchievements();i++){
			Achievement ach=Achievement.getAchievementAt(i);
			AchievementPanel ap=new AchievementPanel(ach, i);
			panel.add(ap);
			panels[i]=ap;
		}
		setViewportView(panel);
		getVerticalScrollBar().setUnitIncrement(20);
	}
	//methods
	public void update(){
		for(AchievementPanel ap : panels){
			ap.update();
		}
	}
}
