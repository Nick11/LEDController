package adapter;

import java.awt.Color;

import com.IOController;

public class LEDOutputAdapter implements OutputAdapter {

	@Override
	public void setColor(Color color) {
		int red = (color.getRed()*100)/255;
		int green = (color.getGreen()*100)/255;
		int blue = (color.getBlue()*100)/255;
		int min = Math.min(Math.min(red, green), blue);
		String message = "SC"+1+" R "+red+"; G "+green+"; B "+blue+";";
		//System.out.println(message);
		IOController controller = IOController.getInstance();
		controller.sendMessageToLEDController(message);
	}
	public void setColor(Color color, int lineNo) {
		int red = (color.getRed()*100)/255;
		int green = (color.getGreen()*100)/255;
		int blue = (color.getBlue()*100)/255;
		String message = "SC"+lineNo+" R "+red+"; G "+green+"; B "+blue+";";
		IOController controller = IOController.getInstance();
		controller.sendMessageToLEDController(message);
	}
	@Override
	public void startTransmission() {
		IOController.getInstance().openPort();
	}
	@Override
	public void endTransmission() {
		IOController.getInstance().closePort();
	}

}
