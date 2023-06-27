package Game;
import java.awt.Point;

/**
 * Ez az osztály a körivet betevö ellenséget vitelezi ki, az ellenség osztály leszármazotja
 * @author Ritter Máté
 */
public class arc extends enemy{
	private double speed = 0.5;
	private double carryOverY = 0;
	private double carryOverX = 0;
	private int startingDir = 0;
	private double direction = 1;
	private double arcRadiusModifier = 0.001;
	
	/**
	 * az arc konstruktora
	 * @param i	ez alapján dönti el hogy hova kerüljön az egység
	 */
	public arc (int i) {
		switch(i){
			case 1:
				p = new Point(0,0);
				startingDir = 1;
				break;
			case 2:
				p = new Point(290,0);
				startingDir = -1;
				break;
			
			default:
				p = new Point(90,0);
				startingDir = 1;
		}
		size = new Point(10,10);
	}
	
	/**
	 * egy kör iven mozgatja az egységet
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
		deltaYWithOverflow = (speed * (direction)) + carryOverY;
		deltaXWithOverflow = (speed * (1 - Math.abs(direction) )+ carryOverX) ;
		carryOverX = (deltaXWithOverflow % 1);
		carryOverY = (deltaYWithOverflow % 1);
		int deltaY = (int) (deltaYWithOverflow - carryOverY);
		int deltaX = (int) (deltaXWithOverflow - carryOverX);
		deltaX =  vegX + (startingDir *deltaX);
		deltaY = vegY + deltaY;	
        direction -= arcRadiusModifier;
        p.setLocation(deltaX, deltaY);
		
	}
	
	/**
	 * @return visza adja hogy melyik sprite az övé
	 */
	public  int print() {
		return 4;
	}

}
