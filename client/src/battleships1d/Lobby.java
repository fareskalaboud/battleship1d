package battleships1d;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * 
 * @author Alexander Hanbury-Botherway
 *
 */
@SuppressWarnings("serial")
public class Lobby extends JFrame {

	private Vector<Room> privateRooms = new Vector<Room>();
	private Vector<Room> publicRooms = new Vector<Room>();
	
	public Lobby() {
		super("Battleships 1-D: Lobby");
		setUpUI();
	}

	/**
	 * @author Alexander Hanbury-Botherway
	 */
	public void setUpUI() {
		//Rooms
		JPanel jpCenter = new JPanel(new BorderLayout());
			
			//Public Rooms
			JPanel jpCenterNorth = new JPanel(new BorderLayout());
				jpCenterNorth.add(new JLabel("Public Rooms: "), BorderLayout.NORTH);
				JList<Room> jlPublicRooms = new JList<Room>(publicRooms);
				JScrollPane jspPublicRooms = new JScrollPane(jlPublicRooms);
				jpCenterNorth.add(jspPublicRooms, BorderLayout.CENTER);
			jpCenter.add(jpCenterNorth,BorderLayout.NORTH);
			
			//Private Rooms
			JPanel jpCenterSouth = new JPanel(new BorderLayout());
				jpCenterSouth.add(new JLabel("Private Rooms: "), BorderLayout.NORTH);
				JList<Room> jlPrivateRooms = new JList<Room>(privateRooms);
				JScrollPane jspPrivateRooms = new JScrollPane(jlPrivateRooms);
				jpCenterSouth.add(jspPrivateRooms);
			jpCenter.add(jpCenterSouth,BorderLayout.SOUTH);
		
		add(jpCenter, BorderLayout.CENTER);
		
		//Create room options		
		JPanel jpEast = new JPanel(new BorderLayout());
		
			//Room variable fields
			JPanel jpEastCenter = new JPanel(new GridLayout(5,1));
				// Room name
				jpEastCenter.add(new JLabel("Room Name:"));
				
				JTextField jtfRoomName = new JTextField(20);
				jpEastCenter.add(jtfRoomName);
			
				// Room password (not visible when public is selected)
				final JLabel jlPassword = new JLabel("Room Password: ");
				jlPassword.setVisible(false);
				jpEastCenter.add(jlPassword);
				
				final JPasswordField jpfRoomPassword = new JPasswordField(20);
				jpfRoomPassword.setEnabled(false);
				jpfRoomPassword.setVisible(false);
				jpEastCenter.add(jpfRoomPassword);
			
				// Create room button
				JButton jbCreateRoom = new JButton("Create Room");
				jpEastCenter.add(jbCreateRoom);
				
			jpEast.add(jpEastCenter, BorderLayout.CENTER);
			
			// Public/private option
			JPanel jpEastNorth = new JPanel(new FlowLayout());
			
				//Public button (chosen as default)
				final JButton jbPublic = new JButton("Public");
				jbPublic.setEnabled(false);
				jpEastNorth.add(jbPublic);
				
				//Private button
				final JButton jbPrivate = new JButton("Private");
				jpEastNorth.add(jbPrivate);
			
				// Action Listeners
					// Public button action listener
					jbPublic.addActionListener(new ActionListener(){
			
						public void actionPerformed(ActionEvent arg0) {
							jbPublic.setEnabled(false);
							jbPrivate.setEnabled(true);
							jpfRoomPassword.setEnabled(false);
							jpfRoomPassword.setVisible(false);
							jlPassword.setVisible(false);
						}});
					
					// Private button action listener
					jbPrivate.addActionListener(new ActionListener(){
			
						public void actionPerformed(ActionEvent arg0) {
							jbPrivate.setEnabled(false);
							jbPublic.setEnabled(true);
							jpfRoomPassword.setEnabled(true);
							jpfRoomPassword.setVisible(true);
							jlPassword.setVisible(true);
						}});
				
			jpEast.add(jpEastNorth, BorderLayout.NORTH);
				
		add(jpEast, BorderLayout.EAST);
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * @return the list of private rooms at the moment
	 */
	public Vector<Room>privateRooms() {
		return privateRooms;
	}

	/**
	 * @return the list of public rooms at the moment
	 */
	public Vector<Room> publicRooms() {
		return publicRooms;
	}
	
	public static void main(String args[]){
		new Lobby().setVisible(true);
	}
}
