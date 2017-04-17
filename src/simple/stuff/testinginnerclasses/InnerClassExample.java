/**
 * Project: TestingStuff
 * Package: simple.stuff.testinginnerclasses
 * File: InnerClassExample.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 6:57:18 PM
 */
package simple.stuff.testinginnerclasses;

/**
 * @author sidmishraw
 *
 *         Qualified Name: simple.stuff.testinginnerclasses.InnerClassExample
 *
 */
public class InnerClassExample {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		InstanceOuter io = new InstanceOuter(12);
		
		// The declaration syntax for instance owned Inner classes (instance
		// inner class)
		InstanceOuter.InstanceInner ii = io.new InstanceInner();
		
		ii.printSomething();
		
		// The declaration syntax for class owned Inner class (static inner
		// class)
		StaticOuter.StaticInner si = new StaticOuter.StaticInner();
		si.printSomething();
	}
	
}
