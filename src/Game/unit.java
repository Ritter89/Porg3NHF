package Game;
import java.awt.Point;

/**
 * az egységek absztrak osztálya
 * @author Ritter Máté
 *
 */
public abstract class unit{
	protected Point p;
	protected Point size;
	/**
	 * absztrak egység mozgás
	 */
	public abstract void move();
	/**
	 * 
	 * @return	visza adja hogy az egységhez hanyás számu sprite tartozik
	 */
	public abstract int print();
	/**
	 * 
	 * @return visza adja az egység pozicioját
	 */
	public Point pGetter() {return p;}
}
