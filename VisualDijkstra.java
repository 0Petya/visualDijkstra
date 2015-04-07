package visualDijkstra;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 *  <p>
 *  VisualDijkstra is a GUI program that allows you create 
 *  random graphs with your specified amount of vertices 
 *  and edges, highlight the shortest path from your specified 
 *  vertices, and display the graph using Graphviz. 
 *
 *  Below are images of what the GUI looks like, as well as what the 
 *  graph looks like after highlighting the shortest path.
 *  </p>
 *
 *  <img src="../../resources/docVisual.png">
 *  <img src="../../resources/docGraph.png">
 *
 *  @author Peter Tran
 */
public class VisualDijkstra
{
    protected static JButton b1, b2, b3;
    private static TextField vertices, edges, from, to;
    Generator graph;

    /**
     *  Sets up the layout for the GUI, and starts the adding process.
     */
    public static void addComponentsToPane(Container pane)
    {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        addAButton(pane);
    }

    /**
     *  Adds all the individual components for the GUI I specified.
     */
    private static void addAButton(Container container)
    {
        // creates the components
        JLabel l1 = new JLabel("Enter the amount of vertices and edges you want in your graph!");
        vertices = new TextField(5);
        edges = new TextField(5);
        b1 = new JButton("Make it!");

        JLabel l2 = new JLabel("Enter the vertices that you want to find the shortest path to!");
        from = new TextField(5);
        to = new TextField(5);
        b2 = new JButton("Color the shortest path!");

        b3 = new JButton("SHOW ME THE GRAPH!");

        // listen for actions on buttons
        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Generator.generate(Integer.parseInt(vertices.getText()), Integer.parseInt(edges.getText()));
                Generator.toDot();
            }
        });

        b2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Shortest.path(Integer.parseInt(from.getText()), Integer.parseInt(to.getText()));
            }
        });
        
        b3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Generator.show();
            }
        });

        // allign components
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        b1.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);
        b2.setAlignmentX(Component.CENTER_ALIGNMENT);
        b3.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add Components to this container
        // using the default FlowLayout
        container.add(l1);
        container.add(vertices);
        container.add(edges);
        container.add(b1);

        container.add(Box.createRigidArea(new Dimension(475, 25)));

        container.add(l2);
        container.add(from);
        container.add(to);
        container.add(b2);

        container.add(Box.createRigidArea(new Dimension(475, 25)));

        container.add(b3);
    }

    /**
     * Create the GUI and show it.  For thread safety, 
     * this method should be invoked from the 
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        // create and set up the window
        JFrame frame = new JFrame("Visual Dijkstra!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up the content pane
        addComponentsToPane(frame.getContentPane());

        // display the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        // schedule a job for the event-dispatching thread
        // creating and showing this application's GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }
}