package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
			JButton button = new JButton(">");
			button.setMargin(new Insets(0,0,0,0));
			button.setFont(new Font("Arial", Font.PLAIN,8));
			button.setPreferredSize(new Dimension(20, 20));
			button.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					addColorToList();
				}
			});
			jButtonPanel.add(button);
		}
		return jButtonPanel;
	}

	protected void addColorToList() {
		// TODO Auto-generated method stub
		
	}
}
