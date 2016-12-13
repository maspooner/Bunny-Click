package org.spooner.java.BunnyClick;

import java.awt.Graphics;
import java.awt.Image;

public class BunnySprite {
	//members
	private static Image[] frames;
	
	private int posx;
	private int posy;
	private int frameIter;
	private boolean isDone;
	//consturctors
	public BunnySprite(int x, int y){
		posx=x;
		posy=y;
		frameIter=0;
		isDone=false;
		if(frames==null)
			initFrames();
	}
	//methods
	private void initFrames(){
		frames=new Image[BunnyConstants.NUM_FRAMES];
		for(int i=0;i<BunnyConstants.NUM_FRAMES;i++){
			frames[i]=BunnyIO.readPicture(BunnyConstants.SPRITE_PATH+i+".png");
		}
	}
	public void draw(Graphics g) {
		g.drawImage(frames[frameIter], posx, posy, null);
	}
	public void switchFrames(){
		if(frameIter<frames.length-1)
			frameIter++;
		else
			frameIter=0;
	}
	public void move(){
		switchFrames();
		if(posx<=-60)
			finish();
		posx-=5;
	}
	public boolean isDone(){return isDone;}
	public int getPosx(){return posx;}
	public int getPosy(){return posy;}
	public int getFrameIter(){return frameIter;}
	public void setPosx(int x){posx=x;}
	public void finish(){isDone=true;}
}
