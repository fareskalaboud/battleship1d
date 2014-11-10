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

public class BoomGif {

	private JFrame gifFrame;

	public BoomGif() {
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

	private ImageIcon boomGif;
	private JLabel gifLabel;
	private Timer gifTimer;
	private JPanel gifPanel;

	public void createGifPanel() {
		gifPanel = new JPanel();
		gifPanel.setBorder(BorderFactory
				.createLineBorder(Color.BLACK, 10, true));

		boomGif = new ImageIcon("boom.gif");

		gifLabel = new JLabel();

		gifPanel.setSize(400, 400);

		gifLabel.setIcon(boomGif);

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
		new BoomGif();
	}

}