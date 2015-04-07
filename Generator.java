package visualDijkstra;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *	<p>
 *	Generator contains several static methods for generating graphs, 
 *	converting them to DOT format, and displaying them using
 *	Graphviz.
 *	</p>
 *
 *	@author Peter Tran
 */
public class Generator
{
	private static File file;
	private static Scanner in;
	private static PrintWriter out;
	private static Runtime runtime;

	private static double round(double value, int places)
	{
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	/**
	 *	Generates random graph given the amount of vertices 
	 *	and edges you specify. The graph generated is completely 
	 *	random. It can have redundant edges, conflicting weights, 
	 *	and self connecting vertices.
	 *
	 *	@param nodes amount of vertices you want
	 *	@param connections amount of edges you want
	 */
	public static void generate(int nodes, int connections)
	{
		String graph = nodes + "\n" + connections + "\n";

		for (int i = 0; i < connections; i++)
		{
			int first = (int) (Math.random() * nodes);
			int second = (int) (Math.random() * nodes);
			double third = round(Math.random(), 2);

			graph = graph + first + " " + second + " " + third + "\n";
		}

		try
		{
			file = new File("visualDijkstra/resources/graph.txt");
			PrintWriter out = new PrintWriter(file);
			out.print(graph);
			out.close();
		}
		catch(Exception e) { System.out.println("Cannot find file."); }
	}

	/**
	 *	Converts the graph.txt file that is generated when you run
	 *	the generate method to a graph.dot file that can be used 
	 *	with Graphviz to display the graph in a .png format.
	 */
	public static void toDot()
	{
		file = new File("visualDijkstra/resources/graph.txt");
		try {in = new Scanner(file);}
		catch(Exception e) {System.out.println("No graph to display! Did you generate a graph?");}

		String graph = "digraph G {\n";
		in.nextLine();
		in.nextLine();
		while (in.hasNext())
		{
			graph = graph + (in.nextInt() + " -> " + in.nextInt() + " [weight = " + ((in.nextDouble() * -1) + 1) + "];\n");
			in.nextLine();
		}

		graph = graph + "}";
		String filename = "visualDijkstra/resources/graph.dot";
		file = new File(filename);

		try {out = new PrintWriter(file);}
		catch(Exception e) {System.out.println("File not found.");}

		out.print(graph);
		out.close();
	}

	/**
	 *	Uses the command line to create a .png from the .dot file 
	 *	and displays it.
	 */
	public static void show()
	{
		String filename = "visualDijkstra/resources/graph.png";
		try
		{
			runtime = Runtime.getRuntime();
			runtime.exec("dot -Tpng " + file + " -o " + filename).waitFor();
			runtime.exec("xdg-open " + filename);
		}
		catch(Exception e) {System.out.println("ERROR");}
	}
}