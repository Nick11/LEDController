package colorReader;

import java.awt.Color;
import java.util.ArrayList;

public class SolidColorReader implements AbstractColorReader {
	/**
	 * array of colors to be shown
	 */
	private Color[] colors;
	/**
	 * index of the next color to be shown
	 */
	private int index;
	
	public SolidColorReader(Color color){
		colors = new Color[]{color};
		index = -1;
	}
	public SolidColorReader(Color[] colors){
		this.colors = colors;
		index = -1;
	}
	@Override
	public Color getColor() {
		index= (index+1)%colors.length;
		return colors[index];
	}

}
