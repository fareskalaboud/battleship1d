package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Alexander Hanbury-Botherway
 * 
 */
@SuppressWarnings("serial")
public class Lobby extends JFrame {

	private Vector<RoomData> privateRooms = new Vector<RoomData>();
	private Vector<RoomData> publicRooms = new Vector<RoomData>();
	private JList<RoomData> jlPublicRooms;
	private JList<RoomData> jlPrivateRooms;
	private AppManager manager;
	private boolean privateRoomSelected;

	public Lobby(AppManager manager) {
		super("Battleships 1-D: Lobby");
		this.manager = manager;
		setUpUI();
		privateRoomSelected = false;
		setActionListeners();
	}

	public Lobby() {
	}

	/**
	 * @author Alexander Hanbury-Botherway
	 */
	private JPanel jpMain, jpMainMain, barPanel, jpCenter, jpCreateRoom,
			jpCenterNorth, jpCenterSouth, jpEast, jpEastNorth, jpEastCenter,
			jpEastSouth;
	private JScrollPane jspPublicRooms, jspPrivateRooms;
	private final JLabel jlPassword = new JLabel("Room Password: ");
	private final JLabel jlConfirmPassword = new JLabel("Confirm Password: ");
	private final JLabel jlblPublicRooms = new JLabel("Public Rooms: ");
	private final JLabel jlblPrivateRooms = new JLabel("Private Rooms: ");
	private final JPasswordField jpfConfirmPassword = new JPasswordField(20);
	private final JButton jbPublic = new JButton("Public");
	private final JButton jbPrivate = new JButton("Private");
	private final JButton jbCreateRoom = new JButton("Create Room");
	private JButton jbRefresh = new JButton();
	private final JPasswordField jpfRoomPassword = new JPasswordField(20);

