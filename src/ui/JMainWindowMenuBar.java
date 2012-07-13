package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class JMainWindowMenuBar extends JMenuBar {

	private JMenu jSettingsMenu = null;
	private JMenuItem jDebuggingWindowMenuItem = null;
	
	public JMainWindowMenuBar(){
		super();
		initialize();
	}

	private void initialize() {
		
		this.add(getJSettingsMenu());
		this.add(getJDebuggingWindowMenuItem());
		
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
	
	private JMenuItem getJDebuggingWindowMenuItem() {
		if(jDebuggingWindowMenuItem==null){
			jDebuggingWindowMenuItem = new JMenuItem("Debugging");
			jDebuggingWindowMenuItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new JDebuggingWindow();
				}
			});
		}
		return jDebuggingWindowMenuItem;
	}
}
