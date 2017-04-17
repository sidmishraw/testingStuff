/**
 * Project: TestingStuff
 * Package: simplicalcomplex
 * File: SimplicalComplex.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 16, 2017 12:39:15 PM
 */
package simplicalcomplex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author sidmishraw
 *
 *         Qualified Name: simplicalcomplex.SimplicalComplex
 *
 */
public class SimplicalComplex {
	
	
	// Define a static logger variable so that it references the
	// Logger instance named "SimplicalComplex".
	private static final Logger logger = LogManager.getLogger(SimplicalComplex.class);
	
	/**
	 * Simplicial complex is a collection of open simplicies, also known as 'K',
	 * such that every
	 * phases of the open simplex is in K. A collection of all association rules
	 * (database object)
	 * form a simplicial complex (geometry object).
	 *
	 * Aprior principle in data mining is called closed condition in simplicial
	 * complex.
	 */
	private static class Simplex {
		
		
		// group name: "0", "0 1", "0 1 2" etc
		public String			groupName;
		// # of rules to be mined, basically the #th simplex to be made
		public Integer			nbrRules;
		// [row#] -- inverted list of 1's
		public List<Integer>	ones	= new ArrayList<>();
		// [simplex, links] -- connected vertices on graph with AND'ed ones
		public List<LinkPair>	links	= new ArrayList<>();
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Simplex [groupName=" + this.groupName + ", nbrRules=" + this.nbrRules + ", ones=" + this.ones
					+ ", links=" + this.links + "]";
		}
	}
	
	/**
	 * // {simplex, links} -- connected vertices on graph with AND'ed ones
	 * 
	 * @author sidmishraw
	 *
	 *         Qualified Name: simplicalcomplex.LinkPair
	 *
	 */
	private static class LinkPair {
		
		
		public Integer			simplex	= 0;
		public List<Integer>	links	= new ArrayList<>();
	}
	
	/**
	 * 
	 * @author sidmishraw
	 *
	 *         Qualified Name: simplicalcomplex.Pair
	 *
	 */
	private static class Pair implements Comparable<Pair> {
		
		
		public static Integer	scolumn	= 0;
		public Integer			column	= 0;
		public Integer			count	= 0;
		
		public void incrementColumn() {
			
			scolumn++;
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Pair o) {
			
			return this.count.compareTo(o.count);
		}
	}
	
	// The bitmap -- input data matrix :data
	private static List<List<Boolean>>		inputDataMatrix		= new ArrayList<>();
	
	// inverted list of 1's :lookup
	private static List<List<Integer>>		invertedLookup		= new ArrayList<>();
	
	// [simplex, count] -- sorted list of qualified counts
	private static List<Pair>				qualifiedColumns	= new ArrayList<>();
	
	// {simplex: frequency}
	private static Map<Integer, Integer>	results				= new HashMap<>();
	
	private static int						numRules;
	private static float					threshold;
	
	// filename -- default
	private static String					outFilename			= "results.txt";
	private static BufferedWriter			bufferedWriter		= null;
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		if (args.length < 5) {
			
			System.out.println(
					"Usage: SimplicialComplex <#rules> <threshold> <#columns> <#records> <data_file> [-brief]");
			System.exit(0);
		}
		
