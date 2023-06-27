package Game;
import java.awt.Point;

/**
 * a fejlesztések osztálya
 * @author Ritter Máté
 *
 */
public class upgrade extends unit {
	boolean type;
	private double speed = 0.375;
	private double carryover = 0;
	/**
	 * az upgrade construktora
	 * @param point	ezzen a ponton van
	 * @param b	ez dönti el hogy fireratet vagy lövedék számot fejleszt
	 */
	public upgrade(Point point, boolean b) {
		p = point;
		type = b;
		size = new Point(10,10);
	}
	/**
	 * ez a fejlesztés mozgását valositja meg
	 */
	public void move() {
		carryover +=speed;
		if(carryover >= 1) {
			int vegY= (int) (p.getY() + Math.floor(carryover));
			p.setLocation(p.getX(),vegY );
			carryover -= Math.floor(carryover);
			if(vegY > 500) {
				control.removeUnit(this);
			}
		}
	}
	/**
	 * viszatér a fejlesztés tipusával
	 * @return a fejlesztés tipusa
	 */
	public boolean getType() {
		return type;
	}
	/**
	 * a fejlesztés ütközése a játékosal
	 * @param player	ezzel a játékosal
	 */
	public void colide(player player) {
		player.colide(this);
	}
	/**
	 * 
	 * @return	visza adja hogy az egységhez hanyás számu sprite tartozik
	 */
	public  int print() {
		return 5;
	}
}
