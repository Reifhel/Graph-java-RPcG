package Graph;

public class Aresta {
    
    // classe aresta (int peso, vertice origem, vertice destino)
    private int peso;
    private Vertice origem;
    private Vertice destino;

    public Aresta(int peso, Vertice origem, Vertice destino) {
        this.peso = peso;
        this.origem = origem;
        this.destino = destino;
    }
    
    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }


}
