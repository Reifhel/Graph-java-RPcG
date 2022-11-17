package Graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Graph {

    // classe grafo
    private int tamanho;
    private HashMap<String, Vertice> vertices;


    // construtor
    public Graph() {
        this.tamanho = 0;
        this.vertices = new HashMap<String, Vertice>();
    }

    public List<Vertice> getVertices() {
        return new ArrayList<Vertice>(vertices.values());
    }

    public Vertice getVertice(String nome) {
        return vertices.get(nome);
    }

    public Vertice getVertice(int id){
        for(Vertice v : vertices.values()){
            if(v.getId() == id){
                return v;
            }
        }
        return null;
    }

    // função para pegar o tamanho do grafo
    public int getTamanho() {
        return tamanho;
    }

    public int getTamanhoArestas() {
        int tamanhoArestas = 0;
        for (Vertice v : vertices.values()) {
            tamanhoArestas += v.getListaAdjacencia().size();
        }
        return tamanhoArestas;
    }

    private boolean criaVertice(String nome, int id) {
        // verifica se o vertice já existe
        if (this.vertices.containsKey(nome)) {
            return false;
        }
        // cria o vertice e o adiciona ao grafo
        this.vertices.put(nome, new Vertice(nome, id));

        // incrementa o tamanho do grafo
        this.tamanho++;
        return true;
    }

    // função para criar um novo vertice
    private boolean criaVertice(String nome) {
        // verifica se o vertice já existe
        if (this.vertices.containsKey(nome)) {
            return false;
        }
        // cria o vertice e o adiciona ao grafo
        this.vertices.put(nome, new Vertice(nome, this.tamanho));

        // incrementa o tamanho do grafo
        this.tamanho++;
        return true;
    }

    // função para criar uma nova aresta
    private boolean criaAresta(String origem, String destino, int peso) {
        
        // se o vertice de origem não existe, cria um novo
        if (!this.vertices.containsKey(origem)) {
            this.criaVertice(origem);
        }
        // se o vertice de destino não existe, cria um novo
        if (!this.vertices.containsKey(destino)) {
            this.criaVertice(destino);
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

    private boolean criaAresta(int idOrigem, int idDestino, int peso){
        try{
            String origem = getVertice(idOrigem).getNome();
            String destino = getVertice(idDestino).getNome();
            this.criaAresta(origem, destino, peso);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    // função para criar uma nova aresta
    public boolean criaArestaDirecionada(String origem, String destino, int peso) {
        return this.criaAresta(origem, destino, peso);
    }

    // função para criar uma nova aresta
    public boolean criaArestaNaoDirecionada(String origem, String destino, int peso) {
        this.criaAresta(origem, destino, peso);
        this.criaAresta(destino, origem, peso);
        return true;
    }



    // função para imprimir o grafo
    public void imprimeGrafo(){
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

    // função de busca de profundidade
    public List<Vertice> busca_Profundidade(String nomeVertice) {
        
        // cria uma lista de vertices
        List<Vertice> vertices = new ArrayList<Vertice>();
        // pega o vertice de origem
        Vertice vertice = this.vertices.get(nomeVertice);
        // chama a função de busca em profundidade
        this.busca_Profundidade(vertice, vertices);

        // limpando a lista de visitados
        this.limpaVertices();
        
        // retorna a lista de vertices
        return vertices;
    }

    private void busca_Profundidade(Vertice vertice, List<Vertice> vertices) {
        // marca o vertice como visitado
        vertice.setVisitado(true);
        // adiciona o vertice na lista de vertices
        vertices.add(vertice);
        // percorre a lista de adjacencia do vertice
        for (Aresta aresta : vertice.getListaAdjacencia()) {
            // pega o vertice de destino da aresta
            Vertice destino = aresta.getDestino();
            // verifica se o vertice de destino já foi visitado
            if (!destino.getVisitado()) {
                // chama a função de busca em profundidade
                this.busca_Profundidade(destino, vertices);
            }
        }
    }

    // função de busca em largura
    public List<Vertice> busca_Largura(String nomeVertice) {
        
        // cria uma lista de vertices
        List<Vertice> vertices = new ArrayList<Vertice>();
        // pega o vertice de origem
        Vertice vertice = this.vertices.get(nomeVertice);
        // chama a função de busca em largura
        this.busca_Largura(vertice, vertices);

        // limpando a lista de visitados
        this.limpaVertices();
        // retorna a lista de vertices
        return vertices;
    }

    private void busca_Largura(Vertice vertice, List<Vertice> vertices) {
        // cria uma fila de vertices
        List<Vertice> fila = new ArrayList<Vertice>();
        // marca o vertice como visitado
        vertice.setVisitado(true);
        // adiciona o vertice na fila
        fila.add(vertice);
        // percorre a fila
        while (!fila.isEmpty()) {
            // pega o primeiro vertice da fila
            Vertice v = fila.remove(0);
            // adiciona o vertice na lista de vertices
            vertices.add(v);
            // percorre a lista de adjacencia do vertice
            for (Aresta aresta : v.getListaAdjacencia()) {
                // pega o vertice de destino da aresta
                Vertice destino = aresta.getDestino();
                // verifica se o vertice de destino já foi visitado
                if (!destino.getVisitado()) {
                    // marca o vertice como visitado
                    destino.setVisitado(true);
                    // adiciona o vertice na fila
                    fila.add(destino);
                }
            }
        }
        // limpa a lista de visitados
        this.limpaVertices();
    }

    // funções projeto colaborativo 2

    // Módulo de gravação de Grafo, ponderado e rotulado em Arquivo (Formato Pajek em anexo);
    public void gravaPajek(String nomeArquivo){

        String filePath = "src/output/" + nomeArquivo + ".txt";

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
                // escreve o vertice no arquivo e o seu id
                bufferedWriter.write(vertice.getId() + " \"" + vertice.getNome() + "\"");
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
                    bufferedWriter.write(aresta.getOrigem().getId() + " " + aresta.getDestino().getId() + " " + aresta.getPeso());
                    bufferedWriter.newLine();
                }

            }
            // fecha o arquivo
            
            System.out.println("--------------------------------");
            System.out.println("Guardando no caminho -> " + filePath);
            System.out.println("Grafo gravado com sucesso!");
            System.out.println("--------------------------------");
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();

            
        }
        
    }
    
    // Módulo de carregamento de Grafo de Arquivo (Formato Pajek em anexo);
    public void carregaPajek(String nomeArquivo){

        // caminho do arquivo
        String filePath = "src/input/" + nomeArquivo + ".txt";

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

            // percorre todos os vertices do grafo
            while (scanner.hasNextLine()) {
                // lê o nome do vertice
                linha = scanner.nextLine();
                // verifica se é o final do arquivo
                if (linha.equals("*Edges")) {
                    break;
                }
                // cria o vertice

                // pegando o nome sem tirar o espaço dele
                String nome = linha.substring(linha.indexOf("\"") + 1, linha.lastIndexOf("\""));
                // vendo se n tem espaço no começo do nome
                if (nome.charAt(0) == ' ') {
                    nome = nome.substring(1);
                }

                int id = Integer.parseInt(linha.substring(0, linha.indexOf(" ")));

                
                this.criaVertice(nome, id);
                System.out.println(this.tamanho);
            }
            // percorre todos os vertices do grafo
            while (scanner.hasNextLine()) {
                // lê o nome do vertice
                linha = scanner.nextLine();
                // cria o vertice
                String[] aresta = linha.split(" ");

                int origem = Integer.parseInt(aresta[0]);
                int destino = Integer.parseInt(aresta[1]);
                int peso = Integer.parseInt(aresta[2]);

                this.criaAresta(origem, destino, peso);

            }
            // fecha o arquivo
            scanner.close();

            System.out.println("--------------------------------");
            System.out.println("Carregando do caminho -> " + filePath);
            System.out.println("Grafo carregado com sucesso!");
            System.out.println("--------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }  
    
    // função para verificar a conexidade do grafo ( caso seja desconexo mostra os subgrafos)
        // um grafo conexo é aquele que possui apenas um componente
        // um grafo desconexo é aquele que possui mais de um componente
    
    public void verificaConexidade(){
        getComponentes();
    }

    // Função que verifica se o grafo é Euleriano ou não
        // um grafo euleriano é um grafo conexo e todo vertice tem grau par
        // um grafo semi-euleriano é um grafo conexo e tem exatamente 2 vertices de grau impar

    public void verificaEuleriano(){
        // caso o grafo seja conexo
        if(conexo()){
            // percorre todos os vertices do grafo
            int contaVerticesImpares = 0;
            for (String nomeVertice : this.vertices.keySet()) {
                // pega o vertice
                Vertice vertice = this.vertices.get(nomeVertice);
                // se o grau do vertice for impar
                if(vertice.getGrau() % 2 != 0){
                    // incrementa o contador de vertices impares
                    contaVerticesImpares++;

                }
            }

            // se o contador de vertices impares for igual a 0 ou 2
            if(contaVerticesImpares == 0 || contaVerticesImpares == 2){
                // o grafo é euleriano
                System.out.println("O grafo é euleriano!");
            } // se o contador de vertices impares for maior que 2
            else{
                // o grafo não é euleriano
                System.out.println("O grafo não é euleriano!");
            }
        } // caso o grafo seja desconexo
        else {
            // como o grafo é desconexo, ele não é euleriano
            System.out.println("O grafo não é euleriano!");
        }
    }

    // Função que verifica se o grafo é Cíclico ou não
        // para um grafo ser ciclico ele tem que ir de vertice em vertice 
        // sem que o vertice de destino já tenha sido visitado
    public void verificaCiclo(){

        // percorre todos os vertices do grafo
        for (String nomeVertice : this.vertices.keySet()) {
            // pega o vertice
            Vertice vertice = this.vertices.get(nomeVertice);
            // se o vertice não foi visitado
            if(!vertice.getVisitado()){
                // verifica se o vertice tem ciclo
                if(verificaCiclo(vertice)){
                    // o grafo tem ciclo
                    System.out.println("O grafo tem ciclo!");
                    this.limpaVertices();
                    return;
                }
            }
        }
        // o grafo não tem ciclo
        System.out.println("O grafo não tem ciclo!");
        this.limpaVertices();
    }

    // função para verificar se o vertice tem ciclo
    private boolean verificaCiclo(Vertice vertice){
        // marca o vertice como visitado
        vertice.setVisitado(true);
        // percorre a lista de adjacencia do vertice
        for (Aresta aresta : vertice.getListaAdjacencia()) {
            // pega o vertice de destino da aresta
            Vertice verticeDestino = aresta.getDestino();
            // se o vertice de destino não foi visitado
            if(!verticeDestino.getVisitado()){
                // verifica se o vertice de destino tem ciclo
                if(verificaCiclo(verticeDestino)){
                    // o vertice tem ciclo
                    return true;
                }
            } // se o vertice de destino já foi visitado
            else{
                // o vertice tem ciclo
                return true;
            }
        }
        // o vertice não tem ciclo
        return false;
    }

    // Função que calcula a Centralidade de Proximidade de cada nó (considere a distância do melhor caminho);
    public void centralidadeProximidade(){

        for(Vertice origin : this.vertices.values()){
            // criando as variaveis
            Queue<Vertice> fila = new LinkedList<>(); // fila para percorrer o grafo
            int[] distancias = new int[this.tamanho + 1]; // vetor para guardar as distancias

            // zerando as distancias
            for(int i = 0; i < distancias.length; i++){
                distancias[i] = -1;
            }

            // colocando o vertice de origem na fila
            fila.add(origin);

            // colocando a distancia do vertice de origem como 0
            distancias[origin.getId()] = 0;
            origin.setVisitado(true);

            
            // usando dfs para percorrer o grafo
            while(!fila.isEmpty()){
                // pega o vertice da fila
                Vertice vertice = fila.poll();

                // percorre a lista de adjacencia do vertice
                for(Aresta aresta : vertice.getListaAdjacencia()){
                    // pega o vertice de destino da aresta
                    Vertice verticeDestino = aresta.getDestino();
                    // se o vertice de destino não foi visitado
                    if(!verticeDestino.getVisitado()){
                        // marca o vertice de destino como visitado
                        verticeDestino.setVisitado(true);
                        // adiciona o vertice de destino na fila
                        fila.add(verticeDestino);
                        // atualiza a distancia do vertice de destino
                        distancias[verticeDestino.getId()] = distancias[vertice.getId()] + 1;
                    }
                }
            }

            // calcula a centralidade de proximidade do vertice
            calculaProximidade(origin, distancias);

            // limpa os vertices
            this.limpaVertices();

        }
    }
    
    // Função que calcula a Centralidade de Intermediação de cada nó (considere a distância do melhor caminho);
    public void centralidadeIntermediacao(){
            
            for(Vertice origin : this.vertices.values()){
                // criando as variaveis
                Queue<Vertice> fila = new LinkedList<>(); // fila para percorrer o grafo
                int[] distancias = new int[this.tamanho + 1]; // vetor para guardar as distancias
                int[] predecessores = new int[this.tamanho + 1]; // vetor para guardar os predecessores
                int[] intermedios = new int[this.tamanho + 1]; // vetor para guardar os intermedios
    
                // zerando as distancias
                for(int i = 0; i < distancias.length; i++){
                    distancias[i] = -1;
                }
    
                // colocando o vertice de origem na fila
                fila.add(origin);
    
                // colocando a distancia do vertice de origem como 0
                distancias[origin.getId()] = 0;
                origin.setVisitado(true);
    
                // enquanto a fila não estiver vazia
                while(!fila.isEmpty()){
                    // remove o vertice da fila
                    Vertice vertice = fila.remove();
                    // percorre a lista de adjacencia do vertice
                    for (Aresta aresta : vertice.getListaAdjacencia()) {
                        // pega o vertice de destino da aresta
                        Vertice verticeDestino = aresta.getDestino();
                        // se o vertice de destino não foi visitado
                        if(!verticeDestino.getVisitado()){
                            // marca o vertice de destino como visitado
                            verticeDestino.setVisitado(true);
                            // adiciona o vertice de destino na fila
                            fila.add(verticeDestino);
                            // atualiza a distancia do vertice de destino
                            distancias[verticeDestino.getId()] = distancias[vertice.getId()] + 1;
                            // atualiza o predecessor do vertice de destino
                            predecessores[verticeDestino.getId()] = vertice.getId();
                        }
                    }
    
                }
    
                // calcula a centralidade de intermediacao do vertice
                calculaIntermediacao(origin, distancias, predecessores, intermedios);
    
                // limpa os vertices
                this.limpaVertices();
    
            }
    }

    // função para gerar um grafo aleatoriamente
    public Graph geradorGrafoAleatorio(int vertice, int arestas, boolean conexidade){

        Graph grafo = new Graph();

        String[] vertices = new String[vertice];

        // adiciona os vertices
        for(int i = 0; i < vertice; i++){
            vertices[i] = "v" + i;
            grafo.criaVertice(vertices[i]);
        }


        // verifica se o grafo é conexo
        if(conexidade){
            // criando o grafo aleatoriamente mas conexo
            for(int i = 0; i < arestas/2; i++){
                // pega um vertice aleatorio
                int verticeAleatorio = (int) (Math.random() * vertice);
                // pega outro vertice aleatorio
                int verticeAleatorio2 = (int) (Math.random() * vertice);
                // cria a aresta entre os vertices
                grafo.criaArestaNaoDirecionada(vertices[verticeAleatorio], vertices[verticeAleatorio2], 1);
            }
        } // se o grafo não é conexo
        else{
            // criando o grafo aleatoriamente mas desconexo
            for(int i = 0; i < arestas; i++){
                // pega um vertice aleatorio
                int verticeAleatorio = (int) (Math.random() * vertice);
                // pega outro vertice aleatorio
                int verticeAleatorio2 = (int) (Math.random() * vertice);
                // cria a aresta entre os vertices
                grafo.criaAresta(vertices[verticeAleatorio], vertices[verticeAleatorio2], 1);
            }
        }


        return grafo;
    }

        // utilitários

    // função de calculo da centralidade de proximidade
    private void calculaProximidade(Vertice origin, int[] distancias){
        double soma = 0;
        double centralidade = 0; // variavel para guardar a centralidade de proximidade
        for(int i = 1; i < distancias.length; i++){
            // soma a distancia
            soma += distancias[i];
        }

        centralidade = (this.tamanho - 1) / soma;
        // imprime a centralidade de proximidade do vertice
        System.out.println("Centralidade de proximidade do vertice " + origin.getNome() + ": " + centralidade);
        // adiciona a centralidade de proximidade do vertice no vertice
        origin.setProximidade(centralidade);
    }


    // função de calculo da centralidade de intermediacao
    private void calculaIntermediacao(Vertice origin, int[] distancias, int[] predecessores, int[] intermedios){
        double centralidade = 0; // variavel para guardar a centralidade de intermediacao
        for(int i = 1; i < distancias.length; i++){
            for(int j = 1; j < distancias.length; j++){
                if(distancias[j] > distancias[i] && predecessores[j] == i){
                    intermedios[i]++;
                }
            }
        }

        for(int i = 1; i < distancias.length; i++){
            centralidade += intermedios[i];
        }

        // imprime a centralidade de intermediacao do vertice
        System.out.println("Centralidade de intermediacao do vertice " + origin.getNome() + ": " + centralidade);
        // adiciona a centralidade de intermediacao do vertice no vertice
        origin.setIntermediacao(centralidade);
    }

    // Função que verifica se o grafo é conexo ou não;
    private boolean conexo(){
        // cria uma lista de vertices visitados
        List<Vertice> visitados = new ArrayList<Vertice>();
        // cria uma lista de vertices a serem visitados
        List<Vertice> fila = new ArrayList<Vertice>();
        // adiciona o primeiro vertice na lista de vertices a serem visitados
        fila.add(this.vertices.get(this.vertices.keySet().iterator().next()));
        // enquanto a lista de vertices a serem visitados não estiver vazia
        while (!fila.isEmpty()) {
            // pega o primeiro vertice da lista de vertices a serem visitados
            Vertice vertice = fila.remove(0);
            
            // se o vertice não foi visitado
            if(!visitados.contains(vertice)){
                // adiciona o vertice na lista de vertices visitados
                visitados.add(vertice);
                // percorre a lista de adjacencia do vertice
                for (Aresta aresta : vertice.getListaAdjacencia()) {
                    // adiciona o vertice de destino na lista de vertices a serem visitados
                    fila.add(aresta.getDestino());
                }
            }
            
        }
        // se o tamanho da lista de vertices visitados for igual ao tamanho do grafo
        if (visitados.size() == this.tamanho) {
            // o grafo é conexo
            return true;
        }
        // o grafo não é conexo
        return false;
    }
    
    // função que mostra os componentes do grafo caso seja desconexo 
    private void getComponentes(){
        // caso seja conexo não há componentes
        if(conexo()){
            System.out.println("O grafo é conexo!");
        } // caso seja desconexo retorna os subgrafos
        else {
            
            // lista de subgrafos
            
            List<List<Vertice>> subgrafos = new ArrayList<List<Vertice>>();
            
            // cria uma lista de vertices visitados
            List<Vertice> visitados = new ArrayList<Vertice>();
            // cria uma lista de vertices a serem visitados
            List<Vertice> fila = new ArrayList<Vertice>();
            
            // percorre todos os vertices do grafo
            for (String nomeVertice : this.vertices.keySet()) {
                // pega o vertice
                Vertice vertice = this.vertices.get(nomeVertice);
                
                // se o vertice não foi visitado
                if(!visitados.contains(vertice)){
                    // cria uma lista de vertices para o subgrafo
                    List<Vertice> subgrafo = new ArrayList<Vertice>();
                    // adiciona o vertice na lista de vertices a serem visitados
                    fila.add(vertice);
                    // enquanto a lista de vertices a serem visitados não estiver vazia
                    while (!fila.isEmpty()) {
                        // pega o primeiro vertice da lista de vertices a serem visitados
                        Vertice v = fila.remove(0);
                        // se o vertice não foi visitado
                        if(!visitados.contains(v)){
                            // adiciona o vertice na lista de vertices visitados
                            visitados.add(v);
                            // adiciona o vertice na lista de vertices do subgrafo
                            subgrafo.add(v);
                            // percorre a lista de adjacencia do vertice
                            for (Aresta aresta : v.getListaAdjacencia()) {
                                // adiciona o vertice de destino na lista de vertices a serem visitados
                                fila.add(aresta.getDestino());
                            }
                        }
                    }
                    // adiciona o subgrafo na lista de subgrafos
                    subgrafos.add(subgrafo);
                }
            }
            
            System.out.println("O grafo é desconexo!");
            System.out.printf("O grafo tem %d componentes e eles são: \n", subgrafos.size());
            
            // percorre todos os subgrafos
            int contador  = 1;
            for (List<Vertice> subgrafo : subgrafos) {
                // percorre todos os vertices do subgrafo
                System.out.println("Subgrafo " + contador + ": ");
                for (Vertice vertice : subgrafo) {
                    // imprime o nome do vertice
                    System.out.print(vertice.getNome() + " | ");
                }
                contador++;
                // pula uma linha
                System.out.println();
            }
            
        }
        
    }

    // função para limpar o estado de visitado do vertices do grafo
    private void limpaVertices() {
        // percorre todos os vertices do grafo
        for (String nomeVertice : this.vertices.keySet()) {
            // pega o vertice
            Vertice vertice = this.vertices.get(nomeVertice);
            // limpa o vertice
            vertice.setVisitado(false);
        }
    }

    // função para mostrar todas as informações do grafo
    public void infoGrafo(){
        System.out.println("--------------------------------");
        System.out.println("Informações do grafo: ");
        System.out.println("Tamanho do grafo: " + this.tamanho); // mostra o tamanho do grafo
        System.out.println("Arestas do grafo: " + this.getTamanhoArestas()); // mostra o numero de arestas do grafo
        System.out.print("Conexidade: " ); this.verificaConexidade(); // mostra a conexidade do grafo e seus componentes caso desconexo
        System.out.print("Euleriano: " ); this.verificaEuleriano(); // mostra se o grafo é euleriano
        System.out.print("Ciclos: " );  this.verificaCiclo(); // mostra se o grafo tem ciclos");

        // print do grafo com a lista de adjacencia
        System.out.println("--------------------------------");
        this.imprimeGrafo();
        System.out.println("--------------------------------");
    }
}