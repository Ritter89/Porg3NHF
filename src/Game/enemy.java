package Game;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * az ellenségek absztak osztálya, az unit osztály leszármazotja
 * @author metin
 *
 */
public abstract class enemy extends unit{
	protected int health;
	double upgradeDrobChanche = 0.2;
	/**
	 * abstract függvény
	 * az ellenség mozgását vitelezi ki
	 */
	public abstract void move();
	
	/**
	 * egy lövedékell valo ütközést vitelez ki, 
	 * @param b ezzel a lövedékkel
	 * 
	 */
	public void colide(bullet b) {
		control.increaseScore(100);
		Random rnd = new Random();
		double d = rnd.nextDouble();
		if(d < upgradeDrobChanche) {
			boolean bool = rnd.nextBoolean();
			control.addUnit(new upgrade(p, bool));
		}
		control.removeUnit(this);
	}
	/**
	 * a horizontal enemynél felölvan irva, itt nem csináll semmit, de kell hogy ha egy enemy listán végig megyek amiben ömlesztve vannak az ellenségek akkor a horizontal lövését meg lehesen állitani
	 */
	public void stop() {
		
	}
	
}
