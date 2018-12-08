package findpath;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        PathFinder pf = new PathFinder();
        Graph g = pf.buildGraph("graph.txt");
        g.showGraph();
        ArrayList<Integer> path = pf.findPath(g, 1, 6);
        System.out.print("The shortest path is ");
        for (Integer nd : path)
            System.out.print(nd + " ");
        System.out.println(pf.getPathString(g, 1, 6));

    }
}
