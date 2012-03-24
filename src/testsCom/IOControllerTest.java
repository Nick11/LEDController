package testsCom;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.*;

public class IOControllerTest {

	private IOController controller;
	private static final int n = 2;
	private String[][] colors = new String[n][3];
	@Before
	public void setUp() throws Exception {
		 controller= IOController.getInstance();
		 controller.openPort();
		 for(int i=0; i<n*3; i++){
			 colors[(int) Math.floor(i/3)][i%3] = String.valueOf((int)(Math.random()*50));
		 }
	}

	@Test
	public void shouldSetColorSC1() {
		String message = "SC1 R "+colors[0][0]+"; G "+colors[0][1]+";";
		//String message = "SC1 R 50; G 10;";
		assert(controller.sendMessageToLEDController(message));
	}
	@Test
	public void shouldSetColorSC2() {
		String message = "SC2 G "+colors[1][1]+"; B "+colors[1][2]+";";
		assert(controller.sendMessageToLEDController(message));
	}
	
	@After
	public void tearDown(){
		controller.closePort();
	}

}
