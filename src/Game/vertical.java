package Game;
import java.awt.Point;

/**
 * ez az osztály vitelezi ki a vertical ellenséget
 * @author Ritter Máté
 *
 */
public class vertical extends enemy {
	private double speed = 0.25;
	private double carryover = 0;
	/**
	 * az vertical konstruktora
	 * @param i	ez alapján dönti el hogy hova kerüljön az egység
	 */
	public vertical (int i) {
		switch(i){
			case 1:
				p= new Point(25,0);
				break;
			case 2:
				p= new Point(75,0);
				break;
			case 3:
				p= new Point(125,0);
				break;
			case 4:
				p= new Point(175,0);
				break;
			case 5:
				p= new Point(225,0);
				break;
			case 6:
				p= new Point(275,0);
				break;
			default:
				p= new Point(90,0);
		}
		size = new Point(12,8);
	}
	/**
	 * a vertical mozgását valositja meg
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
	 * 
	 * @return	visza adja hogy az egységhez hanyás számu sprite tartozik
	 */
	public  int print() {
		return 2;
	}
}
