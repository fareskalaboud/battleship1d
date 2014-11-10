package battleships1d;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class DragFrame implements MouseListener, MouseMotionListener {
	JComponent toMove;
	Point pointToStart;
	Point locToStart;

	public DragFrame(JComponent target) {
		this.toMove = target;
	}

	public static JFrame getFrame(Container toMove) {
		if (toMove instanceof JFrame)
			return (JFrame) toMove;
		return getFrame(toMove.getParent());
	}

	Point getScreenLocation(MouseEvent e) {
		Point cursor = e.getPoint();
		Point target_location = this.toMove.getLocationOnScreen();
		return new Point((int) (target_location.getX() + cursor.getX()),
				(int) (target_location.getY() + cursor.getY()));
	}

	public void mousePressed(MouseEvent e) {
		this.pointToStart = this.getScreenLocation(e);
		this.locToStart = this.getFrame(this.toMove).getLocation();
	}

	public void mouseDragged(MouseEvent e) {
		Point current = this.getScreenLocation(e);
		Point offset = new Point((int) current.getX()
				- (int) pointToStart.getX(), (int) current.getY()
				- (int) pointToStart.getY());

		JFrame myFrame = this.getFrame(toMove);

		Point new_location = new Point(
				(int) (this.locToStart.getX() + offset.getX()),
				(int) (this.locToStart.getY() + offset.getY()));
		myFrame.setLocation(new_location);
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
