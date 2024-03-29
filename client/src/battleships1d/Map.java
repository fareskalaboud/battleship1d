package battleships1d;

import javax.swing.*;

/**
 * @author faresalaboud
 */
public abstract class Map extends JPanel {
    protected UIManager uiManager;
    protected Room room;

    protected GameButton[][] mapTiles;
    //private Ship[] ships;

    protected JPanel mapPanel;
    protected JPanel healthPanel;
    protected JProgressBar healthBar;

    public abstract void setUpUI();
    	
}
