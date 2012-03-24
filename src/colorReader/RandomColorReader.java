package colorReader;

import java.awt.Color;

public class RandomColorReader implements AbstractColorReader {

	@Override
	public Color getColor() {
		return new Color((int)(Math.random()*25),(int)(Math.random()*25),(int)(Math.random()*25));
	}

}
