/**
 *	Project:	TestingStuff
 *  Package:	simple.stuff.testinginnerclasses
 *  File:		InstanceOuter.java
 * 
 *  @author 			sidmishraw
 *  Last modified: 		Apr 12, 2017 6:54:10 PM
 */
package simple.stuff.testinginnerclasses;


/**
 * @author 			sidmishraw
 *
 * Qualified Name: 	simple.stuff.testinginnerclasses.InstanceOuter
 *
 */
public class InstanceOuter {
	
	private int x;
	
	/**
	 * 
	 */
	public InstanceOuter(int xx) {
		x = xx;
	}
	
	class InstanceInner {
		
		public void printSomething() {
			
			System.out.println("The value of x in my outer is " + x);
		}
	}
}
