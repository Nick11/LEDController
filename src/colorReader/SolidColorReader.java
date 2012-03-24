package colorReader;

import java.awt.Color;

public class SolidColorReader implements AbstractColorReader {
	private Color color;
	
	public SolidColorReader(Color color){
		this.color = color;
	}
	@Override
	public Color getColor() {
		return this.color;
	}

}
