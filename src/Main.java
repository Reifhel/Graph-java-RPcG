import Graph.Graph;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph();
        graph.cria_aresta_direcionada("A", "B", 1);
        graph.cria_aresta_direcionada("A", "C", 1);
        graph.cria_aresta_direcionada("A", "D", 1);
        graph.cria_aresta_direcionada("B", "E", 2);
        graph.cria_aresta_direcionada("C", "E", 1);
        graph.cria_aresta_direcionada("D", "E", 1);
        graph.cria_aresta_nao_direcionada("F", "D", 2);

        System.out.println("Tamanho do grafo: " + graph.getTamanho());

        graph.grava_pajek("teste");
        // graph.carrega_pajek("teste");
        graph.imprime_grafo();
    }
}
