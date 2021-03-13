import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ArestaOuArco{
	int verticeOrigem;
	int verticeDestino;
	int peso;
	
	ArestaOuArco(int verticeOrigem, int verticeDestino, int peso){
		this.verticeOrigem = verticeOrigem;
		this.verticeDestino = verticeDestino;
		this.peso = peso;
	}
	
	void imprimeArestaOuArco(){
		System.out.println("Origem: " + verticeOrigem + ", Destino: " + verticeDestino + ", Peso: " + peso);
	}
}

class Grafo{
	int numVertices;
	int numArestas;
	ArrayList <List<Integer>> listaAdjacencias;
	List<ArestaOuArco> arestasOuArcos;
	int[][] matrizPesos; 
	
	Grafo(int numVertices, int numArestas){ //construtor para iniciar a lista de adjacencia
		this.numVertices = numVertices;
		this.numArestas = numArestas;
		arestasOuArcos = new ArrayList<>();
		matrizPesos = new int[numVertices][numArestas];
		
		listaAdjacencias = new ArrayList<>();
		for(int i = 0; i < numVertices; i++) {
			listaAdjacencias.add(new ArrayList<>());
		}
	}
	
	void addArestaOuArco(int origem, int destino, int peso) {
		arestasOuArcos.add(new ArestaOuArco(origem, destino, peso));
	}
	
	void addPesos(int origem, int destino, int peso) {
		matrizPesos[origem][destino] = peso;
	}
	
	//void imprimePesos
	
	void imprimeArestasOuArcos() {
		for(ArestaOuArco a : arestasOuArcos) {
			a.imprimeArestaOuArco();
		}
	}
}

class Fila{
	List <Integer> fila;
	
	Fila(){
		fila = new ArrayList<>();
	}
	
	void addFila(int vertice) {
		fila.add(vertice);
	}
	
	int removeFila() {
		int aux = fila.get(0);		
		fila.remove(0);
		return aux;
	}
	
	void imprimeFila() {
		System.out.println("Fila: ");
		for(int i : fila) {
			System.out.println(i);
		}
	}
	
	boolean filaVazia() {
		return fila.isEmpty();
	}
}

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		
		
		int n; // representa a quantidade de vertices do grafo
		int m; // representa a quantidade de arestas ou arcos do grafo
		int b; // indica se o grafo eh direcionado 1 - sim, 0 - nao
		int i; // indice do vertice a partir do qual sera realizada a busca
		
		n = sc.nextInt();
		m = sc.nextInt();
		b = sc.nextInt();
		i = sc.nextInt();
		
		Grafo g = new Grafo(n, m);		
		
		for(int j=0; j<m; j++) {
			int origem = sc.nextInt();
			int destino = sc.nextInt();
			int peso = sc.nextInt();
			
			g.addPesos(origem-1, destino-1, peso);
			
			addVerticeAdjacente(g.listaAdjacencias, origem-1, destino);
			
			if(b == 0) {
				g.addPesos(destino-1, origem-1, peso);
				addVerticeAdjacente(g.listaAdjacencias, destino-1, origem);
			}
			
		}
		
		imprimeAdjacencias(g.listaAdjacencias);
		
		dijkstra(g, i);
		
		sc.close();
	}
