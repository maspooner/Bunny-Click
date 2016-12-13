package org.spooner.java.BunnyClick;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class StatsTab extends JScrollPane implements ActionListener{
	//members
	private JPanel panel;
	private JLabel madeLabel;
	private JLabel goldenLabel;
	private JLabel clickedLabel;
	private JCheckBox isMusic;
	private JCheckBox isSound;
	private JLabel byLabel;
	//constructors
	public StatsTab(){
		panel=new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(BunnyConstants.SCHEME_5);
		
		addComponents();
		
		setViewportView(panel);
		getVerticalScrollBar().setUnitIncrement(20);
	}
	//methods
	private void addComponents(){
		madeLabel=new JLabel();
		goldenLabel=new JLabel();
		clickedLabel=new JLabel();
		setupLabel(madeLabel);
		setupLabel(goldenLabel);
		setupLabel(clickedLabel);
		isMusic=new JCheckBox("Music", true);
		isSound=new JCheckBox("Sound", true);
		setupCheckbox(isMusic);
		setupCheckbox(isSound);
		byLabel=new JLabel();
		setupLabel(byLabel);
		byLabel.setText("A game by Matt Spooner (Winter 2013)");
		panel.add(madeLabel);
		panel.add(goldenLabel);
		panel.add(clickedLabel);
		panel.add(isMusic);
		panel.add(isSound);
		panel.add(byLabel);
	}
	private void setupLabel(JLabel jl){
		jl.setFont(BunnyConstants.SIDE_FONT);
		jl.setOpaque(true);
		jl.setBackground(BunnyConstants.SCHEME_1);
	}
	private void setupCheckbox(JCheckBox jcb){
		jcb.setFont(BunnyConstants.SIDE_FONT);
		jcb.setBackground(BunnyConstants.SCHEME_1);
		jcb.addActionListener(this);
	}
	public void update(){
		madeLabel.setText("Total bunnies made: "+BunnyClick.format(BunnyStats.totalMade));
		goldenLabel.setText("Golden bunnies clicked: "+BunnyStats.goldenClicks);
		clickedLabel.setText("Bunnies made by clicking: "+BunnyClick.format(BunnyStats.totalClicked));
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		BunnyStats.isMusic=isMusic.isSelected();
		BunnyStats.isSound=isSound.isSelected();
	}
}
