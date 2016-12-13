package org.spooner.java.BunnyClick;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class BunnyConstants {
	//static members
	protected static final String BUILDING_PATH="buildings/";
	protected static final String SPRITE_PATH="sprites/";
	protected static final String ACHIEVEMENT_PATH="achievements/";
	protected static final String UPGRADES_PATH="upgrades/";
	protected static final String SOUND_PATH="sounds/";
	protected static final int NUM_FRAMES=7;
	protected static final int ANIMATION_SPEED=100;
	protected static final int GOLDEN_SPEED=150;
	protected static final Dimension MIN_FRAME_SIZE=new Dimension(700,500);
	protected static final Dimension PREF_FRAME_SIZE=new Dimension(1000,800);
	protected static final int UPDATE_DELAY=500;
	protected static final int BPS_DELAY=950;
	protected static final int SAVE_DELAY=60000;//1 min
	protected static final int TOD_DELAY=10000;//10 sec
	protected static final int RUNWAY_DELAY=20000;//20 sec
	protected static final int CONSOLE_CLEAR_TIMES=10;//means 10 iterations of UPDATE_DELAY
	protected static final int STARTING_CLICK=10;
	protected static final float STARTING_GOLD_BUNNIES=2.5f;
	protected static final int STARTING_CHANCE=5;
	protected static final int TOTAL_CHANCE=75;
	protected static final int RUNWAY_PICTURE_SWITCH=140;
	protected static final int AREA_PICTURE_SWITCH=290;
	protected static final int TOTAL_SONGS=26;
	//colors
	protected static final Color TIP_FOREGROUND=Color.WHITE;
	protected static final Color TIP_BACKGROUND=new Color(80, 80, 112);
	protected static final Color SCHEME_1=new Color(237, 200, 68);
	protected static final Color SCHEME_2=new Color(238, 208, 144);
	protected static final Color SCHEME_3=new Color(218, 189, 149);
	protected static final Color SCHEME_4=new Color(165, 93, 53);
	protected static final Color SCHEME_5=new Color(145, 91, 81);
	protected static final String PURPLE="<font color=#E13FD7>";
	protected static final String RED="<font color=#F7114B>";
	protected static final String GREEN="<font color=#11F734>";
	//fonts
	protected static final Font BIG_FONT=BunnyIO.getFont("font.ttf", 33, Font.BOLD);
	protected static final Font SUB_FONT=BIG_FONT.deriveFont(Font.ITALIC, 28);
	protected static final Font SIDE_FONT=BIG_FONT.deriveFont(Font.PLAIN, 18);
	protected static final Font CONSOLE_FONT=BIG_FONT.deriveFont(Font.PLAIN, 16);
	//borders
	protected static final Border BUILDING_BORDER=BorderFactory.createLineBorder(SCHEME_5, 4, true);
	protected static final Border TIP_BORDER=BorderFactory.createLineBorder(new Color(238, 232, 123), 3);
}
