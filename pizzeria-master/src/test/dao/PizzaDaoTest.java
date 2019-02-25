package test.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fr.pizzeria.dao.IPizzaDao;
import fr.pizzeria.dao.PizzaMemDao;
import fr.pizzeria.model.Pizza;

public class PizzaDaoTest {
	IPizzaDao dao;
	
	@Before
	public void setUp() throws Exception {
		
		dao = new PizzaMemDao();
	}
	
	@Test
	public void testAjoutPizza() {
		assertFalse("Dao ne doit pas être vide", dao.findAllPizzas().isEmpty());
		
		//On ajoute une pizza
		dao.saveNewPizza(new Pizza("PEPA", "Pert", 10));	
		
		//On vérifie que la pizza est bien dans le dao
		assertTrue("J'ai trouvé ma pizza", dao.findPizzaByCode("PEPA").getCode().equals("PEPA"));
	}
	
	@Test
	public void testModifiePizza(){
		
		//On ajoute une pizza
		Pizza pizza = new Pizza("PEPA", "Pert", 10);
		dao.saveNewPizza(pizza);

		assertFalse("Dao ne doit pas être vide", dao.findAllPizzas().isEmpty());

		assertTrue("J'ai trouvé ma pizza", dao.findPizzaByCode("PEPA").getCode().equals("PEPA"));

		//On modifie une pizza
		pizza.setCode("PAP");
		pizza.setLibelle("autre");
		pizza.setPrix(15);

		//On met à jour la pizza
		dao.updatePizza("PEP", pizza);

		//On verifie que la pizza a été bien modifier
		assertTrue("J'ai modifié ma pizza", dao.findPizzaByCode("PAP").getCode().equals("PAP"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testSupprimePizza(){
		
		//On ajoute une pizza
		Pizza pizza = new Pizza("PEPZ", "Pert", 10);
		dao.saveNewPizza(pizza);
		
		assertTrue("J'ai trouvé ma pizza", dao.findPizzaByCode("PEPZ").getCode().equals("PEPZ"));
		
		//Suppression de la pizza
		dao.deletePizza("PEPZ");
		
		assertFalse("La pizza est supprimer de la liste", dao.findPizzaByCode("PEPZ").getCode().equals("PEPZ"));
	}
}
