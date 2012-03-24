package adapter;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class PanelOutputAdapter implements OutputAdapter{
	private JPanel panel;
	
	public PanelOutputAdapter(){
		
	}

	private void initPanel() {
		JFrame frame = new JFrame();
        panel = new JPanel();
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.setEnabled(true);
        frame.setMinimumSize(new Dimension(200, 200));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setColor(Color color){
		panel.setBackground(color);
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
