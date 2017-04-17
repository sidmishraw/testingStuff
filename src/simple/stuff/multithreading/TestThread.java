/**
 * Project: TestingStuff
 * Package: simple.stuff.multithreading
 * File: TestThread.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 14, 2017 9:56:08 AM
 */
package simple.stuff.multithreading;

/**
 * @author sidmishraw
 *
 *         Qualified Name: simple.stuff.multithreading.TestThread
 *
 */
public class TestThread extends Thread {
	
	
	/**
	 * 
	 */
	public TestThread() {
		
		System.out.println("Starting the thread that was created");
		this.start();
	}
	
	/**
	 * @param target
	 */
	public TestThread(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param name
	 */
	public TestThread(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param group
	 * @param target
	 */
	public TestThread(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param group
	 * @param name
	 */
	public TestThread(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param target
	 * @param name
	 */
	public TestThread(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param group
	 * @param target
	 * @param name
	 */
	public TestThread(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param group
	 * @param target
	 * @param name
	 * @param stackSize
	 */
	public TestThread(ThreadGroup group, Runnable target, String name, long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		System.out.println("This threading has started running");
		
		for (int i = 0; i < 1000; i++)
			System.out.println("Thread is running at cycle #" + i);
		
	}
	
}
