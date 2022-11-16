package Graph;
import java.util.LinkedList;

public class Vertice {
    
    // classe vertice (String nome, linkedlist listaAdjacencia, int id)
    private String nome;
    private LinkedList<Aresta> listaAdjacencia;
    private int id;

    private double proximidade;
    private double intermediacao;

    private boolean visitado;
    private int distancia;
    private Vertice pai;


    public Vertice(String nome, int id) {
        this.nome = nome;
        this.listaAdjacencia = new LinkedList<Aresta>();
        this.visitado = false;
        this.id = id;
        this.proximidade = 0;
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
    
    public double getProximidade() {
        return proximidade;
    }

    public void setProximidade(double proximidade) {
        this.proximidade = proximidade;
    }

    public double getIntermediacao() {
        return intermediacao;
    }

    public void setIntermediacao(double intermediacao) {
        this.intermediacao = intermediacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Vertice getPai() {
        return pai;
    }

    public void setPai(Vertice pai) {
        this.pai = pai;
    }

}
