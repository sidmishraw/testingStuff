/**
 * Project: TestingStuff
 * Package: simple.stuff.testinginnerclasses
 * File: TestingLambda.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 13, 2017 10:06:54 AM
 */
package simple.stuff.testinginnerclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sidmishraw
 *
 *         Qualified Name: simple.stuff.testinginnerclasses.TestingLambda
 *
 */
public class TestingLambda {
	
	
	/**
	 * 
	 */
	public TestingLambda() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		
		List<String> myStrings = new ArrayList<>();
		
		myStrings.add("This");
		myStrings.add("is");
		myStrings.add("the");
		myStrings.add("world");
		myStrings.add("of");
		myStrings.add("the");
		myStrings.add("spartans");
		
		long count = myStrings.stream().filter((s) -> s.charAt(0) > 96 && s.charAt(0) < 124).count();
		
		System.out.println(String.format("Count=%s", count));
	}
}
