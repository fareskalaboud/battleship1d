package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

/**
 *
 * @author faresalaboud
 */
public class EnemyMap extends Map {

	EnemyButton[][] enemyButtons;
	boolean[][] hasBeenClicked;
	JPanel topPanel;
	JPanel bottomPanel;
	JButton ready;
	
	
    public EnemyMap(Room room) {
        super();
        this.room = room;
        enemyButtons = new EnemyButton[10][10];
        hasBeenClicked = new boolean[10][10];
        for(int i = 0; i < 10; i++){
        	for(int j = 0; j < 10; j++){
        		hasBeenClicked[i][j] = false;
        	}
        }
        
        
        this.setUpUI();
        
    }

    public void setUpHealthBar() {

    }

    public void setUpUI() {
        this.setLayout(new BorderLayout());
        this.setSize(300, 300);
        
        

        // Initialise UI objects
        mapPanel = new JPanel(new GridLayout(10, 10));
        
        
        addButtons();
        
        bottomPanel = new JPanel();
        ready = new JButton("Ready");
        ready.addActionListener(new ActionListener(){
        	
        	@Override
        	public void actionPerformed(ActionEvent arg0) {
        		// TODO Auto-generated method stub
        		
        	}
        	
        });
        
        bottomPanel.add(ready);
        
        
        Border borderPanel = BorderFactory.createEmptyBorder(6, 0, 5, 0);
        
        
        
        topPanel = new JPanel();
        topPanel.setBorder(borderPanel);
        
        healthPanel = new JPanel();
        healthBar = new JProgressBar(0, 17);

        // Set up health bar
        healthBar.setForeground(new Color(169, 107, 39));
        healthBar.setValue(healthBar.getMaximum());
//        healthBar.setMaximumSize();

        // Start adding everything to the panels
        healthPanel.add(healthBar);
        
        topPanel.add(healthPanel);

        // Add the panels to the map
        this.add(mapPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        
        
        
        
        
        
        setColourTheme();

    }
    
    
    private void setColourTheme() {
    	topPanel.setBackground(new Color(90, 90, 90));
		mapPanel.setBackground(new Color(90, 90, 90));
		healthPanel.setBackground(new Color(90, 90, 90));
		bottomPanel.setBackground(new Color(90, 90, 90));
		
		ready.setBackground(new Color(160, 160, 160));
		
		ready.setForeground(new Color(255, 255, 255));
		
		ready.setFont(new Font("Garamond", Font.BOLD, 15));
		
		
	}

	//only add buttons when room is established, "ready" 
    //could also be in the Room class
    public void addButtons(){
    	for (int i = 0; i<10; i++){
    		for (int j = 0; j<10; j++){
    			final int row = i;
    			final int column = j;
    			enemyButtons[i][j] = new EnemyButton(i, j, room);
    			enemyButtons[i][j].setBackground(Color.gray);
    			enemyButtons[i][j].addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(!hasBeenClicked[row][column]){
							enemyButtons[row][column].playButton();
							hasBeenClicked[row][column] = true;
							updateHealthBars();
						}
					}
    				
    			});
    			mapPanel.add(enemyButtons[i][j]);
    			
    			
    		}
    	}
    }

    public void setActionListeners() {

    }
    
    public void updateHealthBars(){
    	int counter = healthBar.getMaximum();
    	for(int i = 0; i < 10; i++){
    		for(int j = 0; j < 10; j++){
    			if(enemyButtons[i][j].getState() == EnemyButtonState.HIT){
    				counter--;
    			}
    		}
    	}
    	healthBar.setValue(counter);
    }

}
