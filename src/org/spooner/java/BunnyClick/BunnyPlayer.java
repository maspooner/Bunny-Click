package org.spooner.java.BunnyClick;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BunnyPlayer implements Runnable{
	//song ideas: Hawkeye by Alan, Animal Crossing Songs
	//members
	private static final int BUFFER_SIZE = 64*1024;  // 64 KB
	private ArrayList<Integer> lastPlayed;
	private Random rand;
	private Thread playerThread;
	private String musicLocation;
	//constructors
	public BunnyPlayer(){
		lastPlayed=new ArrayList<Integer>();
		rand=new Random();
	}
	//methods
	private AudioInputStream loadSound(String path) throws UnsupportedAudioFileException, IOException{
		if(BunnyClick.IS_TEST){
			return AudioSystem.getAudioInputStream(new File(path));
		}
		return AudioSystem.getAudioInputStream
				(new BufferedInputStream(ClassLoader.class.getResourceAsStream("/" + path)));
	}
	public void playSound(String fileName){
		String path = BunnyConstants.SOUND_PATH + fileName;
		Clip c = null;
		try{
			AudioInputStream ais = loadSound(path);
			c = AudioSystem.getClip();
			c.open(ais);
			c.start();
		}
		catch(Exception e){
			e.printStackTrace();
			c.drain();
			c.close();
			System.exit(1);
		}
	}
	public void play(){
		//play christmas song first
		musicLocation=BunnyConstants.SOUND_PATH+25+".wav";
		lastPlayed.add(25);
		if(playerThread==null){
			playerThread=new Thread(this);
			playerThread.start();
		}
	}
	@Override
	public void run() {
		while(true){
			SourceDataLine sdl=null;
			try{
				//loop indefinitely
				while(true){
					AudioInputStream ais = loadSound(musicLocation);
					//only if music is desired
					while(!BunnyStats.isMusic){
					}
					DataLine.Info info=new DataLine.Info(SourceDataLine.class, ais.getFormat());
					sdl= (SourceDataLine) AudioSystem.getLine(info);
					sdl.open();
					sdl.start();
					int bytesRead=0;
					byte[] data=new byte[BUFFER_SIZE];
					while(bytesRead!=-1){
						bytesRead=ais.read(data, 0, data.length);
						if(bytesRead>=0){
							sdl.write(data, 0, bytesRead);
						}
						if(!BunnyStats.isMusic){
							throw new InterruptedException();
						}
					}
					changeSong();
				}
			}
			catch(InterruptedException ie){
				sdl.drain();
				sdl.close();
				continue;
			}
			catch(Exception e){
				e.printStackTrace();
				sdl.drain();
				sdl.close();
				System.exit(1);
			}
		}
	}
	private void changeSong() {
		main:
		while(true){
			int song=rand.nextInt(BunnyConstants.TOTAL_SONGS)+1; //starts numbering at 1
			for(Integer i : lastPlayed){
				if(song==i) continue main;
			}
			//not in list, break
			lastPlayed.add(song);
			musicLocation=BunnyConstants.SOUND_PATH+song+".wav";
			break;
		}
		if(lastPlayed.size()==11){
			//too many songs on the list
			lastPlayed.remove(0);
		}
	}
}
