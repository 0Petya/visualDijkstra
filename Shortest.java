package visualDijkstra;

import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 *  <p>
 *  Shortest figures out the shortest path through the graph 
 *  generated, using Dijkstra's algorithm, 
 *  and edits the .dot file to color the path.
 *
 *  Below is a graph created after doing an amortized 
 *  analysis of Dijkstra's algorithm.
 *  </p>
 *
 *  <img src="../../resources/docAnalysis.png">
 *
 *  @author Peter Tran
 */
public class Shortest
{
	private static Scanner in;
    private static PrintWriter out;
    private static String[] finalResult;

    /**
     *  Figures out the shortest path through the graph from 
     *  the vertices you give it. Creates a .dot file 
     *  from the .txt file generated from the Generator 
     *  and then edits it to display the shortest path.
     *
     *  @param s vertex you want to begin at
     *  @param t vertex you want to end up at
     */
	public static void path(int s, int t)
	{
		File file = new File("visualDijkstra/resources/graph.txt");
		try {in = new Scanner(file);}
		catch(Exception e) {System.out.println("ERROR");}

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, s);

        // saves the shortest path to a String
        String result = "";
        if (sp.hasPathTo(t))
        {
            if (sp.hasPathTo(t))
            {
                for (DirectedEdge e : sp.pathTo(t))
                {
                    result = result + e;
                    result = result.substring(0, result.indexOf(" ")) + "\n";
                }

                result = result.substring(0, result.lastIndexOf("\n"));
                result = result.replaceAll("->", " -> ");
            }

            finalResult = result.split("\n");
        }

        else
            finalResult = null;

        if (finalResult != null)
            Shortest.edit();
	}

    private static void edit()
    {
        String color = " [color = \"red\"];";

        Generator.toDot();
        File file = new File("visualDijkstra/resources/graph.dot");
        try {in = new Scanner(file);}
        catch(Exception e) {System.out.println("ERROR");}

        String dotFile = "";
        while (in.hasNextLine())
            dotFile = dotFile + in.nextLine() + "\n";

        String[] dotArray = null;
        dotArray = dotFile.split("\n");

        for (int i = 1; i < dotArray.length - 1; i++)
            for (String s : finalResult)
            {
                String tmp = dotArray[i].substring(0, dotArray[i].indexOf("[") - 1);
                if (s.equals(tmp))
                    dotArray[i] = dotArray[i].substring(0, dotArray[i].length() - 1) + color;
            }

        file = new File("visualDijkstra/resources/graph.dot");
        try {out = new PrintWriter(file);}
        catch(Exception x) {System.out.println("ERROR");}

        for (String s : dotArray)
            out.println(s);
        out.close();
    }
}