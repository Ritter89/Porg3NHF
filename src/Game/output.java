package Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;
/**
 * kezeli a játék grafikus outputját
 * @author Ritter Máté
 *
 */
public class output extends JPanel{
	private Timer T = new Timer("output Timer");
	private int currFrame = 0;
	private ArrayList<unit> elemek;
	private ArrayList<mapPiece> palya;
	private BufferedImage[] sprite= new BufferedImage[6];
	/**
	 * a currframe setterje
	 * @param i erre állitja a currframet
	 */
	public void setCurrFrame(int i) {
		currFrame = i;
	}
	/**
	 * az output konstruktora
	 * @param arr 	ez tartalmaza az öszes kirajzolando egységet
	 * @param pa	ez pedig a pályát
	 */
	public output(ArrayList<unit> arr, ArrayList<mapPiece> pa) {
		elemek = arr;
		palya = pa;
		
		//player sprite
		sprite[0] = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);
		Graphics g = sprite[0].getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 11, 11);
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, 2, 3);
		g.fillRect(8, 0, 2, 3);
		g.setColor(Color.WHITE);
		g.fillRect(4, 0, 2, 3);
		
		//bullet sprite
		sprite[1] = new BufferedImage(4,8,BufferedImage.TYPE_INT_ARGB);
		g = sprite[1].getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 5, 9);
		g.setColor(Color.YELLOW);
		g.fillRect(1, 1, 2, 6);	
		g.dispose();
		
		//vertical enemy sprite
		sprite[2] = new BufferedImage(10,8,BufferedImage.TYPE_INT_ARGB);
		g = sprite[2].getGraphics();
		g.setColor(Color.RED);
		g.fillRect(0, 0, 11, 9);
		g.setColor(Color.YELLOW);
		g.fillRect(1, 1, 8, 6);
		g.setColor(Color.BLACK);
		g.fillRect(3, 3, 1, 2);
		g.fillRect(6, 3, 1, 2);
		
		//horizontal enemy sprite
		sprite[3] = new BufferedImage(12,8,BufferedImage.TYPE_INT_ARGB);
		g = sprite[3].getGraphics();
		g.setColor(Color.MAGENTA);
		g.fillRect(0, 0, 13, 9);
		g.setColor(Color.CYAN);
		g.fillRect(2, 2, 8, 4);
		g.setColor(Color.BLACK);
		g.fillRect(3, 3, 1, 2);
		g.fillRect(8, 3, 1, 2);
		
		//arc enemy sprite
		sprite[4] = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);
		g = sprite[4].getGraphics();
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 11, 11);
		g.setColor(Color.PINK);
		g.fillRect(3, 3, 4, 4);
		g.setColor(Color.BLACK);
		g.fillRect(3, 3, 1, 2);
		g.fillRect(6, 3, 1, 2);
		g.dispose();
		
		//upgrade sprite
		sprite[5] = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);
		g = sprite[5].getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 10, 10);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(2, 2, 6, 6);
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, 10, 10);
		g.drawLine(0, 10, 10, 0);
		g.dispose();

		T.scheduleAtFixedRate(new OutputListener(), 0, 33);
	}
	/**
	 * be olvassa a következö pálya darabot és ujra rajzolja  a pályát
	 * @author Ritter Máté
	 *
	 */
	private class OutputListener extends TimerTask{
		/**
		 *  be olvassa a következö pálya darabot és ujra rajzolja  a pályát
		 */
	    public void run() {
	    	currFrame++;
	    	if(palya != null && palya.size() != 0) {
	    		mapPiece temp = palya.get(0);
		    	while(currFrame == temp.getSpawnFrame()){
		    		if(temp.getEnemyType()==1) {
		    			control.addUnit(new vertical(temp.getLocation()));
		    		}
		    		else if(temp.getEnemyType()==2) {
		    			control.addUnit(new horizontal(temp.getLocation()));
		    		
		    		}
		    		else if (temp.getEnemyType()==3) {
		    			control.addUnit(new arc(temp.getLocation()));
		    		}
		    		palya.remove(temp);
		    		if(palya.size() != 0) {
		    			temp = palya.get(0);
		    		}
		    		else {
		    			break;
		    		}
		    	}
	    	}
			repaint(); 
	    }
	}
	/**
	 * ujra rajzolja az öszes egységet a pályán
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	    int i = 0;
	    for(i = 0; i < elemek.size();i++ ) {
			Point p = elemek.get(i).pGetter();
			g.drawImage(sprite[elemek.get(i).print()], (int) p.getX(), (int) p.getY(), this);
		}
	}
	/**
	 * megállitja a kirajzolást
	 */
	public void pause() {
		T = null;
	}
	/**
	 * ujra kezdi a kirajzolást
	 */
	public void unPause() {
		T = new Timer("output Timer");
		T.scheduleAtFixedRate(new OutputListener(), 0, 33);
	}
}
