package battleships1dAI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class EnemyMap extends JFrame{
	
	protected JButton[][] buttons;
	protected boolean[][] hasBeenHit;
	protected String[][] unitSquares;
	protected int moveCounter = 0;
	protected JTextArea counterTextArea;
	
	public EnemyMap(Map map){
		unitSquares = map.getUnitSquares();
		buttons = new JButton[10][10];
		this.hasBeenHit = new boolean[10][10];
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10 ; j++){
				this.hasBeenHit[i][j] = false;
			}
		}
		
		setUpUI();
	}
	
	public void setUpUI(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10, 10));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		JLabel counterLabel = new JLabel("Moves done: ");
		counterTextArea = new JTextArea(""+moveCounter);
		
		
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				final int row = i;
				final int column = j;
				buttons[i][j] = new JButton("");
				buttons[i][j].addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(!wasShot(row, column)){
							
							hitSquare(row, column);
							if(isHit(row, column) == true){
								buttons[row][column].setText("X");
								} else{
								buttons[row][column].setText("O");
							}
							moveCounter++;
							
							counterTextArea.setText(""+moveCounter);
							
							if(checkWin()){
								JOptionPane.showMessageDialog(new JFrame(), "You win");
							}
						}
					
					}
					
				});
				
				mainPanel.add(buttons[i][j]);
			}
		}
		

		
		bottomPanel.add(counterLabel);
		bottomPanel.add(counterTextArea);
		
		
		add(mainPanel);
		add(bottomPanel, BorderLayout.SOUTH);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
		
		
	}
	
	public void hitSquare(int row, int column){
		hasBeenHit[row][column] = true;
	}
	
	public boolean wasShot(int row, int column){
		if(row <= 9 && row >= 0 && column <= 9 && column >= 0){
			if(hasBeenHit[row][column] == false){
				return false;
			} else{
				return true;
			}
		}
		return true;


	}
	
	public boolean isHit(int row, int column){
		if(!unitSquares[row][column].equals("")){
			return true;
		} else{
			return false;
		}
	}
	
	public boolean checkWin(){
		boolean isWin = true;
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10 ; j++){
				if(!unitSquares[i][j].equals("")&&hasBeenHit[i][j] == false){
					isWin = false;
				}
			}
		}
		
		
		return isWin;
	}
	
}
