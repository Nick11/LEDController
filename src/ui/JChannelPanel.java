package ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
/**
 * contains the components to set the colors and playmode of one channel.
 * @author Niclas
 *
 */
public class JChannelPanel extends JPanel{
	/**
	 * id to identify the channel to be used. Beginning with 0.
	 */
	private final int ID;
	
	public JChannelPanel(int id){
		super();
		initialize();
		this.ID=id;
	}
	
	private void initialize() {
		GUIUpdater updater = new GUIUpdater();
		JSliderPanel sliderPanel = new JSliderPanel(updater);
		JRGBPicturePanel picturePanel = new JRGBPicturePanel(updater, 100,100); 
		this.setLayout(new GridBagLayout());
		this.add(sliderPanel, setConstraints(0,0,1,1,0,0,1,1,GridBagConstraints.BOTH, GridBagConstraints.WEST));
		this.add(picturePanel, setConstraints(1,0,1,1,0,0,0.3,0.9,GridBagConstraints.BOTH, GridBagConstraints.WEST));
		this.add(new JColorListPanel(updater, ID), setConstraints(2,0,1,1,0,0,0.1,0.9,GridBagConstraints.BOTH, GridBagConstraints.WEST));
		updater.setParameter(sliderPanel, picturePanel);
	}
	
	/**
	 * creates a GridBagConstrints and sets values
	 * if a parameter is -1, the value is left at default
	 * parameter are named according to the variable names of the GridBagConstraints
	 * 
	 * @return the created GridBagConstraints
	 */
	public static GridBagConstraints setConstraints(int gridx, int gridy, int gridwidth, int gridheight, int ipadx, int ipady, double weightx, double weighty, int fill, int anchor){
		GridBagConstraints c = new GridBagConstraints();
		if(fill != -1)
			c.fill = fill;
		if(anchor != -1)
			c.anchor= anchor;
		if(weightx != -1)
			c.weightx = weightx;
		else
			c.weightx = 0;
		if(weighty != -1)
			c.weighty = weighty;
		else
			c.weighty = 0;
		if(gridx != -1)
			c.gridx = gridx;
		if( gridy!= -1)
			c.gridy = gridy;
		if(gridwidth != -1)
			c.gridwidth = gridwidth;
		if(gridheight != -1)
			c.gridheight = gridheight;
		if(ipadx != -1)
			c.ipadx = ipadx;
		if(ipady != -1)
			c.ipady = ipady;
		return c;
	}
}
