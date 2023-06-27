package Game;
import java.awt.*;
import java.awt.event.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;

/**
 * a játék inputját kezeli
 * @author Ritter Máté
 *
 */
public class input{
	//private Timer T = new Timer(10, new AnimationListener());
	private Timer T = new Timer("inputTimer");

	private JPanel panel;
	private control control;
	private static final String PRESSED = "pressed";
	private static final String RELEASED = "released";
	private Map<Direction, Boolean> dirMap = new EnumMap<>(Direction.class);
	private player p;
	private JFrame frame;
	
	/**
	 * ez tárolja el hogy melyik irány melyik gombhoz és miylen kordináta változásokhoz vezet
	 * @author Ritter Máté
	 *
	 */
	enum Direction {
		LEFT("Left", KeyEvent.VK_LEFT, -1, 0),
	    RIGHT("Right", KeyEvent.VK_RIGHT, 1, 0),
	    UP("Up", KeyEvent.VK_UP, 0, -1),
	    DOWN("Down", KeyEvent.VK_DOWN, 0, 1),
		MENU("Menu",KeyEvent.VK_ESCAPE,0,0);

	    private String name;
	    private int keyCode;
	    private int deltaX;
	    private int deltaY;
	    private Direction(String name, int keyCode, int deltaX, int deltaY) {
	        this.name = name;
	        this.keyCode = keyCode;
	        this.deltaX = deltaX;
	        this.deltaY = deltaY;
	    }
	    public String getName() {
	    	return name;
	    }
	    public int getKeyCode() {
	    	return keyCode;
	    }
	    public int getDeltaX() {
	    	return deltaX;
	    }
	    public int getDeltaY() {
	    	return deltaY;
	    }      
	}
	
	/**
	 * az input construktora
	 * @param pan	a panel amiben mozog a játékos
	 * @param unit	a játékos 
	 * @param cont	a fö vezélés 
	 * @param fram	a frame amiben van a játékos
	 */
	public input(JPanel pan, unit unit, control cont, JFrame fram) {
		panel = pan;
		frame = fram;
		control = cont;
		p = (player) unit;
		for (Direction dir : Direction.values()) {
	         dirMap.put(dir, Boolean.FALSE);
	     }
		setKeyBindings();
		T.scheduleAtFixedRate(new AnimationListener(), 0, 10);
	}
	
	/**
	 * a bekapot inputokat kezeli le
	 * @author Ritter Máté
	 *
	 */
	private class AnimationListener extends TimerTask {
		/**
		 * a bekapot inputokat kezeli le,
		 * nyilakra mozog a játékos,
		 * esc-re megáll a játék
		 */
		@Override
	    public void run() {
			for (Direction dir : Direction.values()) {
	            if (dirMap.get(dir)) {
	            	if(dir.getName() == "Menu" ) {
	            		dirMap.put(dir, Boolean.FALSE);
	            		popUpMenu();
	            		break;
	            	}
	            	else {
	            	p.move(new Point(dir.getDeltaX(),dir.getDeltaY())); 
	            	}
	            }
	         }
	    }
	}
	
	/**
	 * megállitja a T timert
	 */
	public void pause() {
		if(T != null) {
			T.cancel();
			T.purge();
			T = null;
		}
	}
	/**
	 * elinditja a T timert
	 */
	public void unPause() {
			T = new Timer("inputTimer");
			T.scheduleAtFixedRate(new AnimationListener(), 0, 15);
	}
	/**
	 * a játékbeli menü
	 */
	public void popUpMenu() {
		System.out.println("popUpMenu");
		pause();
		control.pause();
		p.pause();
		Object[] options = {"Menu",
                "Unpause"};
		int n = JOptionPane.showOptionDialog(frame,"PopUp Menu","Pause Menu", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(n==0) {
			unPause();
			control.unPause();
			p.unPause();
			control.startMenu();
			//control.gameOver(false);
		}
		else{
			unPause();
			control.unPause();
			p.unPause();
		}
	}
	
	/**
	 * hozzá rendeli a billenytüzet gomjaihoz a hozájuk valo akciokat
	 */
	private void setKeyBindings() {
	      int condition = panel.WHEN_IN_FOCUSED_WINDOW;
	      InputMap inputMap = panel.getInputMap(condition);
	      ActionMap actionMap = panel.getActionMap();
	      for (Direction dir : Direction.values()) {
	         KeyStroke keyPressed = KeyStroke.getKeyStroke(dir.getKeyCode(), 0, false);
	         KeyStroke keyReleased = KeyStroke.getKeyStroke(dir.getKeyCode(), 0, true);
	         inputMap.put(keyPressed, dir.toString() + PRESSED);
	         inputMap.put(keyReleased, dir.toString() + RELEASED);
	         actionMap.put(dir.toString() + PRESSED, new DirAction(dir, PRESSED));
	         actionMap.put(dir.toString() + RELEASED, new DirAction(dir, RELEASED));
	      }
	}
	
	/**
	 * 
	 * @author Ritter Máté
	 *
	 */
	private class DirAction extends AbstractAction {
	      private String pressedOrReleased;
	      private Direction dir;
	      
	      /**
	       * a dir action construktora
	       * @param dir
	       * @param pressedOrReleased	az hogy levan nyomva vagy pedig fel van engedve a gomb
	       */
	      public DirAction(Direction dir, String pressedOrReleased) {
	         this.dir = dir;
	         this.pressedOrReleased = pressedOrReleased;
	      }
	      /**
	       * ha lenyomodik a gomb megvéltoztaja lenyomotá
	       */
	      @Override
	      public void actionPerformed(ActionEvent evt) {
	         if (pressedOrReleased.equals(PRESSED)) {
	            dirMap.put(dir, Boolean.TRUE);
	         } else if (pressedOrReleased.equals(RELEASED)) {
	            dirMap.put(dir, Boolean.FALSE);
	         }
	      }
	   }
}
