package app;
import java.util.List;

import Graph.Graph;
import Graph.Vertice;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();

        //graph.carregaPajek("teste");

        // graph.leTxt("actors");
        graph.carregaPajek("movieDataSet");

        graph.centralidadeProximidade();
        

        //graph.gravaPajek("jorge");    
        //graph.centralidadeProximidade();
        
        System.out.println("tamanho: " + graph.getTamanho());


    }
}
