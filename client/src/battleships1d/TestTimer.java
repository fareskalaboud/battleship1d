package battleships1d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class TestTimer extends JLabel {
	
	private Timer secondsTimer;
	private final JLabel secondsRemainingLabel;
	private int timeRemaining;

	public TestTimer() {
		
		timeRemaining = 30;
		secondsRemainingLabel = new JLabel(String.valueOf(timeRemaining),
				JLabel.CENTER);

		secondsTimer = new Timer(1000, new CountdownTimerListener());

		secondsTimer.start();
	}
	public JLabel getLabel() {
		return secondsRemainingLabel;
	}
	class CountdownTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			timeRemaining -=1;
			
			if (timeRemaining > 0) {
				secondsRemainingLabel.setText(String.valueOf(timeRemaining));
			} else {
				secondsTimer.stop();
			}
		}
	}
}