	/**
	 * Set up the JFrame's UI.
	 */
	public void setUpUI() {
		// Main panel
		jpMain = new JPanel(new BorderLayout());
		jpMainMain = new JPanel(new BorderLayout());
		barPanel = new JPanel();
		this.setUndecorated(true);
		// BAR PANEL
		BarPanel test = new BarPanel(this);
		barPanel = test.getPanel();
		DragFrame testDrag = new DragFrame(barPanel);
		barPanel.addMouseListener(testDrag);
		barPanel.addMouseMotionListener(testDrag);
		jpMainMain.setLayout(new BorderLayout());
		jpMainMain.add(barPanel, BorderLayout.NORTH);

		jpMainMain.add(jpMain, BorderLayout.CENTER);

		// Rooms
		jpCenter = new JPanel(new BorderLayout());
		jpCenter.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Public Rooms
		jpCenterNorth = new JPanel(new BorderLayout());
		jpCenterNorth.add(jlblPublicRooms, BorderLayout.NORTH);
		jlPublicRooms = new JList<RoomData>(publicRooms);
		jspPublicRooms = new JScrollPane(jlPublicRooms);

		jpCenterNorth.add(jspPublicRooms, BorderLayout.CENTER);
		jpCenter.add(jpCenterNorth, BorderLayout.NORTH);

		// Private Rooms
		jpCenterSouth = new JPanel(new BorderLayout());
		jpCenterSouth.add(jlblPrivateRooms, BorderLayout.NORTH);
		jlPrivateRooms = new JList<RoomData>(privateRooms);
		jspPrivateRooms = new JScrollPane(jlPrivateRooms);
		jpCenterSouth.add(jspPrivateRooms);
		jpCenter.add(jpCenterSouth, BorderLayout.SOUTH);

		jpMain.add(jpCenter, BorderLayout.CENTER);

		// Create room options
		jpEast = new JPanel(new BorderLayout());
		jpEast.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Room variable fields
		jpEastCenter = new JPanel(new GridLayout(5, 1));
		// Room password (not visible when public is selected)
		jlPassword.setVisible(false);
		jpEastCenter.add(jlPassword);

		jpfRoomPassword.setEnabled(false);
		jpfRoomPassword.setVisible(false);
		jpEastCenter.add(jpfRoomPassword);

		// Room confirm password (not visible when public is selected)

		jlConfirmPassword.setVisible(false);
		jpEastCenter.add(jlConfirmPassword);

		jpfConfirmPassword.setEnabled(false);
		jpfConfirmPassword.setVisible(false);
		jpEastCenter.add(jpfConfirmPassword);

		// Create panel for create button
		jpCreateRoom = new JPanel();
		jpCreateRoom.setBorder(new EmptyBorder(10, 0, 10, 0));
		jpCreateRoom.add(jbCreateRoom);

		// Add room button
		jbCreateRoom.setPreferredSize(new Dimension(270, 40));
		jpEastCenter.add(jpCreateRoom);
		jpEast.add(jpEastCenter, BorderLayout.CENTER);

		// Add refresh button
		BufferedImage buttonIcon = null;

		try {
			buttonIcon = ImageIO.read(new File("refresh.png"));
			jbRefresh = new JButton(new ImageIcon(buttonIcon));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jbRefresh.setContentAreaFilled(false);
		jbRefresh.setBorder(BorderFactory.createEmptyBorder());
		jpEastNorth = new JPanel(new BorderLayout());
		jpEastNorth.add(jbRefresh, BorderLayout.EAST);
		jpEast.add(jpEastNorth, BorderLayout.NORTH);

		// Public/private option
		jpEastSouth = new JPanel(new FlowLayout());

		// Public button (chosen as default)
		jbPublic.setEnabled(false);
		jpEastSouth.add(jbPublic);

		// Private button
		jpEastSouth.add(jbPrivate);

		jpEast.add(jpEastSouth, BorderLayout.SOUTH);

		jpMain.add(jpEast, BorderLayout.EAST);

		add(jpMainMain);

		pack();

		refreshLists();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setColourTheme();
		setSize(new Dimension(700, 400));
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Set the JFrame's colour theme.
	 */
	private void setColourTheme() {
		jpMain.setBorder(new EmptyBorder(0, 10, 10, 10));
		jpMain.setBackground(new Color(50, 50, 50));

		jpCenter.setBackground(new Color(90, 90, 90));
		jpCenterNorth.setBackground(new Color(90, 90, 90));
		jpCenterSouth.setBackground(new Color(90, 90, 90));
		jpEast.setBackground(new Color(90, 90, 90));
		jpEastCenter.setBackground(new Color(90, 90, 90));
		jpEastSouth.setBackground(new Color(90, 90, 90));
		jpCreateRoom.setBackground(new Color(90, 90, 90));
		jpEastNorth.setBackground(new Color(90, 90, 90));

		jlPassword.setForeground(new Color(255, 255, 255));
		jlPassword.setFont(new Font("Monospaced", Font.BOLD, 15));

		jlblPrivateRooms.setForeground(new Color(255, 255, 255));
		jlblPrivateRooms.setFont(new Font("Monospaced", Font.BOLD, 15));

		jlblPublicRooms.setForeground(new Color(255, 255, 255));
		jlblPublicRooms.setFont(new Font("Monospaced", Font.BOLD, 15));

		jlConfirmPassword.setForeground(new Color(255, 255, 255));
		jlConfirmPassword.setFont(new Font("Monospaced", Font.BOLD, 15));

		if (!jbPublic.isEnabled()) {
			jbPublic.setBackground(new Color(110, 110, 110));
			jbPrivate.setBackground(new Color(160, 160, 160));

			jbPublic.setForeground(new Color(20, 20, 20));
			jbPrivate.setForeground(new Color(255, 255, 255));
		} else {
			jbPrivate.setBackground(new Color(110, 110, 110));
			jbPublic.setBackground(new Color(160, 160, 160));

			jbPrivate.setForeground(new Color(20, 20, 20));
			jbPublic.setForeground(new Color(255, 255, 255));
		}

		jbPublic.setFont(new Font("Garamond", Font.BOLD, 15));
		jbPrivate.setFont(new Font("Garamond", Font.BOLD, 15));

		jbCreateRoom.setBackground(new Color(160, 160, 160));
		jbCreateRoom.setForeground(new Color(255, 255, 255));
		jbCreateRoom.setFont(new Font("Garamond", Font.BOLD, 15));
	}

	/**
	 * @return the list of private rooms at the moment
	 */
	public Vector<RoomData> privateRooms() {
		return privateRooms;
	}

	/**
	 * @return the list of public rooms at the moment
	 */
	public Vector<RoomData> publicRooms() {
		return publicRooms;
	}

	/**
	 * Refreshes the JLists to display the latest set of rooms.
	 */
	public void refreshRoomLists() {
		jlPublicRooms.setListData(publicRooms);
		jlPrivateRooms.setListData(privateRooms);
	}

	/**
	 * @author Alexander Hanbury-Botherway
	 * @param password
	 */
	public RoomData createRoom(String password) {
		RoomData newRoom;

		newRoom = new RoomData(manager.getMainPlayer(), password);

		System.err.println("response from AM: " + newRoom.getRoomID());
		if (newRoom.getRoomID().equals("Error: User In Room")) {
			JOptionPane.showMessageDialog(new JFrame(),
					"You can only create one room!", "Error", 0);
			System.out.println("Error in room");
			return null;

		} else {
			refreshLists();
			new Room(newRoom.getRoomID(), manager);
		}
		return newRoom;

	}

	/**
	 * Sets the action listeners for some of the UI components.
	 */
	public void setActionListeners() {
		// When user double clicks on a private room
		jlPrivateRooms.addMouseListener(new MouseListener() {
			@Override
			/**
			 * @author Alexander Hanbury-Botherway
			 */
			public void mouseClicked(MouseEvent arg0) {

				if (arg0.getClickCount() == 2) {

					int position = jlPrivateRooms.getSelectedIndex();
					if (position == -1) {
						System.err.println("No Room Selected");
						return;
					}

					RoomData selectedRoom = jlPrivateRooms.getSelectedValue();

					String roomID = selectedRoom.getRoomID();

					JPanel panel = new JPanel();
					JPasswordField jpfRoomPasswordCheck = new JPasswordField(10);
					panel.add(new JLabel("Enter room password:"));
					panel.add(jpfRoomPasswordCheck);
					String[] options = new String[] { "OK", "Cancel" };
					int option = JOptionPane.showOptionDialog(null, panel,
							"Password", JOptionPane.NO_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options,
							options[1]);
					if (option == 0) { // pressing OK button
						char[] password = jpfRoomPasswordCheck.getPassword();
						if (jlPrivateRooms.getSelectedValue().getPassword()
								.equals(new String(password))) {
							boolean success = manager.joinRoom(roomID);
							if (success) {
								JOptionPane.showMessageDialog(new JFrame(),
										"Now in " + roomID);
								new Room(roomID, manager);
								setVisible(false);
							} else {
								JOptionPane.showMessageDialog(new JFrame(),
										"Room is already full");
							}
						} else {
							JOptionPane.showMessageDialog(new JFrame(),
									"Password is incorrect");
						}

					}

					refreshRoomLists();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}
		});

		// Action Listeners
		// Public button action listener
		jbPublic.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				jbPublic.setEnabled(false);
				jbPrivate.setEnabled(true);
				jpfRoomPassword.setEnabled(false);
				jpfRoomPassword.setVisible(false);
				jlPassword.setVisible(false);
				jpfConfirmPassword.setEnabled(false);
				jpfConfirmPassword.setVisible(false);
				jlConfirmPassword.setVisible(false);
				privateRoomSelected = false;
				setColourTheme();
			}
		});

		// Private button action listener
		jbPrivate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				jbPrivate.setEnabled(false);
				jbPublic.setEnabled(true);
				jpfRoomPassword.setEnabled(true);
				jpfRoomPassword.setVisible(true);
				jlPassword.setVisible(true);
				jpfConfirmPassword.setEnabled(true);
				jpfConfirmPassword.setVisible(true);
				jlConfirmPassword.setVisible(true);
				privateRoomSelected = true;
				setColourTheme();
			}
		});

		jbRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshLists();
			}
		});

		jlPublicRooms.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int position = jlPublicRooms.getSelectedIndex();
					if (position == -1) {
						System.err.println("No Room Selected");
						return;
					}

					String roomID = jlPublicRooms.getSelectedValue()
							.getRoomID();
					if (jlPublicRooms.getSelectedIndex() >= 0) {
						boolean success = manager.joinRoom(roomID);
						if (success) {
							new Room(publicRooms.get(position).getRoomID(),
									manager);
							setVisible(false);
						} else {
							JOptionPane.showMessageDialog(new JFrame(),
									"Room is already full");
						}
					}

				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}
		});

		jbCreateRoom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String sPassword = "";

				if (privateRoomSelected) {
					char[] password = jpfRoomPassword.getPassword();
					char[] confirmPassword = jpfConfirmPassword.getPassword();

					if (passwordsMatch(password, confirmPassword)) {
						for (char x : password) {
							sPassword += x;
						}
						createRoom(sPassword);
						sPassword = " ";
						refreshLists();
						return;
					} else {
						JOptionPane.showMessageDialog(new JFrame(),
								"Passwords don't match", "Error", 0);
						refreshLists();
						return;
					}
				} else {
					createRoom(" ");
					setVisible(false);
					return;
				}
			}
		});
	}

	/**
	 * Check if password and password confirmation match.
	 * 
	 * @param p1
	 *            Password.
	 * @param p2
	 *            Password confirmation.
	 * @return Whether they are equal.
	 */
	private boolean passwordsMatch(char[] p1, char[] p2) {
		if (p1.length != p2.length) {
			return false;
		}

		for (int i = 0; i < p1.length; i++) {
			if (p1[i] != p2[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Refreshes the lists by contacting the server
	 * 
	 * @author Alexander Hanbury-Botherway
	 */
	private void refreshLists() {
		Vector<RoomData> vAllRooms = manager.getRoomsFromServer();

		publicRooms = new Vector<RoomData>();
		privateRooms = new Vector<RoomData>();

		for (int i = 0; i < vAllRooms.size(); i++) {
			RoomData currentRoom = vAllRooms.get(i);
			if (currentRoom.isPrivate()) {
				privateRooms.add(currentRoom);
			} else {
				publicRooms.add(currentRoom);
			}
		}

		jlPublicRooms.setListData(publicRooms);
		jlPrivateRooms.setListData(privateRooms);
		return;

	}
}
