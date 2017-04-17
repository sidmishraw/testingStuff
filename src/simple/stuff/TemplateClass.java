/**
 * Project: TestingStuff
 * Package: simple.stuff
 * File: TemplateClass.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 14, 2017 10:40:38 PM
 */
package simple.stuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author sidmishraw
 *
 *         Qualified Name: simple.stuff.TemplateClass
 *
 */
public class TemplateClass {
	
	
	public static BufferedReader bufferOpen() {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		return br;
	}
	
	/**
	 * Closes the streams opened
	 * 
	 * @param bufferedReaders
	 */
	public static void bufferClose(BufferedReader... bufferedReaders) {
		
		Arrays.asList(bufferedReaders).forEach((t) -> {
			try {
				
				t.close();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		// sample usage
		BufferedReader br = bufferOpen();
		System.out.println(br.readLine());
		bufferClose(br);
		
	}
	
}