		try {
			
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outFilename))));
			
			numRules = Integer.parseInt(args[0]);
			threshold = Float.parseFloat(args[1]);
			
			int nbrCols = Integer.parseInt(args[2]);
			int nbrRows = Integer.parseInt(args[3]);
			
			String inputFileName = args[4];
			
			// read from .dat file
			readFile(inputFileName, nbrCols, nbrRows);
			
			// String bitmapFileName = "input_bitmap.txt";
			
			// create bitmap file
			// saveToBitMap(bitmapFileName, nbrCols, nbrRows);
			
			// reduce and sort topology space for faster processing
			// the threshold limit is defined as the product of the threshold
			// and
			// number of rows i.e (int) (threshold * nbrRows)
			reduceSpace((int) (threshold * nbrRows));
			
			// visit each simplex once starting with lowest # of 1's
			numRules = (numRules < nbrCols) ? numRules : nbrCols;
			
			System.out.println("Running Simplicial Complex rules: " + numRules + " threshold: " + threshold
					+ " qualified column count: " + qualifiedColumns.size());
			
			IntStream.range(0, qualifiedColumns.size()).map(i -> qualifiedColumns.size() - i - 1).parallel().map(i -> {
				
				int col = qualifiedColumns.get(i).column;
				String groupName = String.valueOf(col);
				
				Simplex simplex = new Simplex();
				
				buildSimplex(simplex, groupName, 1, invertedLookup.get(col), qualifiedColumns, i + 1,
						(int) (threshold * nbrRows));
				
				try {
					connectSimplex(simplex, (int) (threshold * nbrRows));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return 0;
			}).count();
			
			// write the results of the simplicialComplex
			writeResults();
		} catch (IOException e) {
			
			logger.error(e);
		} finally {
			
			if (null != bufferedWriter) {
				
				bufferedWriter.close();
			}
		}
	}
	
	/**
	 * Once the simplex is successfully created by buildSimplex, this function
	 * then uses that information
	 * to connect all possible links on topological space to it. However, it
	 * must do it in a way that
	 * is efficient and avoid reconnecting simplex links by navigating from
	 * left-to-right of all the
	 * qualified columns. As it tries to connect each of the simplex dimension,
	 * it prints out the simplex
	 * name and the large itemsets computed. Once all information are reported
	 * for this simplex, it
	 * affectively combines the next link to build a higher-dimension simplex
	 * and repeat the process.
	 *
	 * This is also known as finding all the star-neighbors for a given
	 * 0-simplex, called 'V' (vertex).
	 * Build all the open simplicies that have 'V' as a phase. Note that once
	 * completed, all these
	 * open simplicies must be removed from 'K'
	 * 
	 * @param simplex
	 * @throws IOException
	 */
	private static void connectSimplex(Simplex simplex, int thresholdLimit) throws IOException {
		
		if (null == results.get(simplex.nbrRules)) {
			
			results.put(simplex.nbrRules, 1);
		} else {
			
			results.put(simplex.nbrRules, results.get(simplex.nbrRules) + 1);
		}
		
		// print simplex
		printSimplex(simplex);
		
		if (simplex.nbrRules < numRules) {
			
			IntStream.range(0, simplex.links.size()).parallel().map(i -> {
				
				LinkPair link = simplex.links.get(i);
				String gname = simplex.groupName + " " + String.valueOf(link.simplex);
				List<Pair> cols = new ArrayList<>();
				
				IntStream.range(i + 1, simplex.links.size()).parallel().map(j -> {
					
					LinkPair sublink = simplex.links.get(j);
					Pair pair = new Pair();
					pair.column = sublink.simplex;
					pair.count = sublink.links.size();
					cols.add(pair);
					
					return 0;
				}).count();
				
				Simplex subsimplex = new Simplex();
				
				buildSimplex(subsimplex, gname, simplex.nbrRules + 1, link.links, cols, 0, thresholdLimit);
				
				try {
					connectSimplex(subsimplex, thresholdLimit);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return 0;
			}).count();
		}
	}
	
	/**
	 * @param simplex
	 * @throws IOException
	 */
	private static void printSimplex(Simplex simplex) throws IOException {
		
		logger.error("HARMLESS: Printing Simplex: " + simplex);
		
		bufferedWriter.write(String.format("[%s] %s\n", simplex.groupName,
				simplex.ones.parallelStream().filter(e -> e != -1).collect(Collectors.toList()).size()));
		
		logger.error("HARMLESS: Printed Simplex: " + simplex);
	}
	
	/**
	 * The goal of this function is to build the simplex structure that is
	 * required for the algorithm to visit each
	 * simplex once, moving from lowest # of 1's to highest. Below is the format
	 * of the structure and what is being
	 * stored inside it.
	 * Simplex Structure:
	 * 1. name of simplex: "0", "0 1", "0 1 2" and etc.
	 * 2. # of rules or simplex dimension: 1 is for 0-simplex, 2 is for
	 * 1-simplex and so on.
	 * 3. indices to all AND'ed ones for this simplex. ex: inverted
	 * list={0,1000,65000}.
	 * 4. all qualified columns associated with this simplex with all AND'ed
	 * indices.
	 * ex: [col=0, inverted list={0,1122,32000,65123}]
	 * [col=4, inverted list={1,1122,32111,65000}]
	 * [col=1256, inverted list={0,32000,65000}]
	 * note: all {col,inverted_list} pairs must qualify the given threshold
	 * 
	 * @param simplex
	 * @param groupName
	 * @param i
	 * @param list
	 * @param qualifiedColumns2
	 * @param j
	 */
	private static void buildSimplex(Simplex simplex, String groupName, int nbrRules, List<Integer> ones,
			List<Pair> cols, int start, int thresholdLimit) {
		
		simplex.groupName = groupName;
		simplex.nbrRules = nbrRules;
		simplex.ones = ones;
		int nlinks = cols.size() - start;
		
		if (nlinks > 0) {
			
			IntStream.range(start, cols.size()).parallel().map(index -> {
				
				int col = cols.get(index).column;
				LinkPair link = new LinkPair();
				
				IntStream.range(0, ones.size()).parallel().map(row -> {
					
					if (ones.get(row) != -1) {
						
						if (inputDataMatrix.get(col).get(ones.get(row))) {
							
							link.links.add(ones.get(row));
						}
					}
					
					return 0;
				}).parallel().count();
				
				if (link.links.size() >= thresholdLimit) {
					
					link.simplex = col;
					simplex.links.add(link);
				}
				
				return 0;
			}).parallel().count();
		}
	}
	
	/**
	 * 
	 */
	private static void writeResults() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This function run once on the data matrix to reduce the number of columns
	 * or 0-simplicies
	 * so that the program can operate on reduced topological space. It also
	 * sorts the columns
	 * depending on the number of 1's appear in each column. This allows the
	 * program to run
	 * a lot faster because any initial 0-simplex below the threshold will be
	 * excluded.
	 */
	private static void reduceSpace(int thresholdLimit) {
		
		invertedLookup.parallelStream().forEach(list -> {
			
			Pair pair = new Pair();
			
			pair.column = Pair.scolumn;
			
			list.parallelStream().map(i -> {
				
				if (i != -1) {
					
					pair.count++;
				}
				
				return 0;
			}).count();
			
			if (pair.count >= thresholdLimit) {
				
				qualifiedColumns.add(pair);
			}
			
			pair.incrementColumn();
		});
		
		qualifiedColumns = qualifiedColumns.parallelStream().filter(e -> e != null).collect(Collectors.toList());
		// sort the qualified columns based on the counts per column
		Collections.sort(qualifiedColumns, (p1, p2) -> p1.compareTo(p2));
	}
	
	/**
	 * @param bitmapFileName
	 * @param nbrCols
	 * @param nbrRows
	 */
	@SuppressWarnings("unused")
	private static void saveToBitMap(String bitmapFileName, int nbrCols, int nbrRows) {
		
		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(new File(bitmapFileName))))) {
			
			System.out.println("Writing bitmap...");
			
			inputDataMatrix.parallelStream().forEach(list -> {
				
				list.parallelStream().forEach(element -> {
					
					int i = (element) ? 1 : 0;
					
					try {
						
						bufferedWriter.write(i);
					} catch (IOException e) {
						
						logger.error(e);
					}
				});
				
				try {
					
					bufferedWriter.write("\n");
				} catch (Exception e) {
					
					logger.error(e);
				}
			});
			
			System.out.println("Finished writing bit-map");
		} catch (IOException e) {
			
			logger.error(e);
		}
	}
	
	/**
	 * // The bitmap -- input data matrix :data
	 * private static List<List<Boolean>> inputDataMatrix = new ArrayList<>();
	 * 
	 * // inverted list of 1's :lookup
	 * private static List<List<Integer>> invertedLookup = new ArrayList<>();
	 * 
	 * @param inputFileName
	 * @param nbrCols
	 * @param nbrRows
	 */
	private static void readFile(String inputFileName, int nbrCols, int nbrRows) {
		
		logger.error("HARMLESS: Started reading from file named: " + inputFileName);
		
		for (int col = 0; col < nbrCols; col++) {
			
			// initialize the list of lists
			invertedLookup.add(Stream.generate(() -> -1).limit(nbrRows).collect(java.util.stream.Collectors.toList()));
			inputDataMatrix
					.add(Stream.generate(() -> false).limit(nbrRows).collect(java.util.stream.Collectors.toList()));
		}
		
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(inputFileName))))) {
			
			String line = null;
			int row = 0;
			int maxColumnLength = -1;
			
			while (null != (line = bufferedReader.readLine())) {
				
				String[] elements = line.split(" ");
				
				// it seems the guy makes a transpose of the matrix while
				// reading in
				// hence building it as a transpose
				// the first element of the line is the length of the column
				for (int i = 1; i <= Integer.parseInt(elements[0]); i++) {
					
					int col = Integer.parseInt(elements[i]);
					
					inputDataMatrix.get(col).set(row, true);
					
					invertedLookup.get(col).set(row, row);
					
					if (col > maxColumnLength) {
						
						maxColumnLength = col;
					}
				}
				
				row++;
			}
			
			System.out.println("Max column length: " + maxColumnLength);
		} catch (IOException e) {
			
			logger.error(e);
		}
		
		logger.error("HARMLESS: Finished reading from file named: " + inputFileName);
	}
	
}
