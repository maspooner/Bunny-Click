package org.spooner.java.BunnyClick;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class UpgradePanel extends JLabel implements MouseListener{
	//members
	private int index;
	private int meta;
	//constructors
	public UpgradePanel(Upgrade u, int index, int meta){
		//meta of -1 means not a building
		this.index=index;
		this.meta=meta;
		setToolTipText(getImageToolTipText(u));
		setIcon(BunnyIO.getIcon(u.getImagePath()));
		setEnabled(u.isBought());
		addMouseListener(this);
	}
	//methods
	private String getImageToolTipText(Upgrade u){
		StringBuilder sb=new StringBuilder();
		sb.append("<html><b>");
		sb.append(BunnyConstants.PURPLE);
		sb.append(u.getName());
		sb.append("</b></font><br><i>");
		sb.append(u.getDesc());
		sb.append("</i><br><br>");
		sb.append(getDescriptorString(u));
		sb.append(getBoughtString(u.isBought()));
		sb.append("</b></font><br>Cost: ");
		sb.append(BunnyConstants.RED);
		sb.append(BunnyClick.format(u.getCost()));
		sb.append("</font></html>");
		return sb.toString();
	}
	public void update(){
		Upgrade u=getUpgrade();
		setEnabled(u.isBought());
		setToolTipText(getImageToolTipText(u));
	}
	private Upgrade getUpgrade(){
		if(meta!=-1){
			//is building
			return Building.getBuildingAt(meta).getUpgradeAt(index);
		}
		else{
			//not building
			return Upgrade.getUpgradeAt(index);
		}
	}
	private String getDescriptorString(Upgrade u){
		//what a monster method
		StringBuilder sb=new StringBuilder();
		sb.append(BunnyConstants.GREEN);
		switch(u.getApplyTo()){
		case BUILD:
			if(u.isBase()){
				sb.append("Adds</font> ");
				sb.append(BunnyConstants.RED);
				sb.append(BunnyClick.format(u.getValue()));
				sb.append("</font> to the ");
			}
			else{
				sb.append("Multiplies</b></font> by ");
				sb.append(BunnyConstants.RED);
				sb.append(u.getValue());
				sb.append("</font> the ");
			}
			sb.append("BPS of ");
			sb.append(BunnyConstants.PURPLE);
			sb.append(Building.getBuildingAt(meta).getName());
			break;
		case CUS:
			sb.append("Recieve </font> an additional ");
			sb.append(BunnyConstants.RED);
			sb.append(u.getValue());
			sb.append("</font>% of your BPS after every ");
			sb.append(BunnyConstants.PURPLE);
			sb.append("Click");
			break;
		case GOL:
			if(u.isBase()){
				sb.append("Increases</font> your chances by ");
				sb.append(BunnyConstants.RED);
				sb.append(BunnyClick.format(u.getValue()/(float)BunnyConstants.TOTAL_CHANCE));
				sb.append("</font> for a ");
				sb.append(BunnyConstants.PURPLE);
				sb.append("Golden Bunny </font>");
				sb.append("to appear");
			}
			else{
				sb.append("Increases</font> by ");
				sb.append(BunnyConstants.RED);
				sb.append(u.getValue());
				sb.append("</font>% the amount of bunnies gained from ");
				sb.append(BunnyConstants.PURPLE);
				sb.append("Golden Bunnies");
			}
			break;
		case BPS:
			sb.append("Adds</font> an extra ");
			sb.append(BunnyConstants.RED);
			sb.append(u.getValue());
			sb.append("</font>% to your total ");
			sb.append(BunnyConstants.PURPLE);
			sb.append("BPS");
			break;
		case ACH:
			sb.append("Multiplies</font> your ");
			sb.append(BunnyConstants.PURPLE);
			sb.append("Achievement Multiplier</font> by an extra ");
			sb.append(BunnyConstants.RED);
			sb.append(u.getValue());
			sb.append("</font>%");
			break;
		}
		sb.append(".</font><br><b>");
		return sb.toString();
	}
	private String getBoughtString(boolean isBought){
		return isBought ? BunnyConstants.GREEN+"Purchased!" : BunnyConstants.RED+"Unbought - Click to buy!";
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent me) {
		getUpgrade().buy(BunnyClick.getBunnies());
	}
}
