package test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaMemDao;
import fr.pizzeria.services.MenuService;
import fr.pizzeria.services.ModifierPizzaService;
import fr.pizzeria.services.SupprimerPizzaService;

public class ListerPizzaServiceTest {
	IPizzaDao dao;

	/** Création d'une "Rule" qui va permettre
	 * de substituer le System.in utilisé par le Scanner
	 * par un mock: systemInMock */
	@Rule
	public TextFromStandardInputStream systemInMock = emptyStandardInputStream();
	
	@Test
	public void executeUCTest(){
		dao = new PizzaMemDao();
		systemInMock.provideLines("1");
		
		MenuService listeService = new SupprimerPizzaService();
		listeService.executeUC(new Scanner(System.in), dao);
		System.out.println(dao.findAllPizzas());

	}
}
