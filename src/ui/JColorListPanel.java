package ui;

import inputAdapters.GUIAdapter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
	/**
	 *	used to synchronize the color's value between the sliders, textfields and the picture. 
	 */
	private GUIUpdater updater;
	/**
	 *	id to identify the channel to be used. Beginning with 0.
	 */
	private final int ID;
	
	public JColorListPanel(GUIUpdater updater, int id){
		super();
		this.updater=updater;
		this.ID=id;
		initialize();
	}

	private void initialize() {
		this.setLayout(new GridBagLayout());
		this.setMinimumSize(new Dimension(100, 100));
		this.add(getJButtonPanel(), JChannelPanel.setConstraints(0,0, 1,3, 0,0, 0.1,1, GridBagConstraints.NONE, GridBagConstraints.WEST));
		this.add(getJListScrollPanel(), JChannelPanel.setConstraints(1,0, 2,3, 0,0, 0.9,1, GridBagConstraints.BOTH, GridBagConstraints.WEST));
	}

	private JScrollPane getJListScrollPanel(){
		if(jScrollPanel== null){
			jScrollPanel = new JScrollPane(getJColorList());
			jScrollPanel.setMinimumSize(new Dimension(75, 100));
		}
	return jScrollPanel;
	}
	
	private JList getJColorList(){
		if(jColorList== null){
			jColorList = new JList();
			DefaultListModel model = new DefaultListModel();
			jColorList.setModel(model);
		}
		return jColorList;
	}
	
	private JPanel getJButtonPanel(){
		if(jButtonPanel==null){
			jButtonPanel = new JPanel();
			jButtonPanel.setLayout(new GridLayout(5,1));
			JSmallButton addButton = new JSmallButton(">");
			JSmallButton removeButton = new JSmallButton("x");
			JSmallButton setButton = new JSmallButton("set");
			JSmallButton playButton = new JSmallButton("play");
			JSmallButton autoButton = new JSmallButton("auto");
			addButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					addColorToList();
				}
			});
			removeButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					removeColorFromList();
				}
			});
			setButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					setColor();
				}
			});
			playButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					playAllColors();
				}
			});
			autoButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					setAutoMode();
				}
			});
			jButtonPanel.add(addButton);
			jButtonPanel.add(removeButton);
			jButtonPanel.add(setButton);
			jButtonPanel.add(playButton);
			jButtonPanel.add(autoButton);
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
		int[] color = updater.getCurrentColor();
		DefaultListModel model = (DefaultListModel)(getJColorList().getModel());
		model.add(model.getSize(), color[0]+","+color[1]+","+color[2]);
	}
	/**
	 * removes the selected color from the list
	 */
	private void removeColorFromList() {
		DefaultListModel model = (DefaultListModel)(getJColorList().getModel());
		int[] indices = getJColorList().getSelectedIndices();
		if(indices.length>0){
			for(int i=0; i<indices.length; i++){
				model.remove(indices[indices.length-1-i]);
			}
		}
	}
	/**
	 * playes the color in the list
	 */
	private void playAllColors() {
		DefaultListModel model = (DefaultListModel) getJColorList().getModel();
		int[][] colors = new int[model.size()][3];
		String element;
		Scanner scan;
		for(int i=0; i<model.size() ;i++){
			element = (String) model.get(i);
			scan = new Scanner(element);
			scan.useDelimiter(",");
			try{
				colors[i][0] = Integer.valueOf(scan.next());
				colors[i][1] = Integer.valueOf(scan.next());
				colors[i][2] = Integer.valueOf(scan.next());
			}catch(NumberFormatException e){e.printStackTrace(System.out); }
		}
		try{
			GUIAdapter.getInstance().setColorSequence(colors,ID);
		}catch(NoSuchElementException e){e.printStackTrace(System.out); }
	}
	/**
	 * sets one single color
	 */
	private void setColor() {
		int[] color = updater.getCurrentColor();
		try{
			GUIAdapter.getInstance().setSingleColor(color[0], color[1], color[2],ID);
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}
	/**
	 * activates the auto-mode
	 */
	private void setAutoMode(){
		try{
			GUIAdapter.getInstance().setAutoMode(ID);
		}catch(NoSuchElementException e){
			e.printStackTrace();
		}
	}
}
