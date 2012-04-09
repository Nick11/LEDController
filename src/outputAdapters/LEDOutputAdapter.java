package outputAdapters;

import java.awt.Color;

import com.IOController;

public class LEDOutputAdapter implements OutputAdapter {

	@Override
	public void setColor(Color color, int channelNo) {
		int red = (color.getRed()*100)/255;
		int green = (color.getGreen()*100)/255;
		int blue = (color.getBlue()*100)/255;
		String message = "SC"+(channelNo+1)+" R "+red+"; G "+green+"; B "+blue+";"; //the software enumerates the channel starting with 0. the protocol starting with 1
		//System.out.println(message);
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
