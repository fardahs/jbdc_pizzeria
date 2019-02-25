package test.service;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.services.MenuService;
import fr.pizzeria.services.ModifierPizzaService;

public class ModifiePizzaServiceTest {


	/** Création d'une "Rule" qui va permettre
	 * de substituer le System.in utilisé par le Scanner
	 * par un mock: systemInMock */
	@Rule
	public TextFromStandardInputStream systemInMock = emptyStandardInputStream();

	@Test
	public void executeUCTest(){
		systemInMock.provideLines("SUP", "PAP", "superieur","12");
		IPizzaDao mockDao = mock(IPizzaDao.class);
		
		MenuService modifieService = new ModifierPizzaService();
		
		 try{
			 modifieService.executeUC(new Scanner(System.in), mockDao);
			 when(mockDao).thenThrow(new NullPointerException());
			 
		 }catch(Exception e){
			System.out.println(e.getMessage());
		 }
		
	   assertNull(mockDao.findPizzaByCode("PAP"));
	
		
	}

	
}
