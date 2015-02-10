
 
import javax.sound.sampled.*;
 
public class SamplePlayer extends Thread {
	
	// Instance data
    private AudioFormat format;
    private DataLine.Info info;
    private SourceDataLine auline;
    private boolean done;
    private byte [] sampleData = new byte[BUFFER_SIZE];
    private SampleProviderIntfc provider;
	
	
    // AudioFormat parameters
    public  static final int     SAMPLE_RATE = 22050;
    private static final int     SAMPLE_SIZE = 16;
    private static final int     CHANNELS = 1;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = true;
 
    // Chunk of audio processed at one time
    public static final int BUFFER_SIZE = 1000;
    public static final int SAMPLES_PER_BUFFER = BUFFER_SIZE / 2;
 
    public SamplePlayer() {
 
        
        format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, CHANNELS, SIGNED, BIG_ENDIAN);
 
        
        info = new DataLine.Info(SourceDataLine.class, format);
    }
     
    public void run() {
 
        done = false;
        int nBytesRead = 0;
 
        try {
            
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
            auline.start();
 
            while ((nBytesRead != -1) && (! done)) {
                nBytesRead = provider.getSamples(sampleData);
                if (nBytesRead > 0) {
                    auline.write(sampleData, 0, nBytesRead);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();                
        } finally {
            auline.drain();
            auline.close();                         
        }
    }       
     
    public void startPlayer() {
        if (provider != null) {
            start();    
        }
    }
     
    public void stopPlayer() {
        done = true;
    }
     
    public void setSampleProvider(SampleProviderIntfc provider) {
        this.provider = provider;
    }
     
   
}