package Game;
import java.awt.Point;

/**
 * Ez az osztály a játékos lövedékeit vitelezi ki, az unit osztály leszármazotja
 * @author Ritter Máté
 *
 */
public class bullet extends unit{
	private double direction;
	private int speed = 4;
	private double carryOverY = 0;
	private double carryOverX = 0;

	/**
	 * ez a lövedék construktora
	 * @param point	ez a lövedék kezdö pozicioja
	 * @param d	ez a lövedék irány
	 */
	public bullet(Point point,double d){
		direction = d;
		p = point;
		size = new Point(4,8);
		//moveTimer.start();
	}
	
	/**
	 * @return visza adja hogy melyik sprite az övé
	 */
	public  int print() {
		return 1;
	}
	
	/**
	 * amikor ütközik egy ellenségel, eltünik
	 * @param e	erre az ellenségre hivja meg hogy ötközöt egy lövedékel
	 */
	public void colide(enemy e) {
		e.colide(this);
		control.removeUnit(this);
	}
	
	/**
	 * a lövedék mozgását valositja meg
	 */
	public void move() {
		int vegX = (int) p.getX();
		int vegY = (int) p.getY();
		if (vegY < 0 || vegX < 0 || vegX > 300) {
	        control.removeUnit(this);
	        return;
	    }
		double deltaYWithOverflow = 0;
		double deltaXWithOverflow = 0;
		deltaYWithOverflow = -1 * (speed * (direction)) + carryOverY;
		deltaXWithOverflow = (speed * (1 - Math.abs(direction) )+ carryOverX) ;
		carryOverX = (deltaXWithOverflow % 1);
		carryOverY = (deltaYWithOverflow % 1);
		int deltaY = (int) (deltaYWithOverflow - carryOverY);
		int deltaX = (int) (deltaXWithOverflow - carryOverX);
		deltaX =  vegX + (deltaX);
		deltaY = vegY + deltaY;	
        p.setLocation(deltaX, deltaY);
        return;
    }
}
	

