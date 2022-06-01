package model;

import java.awt.Color;

public class GridCell {

	private int xCoordinate, yCoordinate;
	private Color color;
	private char cellType;
	private GridCell priorVisitedCell = null;
	private int costToThisCell = 0;

	public GridCell(int xCoord, int yCoord) {
		this.xCoordinate = xCoord;
		this.yCoordinate = yCoord;
		this.color = Color.white;
		this.cellType = 'n';

	}

	@Override
	public boolean equals(Object obj) {
		GridCell otherCell = (GridCell) obj;

		if (this.xCoordinate == otherCell.xCoordinate && this.yCoordinate == otherCell.yCoordinate) {
			return true;
		}
		return false;
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

	public GridCell getPriorVisitedCell() {
		return priorVisitedCell;
	}

	public void setPriorVisitedCell(GridCell priorVisitedCell) {
		this.priorVisitedCell = priorVisitedCell;
	}

	public int getCostToThisCell() {
		return costToThisCell;
	}

	public void setCostToThisCell(int costToThisCell) {
		this.costToThisCell = costToThisCell;
	}
	
}
