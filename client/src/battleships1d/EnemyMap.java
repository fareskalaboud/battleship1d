package battleships1d;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author faresalaboud
 */
public class EnemyMap extends Map {

    public EnemyMap() {
        super();
        // TODO: Fix next line
        this.setUpUI(new EnemyButton(1,1));

    }

    public static void main(String args[]) {
        EnemyMap map = new EnemyMap();
        JFrame frame = new JFrame();
        frame.add(map);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void setUpHealthBar() {

    }

    public void setUpUI(GameButton btn) {
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

    }

    public void setActionListeners() {

    }
}
