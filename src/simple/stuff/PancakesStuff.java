/**
 * 
 */
package simple.stuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author sidmishraw
 *
 */
public class PancakesStuff {



	private static int countS(String p, char c) {
		
		int count 		= 0;

		for (char ch: p.toCharArray()) {

			if (ch == c) {

				count ++;
			}
		}

		return count;
	}



	private static void flip(String p, int k, int s, int e, int passes, int casenbr) {

		int bs 				= countS(p, '-');

		if (bs == 0) {

			System.out.println("Case #"+ (casenbr + 1) + ": " + passes);
			return;
		} else if (passes > 2 * ((float) p.length()) / k) {

			System.out.println("Case #" + (casenbr + 1) + ": IMPOSSIBLE");
			return;
		}

		char[] pArray = p.toCharArray();

		for (int i = s; i < e && i < p.length() && e <= p.length(); i ++) {
			
			if (pArray[i] == '-') {
				
				pArray[i] = '+';
			} else {

				pArray[i] = '-';
			}
		}

		p = String.valueOf(pArray);

		passes ++;
		
		flip(p, k, p.indexOf('-'), p.indexOf('-') + k, passes, casenbr );
		
		return;
	}
	
	public static void main(String[] args) {

		try (BufferedReader br 		= new BufferedReader(new InputStreamReader(System.in))) {

			int t 					= Integer.parseInt(br.readLine());

			for (int i = 0; i < t; i ++) {

				String [] ss 			= br.readLine().split(" ");
				int k 					= Integer.parseInt(ss[1]);
				String p				= ss[0];
				int passes 				= 0;

				flip(p, k, p.indexOf('-'), p.indexOf('-') + k, passes, i);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
