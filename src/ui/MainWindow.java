package ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {
	/**
	 * the main panel, containing all the other components.
	 */
	private JPanel jContentPane;
	/**
	 * maximum value for the colors
	 */
	public static final int  MAX=255;
	/**
	 * minimum value for the colors
	 */
	public static final int  MIN=0;

	
	public MainWindow(){
		super();
		initialize();
	}
	
	public void initialize(){
		this.setSize(400,250);
		this.setContentPane(getJContentPane());
		this.setTitle("LEDController");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(new JMainWindowMenuBar());
	}

	private JPanel getJContentPane() {
		if(jContentPane == null){
			jContentPane = new JPanel();
			LayoutManager layout = new GridLayout(2,1);
			jContentPane.setLayout(layout);
			jContentPane.add(new JChannelPanel(0), 0);
			jContentPane.add(new JChannelPanel(1), 1);
		}
		return jContentPane;
	}
}
