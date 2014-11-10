package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * This class will create the bar from the top of every frame ( _ X)
 * 
 * @author GEORGE RADUTA
 * 
 */
public class BarPanel {
	private JPanel barPanel, buttonsPanel;
	private JLabel escapeLabel, minLabel, spaceLabel;
	private JFrame mainFrame;
	private String roomID;
	private AppManager am;

	/**
	 * Constructor
	 * 
	 * @param mainFrame
	 *            the frame where we want to add the Panel;
	 */
	public BarPanel(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.roomID = "";
	}

	public BarPanel(JFrame mainFrame, String roomID, AppManager am) {
		this.mainFrame = mainFrame;
		this.roomID = roomID;
		this.am = am;
	}

	/**
	 * 
	 * @return a JLabel representing the escapeButton
	 */
	public JLabel getEscapeButton() {
		return escapeLabel;
	}

	/**
	 * A JPanel with 2 JLabels( _ and X) which also contains the ActionListeners
	 * for this JLabels;
	 * 
	 * @return the new JPanel with _ X
	 */
	public JPanel getPanel() {

		barPanel = new JPanel();
		buttonsPanel = new JPanel();
		escapeLabel = new JLabel("CLOSE APP");
		spaceLabel = new JLabel("  |  ");
		minLabel = new JLabel("MINIMIZE");

		barPanel.setBackground(new Color(50, 50, 50));
		buttonsPanel.setBackground(new Color(50, 50, 50));

		escapeLabel.setFont(new Font("Garamond", Font.BOLD, 13));
		escapeLabel.setForeground(Color.WHITE);
		escapeLabel.setBorder(new EmptyBorder(1, 1, 1, 1));

		spaceLabel.setFont(new Font("Garamond", Font.BOLD, 13));
		spaceLabel.setForeground(Color.WHITE);

		minLabel.setFont(new Font("Garamond", Font.BOLD, 13));
		minLabel.setForeground(Color.WHITE);
		minLabel.setBorder(new EmptyBorder(1, 1, 1, 1));

		barPanel.setLayout(new BorderLayout());
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.add(minLabel);
		buttonsPanel.add(spaceLabel);
		buttonsPanel.add(escapeLabel);
		barPanel.add(buttonsPanel, BorderLayout.EAST);

		escapeLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				Border blackBorder = BorderFactory.createLineBorder(new Color(
						50, 50, 50));
				escapeLabel.setBorder(blackBorder);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// escapeLabel.setForeground(Color.DARK_GRAY);
				Border whiteBorder = BorderFactory
						.createLineBorder(Color.WHITE);
				escapeLabel.setBorder(whiteBorder);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (roomID.equals("")) {
					System.exit(0);
				} else {

					am.closeRoom(roomID);
					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Thanks for playing, we hope you enjoyed your game. Now go back and do your homework!",
									"Battleships 1-D",
									JOptionPane.INFORMATION_MESSAGE);

					System.exit(0);
				}

			}
		});
		minLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {

				Border blackBorder = BorderFactory.createLineBorder(new Color(
						50, 50, 50));
				minLabel.setBorder(blackBorder);

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				Border whiteBorder = BorderFactory
						.createLineBorder(Color.WHITE);
				minLabel.setBorder(whiteBorder);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				mainFrame.setState(Frame.ICONIFIED);
			}
		});

		return barPanel;
	}
}
