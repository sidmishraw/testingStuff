/**
 * 
 */
package simple.stuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author sidmishraw
 *
 */
public class TidyNumbers {
	
	private static long checkTidy(long n) {

		int last 						= 10;
		long tidy 						= n; 
		ArrayList<Integer> digits 		= new ArrayList<>();

		while(n > 0) {

			long q 	= n / 10;
			int r 	= (int) (n % 10);

			if (r <= last) {

				digits.add(r);
				last = r;
			} else {

				for (int i = 0; i < digits.size(); i ++) {

					digits.set(i, 9);
				}

				tidy = (n - 1);

				for (int i = digits.size() - 1; i > -1; i --) {

					tidy = (tidy * 10) + digits.get(i); 
				}
				
				break;
			}

			n = q;
		}
		
		if (n == 0) {

			return tidy;
		}

		return checkTidy(tidy);
	}

	/**
	 * 
	 * @param n
	 * @return int
	 */
	private static long lastTidy(long n) {

		return checkTidy(n);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try (BufferedReader br 		= new BufferedReader(new InputStreamReader(System.in))) {

			int t 					= Integer.parseInt(br.readLine());

			for (int i = 0; i < t; i ++) {

				long n 					= Long.parseLong(br.readLine());

				long l 					= lastTidy(n);

				System.out.println("Case #" + (i + 1) + ": " + l);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
