package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import controller.Controller;
import model.Grid;

public class MainWindow extends JFrame {

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
	String[] algorithmsToChoose = { "Test" };

	public MainWindow(Controller controller) {
		this.controller = controller;

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
		this.startButton = new JButton();
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

		this.validate();
		this.repaint();
	}

	public void setNewGrid(Grid gridObject, int size) {
		this.panelHolder = new JPanel[size][size];
		this.gridArea.setLayout(new GridLayout(size, size));

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
			
				JPanel newPanel = new JPanel();
				
				newPanel.setBorder(new LineBorder(Color.black,1));
				
				panelHolder[x][y] = newPanel;
				gridArea.add(panelHolder[x][y]);
				panelHolder[x][y] = gridObject.getGrid()[x][y];
				
			}
		}
		this.validate();
		this.repaint();
	}

}
