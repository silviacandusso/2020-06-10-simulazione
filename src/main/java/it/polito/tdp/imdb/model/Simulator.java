package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;

public class Simulator {
	
	//coda degli eventi
	
	//modello del mondo
	Model model;
	List<Actor>daIntervistare;
	int giorni;
	
	//parametri input
	int n;
	//parametri output
	List<Actor>intervistati;
	int pause;
	
	public Simulator(int n,Model model) {
		this.n=n;
		this.model= model;
		
	}
	
	public void init() {
		daIntervistare=new ArrayList<Actor>(model.getVertici());
		intervistati= new ArrayList<Actor>();
		pause=0;
		giorni=1;
		int random=(int)(Math.random()*daIntervistare.size());
		intervistati.add(daIntervistare.get(random));
	}
	
	public void run() {
		
	}

	public List<Actor> getIntervistati() {
		return intervistati;
	}

	public int getPause() {
		return pause;
	}
	
	

}
