package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {

	UndirectedGraph<String, DefaultEdge> grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
	WordDAO dao = new WordDAO();
	String soluzione;
	ArrayList<String> vicini;

	public List<String> createGraph(int numeroLettere) {

		List<String> parole = dao.getAllWordsFixedLength(numeroLettere);

		for (String p : parole)
			grafo.addVertex(p);

		for (String p : grafo.vertexSet()) {

			for (String s : grafo.vertexSet()) {

				if (!grafo.containsEdge(p, s) && this.analizzaParole(p, s)) {

					grafo.addEdge(p, s);
				}
			}
		}

		return parole;
	}

	public List<String> displayNeighbours(String parolaInserita) {

		if (grafo.containsVertex(parolaInserita))
			return Graphs.neighborListOf(grafo, parolaInserita);

		return null;
	}

	public String findMaxDegree() {

		String temp = "";
		int max = 0;

		for (String p : grafo.vertexSet()) {

			if (grafo.degreeOf(p) > max) {
				max = grafo.degreeOf(p);
				temp = p;
			}
		}

		String s = "Parola = " + temp + " con grado max = " + grafo.degreeOf(temp);

		for (String p : this.displayNeighbours(temp)) {
			s += "\n" + p;
		}

		return s;
	}

	public boolean analizzaParole(String x, String y) {

		char c1[] = x.toCharArray();
		char c2[] = y.toCharArray();
		int c = 0;

		for (int i = 0; i < c1.length; i++) {

			if (c1[i] != c2[i])
				c++;
		}

		if (c == 1)
			return true;
		else
			return false;
	}

	public String itera(String parola) {
		String s = "";
		BreadthFirstIterator<String, DefaultEdge> iterator = new BreadthFirstIterator<String, DefaultEdge>(grafo,
				parola);
		while (iterator.hasNext())
			s += "\n" + iterator.next();
		return s;
	}

	public List<String> ricorsione(String parola) {
		int step = 0;
		soluzione = "";
		ArrayList<String> lista = new ArrayList<String>();

		this.ricorri(parola, lista);

		return null;
	}

	private void ricorri(String parola, ArrayList<String> lista) {
		vicini = new ArrayList<String>(Graphs.neighborListOf(grafo, parola));

		while (vicini != null && !lista.containsAll(vicini)) {
			int n = vicini.size();
			for (String s : vicini)
				if (!lista.contains(s))
					lista.add(s);
			for (int i = 0; i < n; i++)
				this.ricorri(vicini.get(i), lista);

		}

		return;

	}
}
