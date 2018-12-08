package findpath;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class PathFinder implements PathInterface {

    /*
    read the graph data from fileName
     */
    public Graph buildGraph(String fileName) {
        FileReader reader;
        Graph graph = null;
        // load graph data
        try {
            reader = new FileReader(fileName);
            Scanner scanner = new Scanner(reader);
            String[] data = scanner.nextLine().split(" ");
            // read the number of nodes
            int numOfNodes = toDecimal(data[0]);
            //System.out.println("Number of nodes: " + numOfNodes);
            // instantiate the graph
            graph = new Graph(numOfNodes);
            // read the graph configuration
            for (int i = 0; i < numOfNodes; i++) {
                data = scanner.nextLine().split(" ");
                int outNode = toDecimal(data[0]);
                int numChilds = toDecimal(data[1]);
                ArrayList<Integer> inNodes = new ArrayList<>();
                for (int j = 0; j < numChilds; j++)
                    inNodes.add(toDecimal(data[2 + j]));
                graph.nodes.put(outNode, inNodes);
            }

            // read the node interior codes
            ArrayList<String> interiorCodes = new ArrayList<>();
            while (scanner.hasNextLine()) {
                interiorCodes.add(scanner.nextLine());
            }
            // decode the nodes interior
            for (int i = 0; i < interiorCodes.size(); i++)
                decompress(graph, i + 1, interiorCodes.get(i));
            reader.close();
        }
        catch (Exception e) {
            System.out.println("ERROR WHILE READING THE GRAPH");
        }
        return graph;
    }

    /*
    transform binary code string to decimal number
     */
    private int toDecimal(String binary) {
        int size = binary.length();
        int res = 0;
        for (int i = 0; i < size; i++) {
            res += Integer.parseInt(Character.toString(binary.charAt(i))) *
                    Math.pow(2, size - i - 1);
        }
        return res;
    }

    /*
    decode the node interior
     */
    public String decompress(Graph graph, int node, String code) {

        String value = "";

        ArrayList<Integer> idxOpen = new ArrayList<>();
        ArrayList<Integer> idxClose = new ArrayList<>();
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '(') {
                idxOpen.add(i);
            }
            if (code.charAt(i) == ')') {
                idxClose.add(i);
            }
        }

        ArrayList<String> parts = new ArrayList<>();
        ArrayList<Integer> lasts = new ArrayList<>();
        ArrayList<Integer> repetitions = new ArrayList<>();

        int numPars = idxOpen.size();
        //System.out.print("NP=" + numPars);

        if (numPars > 0) {
            // take the beginning part
            value = code.substring(0, idxOpen.get(0));
            //System.out.print(" fp=" + value);
            // read the code parameters
            for (int i = 0; i < numPars; i++) {
                // read the data inside parentheses
                String[] inside = code.substring(idxOpen.get(i) + 1,
                        idxClose.get(i)).split("x");
                lasts.add(Integer.parseInt(inside[0]));
                repetitions.add(Integer.parseInt(inside[1]));
                //System.out.print(" l=" + lasts.get(i) + " r=" + repetitions.get(i));
                // read the string data outside parentheses
                if (i < numPars - 1) {
                    parts.add(code.substring(idxClose.get(i) + 1,
                            idxOpen.get(i + 1)));
                    //System.out.print(" p=" + parts.get(i));
                }
                else { // last part
                    parts.add(code.substring(idxClose.get(i) + 1));
                    //System.out.print(" lp=" + parts.get(i));
                }
            }
            // reconstruct the value
            for (int i = 0; i < numPars; i++) {
                String repPart = value.substring(value.length() - lasts.get(i));
                //System.out.print(" :: rp=" + repPart);
                for (int j = 0; j < repetitions.get(i) - 1; j++)
                    value += repPart;
                value += parts.get(i);
            }
            //System.out.println();
        }
        else {
            value = code;
        }

        // put the decoded value as the node interior
        if (graph.interiorOfNodes.containsKey(node))
            graph.interiorOfNodes.replace(node, value);
        else
            graph.interiorOfNodes.put(node, value);

        return value;
    }

    private Integer[] Dijkstra(Graph graph, int node) {

        // 1  function Dijkstra(Graph, source):
        // 2      dist[source]  := 0                     // Distance from source to source
        // 3      for each vertex v in Graph:            // Initializations
        // 4          if v ≠ source
        // 5              dist[v]  := infinity           // Unknown distance function from source to v
        // 6              previous[v]  := undefined      // Previous node in optimal path from source
        // 7          end if
        // 8          add v to Q                         // All nodes initially in Q
        // 9      end for
        //10
        //11      while Q is not empty:                  // The main loop
        //12          u := vertex in Q with min dist[u]  // Source node in first case
        //13          remove u from Q
        //14
        //15          for each neighbor v of u:           // where v has not yet been removed from Q.
        //16              alt := dist[u] + length(u, v)
        //17              if alt < dist[v]:               // A shorter path to v has been found
        //18                  dist[v]  := alt
        //19                  previous[v]  := u
        //20              end if
        //21          end for
        //22      end while
        //23      return dist[], previous[]
        //24  end function

        // N is the biggest code from nodes
        int N = 0;
        for (Integer nd : graph.interiorOfNodes.keySet()) {
            if (nd > N) N = nd;
        }

        double inf = Double.POSITIVE_INFINITY;

        Double[] dist = new Double[N + 1];
        Integer[] previous = new Integer[N + 1];
        ArrayList<Integer> Q = new ArrayList<>();

        dist[node] = 0.0;

        for (Integer nd : graph.nodes.keySet()) {
            if (nd != node) {
                dist[nd] = inf;
                previous[nd] = null;
            }
            Q.add(nd);
        }

        while (Q.size() != 0) {
            Integer u = Q.get(0);
            double minDist = dist[u];
            for (Integer nd : Q) {
                if (dist[nd] < minDist) {
                    u = nd;
                    minDist = dist[u];
                }
            }
            Q.remove(u);

            // check the neighbours
            for (Integer v : graph.nodes.get(u)) {
                double alt = dist[u] + graph.interiorOfNodes.get(v).length();
                if (alt < dist[v]) {
                    dist[v] = alt;
                    previous[v] = u;
                }
            }
        }

        return previous;
    }


    /*
    returns the shortest path (as codes of nodes) between origin and destination
     */
    public ArrayList<Integer> findPath(Graph graph, int originNode, int destinationNode) {

        //1  S := empty sequence
        //2  u := target
        //3  while previous[u] is defined:                // Construct the shortest path with a stack S
        //4      insert u at the beginning of S           // Push the vertex into the stack
        //5      u := previous[u]                         // Traverse from target to source
        //6  end while

        ArrayList<Integer> path = new ArrayList<>();
        Integer[] prev = this.Dijkstra(graph, originNode);

        Integer u = destinationNode;
        while (prev[u] != null) {
            path.add(0, u);
            u = prev[u];
        }
        path.add(0, originNode);

        return path;
    }

    /*
    metoda zwracajaca rozkodowany ciag znakow, które sa przechowywane przez
    poszczegolne wezly w najkrotszej sciezce pomiedzy dwoma wezlami oznaczonymi
    jako beginingNode oraz destinationNode
    */
    public String getPathString(Graph graph, int originNode, int destinationNode) {
        String pathString = "";
        ArrayList<Integer> path = findPath(graph, originNode, destinationNode);

        for (Integer nd : path)
            pathString += graph.interiorOfNodes.get(nd);

        return pathString;
    }

}
