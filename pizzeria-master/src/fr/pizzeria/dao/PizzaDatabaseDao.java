package fr.pizzeria.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import fr.pizzeria.model.Pizza;

public class PizzaDatabaseDao implements IPizzaDao{
	/* Attribut */
	Properties prop = new Properties();
	InputStream input = null;
	private List<Pizza> pizzas = null;
	Pizza pizza = null;
	String driver;
	String url;
	String user;
	String password;
	

	// Constructeur
	public PizzaDatabaseDao() throws IOException, SQLException{
		this.getProperties();
		try {
			/* Chargement du driver JDBC pour MySQL */
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		/* Initialise la base de données avec les données des pizzas*/
		this.pizzaInitialise();
	}
	
	/* Récupération des données pour accéder à la base de données*/
	public void getProperties() throws IOException, SQLException{
		try {
			this.input = new FileInputStream("pizzeria-master/src/jdbc.properties");
			/* Chargement du fichier properties */
			this.prop.load(input);

			/*Récupération des données */
			this.driver = prop.getProperty("driver");
			this.url = prop.getProperty("database");
			this.user = prop.getProperty("dbuser");
			this.password = prop.getProperty("dbpassword");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {

			if (input != null) {

				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/* Méthode qui retourne une liste de pizza*/ 
	@Override
	public List<Pizza> findAllPizzas() {
		
		Statement statement = null;
		ResultSet resultat = null;
		Connection connexion = null;
		pizzas = new ArrayList<Pizza>();
		
		try {
			connexion =  DriverManager.getConnection(url, user, password);
			
			/* Création de l'objet gérant les requêtes */
			statement = connexion.createStatement();
			
			/* Exécution d'une requête de lecture */
			resultat = statement.executeQuery("SELECT * FROM pizza;");
			
			/* Récupération des données du résultat de la requête de lecture */
			while(resultat.next()) {
				Integer id = resultat.getInt("ID");
				String code = resultat.getString("CODE");
				String libelle = resultat.getString("LIBELLE");
				double prix = resultat.getDouble("PRIX");
				/* Crée une pizza*/
				this.pizza = new Pizza(id,code,libelle,prix);
				
				/* Ajout de la pizza dans la liste */
				this.pizzas.add(this.pizza);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			try {
				resultat.close();
				statement.close();
				connexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return pizzas;
	}
	/* Ajoute une nouvelle pizza */

	@Override
	public void saveNewPizza(Pizza pizza) {
		Connection connexion = null;
		PreparedStatement savePizzaSt = null;
		
		try {
			connexion = DriverManager.getConnection(url, user, password);
			
			savePizzaSt = connexion.prepareStatement("INSERT INTO pizza(CODE,LIBELLE,PRIX) VALUES(?,?,?);");
			savePizzaSt.setString(1, pizza.getCode());
			savePizzaSt.setString(2, pizza.getLibelle());
			savePizzaSt.setDouble(3, pizza.getPrix());
			savePizzaSt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			try {
				savePizzaSt.close();
				connexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}
	/* Met à jour une pizza */
	@Override
	public void updatePizza(String codePizza, Pizza pizza) {
		Connection connexion = null;
		PreparedStatement updatePizzaSt = null;
		
		try {
			connexion = DriverManager.getConnection(url, user, password);
			
			updatePizzaSt = connexion.prepareStatement("UPDATE pizza SET CODE=?, LIBELLE=?, PRIX=? WHERE CODE=? ;");
			updatePizzaSt.setString(1, pizza.getCode());
			updatePizzaSt.setString(2, pizza.getLibelle());
			updatePizzaSt.setDouble(3, pizza.getPrix());
			updatePizzaSt.setString(4, codePizza);
			updatePizzaSt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			try {
				updatePizzaSt.close();
				connexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/* Supprime une pizza */
	@Override
	public void deletePizza(String codePizza) {
		Connection connexion = null;
		PreparedStatement deletePizzaSt = null;
		try {
			connexion = DriverManager.getConnection(url, user, password);
			deletePizzaSt = connexion.prepareStatement("DELETE FROM pizza WHERE CODE=?;");
			deletePizzaSt.setString(1, codePizza);
			deletePizzaSt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			try {
				deletePizzaSt.close();
				connexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Pizza findPizzaByCode(String codePizza) {

		return null;
	}

	@Override
	public boolean pizzaExists(String codePizza) {

		return false;
	}

	/* Initialise la base de données avec les données des pizzas */
	void pizzaInitialise(){
		Connection connexion = null;
		PreparedStatement initPizzaSt = null;
		pizzas = new ArrayList<Pizza>();
		pizzas.add(new Pizza("PEP", "Pépéroni", 12.50));
		pizzas.add(new Pizza("MAR", "Margherita", 14.00));
		pizzas.add(new Pizza("REI", "La Reine", 11.50));
		pizzas.add(new Pizza("FRO", "La 4 fromages", 12.00));
		pizzas.add(new Pizza("CAN", "La cannibale", 12.50));
		pizzas.add(new Pizza("SAV", "La savoyarde", 13.00));
		pizzas.add(new Pizza("ORI", "L’orientale", 13.50));
		pizzas.add(new Pizza("IND", "L’indienne", 14.00));
		try {
			connexion = DriverManager.getConnection(url, user, password);	
			Statement drop = connexion.createStatement();
			drop.execute("TRUNCATE TABLE pizza");
			initPizzaSt = connexion.prepareStatement("INSERT INTO pizza(CODE,LIBELLE,PRIX) VALUES(?, ?, ?);");
			Iterator<Pizza> it = pizzas.iterator();
			while (it.hasNext()){
				Pizza p = it.next();
				initPizzaSt.setString(1, p.getCode());
				initPizzaSt.setString(2, p.getLibelle());
				initPizzaSt.setDouble(3, p.getPrix());
				initPizzaSt.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {			
			try {
				initPizzaSt.close();
				connexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}

}
