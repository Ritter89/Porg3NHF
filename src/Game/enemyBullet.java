package Game;
import java.awt.Point;
import java.util.Random;

/**
 * az ellenség lövedékjeit vallositja meg
 * @author Ritter Máté
 *
 */
public class enemyBullet extends enemy{
	private int speed = 1;
	private double carryover = 0;
	
	/**
	 * a lövedék konstruktora
	 * @param point	ez lesz a lövedék kezdö pontja
	 */
	public enemyBullet(Point point) {
		p = point;
		size = new Point(4,8);
	}
	
	/**
	 * a lövedék mozgását valositja meg 
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
	 * @return visza adja hogy melyik sprite az övé
	 */
	public int print() {
		return 1;
	}
	
	/**
	 * a játékos lövedékével valo találkozást vitelezi ki
	 */
	public void colide(bullet b) {
		control.removeUnit(this);
	}
}
