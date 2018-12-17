package findpath;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.*;

public class PathFinderTest {
    PathFinder pf;
    Graph g;
    ArrayList<Integer> path;

    Graph graph;
    @Before
    public void setUp() throws Exception {
        pf = new PathFinder();
        g = pf.buildGraph("graph.txt");

        path = pf.findPath(g, 1, 6);

        graph = new Graph(5);
    }

    @Test
    public void buildGraph() {
        g.showGraph();

    }

    @Test
    public void decompressTest1() {
        String expected = "antttvaa";
        String actual = pf.decompress(graph, 2, "ant(1x3)va(1x2)");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void decompressTest2() {
        String expected = "anttttvaa";
        String actual = pf.decompress(graph, 2, "ant(1x3)(1x2)va(1x2)");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void decompressTest3() {
        String expected = "antttvattvattva";
        String actual = pf.decompress(graph, 2, "ant(1x3)va(4x3)");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findPath() {

        path = pf.findPath(g, 1, 6);

        for (Integer nd : path)
            System.out.print(nd + " ");
        String expected = "1, 2, 4, 6";
        assertTrue(expected.equals(pf.findPath(g, 1, 6)));
    }

//    giving the same node
    @Test
    public void getPathStringTest() {
        for (Integer nd : path)
            System.out.print(nd + " ");

        String expected = "ttttekskst";
//        System.out.println(pf.getPathString(g, 1, 1));

        assertTrue(expected.equals(pf.getPathString(g, 1, 1)));
    }

    @Test
    public void getPathStringTest1() {
        for (Integer nd : path)
            System.out.print(nd + " ");

        String expected = "ttttekskstttteekseksekstt";
//        System.out.println(pf.getPathString(g, 1, 2));

        assertTrue(expected.equals(pf.getPathString(g, 1, 2)));
    }

//    result for from node 1 to node 6 = from node 6 to node 1
    @Test
    public void getPathStringTest2() {
        for (Integer nd : path)
            System.out.print(nd + " ");
//        System.out.println(pf.getPathString(g, 6, 1));

        String expected = "ttttekskstttteeksekseksttksiaazkadowowodd";

        assertTrue(expected.equals(pf.getPathString(g, 6, 1)));
    }

//    @Test
//    @Parameters

}