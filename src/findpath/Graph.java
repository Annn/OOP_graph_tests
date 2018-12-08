package findpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    private final int numberOfNodes;
    // graph configuration
    public Map <Integer, ArrayList<Integer>> nodes;
    // nodes interior
    public Map <Integer, String> interiorOfNodes;

    public Graph(int number) {
        this.numberOfNodes = number;
        nodes = new HashMap<>();
        interiorOfNodes = new HashMap<>();
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void showGraph() {
        System.out.println("Number of nodes: " + this.numberOfNodes);
        System.out.println("Configuration:");
        for (Map.Entry<Integer, ArrayList<Integer>> entry : nodes.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Integer> value = entry.getValue();
            System.out.print(key + " ::: ");
            for (Integer val : value) {
                System.out.print(val + " : ");
            }
            System.out.println();
        }
        System.out.println("Nodes interior:");
        for (Map.Entry<Integer, String> entry : interiorOfNodes.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " : " + value);
        }

    }

}
