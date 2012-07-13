package ui;

import inputAdapters.GUIAdapter;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JSpeedWindow extends JFrame {
	
	private JPanel jContentPane = null;
	
	public JSpeedWindow(){
		super();
		initialize();
	}
	
	public void initialize(){
		this.setEnabled(true);
		this.setVisible(true);
		this.setSize(100,150);
		this.setContentPane(getJContentPane());
		this.setTitle("Set Speed");
	}

	private Container getJContentPane() {
		if(jContentPane == null){
			jContentPane = new JPanel();
			LayoutManager layout = new GridLayout(3,2);
			jContentPane.setLayout(layout);
			
			JTextField jLEDTimeTextField = new JTextField();
			JTextField jReadTimeTextField = new JTextField();
			JButton jSaveButton = new JButton("save");
			jSaveButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					saveValues();
					close();
				}
			});
			JButton jCancelButton = new JButton("cancel");
			jCancelButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					close();
				}
			});
			loadValues();
			jContentPane.add(new JLabel("readTime"),0);
			jContentPane.add(jReadTimeTextField,1);
			jContentPane.add(new JLabel("LEDTime"),2);
			jContentPane.add(jLEDTimeTextField,3);
			jContentPane.add(jCancelButton);
			jContentPane.add(jSaveButton);
		}
		return jContentPane;
	}
	
	private void loadValues() {
		int readTime = 0;
		int lEDTime = 0;
		try{
			readTime = GUIAdapter.getInstance().getReadColorRefreshRate();
			lEDTime = GUIAdapter.getInstance().getReadColorRefreshRate();
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
		
	}

	private void saveValues() {
		// TODO Auto-generated method stub
		
	}
	
	private void close(){
		this.setVisible(false);
		this.dispose();
	}
}
