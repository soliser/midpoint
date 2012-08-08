import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class midapp extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// GUI elements
	JCheckBox jchkCircle = new JCheckBox("Circle");
	JCheckBox jchkLine = new JCheckBox("Line");
	JTextField jtfX1 = new JTextField("");
	JTextField jtfX2 = new JTextField("");
	JTextField jtfY1 = new JTextField("");
	JTextField jtfY2 = new JTextField("");
	JTextField jtfCenterX = new JTextField("");
	JTextField jtfCenterY = new JTextField("");
	JTextField jtfRadius = new JTextField("");
	JButton jbtnSolve = new JButton("Solve");
	static JPanel p2 = new JPanel();
	

	public midapp(){		
		p2.setLayout(new GridLayout(9, 2));
		p2.add(jchkLine);
		p2.add(jchkCircle);
		p2.add(new JLabel("Line X1 \n(0-41)"));
		p2.add(jtfX1);
		p2.add(new JLabel("Line Y1 \n(0-41)"));
		p2.add(jtfY1);
		p2.add(new JLabel("Line X2 \n(0-41)"));
		p2.add(jtfX2);
		p2.add(new JLabel("Line Y2 \n(0-41)"));
		p2.add(jtfY2);
		p2.add(new JLabel("Circles X \n(0-41)"));
		p2.add(jtfCenterX);
		p2.add(new JLabel("Cirlces Y \n(0-41)"));
		p2.add(jtfCenterY);
		p2.add(new JLabel("Cirlces Radius \n(0-20)"));
		p2.add(jtfRadius);
		p2.add(new JLabel(""));
		p2.add(jbtnSolve);
		jbtnSolve.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {			
				repaint();
			}
		});
	}

	public void paint(Graphics g) {
		
		super.paint(g);
		// Draws grid pixel map
		for (int i = 0; i <= 400; i += 10) {
			g.drawLine(0, i, 400, i);
			g.drawLine(i, 0, i, 400);
		}
		if (jchkLine.isSelected())
			MidpointLine(new Integer(jtfX1.getText()).intValue(),
					new Integer(jtfY1.getText()).intValue(),
					new Integer(jtfX2.getText()).intValue(),
					new Integer(jtfY2.getText()).intValue(), g);
		if (jchkCircle.isSelected())
			CircleMid2(new Integer(jtfCenterX.getText()).intValue(),
					new Integer(jtfCenterY.getText()).intValue(),
					new Integer(jtfRadius.getText()).intValue(), g);
	}

	public static void main(String[] args) {
		Frame f = new JFrame("YAY it Works");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		JApplet applet = new midapp();
		((RootPaneContainer) f).getContentPane().add("Center", applet);
		f.add(p2, BorderLayout.EAST);

		applet.init();
		f.pack();
		f.setSize(new Dimension(653, 437));
		f.setVisible(true);

	}

	void MidpointLine(int x0, int y0, int x1, int y1, Graphics g) {
		if (x0 == x1) {
			for (int i = Math.min(y1, y0); i <= Math.max(y1, y0); i++) {
				WritePixel(x0, i, 10, g);
			}
		} else if (y0 == y1) {
			for (int i = Math.min(x1, x0); i <= Math.max(x1, x0); i++) {
				WritePixel(i, y0, 10, g);
			}
		}

		else {

			int dx = Math.abs(x1 - x0);
			int dy = Math.abs(y1 - y0);
			int d = dx - dy;
			int sx, sy, d2;
			if (x0 < x1)
				sx = 1;
			else
				sx = -1;
			if (y0 < y1)
				sy = 1;
			else
				sy = -1;
			do {
				WritePixel(x0, y0, 10, g);
				d2 = d * 2;
				if (d2 > -dy) {
					x0 += sx;
					d -= dy;
				}

				if (d2 < dx) {
					d += dx;
					y0 += sy;
				}

			} while (x0 != x1 && y0 != y1);
		}
	}

	void CircleMid2(int xpos, int ypos, int R, Graphics g) {
		int x = 0;
		int y = R;
		int d = 1 - R;
		int E = 3;
		int SE = -2 * R + 5;
		do {
			WritePixelCircle(x, y, R, xpos, ypos, g);
			if (d < 0) { /* E */
				d += E;
				E += 2;
				SE += 2;
				x++;
			} else { /* SE */
				d += SE;
				E += 2;
				SE += 4;
				x++;
				y--;
			}
		} while (x <= y);
	}

	void WritePixel(int x, int y, int pixel, Graphics g) {

		g.fillRect(x * 10, y * 10, pixel, pixel);

	}

	void WritePixelCircle(int x, int y, int r, int xpos, int ypos, Graphics g) {
		xpos *= 10;
		ypos *= 10;
		// draw ++ quadrant
		g.fillRect(x * 10 + xpos, y * 10 + ypos, 10, 10);
		g.fillRect(y * 10 + ypos, x * 10 + xpos, 10, 10);
		// draw +- quadrant
		g.fillRect((x *10) + xpos, (-y * 10) + ypos, 10, 10);
		g.fillRect(y * 10 + ypos, (-x * 10) + xpos, 10, 10);
		// draw -+ quadrant
		g.fillRect((-x * 10) + xpos, y * 10 + ypos, 10, 10);
		g.fillRect((-y * 10) + ypos, x * 10 + xpos, 10, 10);
		// draw -- quadrant
		g.fillRect((-x * 10) + xpos, (-y * 10) + ypos, 10, 10);
		g.fillRect((-y * 10) + ypos, (-x * 10) + xpos, 10, 10);

	}
}
