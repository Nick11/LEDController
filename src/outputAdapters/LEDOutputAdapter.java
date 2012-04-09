package outputAdapters;

import java.awt.Color;

import com.IOController;

public class LEDOutputAdapter implements OutputAdapter {
	/**
	 * translates the softwares commands to the commands according to the protocol, understood by the LEDControlelr hardware.
	 * Is a singelton.
	 * @author Niclas
	 */
	/**
	 * An instance of this class.
	 */
	private static LEDOutputAdapter instance=null;
	
	private LEDOutputAdapter(){
	}
	/**
	 * call this to get an instance of this class.
	 * @return
	 */
	public static LEDOutputAdapter getInstance(){
		if(instance==null){
			instance = new LEDOutputAdapter();
		}
		return instance;
	}
	@Override
	public void setColor(Color color, int channelNo) {
		int red = (color.getRed()*100)/255;
		int green = (color.getGreen()*100)/255;
		int blue = (color.getBlue()*100)/255;
		String message = "SC"+(channelNo+1)+" R "+red+"; G "+green+"; B "+blue+";"; //the software enumerates the channel starting with 0. the protocol starting with 1
		IOController controller = IOController.getInstance();
		controller.sendMessageToLEDController(message);
	}
	
	@Override
	public void startTransmission() {
		IOController.getInstance().openPort();
	}
	@Override
	public void endTransmission() {
		setColor(new Color(0,0,1),0);
		setColor(new Color(0,0,1),1);
		IOController.getInstance().closePort();
	}

}
