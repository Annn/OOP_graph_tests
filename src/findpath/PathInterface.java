package findpath;

import java.util.ArrayList;
import java.util.Collection;

public interface PathInterface {

    /*
    metoda, która jest odpowiedzialna za odczytanie pliku graph.txt, zamiane
    informacji zapisanej w postaci binarnej na dziesietna oraz zbudowanie
    grafu na podstawie odczytanego i rozkodowanego kodu binarnego
    */
    Graph buildGraph(String fileName);

    /*
    metoda dekodujaca i zwracajaca ciag znakow znajdujacy sie w zadanym wezle
    */
    String decompress(Graph graph, int node, String code);

    /*
    metoda znajdująca najkrotsza ścieżkę (numery wezlow) pomiędzy dwoma wezlami
    zadanymi na wejsciu
    */
    ArrayList<Integer> findPath(Graph graph, int originNode, int destinationNode);

    /*
    metoda zwracajaca rozkodowany ciag znakow, które sa przechowywane przez
    poszczegolne wezly w najkrotszej sciezce pomiedzy dwoma wezlami oznaczonymi
    jako beginingNode oraz destinationNode
    */
    String getPathString(Graph graph, int originNode, int destinationNode);
}
