package test.service;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.services.ListerPizzasService;
import fr.pizzeria.services.MenuService;

public class ListerPizzaServiceTest {


	/** Création d'une "Rule" qui va permettre
	 * de substituer le System.in utilisé par le Scanner
	 * par un mock: systemInMock */
	@Rule
	public TextFromStandardInputStream systemInMock = emptyStandardInputStream();
	
	@Test
	public void executeUCTest(){
	
		systemInMock.provideLines("1");
		IPizzaDao mockDao = mock(IPizzaDao.class);
		List<Pizza> pizzas= new ArrayList<Pizza>();
		
		when(mockDao.findAllPizzas()).thenReturn(new ArrayList<>());
		
		MenuService listeService = new ListerPizzasService();

		try {
			listeService.executeUC(new Scanner(System.in), mockDao);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		assertEquals("La liste des pizzas est vide", pizzas, mockDao.findAllPizzas() );
	}
}
