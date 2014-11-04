package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author faresalaboud
 */
public class EnemyMap extends Map {

    public EnemyMap(Room room) {
        super();
        this.room = room;
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
        healthBar.setForeground(new Color(169, 107, 39));
        healthBar.setValue(healthBar.getMaximum());
//        healthBar.setMaximumSize();

        // Start adding everything to the panels
        healthPanel.add(healthBar);

        // Add the panels to the map
        this.add(mapPanel, BorderLayout.CENTER);
        mapPanel.add(healthPanel, BorderLayout.NORTH);
        
        addButtons();

    }
    
    public void addButtons(){
    	for (int i = 0; i<10; i++){
    		for (int j = 0; j<10; j++){
    			mapPanel.add(new EnemyButton(i,j, room));
    		}
    	}
    }

    public void setActionListeners() {

    }

}
