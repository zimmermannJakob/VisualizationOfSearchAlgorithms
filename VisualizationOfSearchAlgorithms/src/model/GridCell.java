package model;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class GridCell extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xCoordinate, yCoordinate;
	private Color color;
	private char cellType;
	
	GridCell(int xCoord, int yCoord){
		this.xCoordinate = xCoord;
		this.yCoordinate = yCoord;
		this.color = Color.white;
		this.cellType = 'n';
		this.setBorder(new LineBorder(Color.DARK_GRAY, 1));
		this.setVisible(true);
		
		//for testing
		this.setBackground(Color.green);
	}

	public Color getColor() {
		return color;
	}

	public char getCellType() {
		return cellType;
	}

	public void setCellType(char cellType) {
		this.cellType = cellType;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX() {
		return xCoordinate;
	}

	public int getY() {
		return yCoordinate;
	}
}
