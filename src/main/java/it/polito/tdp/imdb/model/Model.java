package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private ImdbDAO dao;
	Graph <Actor,DefaultWeightedEdge>grafo;
	Map<Integer,Actor>idMap;
	List<Actor> vertici;
	
	public Model() {
		dao= new ImdbDAO();
		idMap= new HashMap<Integer,Actor>();
	}

	public List<String> getGeneri() {
		return dao.getGeneri();
	}

	public void creaGrafo(String genere) {
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//aggiungi vertici
		vertici= new ArrayList<Actor>();
		for(Actor ac: dao.getVertici(genere, idMap) ) {
		grafo.addVertex(ac);
		vertici.add(ac);
		}
		//aggiungi gli archi
		for(Adiacenza a: dao.getArchi(genere, idMap)) {
		   Graphs.addEdgeWithVertices(grafo, a.getA1(),a.getA2(),a.getPeso());
		}
		
	}
	
	
	public List<Actor> getVertici() {
		Collections.sort(vertici);
		return vertici;
	}

	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Actor> getAttSimili(Actor att) {
		List<Actor> result= new ArrayList<Actor>();
		if(!grafo.containsVertex(att)) {
			throw new RuntimeException("Il vertice non esiste nel grafo");
		}
		//con GraphIterator
		GraphIterator<Actor, DefaultWeightedEdge> dfv = new DepthFirstIterator<Actor,DefaultWeightedEdge>(grafo, att);
		while (dfv.hasNext()) {
			result.add(dfv.next());
		}
		result.remove(att);
		Collections.sort(result);
		return result;
	}

}
