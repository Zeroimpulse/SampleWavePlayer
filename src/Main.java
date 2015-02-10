



public class Main {

	
	public static void main(String[] args) {
		Oscillator osc = new Oscillator();
		
		osc.setFrequency(500);
		
		osc.setOscWaveshape(WAVESHAPE.SQU);
		
		SamplePlayer player = new SamplePlayer();
		
		player.setSampleProvider(osc);
		
		player.startPlayer();
		
		
		
		//player.stopPlayer();
		
		

	}

}
