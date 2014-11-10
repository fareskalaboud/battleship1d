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

public class SplashGif {

	private JFrame gifFrame;

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
				
				gifFrame.setLocationRelativeTo(null);
				gifFrame.setVisible(true);
				gifFrame.pack();

			}
		});
	}

	private ImageIcon splashGif;
	private JLabel gifLabel;
	private Timer gifTimer;
	private JPanel gifPanel;

	public void createGifPanel() {
		gifPanel = new JPanel();
		gifPanel.setBorder(BorderFactory
				.createLineBorder(Color.BLACK, 10, true));

		splashGif = new ImageIcon("splash.gif");

		gifLabel = new JLabel();

		gifPanel.setSize(400, 400);

		gifLabel.setIcon(splashGif);

		gifTimer = new Timer(2500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		gifTimer.setRepeats(false);
		gifTimer.restart();
		gifPanel.setLayout(new BorderLayout());
		gifPanel.add(gifLabel);
		gifFrame.add(gifPanel);
	}

	public static void main(String[] args) {
		new SplashGif();
	}

}