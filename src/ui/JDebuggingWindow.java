package ui;

import inputAdapters.GUIAdapter;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.NoSuchElementException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import executor.Executor;

public class JDebuggingWindow extends JFrame {
	
	private JPanel jContentPane = null;
	
	public JDebuggingWindow(){
		super();
		initialize();
	}
	
	public void initialize(){
		this.setEnabled(true);
		this.setVisible(true);
		this.setSize(500,150);
		this.setContentPane(getJContentPane());
		this.setTitle("Debugging");
	}
	private Container getJContentPane() {
		if(jContentPane == null){
			jContentPane = new JPanel();
			LayoutManager layout = new GridLayout(8,1+Executor.NOCHANNELS);
			jContentPane.setLayout(layout);
			jContentPane.add(new JLabel("Description"));
			for(int i=0; i<Executor.NOCHANNELS; i++){
				jContentPane.add(new JLabel("Channel "+i));
			}
			GUIAdapter adapter;
			try{
				 adapter = GUIAdapter.getInstance();
				 jContentPane.add(new JLabel("ColorReader"));
				for(int i=0; i<Executor.NOCHANNELS; i++){
					jContentPane.add(new JLabel((""+adapter.getColorReader()[i].getClass()).substring(18)));
				}
				jContentPane.add(new JLabel("OutputAdapter"));
				for(int i=0; i<Executor.NOCHANNELS; i++){
					jContentPane.add(new JLabel((""+adapter.getOutputAdapter()[i].getClass()).substring(21)));
				}
				jContentPane.add(new JLabel("ColorAverager"));
				for(int i=0; i<Executor.NOCHANNELS; i++){
					jContentPane.add(new JLabel((""+adapter.getColorAverager()[i].getClass()).substring(20)));
				}
				jContentPane.add(new JLabel("ReadColorRate"));
				for(int i=0; i<Executor.NOCHANNELS; i++){
					jContentPane.add(new JLabel(""+adapter.getReadColorRefreshRate()[i]));
				}
				jContentPane.add(new JLabel("OutputColorRate"));
				for(int i=0; i<Executor.NOCHANNELS; i++){
					jContentPane.add(new JLabel(""+adapter.getOutColorRefreshRate()[i]));
				}
				jContentPane.add(new JLabel("ScreenNumber"));
				for(int i=0; i<Executor.NOCHANNELS; i++){
					jContentPane.add(new JLabel(""+adapter.getScreenNr()[i]));
				}
				jContentPane.add(new JLabel("isRunning"));
				for(int i=0; i<Executor.NOCHANNELS; i++){
					jContentPane.add(new JLabel(""+adapter.areRunning()[i]));
				}
			}catch(NoSuchElementException e)
			{
				e.printStackTrace();
				jContentPane.add(new JLabel("Adapter not found"));
			}
			
			
		}
		return jContentPane;
	}
}
