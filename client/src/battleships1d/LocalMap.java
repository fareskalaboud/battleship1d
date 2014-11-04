package battleships1d;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author faresalaboud
 */
public class LocalMap extends Map {

    public LocalMap(Room room) {
        super();
        // TODO: Fix next line
        this.setUpUI();

    }



    public void setUpHealthBar() {

    }

    public void setUpUI() {
        this.setLayout(new BorderLayout());
        this.setSize(300, 300);

        // Initialise UI objects
        mapPanel = new JPanel(new GridLayout(10, 10));
        healthPanel = new JPanel();
        healthBar = new JProgressBar(0, 17);

        // Set up health bar
        healthBar.setForeground(new Color(0, 169, 43));
        healthBar.setValue(healthBar.getMaximum());
//        healthBar.setMaximumSize();

        // Start adding everything to the panels
        healthPanel.add(healthBar);

        // Add the panels to the map
        this.add(mapPanel, BorderLayout.CENTER);
        mapPanel.add(healthPanel, BorderLayout.NORTH);

    }

    public void setActionListeners() {

    }
    public LocalButton getClikedButton(int row, int col) {
    	// TO DO :  Fares, return the button at given coordinates;
    	return new LocalButton(0, 0);
    	
    }
}
