package es.codeurjc.test.Board;

import java.util.ArrayList;
import java.util.List;



public class CasosPartida {

	public List<Jugada> ganaJugador1;
	public List<Jugada> ganaJugador2;
	public List<Jugada> empate;
	String nombre1;
	String nombre2;
	String label1;
	String label2;
	int [] lineaGanadora1={0,1,2};
	int [] lineaGanadora2= {3,4,5};
	
	
	
	
	public CasosPartida() {
		
		this.nombre1= "Celia";
		this.nombre2= "Dani";
		this.label1= "X";
		this.label2= "O";
		
		this.ganaJugador1= new ArrayList<>();
		ganaJugador1.add(new Jugada(nombre1,label1,0,false,null));
		ganaJugador1.add(new Jugada(nombre2,label2,3,false,null));
		ganaJugador1.add(new Jugada(nombre1,label1,1,false,null));
		ganaJugador1.add(new Jugada(nombre2,label2,6,false,null));
		ganaJugador1.add(new Jugada(nombre1,label1,2,true,lineaGanadora1));
		
		this.ganaJugador2= new ArrayList<>();
		ganaJugador2.add(new Jugada(nombre1,label1,0,false,null));
		ganaJugador2.add(new Jugada(nombre2,label2,3,false,null));
		ganaJugador2.add(new Jugada(nombre1,label1,1,false,null));
		ganaJugador2.add(new Jugada(nombre2,label2,4,false,null));
		ganaJugador2.add(new Jugada(nombre1,label1,8,false,null));
		ganaJugador2.add(new Jugada(nombre2,label2,5,true,lineaGanadora2));
		
		this.empate= new ArrayList<>();
		empate.add(new Jugada(nombre1,label1,0,false,null));
		empate.add(new Jugada(nombre2,label2,3,false,null));
		empate.add(new Jugada(nombre1,label1,1,false,null));
		empate.add(new Jugada(nombre2,label2,4,false,null));
		empate.add(new Jugada(nombre1,label1,8,false,null));
		empate.add(new Jugada(nombre1,label1,7,false,null));
		empate.add(new Jugada(nombre2,label2,5,false,null));
		empate.add(new Jugada(nombre1,label1,2,false,null));
		empate.add(new Jugada(nombre2,label2,6,true,null));	
	}
	
	public List<Jugada> getGanaJugador1() {
		return this.ganaJugador1;
	}
	public List<Jugada> getGanaJugador2() {
		return this.ganaJugador2;
	}
	public List<Jugada> getEmpate() {
		return this.empate;
	}
}
