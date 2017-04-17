/**
 * Project: TestingStuff
 * Package: hackerrank.algorithms
 * File: SherlockAnagram.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 14, 2017 2:59:40 PM
 */
package hackerrank.algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sidmishraw
 *
 *         Qualified Name: hackerrank.algorithms.SherlockAnagram
 *
 */
public class SherlockAnagram {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			
			int n = Integer.parseInt(br.readLine());
			
			for (int i = 0; i < n; i++) {
				
				char[] charsArr = br.readLine().toCharArray();
				ArrayList<Character> chars = new ArrayList<>();
				
				for (char c : charsArr) {
					
					chars.add(c);
				}
				
				Map<Character, Integer> charCountMap = new HashMap<>();
				
				chars.forEach((Character c) -> {
					if (null == charCountMap.get(c)) {
						
						charCountMap.put(c, 1);
					} else {
						
						int count = charCountMap.get(c);
						charCountMap.put(c, count + 1);
					}
				});
				
				charCountMap.forEach((Character c, Integer j) -> {
					
				});
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
}
