package test.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaMemDao;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.services.AjouterPizzaService;
import fr.pizzeria.services.MenuService;

import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Scanner;

public class AjoutPizzaServiceTest {
	
	IPizzaDao dao;
	
	/** Création d'une "Rule" qui va permettre
	* de substituer le System.in utilisé par le Scanner
	* par un mock: systemInMock */
	@Rule
	public TextFromStandardInputStream systemInMock = emptyStandardInputStream();
	
	@Test
	public void executeUCTest(){
		
		dao = new PizzaMemDao();
		
		MenuService ajoutPizzaService = new AjouterPizzaService();
		
		systemInMock.provideLines("PEPA", "Pert", "10");
		
		ajoutPizzaService.executeUC(new Scanner(System.in), dao);
		
		assertTrue("La pizza existe", dao.pizzaExists("PEPA"));
		
		
	}
}
