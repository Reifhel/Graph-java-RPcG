package Graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {

    // classe grafo
    private int tamanho;
    private HashMap<String, Vertice> vertices;
    
    // arestas


    public Graph() {
        this.tamanho = 0;
        this.vertices = new HashMap<String, Vertice>();
    }

    public int getTamanho() {
        return tamanho;
    }

    // função para criar um novo vertice
    private boolean cria_vertice(String nome) {
        // verifica se o vertice já existe
        if (this.vertices.containsKey(nome)) {
            return false;
        }
        // cria o vertice e o adiciona ao grafo
        this.vertices.put(nome, new Vertice(nome));

        // incrementa o tamanho do grafo
        this.tamanho++;
        return true;
    }

    // função para remover vertice
    private boolean remove_vertice(String nome) {
        // verifica se o vertice existe
        if (!this.vertices.containsKey(nome)) {
            return false;
        }
        // remove o vertice do grafo
        this.vertices.remove(nome);

        // decrementa o tamanho do grafo
        this.tamanho--;
        return true;
    }

    // função para criar uma nova aresta
    private boolean cria_aresta(String origem, String destino, int peso) {
        
        // se o vertice de origem não existe, cria um novo
        if (!this.vertices.containsKey(origem)) {
            this.cria_vertice(origem);
        }
        // se o vertice de destino não existe, cria um novo
        if (!this.vertices.containsKey(destino)) {
            this.cria_vertice(destino);
        }

        // pegando os vertices de origem e destino
        Vertice origemVertice = this.vertices.get(origem);
        Vertice destinoVertice = this.vertices.get(destino);
        // criando a aresta
        Aresta aresta = new Aresta(peso, origemVertice, destinoVertice);
        // adicionando a aresta na lista de adjacencia do vertice de origem
        origemVertice.getListaAdjacencia().add(aresta);
        return true;
    }

    // função para criar uma nova aresta
    public boolean cria_aresta_direcionada(String origem, String destino, int peso) {
        return this.cria_aresta(origem, destino, peso);
    }

    // função para criar uma nova aresta
    public boolean cria_aresta_nao_direcionada(String origem, String destino, int peso) {
        this.cria_aresta(origem, destino, peso);
        this.cria_aresta(destino, origem, peso);
        return true;
    }

    // função para imprimir o grafo
    public void imprime_grafo(){
        // percorre todos os vertices do grafo
        for (String nomeVertice : this.vertices.keySet()) {
            // pega o vertice
            Vertice vertice = this.vertices.get(nomeVertice);
            // imprime o nome do vertice
            System.out.print(vertice.getNome() + " -> ");
            // percorre a lista de adjacencia do vertice
            for (Aresta aresta : vertice.getListaAdjacencia()) {
                // imprime o nome do vertice de destino e o peso da aresta
                System.out.printf("%s(%d) ", aresta.getDestino().getNome(), aresta.getPeso());
            }

            System.out.println();
        }
    }

    // gravação em formnato pajek
    public void grava_pajek(String nomeArquivo){

        String filePath = "output/" + nomeArquivo + ".txt";
        System.out.println(filePath);

        File arquivo = new File(filePath);
        try {
            // cria o arquivo
            arquivo.createNewFile();
            // cria o objeto para escrever no arquivo
            FileWriter fileWriter = new FileWriter(arquivo);
            // cria o objeto para escrever no arquivo
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            // escreve o cabeçalho do arquivo
            bufferedWriter.write("*Vertices " + this.tamanho);
            bufferedWriter.newLine();
            // percorre todos os vertices do grafo
            for (String nomeVertice : this.vertices.keySet()) {
                // pega o vertice
                Vertice vertice = this.vertices.get(nomeVertice);
                // escreve o nome do vertice
                bufferedWriter.write(vertice.getNome());
                bufferedWriter.newLine();
            }
            // escreve o cabeçalho do arquivo
            bufferedWriter.write("*Edges");
            bufferedWriter.newLine();
            // percorre todos os vertices do grafo
            for (String nomeVertice : this.vertices.keySet()) {
                // pega o vertice
                Vertice vertice = this.vertices.get(nomeVertice);
                // percorre a lista de adjacencia do vertice
                for (Aresta aresta : vertice.getListaAdjacencia()) {
                    // escreve o nome do vertice de destino e o peso da aresta
                    bufferedWriter.write(aresta.getOrigem().getNome() + " " + aresta.getDestino().getNome() + " " + aresta.getPeso());
                    bufferedWriter.newLine();
                }

            }
            // fecha o arquivo
            System.out.println("Grafo gravado com sucesso!");
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();

            
        }
        
    }
    
    // função para carrergar um grafo a partir de um arquivo pajek
    public void carrega_pajek(String nomeArquivo){
        String filePath = "input/" + nomeArquivo + ".txt";
        System.out.println(filePath);

        File arquivo = new File(filePath);
        try {
            // cria o objeto para ler o arquivo
            FileReader fileReader = new FileReader(arquivo);
            // cria o objeto para ler o arquivo
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // cria o objeto para ler o arquivo
            Scanner scanner = new Scanner(bufferedReader);
            // lê o cabeçalho do arquivo
            String linha = scanner.nextLine();
            // lê o cabeçalho do arquivo
            linha = scanner.nextLine();
            // percorre todos os vertices do grafo
            while (scanner.hasNextLine()) {
                // lê o nome do vertice
                linha = scanner.nextLine();
                // verifica se é o final do arquivo
                if (linha.equals("*Edges")) {
                    break;
                }
                // cria o vertice
                this.cria_vertice(linha);
            }
            // percorre todos os vertices do grafo
            while (scanner.hasNextLine()) {
                // lê o nome do vertice
                linha = scanner.nextLine();
                // cria o vertice
                String[] aresta = linha.split(" ");
                this.cria_aresta(aresta[0], aresta[1], Integer.parseInt(aresta[2]));
            }
            // fecha o arquivo
            scanner.close();

            System.out.println("Grafo carregado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }


}