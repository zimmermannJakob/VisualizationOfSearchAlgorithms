package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.Controller;

public class GridMouseListener extends MouseAdapter {

	private MainWindow window;
	private Controller controller;

	public GridMouseListener(MainWindow window, Controller controller) {
		this.window = window;
		this.controller = controller;
	}

	public void mousePressed(MouseEvent e) {
		double x, y;
		x = e.getX();
		y = e.getY();
		window.cellClicked((int) (Math.ceil((x / e.getComponent().getWidth()) * controller.getCurrentGridSize()) - 1),
				(int) (Math.ceil((y / e.getComponent().getHeight()) * controller.getCurrentGridSize())) - 1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		window.reportMouseReleased();
	}
}
