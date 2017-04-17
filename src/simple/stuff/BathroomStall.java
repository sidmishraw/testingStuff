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
public class BathroomStall {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try (BufferedReader br 		= new BufferedReader(new InputStreamReader(System.in))) {

			int t 					= Integer.parseInt(br.readLine());

			for (int i = 0; i < t; i ++) {
				
				String [] nk 	= br.readLine().split(" ");
				int n 			= Integer.parseInt(nk[0]);
				int k 			= Integer.parseInt(nk[1]);
				
				System.out.println("Case #" + (i + 1) + ": " + bathStall(n, k));
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param n - number of empty stalls intially
	 * @param k - number of people in line
	 * @return {@link String} - the result
	 */
	private static String bathStall(int n, int k) {

		boolean[] stalls 	= new boolean[n + 2];
		int [] ls			= new int[n+2];
		int [] rs			= new int[n+2];

		// true means occupied
		// false means empty
		stalls[0] 					= true;
		stalls[stalls.length - 1] 	= true;

		for (int p = 0; p < k; p ++) {

			// computing the ls, rs for each stall for the person
			for (int i = 1; i < stalls.length; i ++) {

				if (stalls[i]) {

					ls[i]		= -1;
					rs[i]		= -1;
					continue;
				}

				int li = 0, ri = 0;

				for (int j = i - 1; j > 0; j --) {

					if (!stalls[j]) li ++;
				}

				for (int j = i + 1; j < stalls.length; j ++) {

					if (!stalls[j]) ri ++;
				}

				ls[i] = li;
				rs[i] = ri;
			}

			int minRL = -1;
			int maxRL = 9999999;

			for (int i = 0; i < ls.length; i ++) {
				
				if (ls[i] < rs[i]) {
					
					minRL = ls[i];
				}
			}
		}
		
		return "";
	}
}
