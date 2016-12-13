package org.spooner.java.BunnyClick;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

@SuppressWarnings("serial")
public class BunnyFrame extends JFrame implements Runnable, WindowListener{
	//members
	private Thread updateThread;
	
	private BunnyRunway runway;
	private JLabel bunnyLabel;
	private JLabel BPSLabel;
	private BunnyGenerationArea genArea;
	private JLabel TODLabel;
	private JLabel multiplierLabel;
	private JLabel console;
	private BunnySidePanel sidePanel;
	private short consoleIter;
	//constructors
	public BunnyFrame(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				init();
			}
		});
	}
	//methods
	private void init(){
		setPreferredSize(BunnyConstants.PREF_FRAME_SIZE);
		setMinimumSize(BunnyConstants.MIN_FRAME_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setTitle("Bunny Clicker!");
		setIconImage(BunnyIO.readPicture("icon.png"));
		addWindowListener(this);
		consoleIter=0;
		
		//important for tooltips
		setToolTipFlags();
		
		initComponents();
		arrangeComponents();
		
		pack();
		setVisible(true);
		updateThread=new Thread(this);
		updateThread.start();
	}
	private void setToolTipFlags(){
		ToolTipManager.sharedInstance().setInitialDelay(0);
		ToolTipManager.sharedInstance().setDismissDelay(2000);
		UIManager.put("ToolTip.foreground", 
				new ColorUIResource(BunnyConstants.TIP_FOREGROUND));
		UIManager.put("ToolTip.background", 
				new ColorUIResource(BunnyConstants.TIP_BACKGROUND));
		UIManager.put("ToolTip.border", BunnyConstants.TIP_BORDER);
		UIManager.put("ToolTip.font", 
				new FontUIResource(BunnyConstants.CONSOLE_FONT));
	}
	private void initComponents() {
		//c is convenience only
		final int c=JLabel.CENTER;
		runway=new BunnyRunway();
		bunnyLabel=new JLabel("", c);
		bunnyLabel.setFont(BunnyConstants.BIG_FONT);
		bunnyLabel.setOpaque(true);
		bunnyLabel.setBackground(BunnyConstants.SCHEME_5);
		BPSLabel=new JLabel("", c);
		BPSLabel.setFont(BunnyConstants.SUB_FONT);
		BPSLabel.setOpaque(true);
		BPSLabel.setBackground(BunnyConstants.SCHEME_5);
		TODLabel=new JLabel("BONUS LABEL", c);
		TODLabel.setFont(BunnyConstants.SUB_FONT);
		TODLabel.setOpaque(true);
		TODLabel.setBackground(BunnyConstants.SCHEME_4);
		multiplierLabel=new JLabel("Multiplier", c);
		multiplierLabel.setFont(BunnyConstants.SUB_FONT);
		multiplierLabel.setOpaque(true);
		multiplierLabel.setBackground(BunnyConstants.SCHEME_4);
		console=new JLabel("Welcome to Bunny Click!", c);
		console.setFont(BunnyConstants.CONSOLE_FONT);
		console.setOpaque(true);
		console.setBackground(BunnyConstants.SCHEME_3);
		genArea=new BunnyGenerationArea();
		sidePanel=new BunnySidePanel();
	}
	private void arrangeComponents() {
		GridBagConstraints c=new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.5;
		c.weighty=0.2;
		c.fill=GridBagConstraints.BOTH;
		add(runway, c);
		c.gridy=1;
		c.weighty=0.05;
		add(bunnyLabel, c);
		c.gridy=2;
		add(BPSLabel, c);
		c.gridy=3;
		c.weighty=0.5;
		add(genArea, c);
		c.gridy=4;
		c.weighty=0.03;
		add(TODLabel, c);
		c.gridy=5;
		add(multiplierLabel, c);
		c.gridy=6;
		add(console, c);
		c.gridx=1;
		c.gridy=0;
		c.gridheight=7;
		c.weightx=0.5;
		add(sidePanel, c);
	}
	public void consoleWrite(String s){
		consoleIter=0;
		console.setText(s);
	}
	@Override
	public void run() {
		while(true){
			try{
				bunnyLabel.setText(BunnyClick.getBunnyString());
				BPSLabel.setText(BunnyClick.getBPSString());
				genArea.update();
				final String bonusName=Bonus.getBonus().getName();
				//if TOD has changed
				if(!TODLabel.getText().equals(bonusName)){
					TODLabel.setText(bonusName);
					TODLabel.setToolTipText(Bonus.getToolTipText());
				}
				multiplierLabel.setText(Achievement.getMultiplierString());
				
				//manage console clearing
				consoleIter++;
				if(consoleIter==BunnyConstants.CONSOLE_CLEAR_TIMES){
					consoleIter=0;
					console.setText("Bunny Click");
				}
				//update only tab in view
				sidePanel.update();
				Thread.sleep(BunnyConstants.UPDATE_DELAY);
			}
			catch(Exception e){
				e.printStackTrace();
				System.exit(7);
			}
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		BunnyClick.save();
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
}
