package model;

public class PQueueEntry implements Comparable<PQueueEntry>{
	
	private GridCell cell;
	private double key;
	
	public PQueueEntry(GridCell cell, double key){
		this.cell = cell;
		this.key = key;
	}

	public GridCell getCell() {
		return cell;
	}

	public double getKey() {
		return key;
	}

	@Override
	public int compareTo(PQueueEntry other) {
		if(other.key == this.key) {
			return 0;
		}
		
		if(other.getKey()<this.key) {
			return 1;
		}
		
		return -1;
	}
	
	
}
