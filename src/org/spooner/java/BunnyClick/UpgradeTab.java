package org.spooner.java.BunnyClick;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class UpgradeTab extends JScrollPane{
	//members
	private JPanel panel;
	private UpgradePanel[] panels;
	//constructors
	public UpgradeTab(){
		panel=new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(BunnyConstants.SCHEME_5);
		addPanels();
		
		setViewportView(panel);
		getVerticalScrollBar().setUnitIncrement(20);
	}
	//methods
	private void addPanels(){
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=-1;
		c.gridy=-1;
		c.weightx=0.1;
		c.weighty=0.1;
		//holds building and misc upgrades
		panels=new UpgradePanel[Building.getTotalUpgradeNum()+Upgrade.getNumUpgrades()];
		c.gridy++;
		//first buildings
		int iter=0;
		for(int i=0;i<Building.getNumBuildings();i++){
			c.gridx=-1;
			c.gridy++;
			Building b=Building.getBuildingAt(i);
			//add label
			c.fill=GridBagConstraints.HORIZONTAL;
			c.anchor=GridBagConstraints.WEST;
			c.gridwidth=10;
			panel.add(getJLabel(b.getName()), c);
			c.gridy++;
			c.fill=GridBagConstraints.NONE;
			c.anchor=GridBagConstraints.CENTER;
			c.gridwidth=1;
			for(int k=0;k<b.getUpgrades().size(); k++){
				c.gridx++;
				UpgradePanel up=new UpgradePanel(b.getUpgradeAt(k), k, i);
				panel.add(up, c);
				panels[iter]=up;
				iter++;
			}
		}
		//then others
		Upgrade.ApplyTo lastApplyTo=null;
		c.gridx=-1;
		for(int j=0;j<Upgrade.getNumUpgrades();j++){
			Upgrade u=Upgrade.getUpgradeAt(j);
			if(lastApplyTo==null || !lastApplyTo.equals(u.getApplyTo())){
				//change row
				lastApplyTo=u.getApplyTo();
				c.gridx=-1;
				c.gridy++;
				//add label
				c.fill=GridBagConstraints.HORIZONTAL;
				c.anchor=GridBagConstraints.WEST;
				c.gridwidth=10;
				panel.add(getJLabel(getString(u.getApplyTo())), c);
				c.gridy++;
				c.fill=GridBagConstraints.NONE;
				c.anchor=GridBagConstraints.CENTER;
				c.gridwidth=1;
			}
			c.gridx++;
			UpgradePanel up=new UpgradePanel(u, j, -1);
			panel.add(up, c);
			panels[iter]=up;
			iter++;
		}
	}
	private JLabel getJLabel(String text){
		JLabel jl=new JLabel(text);
		jl.setFont(BunnyConstants.SIDE_FONT);
		jl.setOpaque(true);
		jl.setBackground(BunnyConstants.SCHEME_1);
		return jl;
	}
	private String getString(Upgrade.ApplyTo app){
		switch(app){
		case BUILD: return "Buildings:"; 
		case CUS: return "Cursor:";
		case GOL: return "Golden Bunny:";
		case BPS: return "Bunnies:";
		case ACH: return "Drinks:";
		default: return null;
		}
	}
	public void update(){
		for(UpgradePanel up : panels){
			up.update();
		}
	}
}
