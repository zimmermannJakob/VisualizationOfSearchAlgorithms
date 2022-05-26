package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import controller.Controller;
import model.AnimationInstruction;
import model.Grid;
import model.GridCell;
import model.SearchAlgorithms;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JPanel gridArea;
	private JPanel optionArea;
	private JPanel buttonArea;
	private JPanel[][] panelHolder;
	private JTextArea textArea;
	private JSlider animationSpeedSlider;
	private JSlider gridSizeSlider;
	private JButton editPlayModeToggle;
	private JButton startButton;
	private JComboBox<String> algorithmSelection;

	// TODO Edit here to display new algorithms
	String[] algorithmsToChoose = { "BFS" };
	private MainWindow window;

	public MainWindow(Controller controller) {
		this.controller = controller;
		this.window = this;
		// initialization of UI compponents
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1080, 720);
		this.setVisible(true);

		this.gridArea = new JPanel();
		this.optionArea = new JPanel();
		this.buttonArea = new JPanel();
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.animationSpeedSlider = new JSlider();
		this.gridSizeSlider = new JSlider();
		this.editPlayModeToggle = new JButton();
		this.startButton = new JButton("Start");
		this.algorithmSelection = new JComboBox<>(algorithmsToChoose);

		// adding UI components to the MainWindow
		this.setLayout(new BorderLayout());

		this.add(gridArea, BorderLayout.CENTER);
		this.add(optionArea, BorderLayout.EAST);

		// option menu
		buttonArea.setPreferredSize(new Dimension(250, 250));
		optionArea.setLayout(new BorderLayout());
		optionArea.add(buttonArea, BorderLayout.NORTH);
		optionArea.add(textArea);
		buttonArea.setLayout(new GridLayout(5, 1));
		// determining layout of the options
		buttonArea.add(gridSizeSlider);
		buttonArea.add(animationSpeedSlider);
		buttonArea.add(algorithmSelection);
		buttonArea.add(editPlayModeToggle);
		buttonArea.add(startButton);

		// mouse IO
		gridArea.addMouseListener(new GridMouseListener(this));

		this.validate();
		this.repaint();
		
		//buttonIO
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String algorithm = (String) algorithmSelection.getSelectedItem();
				Grid grid = controller.getCurrentGrid();
				
				if(algorithm == "BFS") {
					displayMessage("Starting path calculation...");
					Queue<AnimationInstruction> animationQueue = SearchAlgorithms.BFS(grid, grid.getStartCell(),grid.getEndCell());
					
					AnimationThread animation = new AnimationThread(animationQueue, window, controller);
					Thread animationThread = new Thread(animation);
					animationThread.start();
					
				}
			}
			
		});
		startButton.setFocusable(false);
	}

	public void setNewGrid(Grid gridObject, int size) {
		this.panelHolder = new JPanel[size][size];
		this.gridArea.setLayout(new GridLayout(size, size));

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				JPanel newPanel = new JPanel();
				newPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

				panelHolder[x][y] = newPanel;
				gridArea.add(panelHolder[x][y]);

				panelHolder[x][y].setBackground(gridObject.getGrid()[x][y].getColor());

			}
		}
		this.validate();
		this.repaint();
	}

	public void drawGridCells(ArrayList<GridCell> cellsToUpdate) {
		if(cellsToUpdate != null) {
		for (GridCell currentCell : cellsToUpdate) {
			this.panelHolder[currentCell.getX()][currentCell.getY()].setBackground(currentCell.getColor());
		}
		this.validate();
		this.repaint();
		}
	}

	public void cellClicked(int y, int x) {
		if(this.panelHolder[x][y].getBackground().equals(Color.green.brighter())||this.panelHolder[x][y].getBackground().equals(Color.green.darker())) {
			//start or endCell can't be painted as a wall 
			return;
		}
		if (this.panelHolder[x][y].getBackground().equals(Color.black)) {
			// change to whiteCell 'n'
			GridCell newGridCell = new GridCell(x, y);

			newGridCell.setColor(Color.white);
			newGridCell.setCellType('n'); // n for none

			ArrayList<GridCell> listOfCellsToUpdate = new ArrayList<GridCell>();
			listOfCellsToUpdate.add(newGridCell);
			long l = 0;
			controller.applyChanges(0, l, listOfCellsToUpdate);

		} else {
			// change to black Cell 'w'
			GridCell newGridCell = new GridCell(x, y);

			newGridCell.setColor(Color.black);
			newGridCell.setCellType('w'); // w for wall

			ArrayList<GridCell> listOfCellsToUpdate = new ArrayList<GridCell>();
			listOfCellsToUpdate.add(newGridCell);
			long l = 0;
			controller.applyChanges(0, l, listOfCellsToUpdate);
		}
	}

	public void displayMessage(String text) {
		this.textArea.setText(text);
		
	}

}
