package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ez az osztály valositja meg a játék gondolkodását
 * @author Ritter Máté
 *
 */
public class control{
	private static int score = 0;
	private int frameNmbr = 0;
	private  JFrame frame;
	private  output panel;
	private String palyaName= "1palya.txt";
	private static ArrayList<unit> elemek = new ArrayList<unit>();
	private Timer moveTimer = new Timer(10, new mover());
	JButton bStart = new JButton("Start");
	JButton bBack = new JButton("Fö menu");
	JButton bTop = new JButton("Toplista");
	JTextArea tTop = new JTextArea("");
	private static ArrayList<enemy> enemyHolder = new ArrayList<enemy>();
	private static ArrayList<upgrade> upgradeHolder = new ArrayList<upgrade>();
	private static ArrayList<bullet> bulletHolder = new ArrayList<bullet>();
	private input in;
	private static player Player;
	private ArrayList<mapPiece> palya ;
	private topLista top;
	private boolean gameOverFlag=false;
	
	/**
	 * megnöveli a score-t
	 * @param i	ezzel a számmal növeli meg a score-t
	 */
	public static void increaseScore(int i){
		score +=i;
	}
	
	/**
	 * a játék végét valositja meg
	 * @param b	ez dönti el hogy nyert vagy vesztett a játékos
	 */
	public void gameOver(boolean b ) {
		gameOverFlag=true;
		in.pause();
		pause();
		Player.pause();
		if(enemyHolder != null && enemyHolder.size() != 0) {
			for(int i = 0; i < enemyHolder.size(); i++) {
				enemyHolder.get(i).stop();;
			}
		}
		if(b) {
			score += Player.getHealth() * 100;
			JOptionPane.showMessageDialog(frame, "Gratulálok nyertél \n" + score + " pontal");
		}
		else{
			JOptionPane.showMessageDialog(frame, "Sajnos vesztetél \n" + score + " pontal");
		}
		top.add(score);
		try {
			FileOutputStream fileOut = new FileOutputStream("toplist.txt");
		 	ObjectOutputStream out = new ObjectOutputStream(fileOut);
		 	out.writeObject(top);
		  	out.close();
		 	fileOut.close();
		} 
		catch (IOException i) {
		    i.printStackTrace();
		}
		startMenu();
	}
	
	/**
	 * a játék kezdetét valositja meg
	 */
	public void gameStart() {
		frame = new JFrame();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bTop.addActionListener(new topListButton());
		bStart.addActionListener(new startButton());
		bBack.addActionListener(new mainMenuButton());
		bBack.setBounds(50, 150, 100, 30);
		bTop.setBounds(50, 150, 100, 30);
		bStart.setBounds(50, 150, 100, 30);
		startMenu();
	}
	
