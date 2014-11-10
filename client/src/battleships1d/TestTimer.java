package battleships1d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * JLabel with the Timer for the user
 * 
 * @author GEORGE RADUTA
 * 
 */
public class TestTimer extends JLabel {

	private Timer secondsTimer;
	private final JLabel secondsRemainingLabel;
	private int timeRemaining;

	/**
	 * Constructor which will create the JLabel
	 */
	public TestTimer() {

		timeRemaining = 30;
		secondsRemainingLabel = new JLabel(String.valueOf(timeRemaining),
				JLabel.CENTER);

		secondsTimer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timeRemaining -= 1;

				if (timeRemaining > 0) {
					secondsRemainingLabel.setText(String.valueOf(timeRemaining));
				} else {
					secondsTimer.stop();
				}
				
			}
		});

		secondsTimer.start();
	}

	/**
	 * Returning the JLabel
	 * 
	 * @return
	 */
	public JLabel getLabel() {
		return secondsRemainingLabel;
	}
}
