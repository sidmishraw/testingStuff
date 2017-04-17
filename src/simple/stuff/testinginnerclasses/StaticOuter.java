/**
 *	Project:	TestingStuff
 *  Package:	simple.stuff.testinginnerclasses
 *  File:		StaticOuter.java
 * 
 *  @author 			sidmishraw
 *  Last modified: 		Apr 12, 2017 6:55:54 PM
 */
package simple.stuff.testinginnerclasses;


/**
 * @author 			sidmishraw
 *
 * Qualified Name: 	simple.stuff.testinginnerclasses.StaticOuter
 *
 */
public class StaticOuter {
	
	private static int x = 24;

	static class StaticInner {
		
		public void printSomething() {
			
			System.out.println("The value of x in my outer is " + x);
		}
	}
	
}
