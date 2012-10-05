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

import executor.Executor;

public class JSpeedWindow extends JFrame {
	
	private JPanel jContentPane = null;
	private JTextField[] jReadColorRefreshRateTextFields = null;
	private JTextField[] jOutColorRefreshRateTextFields = null;
	
	public JSpeedWindow(){
		super();
		initialize();
	}
	
	public void initialize(){
		this.setEnabled(true);
		this.setVisible(true);
		this.setSize(250,150);
		this.setContentPane(getJContentPane());
		this.setTitle("Set Speed");
	}

	private Container getJContentPane() {
		if(jContentPane == null){
			jContentPane = new JPanel();
			LayoutManager layout = new GridLayout(4,1+Executor.NOCHANNELS);
			jContentPane.setLayout(layout);
			
			
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
			jContentPane.add(new JLabel("Description"));
			for(int i=0; i< Executor.NOCHANNELS; i++){
				jContentPane.add(new JLabel("Channel "+i));
			}
			jContentPane.add(new JLabel("readTime"));
			for(int i=0; i< Executor.NOCHANNELS; i++){
				jContentPane.add(getJReadColorRefreshRateTextFields()[i]);
			}
			jContentPane.add(new JLabel("LEDTime"));
			for(int i=0; i< Executor.NOCHANNELS; i++){
				jContentPane.add(getJOutColorRefreshRateTextFields()[i]);
			}
			jContentPane.add(jCancelButton);
			jContentPane.add(jSaveButton);
		}
		return jContentPane;
	}

	private JTextField[] getJReadColorRefreshRateTextFields() {
		if(jReadColorRefreshRateTextFields == null){
			jReadColorRefreshRateTextFields = new JTextField[Executor.NOCHANNELS];
			for(int i=0; i<Executor.NOCHANNELS; i++){
				jReadColorRefreshRateTextFields[i] = new JTextField();
			}
		}
		return jReadColorRefreshRateTextFields;
	}
	
	private JTextField[] getJOutColorRefreshRateTextFields() {
		if(jOutColorRefreshRateTextFields == null){
			jOutColorRefreshRateTextFields = new JTextField[Executor.NOCHANNELS];
			for(int i=0; i<Executor.NOCHANNELS; i++){
				jOutColorRefreshRateTextFields[i] = new JTextField();
			}
		}
		return jOutColorRefreshRateTextFields;
	}
	
	private void loadValues() {
		int[] periods = new int[]{};
		int[] lEDRefreshRate = new int[]{};
		try{
			periods = GUIAdapter.getInstance().getPeriodsBetweenReading();
			lEDRefreshRate = GUIAdapter.getInstance().getOutColorRefreshRate();
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
		for(int i=0; i<Executor.NOCHANNELS; i++){
			getJReadColorRefreshRateTextFields()[i].setText(periods[i]+"");
			getJOutColorRefreshRateTextFields()[i].setText(lEDRefreshRate[i]+"");
		}
	}

	private void saveValues() {
		int[] periods = new int[Executor.NOCHANNELS];
		int[] lEDRefreshRate = new int[Executor.NOCHANNELS];
		try{
			for(int i=0; i<Executor.NOCHANNELS; i++){
				periods[i] = Integer.valueOf(getJReadColorRefreshRateTextFields()[i].getText());
				lEDRefreshRate[i] = Integer.valueOf(getJOutColorRefreshRateTextFields()[i].getText());
			}
		}catch(NumberFormatException e) {e.printStackTrace();}
		try{
			for(int i=0; i<Executor.NOCHANNELS; i++){
				GUIAdapter.getInstance().setPeriodsBetweenReading(periods[i], i);
				GUIAdapter.getInstance().setOutColorRefreshRate(lEDRefreshRate[i], i);
			}
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}
	
	private void close(){
		this.setVisible(false);
		this.dispose();
	}
}
