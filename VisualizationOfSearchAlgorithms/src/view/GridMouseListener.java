package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridMouseListener extends MouseAdapter{

	private MainWindow window;

	public GridMouseListener(MainWindow window) {
		this.window = window;
	}
	
	public void mousePressed(MouseEvent e) {
		double x,y;
		x = e.getX();
		y = e.getY();
		//TODO remove magic number
		window.cellClicked((int)(Math.ceil((x/e.getComponent().getWidth())*10)-1), (int)(Math.ceil((y/e.getComponent().getHeight())*10))-1);
	}
}
