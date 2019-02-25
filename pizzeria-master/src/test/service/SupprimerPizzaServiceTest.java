package test.service;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaMemDao;
import fr.pizzeria.services.AjouterPizzaService;
import fr.pizzeria.services.MenuService;
import fr.pizzeria.services.SupprimerPizzaService;

public class SupprimerPizzaServiceTest {

	IPizzaDao dao;
	
	/** Création d'une "Rule" qui va permettre
	* de substituer le System.in utilisé par le Scanner
	* par un mock: systemInMock */
	@Rule
	public TextFromStandardInputStream systemInMock = emptyStandardInputStream();
	

	@Test(expected=NullPointerException.class)
	public void executeUCTest(){
		dao = new PizzaMemDao();
		systemInMock.provideLines("PEP");
		
		MenuService supprimePizzaService = new SupprimerPizzaService();
		supprimePizzaService.executeUC(new Scanner(System.in), dao);

		assertFalse(dao.findPizzaByCode("PEP").getCode().equals("PEP"));
		
		
	}

}
