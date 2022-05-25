package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import model.Grid;

public class MainWindow extends JFrame{

	JPanel gridArea;
	JPanel optionArea;
	JPanel buttonArea;
	JPanel[][] panelHolder;
	JTextArea textArea;
	JSlider animationSpeedSlider;
	JSlider gridSizeSlider;
	JButton editPlayModeToggle;
	JButton startButton;
	JComboBox<String> algorithmSelection;
	
	//TODO Edit here to display new algorithms
	String[] algorithmsToChoose = {"Test"};
	
	public MainWindow(){
		
		
		//initialization of UI compponents
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
		
		//adding UI components to the MainWindow
		this.setLayout(new BorderLayout());
		
		this.add(gridArea, BorderLayout.CENTER);
		this.add(optionArea, BorderLayout.EAST);
		
		//option menu
		buttonArea.setPreferredSize(new Dimension(250,250));
		optionArea.setLayout(new BorderLayout());
		optionArea.add(buttonArea, BorderLayout.NORTH);
		optionArea.add(textArea);
		buttonArea.setLayout(new GridLayout(5,1));
		//determining layout of the options
		buttonArea.add(gridSizeSlider);
		buttonArea.add(animationSpeedSlider);
		buttonArea.add(algorithmSelection);
		buttonArea.add(editPlayModeToggle);
		buttonArea.add(startButton);
		
		this.validate();
		this.repaint();
	}
	
}