	/**
	 * beolvasa a pályát
	 */
	public void readMap(){
		palya = new ArrayList<mapPiece>();
		try {
			File f = new File(palyaName);
			Scanner reader = new Scanner(f);
			while(reader.hasNextLine()) {
				String data = reader.nextLine();
				String[] temp = data.split(" ");
				palya.add(new mapPiece(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * futtatja a játékot
	 */
	public void gameRun() {
		gameOverFlag=false;
		readMap();
		score = 0;
		if(Player != null) {
			Player.pause();
		}
		elemek.clear();
		Player = new player(this);
		elemek.add(Player);
		panel = new output(elemek,palya);
		frame.setSize(316, 539);
		frame.setResizable(false);
		panel.setSize(300, 500);
		frame.getContentPane().add(panel);
		panel.setBackground(Color.WHITE);
		in = new input(panel,elemek.get(0),this, frame);
		unPause();
	}
	
	/**
	 * megállitja az ellenség és a lövedékek mozgását, grafikai outputot
	 */
	public void pause() {
		if(moveTimer.isRunning()) {
			moveTimer.stop();
			panel.pause();
		}
	}
	
	/**
	 * elinditja az ellenség és a lövedékek mozgását, grafikai outputot
	 */
	public void unPause() {
		if(!moveTimer.isRunning()) {
			moveTimer.start();
			panel.unPause();
		}
	}
	
	/*public void resetTopList() {
		top= new topLista(new int[]{10000,9500,9000,8500,8000,7500,7000,6500,6000,5500,5000,4500,4000,3500,3000,2500,2000,1500,1000,500}) ;
		try {
			FileOutputStream fileOut = new FileOutputStream("toplist.txt");
		 	ObjectOutputStream out = new ObjectOutputStream(fileOut);
		 	out.writeObject(top);
		  	out.close();
		 	fileOut.close();
		} 
		catch (IOException i) {
		    i.printStackTrace();
		}
	}*/
	
	/**
	 * a start menüt valositja meg 
	 */
	public void startMenu() {
		//resetTopList();
		if(enemyHolder != null && enemyHolder.size() != 0) {
			for(int i = 0; i < enemyHolder.size(); i++) {
				enemyHolder.get(i).stop();;
			}
		}
		enemyHolder = new ArrayList<enemy>();
		upgradeHolder = new ArrayList<upgrade>();
		bulletHolder = new ArrayList<bullet>();
		try {
			top = null;
			FileInputStream fileIn = new FileInputStream("toplist.txt");
			ObjectInputStream fin = new ObjectInputStream(fileIn);
			top = (topLista) fin.readObject();
			fin.close();
			fileIn.close();
		} catch (IOException i) {
	      	i.printStackTrace();
	   	} catch (ClassNotFoundException c) {
	        c.printStackTrace();
	    }
		bBack.setVisible(false);
		tTop.setVisible(false);
		if(frame.isDisplayable()) {
			frame.remove(bBack);
			frame.remove(tTop);
		}
		if(panel != null && panel.isDisplayable() ) {
			frame.remove(panel);
		}
		bStart.setVisible(true);
		frame.add(bStart,BorderLayout.NORTH);	
		bTop.setVisible(true);
		frame.add(bTop,BorderLayout.SOUTH);
		frame.pack();
	}
	
	/**
	 * a játék kezdö gombot valositja meg 
	 * @author Ritter Máté
	 *
	 */
	private class startButton implements ActionListener{
		/**
		 * a játék kezdö gombot valositja meg 
		 */
		public void actionPerformed(ActionEvent e) {
			frame.remove(bTop);
			frame.remove(bStart);
			
			bTop.setVisible(false);
			bStart.setVisible(false);
			//frame.add(panel);
			frame.pack();
			gameRun();
		}
	}
	
	/**
	 * a toplistához vezetö gombot valositja meg 
	 * @author Ritter Máté
	 *
	 */
	private class topListButton implements ActionListener{
		
		/**
		 * a megmutattja a toplistát  
		 */
		public void actionPerformed(ActionEvent e) {
			frame.remove(bTop);
			frame.remove(bStart);
			bTop.setVisible(false);
			bStart.setVisible(false);
			bBack.setVisible(true);
			tTop.setVisible(true);
			frame.add(tTop,BorderLayout.NORTH);	
			int a [] = top.gettoplista();
			tTop.setText("Toplista \r\n 1:"+ a[0] + "\r\n 2:" + a[1] + "\r\n 3:" + a[2] + "\r\n 4:"+ a[3] + "\r\n 5:"+ a[4] + "\r\n 6:"+ a[5] + "\r\n 7:"+ a[6] + "\r\n 8:"+ a[7] + "\r\n 9:"+ a[8] + "\r\n 10:"+ a[9] + "\r\n 11:"+ a[10] + "\r\n 12:"+ a[11] + "\r\n 13:"+ a[12] + "\r\n 14:"+ a[13] + "\r\n 15:"+ a[14] + "\r\n 16:"+ a[15] + "\r\n 17:"+ a[16] + "\r\n 18:"+ a[17] + "\r\n 19:"+ a[18] + "\r\n 20:"+ a[19]);
			frame.add(bBack,BorderLayout.SOUTH);
			frame.pack();
		}
	}
	
	/**
	 * a fömenü gombját valositja meg 
	 * @author Ritter Máté
	 *
	 */
	private class mainMenuButton implements ActionListener{
		/**
		 * meghivja a fömenü föggvényét
		 */
		public void actionPerformed(ActionEvent e) {
			startMenu();
		}
	}

	/**
	 * Az ellenségek és lövedékek mozgását és collision chekeket valositja meg 
	 * @author Ritter Máté
	 *
	 */
	private class mover implements ActionListener{
		/**
		 * Az ellenségek és lövedékek mozgását és collision chekeket valositja meg 
		 */
		@Override
	    public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < elemek.size();i++) {
				elemek.get(i).move();
			}
			if(enemyHolder != null && enemyHolder.size() != 0) {
				for(int i = 0; i < enemyHolder.size(); i++) {
					enemy tempEnemy = enemyHolder.get(i);
					if(bulletHolder != null && bulletHolder.size() != 0) {
						for(int u = 0; u < bulletHolder.size(); u++) {
							bullet tempBullet = bulletHolder.get(u);
							if((tempEnemy.p.x + tempEnemy.size.x) > tempBullet.p.x && (tempBullet.p.x + tempBullet.size.x) > tempEnemy.p.x && (tempEnemy.p.y + tempEnemy.size.y) > tempBullet.p.y && (tempBullet.p.y + tempBullet.size.y) > tempEnemy.p.y) {
								tempBullet.colide(tempEnemy);
								break;
							}
						}
					}
					if((tempEnemy.p.x + tempEnemy.size.x) > Player.p.x && (Player.p.x + Player.size.x) > tempEnemy.p.x && (tempEnemy.p.y + tempEnemy.size.y) > Player.p.y && (Player.p.y + Player.size.y) > tempEnemy.p.y) {
						Player.colide(tempEnemy);
					}
				}
			}
			if(upgradeHolder != null && upgradeHolder.size() != 0) {
				for(int i = 0; i < upgradeHolder.size(); i++) {
					upgrade tempUpgrade = upgradeHolder.get(i);
					if((tempUpgrade.p.x + tempUpgrade.size.x) > Player.p.x && (Player.p.x + Player.size.x) > tempUpgrade.p.x && (tempUpgrade.p.y + tempUpgrade.size.y) > Player.p.y && (Player.p.y + Player.size.y) > tempUpgrade.p.y) {
						Player.colide(tempUpgrade);
					}
				}
			}
			frame.setTitle("Score: " + score + "Health: " + Player.getHealth());
			if((palya == null || palya.size() == 0 ) && (enemyHolder == null || enemyHolder.size() == 0) && !gameOverFlag) {
	    		gameOver(true);
	    	}
		}
	}
	
	/**
	 * hozá add egy ellenséget a változokhoz
	 * @param u	ezt az ellenséget
	 */
	public static void addUnit(enemy u) {
		enemyHolder.add(u);
		elemek.add(u);
	}
	
	/**
	 * elvesz egy ellenséget a tárolokbol
	 * @param u ezt az ellenséget
	 */
	public static void removeUnit(enemy u) {
		enemyHolder.remove(u);
		elemek.remove(u);
	}
	
	/**
	 * hozá add egy lövedéket a változokhoz
	 * @param u	ezt a lövedéket
	 */
	public static void addUnit(bullet u) {
		bulletHolder.add(u);
		elemek.add(u);
	}
	
	/**
	 * elvesz egy lövedéket a tárolokbol
	 * @param u ezt a lövedéket
	 */
	public static void removeUnit(bullet u) {
		bulletHolder.remove(u);
		elemek.remove(u);
	}
	
	/**
	 * hozá add egy fejlesztést a változokhoz
	 * @param u	ezt a fejlesztést
	 */
	public static void addUnit(upgrade u) {
		upgradeHolder.add(u);
		elemek.add(u);
	}
	
	/**
	 * elvesz egy fejlesztést a tárolokbol
	 * @param u ezt a fejlesztést
	 */
	public static void removeUnit(upgrade u) {
		upgradeHolder.remove(u);
		elemek.remove(u);
	}
}
