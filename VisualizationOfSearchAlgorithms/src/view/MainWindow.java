package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;
import model.AnimationInstruction;
import model.Grid;
import model.GridCell;
import model.SearchAlgorithms;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JPanel gridArea;
	private JPanel optionArea;
	private JPanel buttonArea;
	private JPanel gridSizeSliederPanel = new JPanel();
	private JPanel animationSpeedSliderPanel = new JPanel();
	
	private JPanel[][] panelHolder;
	private JTextArea textArea;
	private JSlider animationSpeedSlider;
	private JSlider gridSizeSlider;
	private JButton resetGridButton;
	private JButton startButton;
	private JComboBox<String> algorithmSelection;

	// TODO Edit here to display new algorithms
	String[] algorithmsToChoose = { "BFS", "DFS" };
	private MainWindow window;
	private int status = 0; // 0 before start of the animation, 1 after start of animation, 2 after end of
							// animation
	private ArrayList<GridCell> cellsAlreadyRefreshed = new ArrayList<GridCell>();

	public MainWindow(Controller controller) {
		this.controller = controller;
		this.window = this;
		// initialization of UI components
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1080, 720);
		this.setVisible(true);

		this.gridArea = new JPanel();
		this.optionArea = new JPanel();
		this.buttonArea = new JPanel();
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.animationSpeedSlider = new JSlider(0, 1000);
		this.gridSizeSlider = new JSlider(10, 50);
		this.resetGridButton = new JButton("Reset Grid");
		this.startButton = new JButton("Start");
		this.algorithmSelection = new JComboBox<>(algorithmsToChoose);

		// adding UI components to the MainWindow
		this.setLayout(new BorderLayout());

		this.add(gridArea, BorderLayout.CENTER);
		this.add(optionArea, BorderLayout.EAST);

		// option menu
		buttonArea.setPreferredSize(new Dimension(250, 350));
		optionArea.setLayout(new BorderLayout());
		optionArea.add(buttonArea, BorderLayout.NORTH);
		optionArea.add(textArea);
		buttonArea.setLayout(new GridLayout(5, 1));
		// determining layout of the options
		
		buttonArea.add(gridSizeSliederPanel);
		buttonArea.add(animationSpeedSliderPanel);
		gridSizeSliederPanel.setLayout(new BorderLayout());
		animationSpeedSliderPanel.setLayout(new BorderLayout());
		
		gridSizeSliederPanel.add(gridSizeSlider,BorderLayout.CENTER);
		gridSizeSliederPanel.add(new JLabel("Grid Size Settings"), BorderLayout.NORTH);
		
		animationSpeedSliderPanel.add(animationSpeedSlider,BorderLayout.CENTER);
		animationSpeedSliderPanel.add(new JLabel("Animation Speed Settings [ms per step]"), BorderLayout.NORTH);
		
		
		//buttonArea.add(gridSizeSlider);
		//buttonArea.add(animationSpeedSlider);
		buttonArea.add(algorithmSelection);
		buttonArea.add(resetGridButton);
		buttonArea.add(startButton);

		// mouse IO
		gridArea.addMouseListener(new GridMouseListener(this, controller));
		gridArea.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e) {

				double x, y;

				x = e.getX();
				y = e.getY();

				x = (Math.ceil((x / e.getComponent().getWidth()) * controller.getCurrentGridSize()) - 1);
				y = (Math.ceil((y / e.getComponent().getHeight()) * controller.getCurrentGridSize()) - 1);

				cellClicked((int) x, (int) y);

			}
		});
		//Slider
		this.animationSpeedSlider.setMajorTickSpacing(200);
		this.animationSpeedSlider.setPaintTicks(true);
		this.animationSpeedSlider.setPaintLabels(true);
		this.animationSpeedSlider.setValue((int) controller.accessStepSize(-1));
		
		this.gridSizeSlider.setMajorTickSpacing(10);
		this.gridSizeSlider.setPaintTicks(true);
		this.gridSizeSlider.setPaintLabels(true);
		this.gridSizeSlider.setValue((int) controller.getCurrentGridSize());
		
		// buttonIO
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 1;
				startButton.setEnabled(false);
				gridSizeSlider.setEnabled(false);
				resetGridButton.setEnabled(false);
				algorithmSelection.setEnabled(false);

				String algorithm = (String) algorithmSelection.getSelectedItem();
				Grid grid = controller.getCurrentGrid();

				if (algorithm == "BFS") {
					displayMessage("Starting path calculation...");
					Queue<AnimationInstruction> animationQueue = SearchAlgorithms.BFS(grid, grid.getStartCell(),
							grid.getEndCell());
					displayMessage("Starting animation...");

					AnimationThread animation = new AnimationThread(animationQueue, window, controller);
					Thread animationThread = new Thread(animation);
					animationThread.start();
				}

				if (algorithm == "DFS") {
					displayMessage("Starting path calculation...");
					Queue<AnimationInstruction> animationQueue = SearchAlgorithms.DFS(grid, grid.getStartCell(),
							grid.getEndCell());
					displayMessage("Starting animation...");

					AnimationThread animation = new AnimationThread(animationQueue, window, controller);
					Thread animationThread = new Thread(animation);
					animationThread.start();
				}
			}

		});
		startButton.setFocusable(false);

		resetGridButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gridSizeSlider.setEnabled(true);
				startButton.setEnabled(true);
				algorithmSelection.setEnabled(true);
				textArea.setText("");
				controller.createNewGridRepresentation();
				status = 0;
			}

		});
		resetGridButton.setFocusable(false);

		// Slider
		this.animationSpeedSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.applyChanges(0, animationSpeedSlider.getValue(), null);
				displayMessage("Set the time in between animation steps to "+animationSpeedSlider.getValue()+" ms");

			}
		});
		this.gridSizeSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				controller.applyChanges(gridSizeSlider.getValue(), 0, null);
				controller.createNewGridRepresentation();
				displayMessage("Set the Grid size to "+gridSizeSlider.getValue());
			}
		});

		
		//
		this.validate();
		this.repaint();
	}

	public void animationEnded() {
		this.status = 2;

		resetGridButton.setEnabled(true);
	}

	public void setNewGrid(Grid gridObject, int size) {
		this.panelHolder = new JPanel[size][size];
		this.gridArea.removeAll();
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

	public synchronized void drawGridCells(ArrayList<GridCell> cellsToUpdate) {
		if (cellsToUpdate != null) {
			for (GridCell currentCell : cellsToUpdate) {
				this.panelHolder[currentCell.getX()][currentCell.getY()].setBackground(currentCell.getColor());
			}
			this.validate();
			this.repaint();
		}
	}

	public void cellClicked(int y, int x) {

		if (!cellsAlreadyRefreshed.contains(new GridCell(x, y))) {
			cellsAlreadyRefreshed.add(new GridCell(x, y));
		} else {
			return;
		}

		if (this.status == 1 || this.status == 2) {
			return;
		}

		if (this.panelHolder[x][y].getBackground().equals(Color.green.brighter())
				|| this.panelHolder[x][y].getBackground().equals(Color.green.darker())) {
			// start or endCell can't be painted as a wall
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

	public synchronized void displayMessage(String text) {
		if (text == null) {
			return;
		}

		this.textArea.setText(text);

	}

	public void reportMouseReleased() {
		this.cellsAlreadyRefreshed = new ArrayList<GridCell>();

	}

}
