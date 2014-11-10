package battleships1d;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class DragFrame implements MouseListener, MouseMotionListener {
	JComponent toMove;
	Point pointToStart;
	Point locToStart;

	public DragFrame(JComponent target) {
		this.toMove = target;
	}
	

	public static JFrame getFrame(Container target) {
		if (target instanceof JFrame) return (JFrame) target;
		return getFrame(target.getParent());
	}

	Point getScreenLocation(MouseEvent e) {
		Point cursor = e.getPoint();
		Point target_location = this.toMove.getLocationOnScreen();
		return new Point((int) (target_location.getX() + cursor.getX()), (int) (target_location.getY() + cursor.getY()));
	}

	public void mouseClicked(MouseEvent e) { }

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) { }

	public void mousePressed(MouseEvent e) {
		this.pointToStart = this.getScreenLocation(e);
		this.locToStart = this.getFrame(this.toMove).getLocation();
	}

	public void mouseReleased(MouseEvent e) { }

	public void mouseDragged(MouseEvent e) {
		Point current = this.getScreenLocation(e);
		Point offset = new Point(
				(int) current.getX() - (int) pointToStart.getX(),
				(int) current.getY() - (int) pointToStart.getY());
		JFrame frame = this.getFrame(toMove);
		Point new_location = new Point(
				(int) (this.locToStart.getX() + offset.getX()),
				(int) (this.locToStart.getY() + offset.getY()));
		frame.setLocation(new_location);
	}

	public void mouseMoved(MouseEvent e) { }
}
