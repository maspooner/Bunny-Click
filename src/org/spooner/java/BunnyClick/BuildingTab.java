package org.spooner.java.BunnyClick;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class BuildingTab extends JScrollPane{
	//members
	private JPanel panel;
	private BuildingPanel[] panels;
	//constructors
	public BuildingTab(){
		panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panels=new BuildingPanel[Building.getNumBuildings()];
		for(int i=0;i<Building.getNumBuildings();i++){
			Building b=Building.getBuildingAt(i);
			BuildingPanel bp=new BuildingPanel(b, i);
			panel.add(bp);
			panels[i]=bp;
		}
		setViewportView(panel);
		getVerticalScrollBar().setUnitIncrement(20);
	}
	//methods
	public void update(){
		for(BuildingPanel bp : panels){
			bp.update();
		}
	}
}
