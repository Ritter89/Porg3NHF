package Game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * a játékost vitelezi ki
 * @author Ritter Máté
 *
 */
public class player extends unit{
	private int health = 3;
	private int numberOfBullets = 1;
	private int fireRate = 1;
	private int speed = 1;
	private Timer shotTimer= new Timer(500/fireRate, new shoot()  );
	private control c;
	
	/**
	 * visza adja a játékos életét 
	 * @return	a játékos élete
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * a játékos konstruktora
	 * @param cont	a fö vezérlés
	 */
	public player(control cont) {
		p = new Point(145,490);
		c = cont;
		size = new Point(10,10);
		shotTimer.start();
		return;
	}
	/**
	 * megállitja a játékos lövését
	 */
	public void pause() {
		if(shotTimer.isRunning()) {
			shotTimer.stop();
		}
	}
	/**
	 * ujra inditja a játékos lövését
	 */
	public void unPause() {
		if(!shotTimer.isRunning()) {
			shotTimer.start();
		}
	}
	/**
	 * nem csinál semmit, azért van benne mert az abstrakt osztályba benne van hiszen más leszármazot használja
	 */
	public void move() {
		return;
	}
	/**
	 * @return visza adja hogy melyik sprite az övé
	 */
	public  int print() {
		return 0;
	}
	
	/**
	 * mozgást valositjameg
	 * @param pTemp	erre felé mozog
	 */
	public void move(Point pTemp) {
		int deltaX = (int) pTemp.getX();
		int deltaY = (int) pTemp.getY();
		int vegX = (int) p.getX();
		int vegY = (int) p.getY();
		deltaX = (int) (vegX + (deltaX * speed));
		deltaY = (int) (vegY + (deltaY * speed));
		
        if (deltaX >= 0 && deltaX <= 290) {
            vegX = deltaX;
        }
        else if(deltaX < speed) {
        	vegX= 0;
        }
        else if(deltaX > 290 - speed) {
        	vegX= 290;
        }
        if(deltaY >= 0 && deltaY <= 490) {
        	vegY = deltaY;
        }
        else if(deltaY < speed) {
        	vegY= 0;
        }
        else if(deltaY > 490 - speed) {
        	vegY= 490;
        }
        p.setLocation(vegX, vegY);
        return;
	}
	/**
	 * az ellenségel valo ötközés
	 * a játékos elveszit 1 életett és az ellenség meghal
	 * @param e
	 */
	public void colide(enemy e) {
		health --;
		if(health <= 0) {
			c.gameOver(false);
		}
		control.removeUnit(e);
	}
	/**
	 * fejlesztésel valo ütközés
	 * @param u	ez dönti el hogy milyen fajta upgrade volt
	 */
	public void colide(upgrade u) {
		if(u.getType()) {
			fireRate++;
			shotTimer.setDelay((int)500/fireRate);
		}
		else if(! u.getType()) {
			numberOfBullets++;
		}
		control.removeUnit(u);
		//return;
	}
	/**
	 * a játékos lövését valositja meg
	 * @author Ritter Máté
	 *
	 */
	private class shoot implements ActionListener{
		/**
		 * a játékos lövését valositja meg
		 */
		@Override
	    public void actionPerformed(ActionEvent e) {
			double bulletDirection = 0.25;
			double bulletDirectionChange = 1.5/(numberOfBullets + 1);
			for(int i = 0 ; i < numberOfBullets;i++) {
				bulletDirection += bulletDirectionChange;
				control.addUnit(new bullet(new Point((int)p.getX()+3, (int)p.getY()-8),bulletDirection));
			} 
			
	    }
	}
	
	
}
