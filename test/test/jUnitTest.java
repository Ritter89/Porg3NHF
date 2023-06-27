package test;
import Game.*;
import junit.framework.Assert;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Point;
import org.junit.jupiter.api.Test;

/**
 * Egy osztály a JUnit teszteléshez
 * @author Ritter Máté
 *
 */
class jUnitTest {
	/**
	 * A player construktor müködését teszteli le
	 */
	@Test
	void playerConstruktor() {
		control c = new control();
		player pl = new player(c);
		int x,y;
		x = (int) pl.pGetter().getX();
		y = (int) pl.pGetter().getY();
		Assert.assertEquals(x, 145);
		Assert.assertEquals(y, 490);
	}
	/**
	 * a player Move() fügvényét teszteli le
	 */
	@Test
	void playerMove() {
		control c = new control();
		player pl = new player(c);
		pl.move(new Point(-2,-2));
		int x,y;
		x = (int) pl.pGetter().getX();
		y = (int) pl.pGetter().getY();
		Assert.assertEquals(x, 143);
		Assert.assertEquals(y, 488);
	}
	/**
	 * azt nézi meg hogy a player sebzödik-e ha ütközik egy enemy-vel
	 */
	@Test
	void playerDamged() {
		control c = new control();
		player pl = new player(c);
		Assert.assertEquals(pl.getHealth(), 3);
		pl.colide(new horizontal(1));
		Assert.assertEquals(pl.getHealth(), 2);
	}
	/**
	 * az ellenséges lövedék Move()-ját teszteli
	 */
	@Test
	void enemyBulletMove() {
		enemyBullet b = new enemyBullet(new Point(0,0));
		b.move();
		int x,y;
		x = (int) b.pGetter().getX();
		y = (int) b.pGetter().getY();
		Assert.assertEquals(x, 0);
		Assert.assertEquals(y, 1);
	}
	/**
	 * A horizontal construktor müködését teszteli le
	 */
	@Test
	void horizontalContruktor() {
		horizontal h = new horizontal(1);
		int x,y;
		x = (int) h.pGetter().getX();
		y = (int) h.pGetter().getY();
		Assert.assertEquals(x, 0);
		Assert.assertEquals(y, 25);
	}
	/**
	 *  a horizontal mozgását teszteli le
	 */
	@Test
	void horizontalMove() {
		horizontal h = new horizontal(1);
		h.move();
		h.move();
		h.move();
		h.move();
		int x,y;
		x = (int) h.pGetter().getX();
		y = (int) h.pGetter().getY();
		Assert.assertEquals(x, 1);
		Assert.assertEquals(y, 25);
	}
	/**
	 * a vertical konstruktorát teszteli le
	 */
	@Test
	void verticalContruktor() {
		vertical v = new vertical(1);
		int x,y;
		x = (int) v.pGetter().getX();
		y = (int) v.pGetter().getY();
		Assert.assertEquals(x, 25);
		Assert.assertEquals(y, 0);
	}
	/**
	 * a vertical mozgását teszteli le
	 */
	@Test
	void verticalMove() {
		vertical v = new vertical(1);
		v.move();
		v.move();
		v.move();
		v.move();
		int x,y;
		x = (int) v.pGetter().getX();
		y = (int) v.pGetter().getY();
		Assert.assertEquals(x, 25);
		Assert.assertEquals(y, 1);
	}
	/**
	 * az arc construktorát teszteli le
	 */
	@Test
	void arcConstruktor() {
		arc a = new arc(1);
		int x,y;
		x = (int) a.pGetter().getX();
		y = (int) a.pGetter().getY();
		Assert.assertEquals(x, 0);
		Assert.assertEquals(y, 0);
	}
	/**
	 * az arc mozgását teszteli le
	 */
	@Test
	void arcMove() {
		arc a = new arc(1);
		a.move();
		a.move();
		a.move();
	
		int x,y;
		x = (int) a.pGetter().getX();
		y = (int) a.pGetter().getY();
		Assert.assertEquals(x, 0);
		Assert.assertEquals(y, 1);
	}
	
	
	
	

}
