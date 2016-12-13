package org.spooner.java.BunnyClick;

import java.awt.Graphics;
import java.awt.Image;

public class GoldenSprite extends BunnySprite {
	//members
	private static Image[] frames;
	//constructors
	public GoldenSprite(int y){
		super(-50, y); //start a bit off screen
		if(frames==null)
			initFrames();
	}
	//methods
	private void initFrames(){
		frames=new Image[BunnyConstants.NUM_FRAMES];
		for(int i=0;i<BunnyConstants.NUM_FRAMES;i++){
			frames[i]=BunnyIO.readPicture(BunnyConstants.SPRITE_PATH+i+"g.png");
		}
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(frames[getFrameIter()], getPosx(), getPosy(), null);
	}
	public void move(int width) {
		switchFrames();
		if(getPosx()>=width+60)
			finish();
		setPosx(getPosx()+5);
	}
}