static void addVerticeAdjacente(ArrayList <List<Integer>> listaAdjacencias, int vertice , int novoVerticeAdjacente) { //adiciona um novo vertice adjacente de forma que a lista ja fique ordenada
		
		if (listaAdjacencias.get(vertice).isEmpty())
			listaAdjacencias.get(vertice).add(novoVerticeAdjacente);
		
		else {
			for(int i = 0; i < listaAdjacencias.get(vertice).size(); i++) {
				if(novoVerticeAdjacente < listaAdjacencias.get(vertice).get(i)) { //verifica se eh menor que um elemento presente na posicao i da lista
					listaAdjacencias.get(vertice).add(i, novoVerticeAdjacente);
					return;
				}
				else if (i+1 == listaAdjacencias.get(vertice).size()){ //verifica se eh o ultimo elemento da lista, se for adiciona o elemento na ultima posicao
					listaAdjacencias.get(vertice).add(novoVerticeAdjacente);
					return;
				}
			}
		}
	}
	
	static void imprimeAdjacencias(ArrayList <List<Integer>> listaAdjacencias) {
		int v = 1;
		for(List<Integer> l: listaAdjacencias) {
			System.out.print("Vertice " + v + ": ");
			for(int i=0; i<l.size(); i++) {
				System.out.print("->" + l.get(i) + " ");
			}
			v++;
			System.out.println();
		}
	}
	
	static void dijkstra(Grafo g, int verticeInicial) {
		int[] distancias = new int[g.numVertices]; // armazena as menores distancias ate cada um dos vertices
		int[] antecessores = new int[g.numVertices]; // armazena os antecessores de cada vertice
		
		//List<Integer> abertos = new ArrayList<>();
		//List<Integer> fechados = new ArrayList<>();
		
		
		boolean[] abertos = new boolean[g.numVertices];
		boolean[] fechados = new boolean[g.numVertices];
		
		distancias[verticeInicial-1] = 0;
		antecessores[verticeInicial-1] = verticeInicial;
		
		for(int i=0; i<g.numVertices; i++) {
			if(i+1 != verticeInicial) {
				distancias[i] = 2147483647;
				antecessores[i] = 0;
			}
		}
		
		for(int i=0; i<g.numVertices; i++) { // marca todos os vertices como abertos
			abertos[i] = true;
		}
		
		while(!todosFechados(fechados)) {
			int r = verticeAbertoComMenorDistancia(g, abertos, distancias);
			//System.out.println("Vertice com menor distancia: " + r);
			fechados[r-1] = true;
			abertos[r-1] = false;
			
			List<Integer> vizinhosAbertos = new ArrayList<>();
			
			for(int i=0; i<g.listaAdjacencias.get(r-1).size(); i++) {
				if(abertos[g.listaAdjacencias.get(r-1).get(i)-1]) {
					vizinhosAbertos.add(g.listaAdjacencias.get(r-1).get(i));
				}
			}
			
			//System.out.println("\n\nVERTICE: " + r);
			
			for(Integer vizinho : vizinhosAbertos) {
				//System.out.println("\n\nVIZINHO: " + vizinho);
				int p = min(distancias[vizinho-1], distancias[r-1]+g.matrizPesos[r-1][vizinho-1]);
				//System.out.println("\n\nVIZINHO MINIMO: " + vizinho);
				if(p < distancias[vizinho-1]) {
					distancias[vizinho-1] = p;
					antecessores[vizinho-1] = r;
				}
			}
			
			
			/*
			System.out.println("\nAbertos: ");
			for(int i = 0; i <g.numVertices; i ++) {
				System.out.print((i+1) + ":" + abertos[i] + " ");
			}
			
			System.out.println("");
			
			System.out.println("Fechados: ");
			for(int i = 0; i <g.numVertices; i ++) {
				System.out.print((i+1) + ":" + fechados[i] + " ");
			}
			*/
		}
		
		System.out.println();
		
		for(int i = 0; i <g.numVertices; i ++) {
			System.out.print("  " + (i+1) + "  |");
		}
		System.out.println();
		for(int i = 0; i <g.numVertices; i ++) {
			System.out.print("  " + distancias[i] + "  |");
		}
		System.out.println();
		for(int i = 0; i <g.numVertices; i ++) {
			System.out.print("  " + (antecessores[i]) + "  |");
		}
		System.out.println();
		for(int i = 0; i <g.numVertices; i ++) {
			if(distancias[i]!=0 && distancias[i]!= 2147483647) {
				System.out.print((i+1) + " ");
				System.out.print("(" + distancias[i] + ") :");
				System.out.print(" " + verticeInicial);
				if(antecessores[i] != verticeInicial)
					System.out.print(" " + antecessores[i]);
				int j = i;
				//while (antecessores[antecessores[j]-1] != verticeInicial)
				while (antecessores[antecessores[j]-1] != verticeInicial) {
					System.out.print(" " + antecessores[antecessores[j]-1]);
					j--;
				}
				System.out.println(" " + (i+1));
			}
		}
	}
	
	static boolean todosFechados(boolean[] fechados) {
		for(int i = 0; i<fechados.length; i++) {
			if (fechados[i] == false)
				return false;
		}
		return true;
	}
	
	static int verticeAbertoComMenorDistancia(Grafo g, boolean[] abertos, int[] distancias) {
		int menor=0;
		int verticeMenorDistancia=0;
		
		boolean primeiro = true;
		
		for(int i=0; i<g.numVertices; i++) {
			if(abertos[i]) { // analiso se o vertice esta aberto
				if(primeiro) { //considero o primeiro vertice aberto contendo a menor distancia
					menor = distancias[i];
					verticeMenorDistancia = i;
					primeiro = false;
				}
				else if(distancias[i] <= menor) { // comparo o primeiro vertice aberto econtrado com os demais
					menor = distancias[i];
					verticeMenorDistancia = i;
				}
			}
		}
		
		return verticeMenorDistancia+1;
	}
	
	static int min(int distanciaVizinho, int custoVerticeAtual) {
		if (distanciaVizinho < custoVerticeAtual)
			return distanciaVizinho;
		else
			return custoVerticeAtual;
	}
	
}
