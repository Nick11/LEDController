package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class JMainWindowMenuBar extends JMenuBar {

	private JMenu jSettingsMenu = null;

	
	public JMainWindowMenuBar(){
		super();
		initialize();
	}

	private void initialize() {
		
		this.add(getJSettingsMenu());
		
	}

	private JMenu getJSettingsMenu() {
		if(jSettingsMenu == null){
			jSettingsMenu = new JMenu("settings");
			JMenuItem jSpeedMenuItem = new JMenuItem("speed");
			jSpeedMenuItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event) {
					new JSpeedWindow();
				}
			});
			jSettingsMenu.add(jSpeedMenuItem);
		}
		
		return jSettingsMenu;
	}
}
