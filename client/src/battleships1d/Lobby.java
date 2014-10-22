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
		super("Lobby");
		setUpUI();
	}

	/**
	 * @author Alexander Hanbury-Botherway
	 */
	public void setUpUI() {
		//THE ROOMS
		
		JPanel jpCenter = new JPanel(new BorderLayout());
		JPanel jpCenterNorth = new JPanel();
		JPanel jpCenterSouth = new JPanel();
		
		
		JList<Room> jlPublicRooms = new JList<Room>(publicRooms);
		jpCenterNorth.add(jlPublicRooms);
		
		JList<Room> jlPrivateRooms = new JList<Room>(privateRooms);
		jpCenterSouth.add(jlPrivateRooms);
		
		jpCenter.add(jpCenterNorth,BorderLayout.NORTH);
		jpCenter.add(jpCenterSouth,BorderLayout.SOUTH);
		
		add(jpCenter, BorderLayout.CENTER);
		
		//ADD ROOM OPTION
		
		JPanel jpEast = new JPanel(new BorderLayout());
		JPanel jpEastCenter = new JPanel(new GridLayout(5,1));
			
		jpEastCenter.add(new JLabel("Room Name:"));
		
		JTextField jtfRoomName = new JTextField(20);
			jpEastCenter.add(jtfRoomName);
			
		jpEastCenter.add(new JLabel("Room Password:"));
			final JPasswordField jpfRoomPassword = new JPasswordField(20);
			jpfRoomPassword.setEnabled(false);
			
			jpEastCenter.add(jpfRoomPassword);
			
			jpEast.add(jpEastCenter, BorderLayout.CENTER);
			
			JButton jbCreateRoom = new JButton("Create Room");
			jpEastCenter.add(jbCreateRoom);
			
		JPanel jpEastNorth = new JPanel(new FlowLayout());
			final JButton jbPublic = new JButton("Public");
			jbPublic.setEnabled(false);
			jpEastNorth.add(jbPublic);
			final JButton jbPrivate = new JButton("Private");
			jpEastNorth.add(jbPrivate);
			
			jbPublic.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					jbPublic.setEnabled(false);
					jbPrivate.setEnabled(true);
					jpfRoomPassword.setEnabled(false);
				}});
			jbPrivate.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					jbPrivate.setEnabled(false);
					jbPublic.setEnabled(true);
					jpfRoomPassword.setEnabled(true);
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
