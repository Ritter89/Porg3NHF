package Game;

/**
 * ez tárolja el a pálya 1 sorát
 * @author Ritter Máté
 *
 */
public class mapPiece {
	int spawnFrame;
	int enemyType;
	int location;
	
	/**
	 * mapPiece konstruktora
	 * @param s	az hogy mikor spawnol le az enemy
	 * @param e	milyen enemy
	 * @param l	hova spawnol
	 */
	public mapPiece(int s, int e, int l ){
		spawnFrame = s;
		enemyType = e;
		location = l;
	}
	/**
	 * spawnframe getterje
	 * @return	vissza adja a spawnframet
	 */
	public int getSpawnFrame() {
		return spawnFrame;
	}
	/**
	 * enemyType getterje
	 * @return	vissza adja a enemyType
	 */
	public int getEnemyType() {
		return enemyType;
	}
	/**
	 * location getterje
	 * @return	vissza adja a location
	 */
	public int getLocation() {
		return location;
	}
}
