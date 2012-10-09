package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * a panel containing the sliders, labels and textfields.
 * @author Niclas
 */
public class JSliderPanel extends JPanel implements ChangeListener,
		ActionListener {
	
	/**
	 * the sliders for red, green and blue.
	 */
	private JSlider[] rgbSliders=null;
	/**
	 * containing the textfields with the red,green and blue value
	 */
	private JPanel jNumberFieldPanel=null;
	/**
	 * the textfield, which contain the current value of red, green, and blue
	 */
	private JTextField[] textFields=null;
	/**
	 *	used to synchronize the color's value between the sliders, textfields and the picture. 
	 */
	private GUIUpdater updater;
	
	public JSliderPanel(GUIUpdater updater){
		super();
		this.updater=updater;
		initialize();
	}
	private void initialize() {
		this.setLayout(new GridLayout(3,2));
		this.add(new JLabel("red"),0);
		this.add(getRgbSliders()[0],1);
		this.add(getTextFields()[0],2);
		this.add(new JLabel("green"),3);
		this.add(getRgbSliders()[1],4);
		this.add(getTextFields()[1],5);
		this.add(new JLabel("blue"),6);
		this.add(getRgbSliders()[2],7);
		this.add(getTextFields()[2],8);
	}
	/**
	 * creates sliders if they don't exist
	 * @return array of the sliders
	 */
	private JSlider[] getRgbSliders(){
		if(rgbSliders==null){
			rgbSliders = new JSlider[3];
			for(int i=0; i<rgbSliders.length; i++){
				rgbSliders[i] = new JSlider(MainWindow.MIN,MainWindow.MAX,0);
				rgbSliders[i].setMajorTickSpacing((MainWindow.MAX-MainWindow.MIN)/4);
				rgbSliders[i].setPaintTicks(true);
				rgbSliders[i].setPaintLabels(false);
				rgbSliders[i].setPaintTrack(true);
				rgbSliders[i].addChangeListener(this);
			}
		}
		return rgbSliders;
	}
	
	private JTextField[] getTextFields(){
		if(textFields==null){
			textFields = new JTextField[3];
			for(int i=0; i<textFields.length;i++){
				textFields[i] = new JTextField();
				textFields[i].setText(""+MainWindow.MIN);
				textFields[i].setMinimumSize(new Dimension(30,20));
				textFields[i].addActionListener(this);
			}
		}
		return textFields;
	}
	
	/**
	 * notified when a slider changes its state
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		int red = getRgbSliders()[0].getValue();
		int green = getRgbSliders()[1].getValue();
		int blue = getRgbSliders()[2].getValue();
		assert(red>=MainWindow.MIN && green>=MainWindow.MIN && blue>=MainWindow.MIN);
		assert(red<=MainWindow.MAX && green<=MainWindow.MAX && blue<=MainWindow.MAX);
		updater.update(red,green, blue);
	}
	/**
	 * notified when a textfield changes
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		assert(e.getSource().getClass().equals(JTextField.class));
		int[] color = getTextFieldValues();
		updater.update(color[0], color[1], color[2]);
	}
	/**
	 * to be called when the selected color value has changed. updates the <code>textFields</code> values.
	 * @param red between 0 and 100
	 * @param green between 0 and 100
	 * @param blue between 0 and 100
	 */
	public void updateTextFields(int red, int green, int blue){
		assert(red<=MainWindow.MAX && green<=MainWindow.MAX && blue<=MainWindow.MAX);
		assert(red>=MainWindow.MIN  && green>=MainWindow.MIN  && blue>=MainWindow.MIN );
		getTextFields()[0].setText(""+red);
		getTextFields()[1].setText(""+green);
		getTextFields()[2].setText(""+blue);
	}
	/**
	 * to be called when the selected color value has changed. updates the <code>rgbSliders</code> values.
	 * @param red between 0 and 100
	 * @param green between 0 and 100
	 * @param blue between 0 and 100
	 */
	public void updateSliders(int red, int green, int blue){
		assert(red<=MainWindow.MAX && green<=MainWindow.MAX && blue<=MainWindow.MAX);
		assert(red>=MainWindow.MIN  && green>=MainWindow.MIN  && blue>=MainWindow.MIN );
		getRgbSliders()[0].setValue(red);
		getRgbSliders()[1].setValue(green);
		getRgbSliders()[2].setValue(blue);
	}
	/**
	 * reads and returns values of <code>textFields</code>  as <code>int[]</code>
	 * @return current values of <code>textFields</code>
	 */
	public int[] getTextFieldValues() {
		int[] color = new int[3];
		for(int i=0; i<getTextFields().length; i++){
			try{
				color[i] = Integer.valueOf(getTextFields()[i].getText());
				assert(color[i]>=MainWindow.MIN && color[i]<= MainWindow.MAX);
			}catch(Throwable ex){
				System.out.println("Invalid input in a textfield.");
			}
		}
		return color;
	}
	
}
