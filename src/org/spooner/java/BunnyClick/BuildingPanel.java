package org.spooner.java.BunnyClick;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BuildingPanel extends JPanel implements ActionListener{
	//members
	private int index;
	private JLabel imageLabel;
	private JLabel nameLabel;
	private JLabel numberLabel;
	private JButton buyButton;
	private JButton sellButton;
	//constructors
	public BuildingPanel(Building b, int index){
		setBorder(BunnyConstants.BUILDING_BORDER);
		setBackground(BunnyConstants.SCHEME_4);
		
		this.index=index;
		imageLabel=new JLabel(BunnyIO.getIcon(b.getImagePath()));
		imageLabel.setToolTipText(getImageToolTipText(b));
		nameLabel=new JLabel(b.getName());
		numberLabel=new JLabel("x "+b.getNumber());
		buyButton=new JButton("Buy");
		buyButton.setBackground(BunnyConstants.SCHEME_3);
		buyButton.addActionListener(this);
		sellButton=new JButton("Sell");
		sellButton.setBackground(BunnyConstants.SCHEME_3);
		sellButton.addActionListener(this);
		
		setFontOf(nameLabel);
		setFontOf(numberLabel);
		setFontOf(buyButton);
		setFontOf(sellButton);
		setLayout(new GridBagLayout());
		arrangeComponents();
	}
	private void arrangeComponents() {
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.01;
		c.weighty=0.01;
		c.gridheight=2;
		c.anchor=GridBagConstraints.WEST;
		c.fill=GridBagConstraints.BOTH;
		add(imageLabel, c);
		c.gridx=1;
		c.gridheight=1;
		c.weightx=0.5;
		add(nameLabel, c);
		c.gridy=1;
		add(numberLabel, c);
		c.gridx=2;
		c.gridy=0;
		c.weightx=0.1;
		add(buyButton, c);
		c.gridy=1;
		add(sellButton, c);
	}
	//methods
	private String getImageToolTipText(Building b){
		StringBuilder sb=new StringBuilder();
		sb.append("<html><b>");
		sb.append(BunnyConstants.PURPLE);
		sb.append(b.getName());
		sb.append("</b></font><br><i>");
		sb.append(b.getDesc());
		sb.append("</i><br><br>Base BPS: ");
		sb.append(BunnyConstants.RED);
		sb.append(BunnyClick.format(b.getBaseBPS()));
		sb.append("</font><br>Total BPS: ");
		sb.append(BunnyConstants.RED);
		sb.append(BunnyClick.format(b.getNumber() * b.getBaseBPS()));
		return sb.toString();
	}
	private String getBuyText(Building b){
		StringBuilder sb=new StringBuilder();
		sb.append("<html><b>Buy for: </b>");
		sb.append(BunnyConstants.RED);
		sb.append(BunnyClick.format(b.getCost(b.getNumber()+1)));
		sb.append(" bunnies</font></html>");
		return sb.toString();
	}
	private String getSellText(Building b){
		StringBuilder sb=new StringBuilder();
		sb.append("<html><b>Sell for: </b>");
		sb.append(BunnyConstants.GREEN);
		sb.append(BunnyClick.format(b.getSell()));
		sb.append(" bunnies</font></html>");
		return sb.toString();
	}
	private Building getBuilding(){return Building.getBuildingAt(index);}
	public void update(){
		Building b=getBuilding();
		imageLabel.setToolTipText(getImageToolTipText(b));
		buyButton.setToolTipText(getBuyText(b));
		sellButton.setToolTipText(getSellText(b));
	}
	public void entireUpdate(){
		numberLabel.setText("x "+getBuilding().getNumber());
		update();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		String ac=ae.getActionCommand();
		if(ac.equals("Buy")){
			BunnyClick.buy(index);
			Achievement.checkType(Achievement.Type.NUMBER,
					Building.getBuildingAt(index).getNumber(), index);
		}
		else{
			//sell
			BunnyClick.sell(index);
		}
		entireUpdate();
		BunnyClick.doRecalculation();
	}
	private void setFontOf(Component c){
		c.setFont(BunnyConstants.SIDE_FONT);
	}
}
