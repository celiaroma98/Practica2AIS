package es.codeurjc.test.Board;



//import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import es.codeurjc.ais.tictactoe.WebApp;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PruebasSistema {

	private List<WebDriver> listadrivers = new ArrayList<WebDriver>();
	private List<WebDriverWait> listadriverswaits = new ArrayList<WebDriverWait>();
	
	@BeforeAll
	public static void setUpClass() {
		WebDriverManager.chromedriver().setup();
		WebApp.start();
	}
	@AfterAll
	public static void tearDownClass() {
		WebApp.stop();
	}
	@BeforeEach
	public void setUp() {
		WebDriver drive1 = new ChromeDriver();
		WebDriver drive2= new ChromeDriver();
		listadrivers.add(drive1);
		listadrivers.add(drive2);
		WebDriverWait w1 = new WebDriverWait(listadrivers.get(0),50);
		WebDriverWait w2 = new WebDriverWait(listadrivers.get(1),50);
		listadriverswaits.add(w1);
		listadriverswaits.add(w2);
	}
	@AfterEach
	public void teardown() {
		for (WebDriver dr: listadrivers) {
			if (dr != null) {
				dr.quit();
			}
		}
	}
	
	@ParameterizedTest(name = "CasoPartida: {index}")
	 @MethodSource("values")
	public void TestGenericoWeb(List<Jugada> jugadas) throws InterruptedException{
		for (WebDriver dr: listadrivers) {
			dr.get("http://localhost:8080/");
		}
		listadrivers.get(0).findElement(By.id("nickname")).sendKeys(jugadas.get(0).nombre);
		listadrivers.get(0).findElement(By.id("startBtn")).click();

		listadrivers.get(1).findElement(By.id("nickname")).sendKeys(jugadas.get(1).nombre);
		listadrivers.get(1).findElement(By.id("startBtn")).click();
		
		int turnosig= 1;
		int turno = 0;
		
		for(Jugada jugada:jugadas) {
			if (turnosig==1){
				turno = 0;
			}
			else{
				turno = 1;
			}
		
				listadriverswaits.get(turno).until(ExpectedConditions.elementToBeClickable(By.id("cell-" + jugada.posTablero)));
				listadrivers.get(turno).findElement(By.id("cell-" + jugada.posTablero)).click();
				
				if (jugada.hayGanador()) {
					listadriverswaits.get(turno).until(ExpectedConditions.alertIsPresent());
					listadriverswaits.get(turnosig).until(ExpectedConditions.alertIsPresent());
					String res=listadrivers.get(turno).switchTo().alert().getText();
					String expected= jugada.nombre + " wins! " + jugadas.get(turnosig).nombre + " looses.";
					assertEquals(res,expected);
						
				} else if (jugada.hayEmpate()) {
					listadriverswaits.get(turno).until(ExpectedConditions.alertIsPresent());
					listadriverswaits.get(turnosig).until(ExpectedConditions.alertIsPresent());
					String res=listadrivers.get(turno).switchTo().alert().getText();
					String expected= "Draw!";
					assertEquals(res,expected);
				}
				if (turnosig==1){
					turnosig = 0;
				}
				else{
					turnosig = 1;
				}
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*@Test
	public void victoriaPlayer1() {
		drive1.get("http://localhost:8080/");
		drive2.get("http://localhost:8080/");
		drive1.findElement(By.id("nickname")).sendKeys("Daniel");
		drive2.findElement(By.id("nickname")).sendKeys("Celia");
		drive1.findElement(By.xpath("//button[@id='startBtn']")).click();
		drive2.findElement(By.xpath("//button[@id='startBtn']")).click();
		
		w1.until(ExpectedConditions.elementToBeClickable(By.xpath("//table/tr/td[@id='cell-0']")));
		drive1.findElement(By.xpath("//table/tr/td[@id='cell-0']")).click();
		w2.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id='cell-8']")));
		drive2.findElement(By.xpath("//td[@id='cell-8']")).click();
		w1.until(ExpectedConditions.elementToBeClickable(By.id("cell-3")));
		drive1.findElement(By.id("cell-3")).click();
		w2.until(ExpectedConditions.elementToBeClickable(By.id("cell-2")));
		drive2.findElement(By.id("cell-2")).click();
		w1.until(ExpectedConditions.elementToBeClickable(By.id("cell-6")));
		drive1.findElement(By.id("cell-6")).click();
		
		w1.until(ExpectedConditions.alertIsPresent());
		w2.until(ExpectedConditions.alertIsPresent());
		boolean res= drive1.switchTo().alert().getText().equals("Daniel gana. Celia Pierde");
		boolean res2= drive2.switchTo().alert().getText().equals("Daniel gana. Celia Pierde");
		assertTrue(res);
		assertTrue(res2);
		
	}
	@Test
	public void victoriaPlayer2() {
		drive1.get("http://localhost:8080/");
		drive2.get("http://localhost:8080/");
		drive1.findElement(By.id("nickname")).sendKeys("Daniel");
		drive2.findElement(By.id("nickname")).sendKeys("Celia");
		drive1.findElement(By.xpath("//button[@id='startBtn']")).click();
		drive2.findElement(By.xpath("//button[@id='startBtn']")).click();
		
		w1.until(ExpectedConditions.elementToBeClickable(By.xpath("//table/tr/td[@id='cell-1']")));
		drive1.findElement(By.xpath("//table/tr/td[@id='cell-1']")).click();
		w2.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id='cell-0']")));
		drive2.findElement(By.xpath("//td[@id='cell-0']")).click();
		w1.until(ExpectedConditions.elementToBeClickable(By.id("cell-7")));
		drive1.findElement(By.id("cell-7")).click();
		w2.until(ExpectedConditions.elementToBeClickable(By.id("cell-3")));
		drive2.findElement(By.id("cell-3")).click();
		w1.until(ExpectedConditions.elementToBeClickable(By.id("cell-8")));
		drive1.findElement(By.id("cell-8")).click();
		w2.until(ExpectedConditions.elementToBeClickable(By.id("cell-6")));
		drive2.findElement(By.id("cell-6")).click();
		
		
		w1.until(ExpectedConditions.alertIsPresent());
		w2.until(ExpectedConditions.alertIsPresent());
		boolean res= drive1.switchTo().alert().getText().equals("Celia gana. Daniel Pierde");
		boolean res2= drive2.switchTo().alert().getText().equals("Celia gana. Daniel Pierde");
		assertTrue(res);
		assertTrue(res2);
	
		
}
	@Test
	public void EMPATE() {
		drive1.get("http://localhost:8080/");
		drive2.get("http://localhost:8080/");
		drive1.findElement(By.id("nickname")).sendKeys("Daniel");
		drive2.findElement(By.id("nickname")).sendKeys("Celia");
		drive1.findElement(By.xpath("//button[@id='startBtn']")).click();
		drive2.findElement(By.xpath("//button[@id='startBtn']")).click();
		
		w1.until(ExpectedConditions.elementToBeClickable(By.xpath("//table/tr/td[@id='cell-0']")));
		drive1.findElement(By.xpath("//table/tr/td[@id='cell-0']")).click();
		w2.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id='cell-1']")));
		drive2.findElement(By.xpath("//td[@id='cell-1']")).click();
		w1.until(ExpectedConditions.elementToBeClickable(By.id("cell-4")));
		drive1.findElement(By.id("cell-4")).click();
		w2.until(ExpectedConditions.elementToBeClickable(By.id("cell-2")));
		drive2.findElement(By.id("cell-2")).click();
		w1.until(ExpectedConditions.elementToBeClickable(By.id("cell-5")));
		drive1.findElement(By.id("cell-5")).click();
		w2.until(ExpectedConditions.elementToBeClickable(By.id("cell-3")));
		drive2.findElement(By.id("cell-3")).click();
		w1.until(ExpectedConditions.elementToBeClickable(By.xpath("//table/tr/td[@id='cell-6']")));
		drive1.findElement(By.xpath("//table/tr/td[@id='cell-6']")).click();
		w2.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id='cell-8']")));
		drive2.findElement(By.xpath("//td[@id='cell-8']")).click();
		w1.until(ExpectedConditions.elementToBeClickable(By.id("cell-7")));
		drive1.findElement(By.id("cell-7")).click();
		
		
		w1.until(ExpectedConditions.alertIsPresent());
		w2.until(ExpectedConditions.alertIsPresent());
		
		boolean res= drive1.switchTo().alert().getText().equals("Empate");
		boolean res2= drive2.switchTo().alert().getText().equals("Empate");
		assertTrue(res);
		assertTrue(res2);
		
	}
		/*assertThat(drive1.switchTo().alert().getText().equals("Empate"));
		assertThat(drive2.switchTo().alert().getText().equals("Empate"));
		
	}*/



