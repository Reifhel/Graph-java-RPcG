package Graph;
import java.util.LinkedList;

public class Vertice {
    
    // classe vertice (String nome, linkedlist listaAdjacencia)
    private String nome;
    private LinkedList<Aresta> listaAdjacencia;
    private boolean visitado;

    public Vertice(String nome) {
        this.nome = nome;
        this.listaAdjacencia = new LinkedList<Aresta>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getVisitado() {
        return visitado;
    }

    public void setVisitado(Boolean visitado) {
        this.visitado = visitado;
    }

    public LinkedList<Aresta> getListaAdjacencia() {
        return listaAdjacencia;
    }

    public int getGrau(){
        return listaAdjacencia.size();
    }
}
