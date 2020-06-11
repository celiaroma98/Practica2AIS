package es.codeurjc.test.Board;


import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.ArgumentCaptor;
import org.mockito.hamcrest.MockitoHamcrest;

import es.codeurjc.ais.tictactoe.Connection;
import es.codeurjc.test.Board.Jugada;
import es.codeurjc.test.Board.CasosPartida;
import es.codeurjc.ais.tictactoe.Player;
import es.codeurjc.ais.tictactoe.TicTacToeGame;
import es.codeurjc.ais.tictactoe.TicTacToeGame.CellMarkedValue;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;


public class TicTacToeGameTest {
	TicTacToeGame juego;
	Connection conexion1;
	Connection conexion2;
	List<Player> jugadores;
	
	
	
	
	@BeforeEach
	public void setUp() {
		 juego = new TicTacToeGame();
	}
	
		
	 @ParameterizedTest(name = "CasoPartida: {index}")
	 @MethodSource("values")
	public void TestGenerico(List<Jugada> jugadas) {
		
		
		conexion1 = mock(Connection.class);
		conexion2 = mock(Connection.class);
			
		juego.addConnection(conexion1);
		juego.addConnection(conexion2);
		
		jugadores =  new CopyOnWriteArrayList<>();
		
		jugadores.add(new Player(0,"X","Celia"));	
		juego.addPlayer(jugadores.get(0));
		
		jugadores.add(new Player(1,"O","Dani"));
		juego.addPlayer(jugadores.get(1));
		
		verify(conexion1,times(2)).sendEvent(eq(EventType.JOIN_GAME),MockitoHamcrest.argThat(hasItems(jugadores.get(0),jugadores.get(1))));
		verify(conexion2,times(2)).sendEvent(eq(EventType.JOIN_GAME), MockitoHamcrest.argThat(hasItems(jugadores.get(0),jugadores.get(1))));

		verify(conexion1).sendEvent(eq(EventType.SET_TURN), eq(jugadores.get(0)));
		verify(conexion2).sendEvent(eq(EventType.SET_TURN), eq(jugadores.get(0)));
		
		int turnosig = 1;
		int turno = 0;
		
				for (Jugada jugada:jugadas) {
					if (turnosig==1){
						turno = 0;
					}
					else{
						turno = 1;
					}
					
						juego.mark(jugada.posTablero);
						
						if ((!jugada.hayGanador()) && (!jugada.hayEmpate())) {
							verify(conexion1).sendEvent(eq(EventType.SET_TURN), eq(jugadores.get(turnosig)));
							verify(conexion2).sendEvent(eq(EventType.SET_TURN), eq(jugadores.get(turnosig)));
							
						}
					
						else if (jugada.hayGanador()) {
						
							    ArgumentCaptor<WinnerValue> ac = ArgumentCaptor.forClass(WinnerValue.class);
						
								verify(conexion1).sendEvent(eq(EventType.GAME_OVER), ac.capture());
								int[] res= ac.getValue().getPos();
								assertArrayEquals(res,jugada.lineaResult);
								assertTrue(ac.getValue().getPlayer().getId()== turno);
								
								verify(conexion2).sendEvent(eq(EventType.GAME_OVER), ac.capture());
								int[] res2= ac.getValue().getPos();
								assertArrayEquals(res2,jugada.lineaResult);
								assertTrue(ac.getValue().getPlayer().getId()== turno);
								
					}
					    else if (jugada.hayEmpate()) {
								ArgumentCaptor<WinnerValue> ac2 = ArgumentCaptor.forClass(WinnerValue.class);
								
								verify(conexion1).sendEvent(eq(EventType.GAME_OVER), ac2.capture());
								assertNull(ac2.getValue());
								
								verify(conexion2).sendEvent(eq(EventType.GAME_OVER), ac2.capture());
								assertNull(ac2.getValue());
					}
					if (turnosig==1){
						turnosig = 0;
						}
					else{
						turnosig = 1;
						}
						reset(conexion1);
						reset(conexion2);
					}
				}
	 public static Collection<Object[]> values() {
			CasosPartida casos = new CasosPartida();
			 Object[][] values = {
					{casos.ganaJugador1},
					{casos.empate},{casos.ganaJugador2}
					};
			
					return Arrays.asList(values);
		}
		
				
		
	}
