package Game;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

/**
 * a horizontal ellenség tipust vitelezi ki
 * @author Ritter Máté
 *
 */
public class horizontal extends enemy {
	private double speed = 0.25;
	private double carryover = 0;
	private int direction = 0;
	private Timer shotTimer= new Timer(2000, new shoot()  );
	
	/**
	 * az horizontal konstruktora
	 * @param i	ez alapján dönti el hogy hova kerüljön az egység
	 */
	public horizontal (int i) {
		switch(i){
			case 1:
				p= new Point(0,25);
				direction = 1;
				break;
			case 2:
				p= new Point(0,125);
				direction = 1;
				break;
			case 3:
				p= new Point(0,225);
				direction = 1;
				break;
			case 4:
				p= new Point(288,75);
				direction = -1;
				break;
			case 5:
				p= new Point(288,175);
				direction = -1;
				break;
			case 6:
				p= new Point(288,275);
				direction = -1;
				break;
			default:
				p= new Point(0,0);
				direction = 1;
		}
		size = new Point(10,8);
		shotTimer.start();
	}
	
	/**
	 * megállija a lövést
	 */
	public void stop() {
		if(shotTimer.isRunning()) {
		shotTimer.stop();
		shotTimer = null;
		}
	}
	
	/**
	 * mozgatja az egységet
	 */
	public void move() {
		carryover +=speed;
		if(carryover >= 1) {
			int vegX = (int) (p.getX() + (direction *  Math.floor(carryover)));
			p.setLocation(vegX, p.getY());
			carryover -= Math.floor(carryover);
			if(vegX > 300|| vegX<0) {
				shotTimer.stop();
				control.removeUnit(this);
			}
		}
	}
	/**
	 * a lövést valositja meg
	 * @author Ritter Máté
	 *
	 */
	private class shoot implements ActionListener{
		/**
		 * a lövést valositja meg
		 */
		@Override
	    public void actionPerformed(ActionEvent e) {
			control.addUnit(new enemyBullet(new Point((int)p.getX()+3, (int)p.getY()+10)));
	    }
	}
	/**
	 * a játékos lövedékével valo ötközést valositja meg
	 * a játékos pontja nö és ez a egység elpusztul
	 */
	public void colide(bullet b) {
		control.increaseScore(100);
		Random rnd = new Random();
		double d = rnd.nextDouble();
		if(d < upgradeDrobChanche) {
			boolean bool = rnd.nextBoolean();
			control.addUnit(new upgrade(p, bool));
		}
		shotTimer.stop();
		control.removeUnit(this);
	}
	
	/**
	 * @return visza adja hogy melyik sprite az övé
	 */
	public  int print() {
		return 3;
	}
}
