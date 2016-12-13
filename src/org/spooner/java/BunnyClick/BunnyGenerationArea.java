package org.spooner.java.BunnyClick;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class BunnyGenerationArea extends JPanel implements MouseListener, ActionListener{
	//members
	private ArrayList<BunnySprite> sprites;
	private Timer clock;
	private Image background;
	//consturctors
	public BunnyGenerationArea(){
		sprites=new ArrayList<BunnySprite>();
		clock=new Timer(BunnyConstants.ANIMATION_SPEED, this);
		clock.start();
		background=BunnyIO.readPicture("grasslands.png");

		addMouseListener(this);
	}
	//methods
	private String getImageToolTip(){
		StringBuilder sb=new StringBuilder();
		sb.append("<html><b>");
		sb.append(BunnyConstants.GREEN);
		sb.append("Click</b></font> to generate: <br>");
		sb.append(BunnyConstants.RED);
		sb.append(BunnyClick.format(BunnyClick.getBPC()));
		sb.append("</font> bunnies");
		return sb.toString();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//background paint
		if(getHeight()<BunnyConstants.AREA_PICTURE_SWITCH)
			g.drawImage(background, 0, -130, this);
		else
			g.drawImage(background, 0, 0, this);
		for(int i=0;i<sprites.size();i++){
			BunnySprite bs=sprites.get(i);
			if(!bs.isDone()){
				bs.move();
				bs.draw(g);
			}
			else{
				bs=null;
				sprites.remove(i);
			}
		}
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent me) {
		int x=me.getX();
		int y=getHeight()-70;
		int w=getWidth();
		//if x is too far right
		if(x>w-60) x=w-60;
		sprites.add(new BunnySprite(x, y));
		BunnyClick.click();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		repaint();
	}
	public void update(){
		setToolTipText(getImageToolTip());
	}
}
