package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The class that will create a JFrame with a "Sink.gif" when the user hits a
 * ship
 * 
 * @author GEORGE RADUTA
 * 
 */
public class SplashGif {

	private JFrame gifFrame;

	/**
	 * Constructor which will run and stop the JFrame with the gif;
	 */
	public SplashGif() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}

				gifFrame = new JFrame("Gif Frame");
				gifFrame.setUndecorated(true);

				gifFrame.setLayout(new BorderLayout());
				createGifPanel();

				gifFrame.pack();
				gifFrame.setLocationRelativeTo(null);
				gifFrame.setVisible(true);

			}
		});
	}

	private ImageIcon splashGif;
	private JLabel gifLabel;
	private Timer gifTimer;
	private JPanel gifPanel;

	/**
	 * Creating the Panel with the gif image; Also adding functionality to the
	 * frame;
	 */
	public void createGifPanel() {
		gifPanel = new JPanel();

		splashGif = new ImageIcon("splash.gif");

		gifLabel = new JLabel();

		gifPanel.setSize(400, 400);

		gifLabel.setIcon(splashGif);

		gifTimer = new Timer(2500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gifFrame.dispose();
			}
		});
		gifTimer.setRepeats(false);
		gifTimer.restart();
		gifPanel.setLayout(new BorderLayout());
		gifPanel.add(gifLabel);
		gifFrame.add(gifPanel);
	}
}