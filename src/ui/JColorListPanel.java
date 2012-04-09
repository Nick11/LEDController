package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

public class JColorListPanel extends JPanel{

	/**
	 * containing the <code>jColorList</code>.
	 */
	private JScrollPane jScrollPanel=null;
	/**
	 * containing the selected colors.
	 */
	private JList jColorList=null;
	/**
	 * contains the add- and remove button.
	 */
	private JPanel jButtonPanel=null;
	
	public JColorListPanel(){
		super();
		this.setLayout(new GridBagLayout());
		this.setMinimumSize(new Dimension(85, 100));
		this.add(getJButtonPanel(), JChannelPanel.setConstraints(0,0, 1,3, 0,0, 0.1,1, GridBagConstraints.NONE, GridBagConstraints.WEST));
		this.add(getJListScrollPanel(), JChannelPanel.setConstraints(1,0, 2,3, 0,0, 0.9,1, GridBagConstraints.BOTH, GridBagConstraints.WEST));
	}

	private JScrollPane getJListScrollPanel(){
		if(jScrollPanel== null){
			jScrollPanel = new JScrollPane(getJColorList());
		}
	return jScrollPanel;
	}
	
	private JList getJColorList(){
		if(jColorList== null){
			String[] data = {"255,255,255",""};
			jColorList = new JList(data);
		}
		return jColorList;
	}
	
	private JPanel getJButtonPanel(){
		if(jButtonPanel==null){
			jButtonPanel = new JPanel();
			jButtonPanel.setLayout(new GridLayout(2,1));
			JSmallButton addButton = new JSmallButton(">");
			JSmallButton removeButton = new JSmallButton("x");
			addButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					addColorToList();
				}
			});
			removeButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					removeColorToList();
				}
			});
			jButtonPanel.add(addButton);
			jButtonPanel.add(removeButton);
		}
		return jButtonPanel;
	}
	
	/**
	 * a JButton with some modifications on the default size and fontsize.
	 * @author Niclas
	 *
	 */
	private class JSmallButton extends JButton{
		public JSmallButton(String s){
			super(s);
			this.setMargin(new Insets(0,0,0,0));
			this.setFont(new Font("Arial", Font.PLAIN,8));
			this.setPreferredSize(new Dimension(20, 20));
		}
	}
	/**
	 * adds a color with the current values of the textfields to the list
	 */
	private void addColorToList() {
		
	}
	/**
	 * removes the selected color from the list
	 */
	private void removeColorToList() {
		
	}
}
