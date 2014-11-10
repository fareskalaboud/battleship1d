package battleships1d;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Class DragFrame is used in every JFrame in order to be able to drag the
 * JFrame around from the top barPanel;
 * 
 * @author GEORGE RADUTA
 * 
 */
public class DragFrame implements MouseListener, MouseMotionListener {
	JComponent toMove;
	Point pointToStart;
	Point locToStart;

	/**
	 * Constructor with the specified component toMove;
	 * 
	 * @param toMove
	 */
	public DragFrame(JComponent toMove) {
		this.toMove = toMove;
	}

	/**
	 * 
	 * @param toMove
	 *            - specified component toMove;
	 * @return - content;
	 */
	public static JFrame getFrame(Container toMove) {
		if (toMove instanceof JFrame)
			return (JFrame) toMove;
		return getFrame(toMove.getParent());
	}

	private Point cursorOnScreen, locationOfTheTarget;

	/**
	 * 
	 * @param e
	 *            - the MouseEvent
	 * @return
	 */
	private Point getTheLocationOnTheScreen(MouseEvent e) {
		cursorOnScreen = e.getPoint();
		locationOfTheTarget = this.toMove.getLocationOnScreen();

		Point toSend = new Point(
				(int) (locationOfTheTarget.getX() + cursorOnScreen.getX()),
				(int) (locationOfTheTarget.getY() + cursorOnScreen.getY()));

		return toSend;
	}

	/**
	 * Event when the mouse is pressed;
	 */
	public void mousePressed(MouseEvent e) {
		this.pointToStart = this.getTheLocationOnTheScreen(e);
		this.locToStart = this.getFrame(this.toMove).getLocation();
	}

	private JFrame myFrame;
	private Point newLocation, currentLocation, toLocation;

	/**
	 * Event when the mouse is dragged;
	 */
	public void mouseDragged(MouseEvent e) {
		currentLocation = this.getTheLocationOnTheScreen(e);
		toLocation = new Point((int) currentLocation.getX()
				- (int) pointToStart.getX(), (int) currentLocation.getY()
				- (int) pointToStart.getY());

		myFrame = this.getFrame(toMove);

		newLocation = new Point(
				(int) (this.locToStart.getX() + toLocation.getX()),
				(int) (this.locToStart.getY() + toLocation.getY()));
		myFrame.setLocation(newLocation);
	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}
