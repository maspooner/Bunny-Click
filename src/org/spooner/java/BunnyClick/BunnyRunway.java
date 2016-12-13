package org.spooner.java.BunnyClick;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class BunnyRunway extends JPanel implements MouseListener, ActionListener, Runnable{
	//members
	private Image background;
	private GoldenSprite sprite;
	private Random random;
	private Thread spawner;
	private Timer clock;
	//constructors
	public BunnyRunway(){
		addMouseListener(this);
		background=BunnyIO.readPicture("sunset.png");
		random=new Random();
		spawner=new Thread(this);
		spawner.start();
		clock=new Timer(BunnyConstants.GOLDEN_SPEED, this);
		clock.start();
	}
	//methods
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//background paint
		if(getHeight()<BunnyConstants.RUNWAY_PICTURE_SWITCH)
			g.drawImage(background, 0, -65, this);
		else
			g.drawImage(background, 0, 0, this);
		//sprite paint only if active
		if(sprite!=null){
			sprite.move(getWidth());
			sprite.draw(g);
			if(sprite.isDone())
				sprite=null;
		}
	}
	public void sendBunny(){
		//only send if there is not a current one
		if(sprite==null){
			//pass the starting y coord
			int y=(int) getHeight() / 2;// about half way up the frame
			//offset if picture view is switched
			sprite=new GoldenSprite(getHeight()<BunnyConstants.RUNWAY_PICTURE_SWITCH ? y-20 : y);
		}
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent me) {
		if(sprite!=null){
			int sx=sprite.getPosx();
			int sy=sprite.getPosy();
			int x=me.getX();
			int y=me.getY();
			//test if clicked on the bunny (bunny is 60 x 56px)
			if(x>sx && x<60+sx && y>sy && y<56+sy){
				sprite=null;
				BunnyClick.playSound("gold.wav");
				BunnyClick.doBonus();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		repaint();
	}
	@Override
	public void run() {
		while(true){
			try{
				//sleep first so gui has time to build
				Thread.sleep(BunnyConstants.RUNWAY_DELAY);
				
				int rand=random.nextInt(BunnyConstants.TOTAL_CHANCE);
				if(rand<=BunnyClick.getGoldenChance())
					sendBunny();
			}
			catch(Exception e){
				e.printStackTrace();
				System.exit(17);
			}
		}
	}
}
