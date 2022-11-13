package app;
import java.util.List;

import Graph.Graph;
import Graph.Vertice;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();

        graph.carregaPajek("teste");
        //graph.gravaPajek("teste");

        graph.verificaCiclo();

        //graph.infoGrafo();

    }
}
