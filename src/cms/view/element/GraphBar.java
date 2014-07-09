package cms.view.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.model.data.BeanNode;
import cms.view.GraphicsConstants;

public class GraphBar extends JComponent implements MouseListener,
		GraphicsConstants {
	private static final long serialVersionUID = 1L;

	private int width;

	private BeanNode node;

	private static int displaytype;
	private static int value;

	public GraphBar(int width, BeanNode node) {
		super();

		this.width = width;
		this.node = node;

		enableInputMethods(true);
		setFocusable(true);

		addMouseListener(this);
		setPreferredSize(new Dimension(width, 100));
		setVisible(true);

	}

	public void displaytypesettings(int displaytype) {
		this.displaytype = displaytype;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		/*Color random = new Color((float) Math.floor(Math.random()),
				(float) Math.floor(Math.random()), (float) Math.floor(Math
						.random()));*/
		g.setColor(AVA);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

		/*
		 * //Icon switch(core.getState()){ case 0: foreground = ABS; break; case
		 * 10: foreground = PRE; break; case 20: foreground = AVA; break; case
		 * 30: foreground = RUN; break; case 40: foreground = ERR; break;
		 * default: foreground = ABS; break; } IconFactory.icon(g, foreground,
		 * background, getWidth(), getHeight(), core.getType());
		 */
	}

	@Override
	public void mouseClicked(MouseEvent e) {

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

}
