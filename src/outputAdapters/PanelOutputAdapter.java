package outputAdapters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * translates the softwares command to a color on a panel
 * Is a singelton.
 * @author Niclas
 */
public class PanelOutputAdapter implements OutputAdapter{
	private JPanel[] panels;
	/**
	 * An instance of this class.
	 */
	private static PanelOutputAdapter instance=null;
	
	private PanelOutputAdapter(){
	}
	
	/**
	 * call this to get an instance of this class.
	 * @return
	 */
	public static PanelOutputAdapter getInstance(){
		if(instance==null){
			instance = new PanelOutputAdapter();
		}
		return instance;
	}
	
	private void initPanel() {
		JFrame frame = new JFrame();
		frame.setLocation(new Point(500,-250));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panels = new JPanel[2];
        for(int i=0; i<panels.length; i++){
        	panels[i] = new JPanel();
        	panels[i].setMinimumSize(new Dimension(100,50));
        	panel.add(panels[i],i);
        }
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.setEnabled(true);
        frame.setMinimumSize(new Dimension(200, 200));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setColor(Color color, int channelNo){
		panels[channelNo].setBackground(color);
	}

	@Override
	public void startTransmission() {
		initPanel();
	}
	/**
	 * nothing to do, as java closes all the windows when calling System.exit
	 */
	@Override
	public void endTransmission() {}
}
