package es.codeurjc.test.Board;

public class Jugada {
	
	public String label;
	public String nombre;
	public int posTablero;
	public boolean resultado;
	public int[] lineaResult;
	
		
		public Jugada(String nombre,String label,int posTablero, boolean resultado, int[] lineaResult) {
			this.nombre= nombre;
			this.label = label;
			this.posTablero = posTablero;
			this.resultado = resultado;
			this.lineaResult = lineaResult;
		}
		
		public boolean hayGanador() {
			return this.resultado && this.lineaResult != null;
		};
		
		public boolean hayEmpate() {
			return this.resultado && this.lineaResult == null;
		};
	}

