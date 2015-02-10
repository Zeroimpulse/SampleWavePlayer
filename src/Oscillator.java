

public class Oscillator implements SampleProviderIntfc
{
	private WAVESHAPE waveshape;
	private long periodSamples;
	private long sampleNumber;
	
	
	public Oscillator()
	{
		setOscWaveshape(WAVESHAPE.SIN);
		setFrequency(1000.0);
	}
	
	public void setOscWaveshape(WAVESHAPE waveshape)
	{
		this.waveshape = waveshape;
	}
	
	public void setFrequency(double frequency)
	{
		periodSamples = (long)(SamplePlayer.SAMPLE_RATE / frequency);
	}
	
	protected double getSample()
	{
		double value;
		double x = sampleNumber / (double) periodSamples;
		
		switch (waveshape)
		{
			default:
			case SIN:
				value = Math.sin(2.0 * Math.PI * x);
				break;
				
			case SQU:
				if (sampleNumber < (periodSamples / 2))
				{
					value = 1.0;
				}
				else
				{
					value = -1.0;
				}
				break;
				
			case SAW:
				value = 2.0 * (x - Math.floor((x + .5)));
				break;
		}
		sampleNumber = (sampleNumber + 1) % periodSamples;
		return value;
	}
	
	public int getSamples (byte [] buffer) 
	{
		int index = 0;
		for (int i = 0; i < SamplePlayer.SAMPLES_PER_BUFFER; i++)
		{
			double ds = getSample() * Short.MAX_VALUE;
			short ss = (short) Math.round(ds);
			buffer[index++] = (byte) (ss >> 8);
			buffer [index++] = (byte) (ss & 0xFF);
		}
		return SamplePlayer.BUFFER_SIZE;
	}
}
