package Game;
/**
* egy szeriazálhato osztály ami a top 20 játékos pontszámát menti el
* @author Ritter Máté
*
*/
public class topLista implements java.io.Serializable{
		private int toplista[] = new int[20];
		private static final long serialVersionUID = 0;
		/**
		 * a toplista konstruktorja
		 * @param vector	ez a top 20 pontszám
		 */
		public topLista(int [] vector) {
			toplista = new int[vector.length];
			System.arraycopy( vector, 0, toplista,0, vector.length);
		}
		/**
		 * 
		 * @param i	ezt a pontszámot probálja bele tenni a top 20 sázmba ha elég nagy
		 * @return	viszatér hogy hova birta teni a számot
		 */
		public int add(int i) {
			int u = 0;
			int temp = 0;
			for(;u < toplista.length;u++) {
				if(toplista[u] < i) {
					temp = toplista[u];
					toplista[u] = i;
					i = temp;
				}
			}
			return u;
		}
		
		/**
		 * 
		 * @return	vissza tér a top 20 pontszámal
		 */
		public int[] gettoplista() {
			return toplista;
		}
}
