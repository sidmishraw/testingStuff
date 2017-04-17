/**
 * Project: TestingStuff
 * Package: simple.stuff.multithreading
 * File: ThreadDriver.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 14, 2017 9:58:37 AM
 */
package simple.stuff.multithreading;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

/**
 * @author sidmishraw
 *
 *         Qualified Name: simple.stuff.multithreading.ThreadDriver
 *
 */
public class ThreadDriver {
	
	
	/**
	 * 
	 */
	public ThreadDriver() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		
		TestThread t = new TestThread();
		
		System.out.println("Going to wait for t to finish");
		
		// Have t.join() in the code if you want the main thread or the thread
		// from which
		// t spawned off from (in this case the main thread) to wait till t is
		// done with its
		// execution or is killed. To check if t is still alive check
		// t.isAlive()
		
		if (t.isAlive()) {
			
			t.join();
		}
		
		System.out.println("t has finished");
		
		try (Synthesizer synthesizer = MidiSystem.getSynthesizer()) {
			
			synthesizer.open();
			MidiChannel[] channels = synthesizer.getChannels();
			
			for (int i = 57; i < 66; i++) {
				
				System.out.println("Playing note #" + i);
				channels[1].noteOn(i, 180);
				
				Thread.sleep(2000);
				
				channels[1].noteOff(i);
			}
			
			synthesizer.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}
