package jdbc.properties;

import java.sql.Statement;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import fr.pizzeria.model.Pizza;


public class SQL {

	private Connection connexion;
	private Statement lien;
	private Properties prop = new Properties();
	OutputStream output = null;
	
	public SQL()
	{
		try {

			output = new FileOutputStream("jdbc.properties");

			// set the properties value
			prop.setProperty("database", "jdbc:mysql://localhost:3306/tp_jdbc_1?useSSL=false");
			prop.setProperty("dbuser", "root");
			prop.setProperty("dbpassword", "");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	private void initconnexion()
	{		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.
					getConnection(prop.getProperty("database"), prop.getProperty("dbuser"),prop.getProperty( "dbpassword"));
			lien = connexion.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void finnaly(){
		if(connexion != null){
			try{
				/*Fermeture de la connexion*/                
				connexion.close();
			}catch(SQLException ignore){
				/*Si une erreur survient, on l'ignore*/
			}
		}
	}

	public int insert(String requete)
	{
		int nbPizza = 0;
		initconnexion();

		try {
			nbPizza = ((java.sql.Statement) lien).executeUpdate(requete);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finnaly();
		return nbPizza;
	}

	public int insert(Pizza requete)
	{
		int nbPizza = 0;
		initconnexion();

		String chaine = "INSERT INTO pizza (pizza_code, pizza_libelle, pizza_price)"
				+ " values ('"
				+requete.getCode()+"','"
				+requete.getLibelle()+"','"
				+requete.getPrix()+"')";
		System.out.println(chaine);
		try {
			nbPizza += ((java.sql.Statement) lien).executeUpdate(chaine);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finnaly();
		return nbPizza;
	}

	public int insert (ArrayList<Pizza> requete)
	{
		int nbPizza = 0;
		initconnexion();

		for(int i = 0 ; i < requete.size() ; i ++)
		{
			String chaine = "INSERT INTO pizza (pizza_code, pizza_libelle, pizza_price)"
					+ " values ('"
					+requete.get(i).getCode()+"','"
					+requete.get(i).getLibelle()+"','"
					+requete.get(i).getPrix()+"')";
			System.out.println(chaine);
			try {
				nbPizza += ((java.sql.Statement) lien).executeUpdate(chaine);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		finnaly();
		return nbPizza;
	}

	public void select(String requete)
	{
		initconnexion();
		try {
			ResultSet resultat = ((java.sql.Statement) lien).executeQuery(requete);
			while(resultat.next())
			{
				Integer id = resultat.getInt("ID");
				String name = resultat.getString("NAME");
				BigDecimal price = resultat.getBigDecimal("PRICE");
				System.out.println("[id=" + id + " name=" + name + " price=" + price + "]");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		finnaly();
	}

	public ArrayList select_list(String requete)
	{
		ArrayList<Pizza>pizza = new ArrayList<Pizza>();
		
		initconnexion();
		try {
			ResultSet resultat = ((java.sql.Statement) lien).executeQuery(requete);
			while(resultat.next())
			{
				
				Integer id = resultat.getInt("id_pizza");
				String code = resultat.getString("pizza_code");
				String name = resultat.getString("pizza_libelle");
				BigDecimal price = resultat.getBigDecimal("pizza_price");
				pizza.add(new Pizza(id, code, name,price.doubleValue()));
				System.out.println("[id=" + id + " name=" + name + " price=" + price + "]");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		finnaly();
		
		return pizza;
	}
	
	public int update(String requete)
	{
		System.out.println(requete);
		int nbPizzasMaj = 0;
		initconnexion();

		try {
			nbPizzasMaj = ((java.sql.Statement) lien).executeUpdate(requete);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finnaly();
		return nbPizzasMaj;
	}
	
	public void delete(String requete)
	{
		initconnexion();
		
		try {
			lien.executeUpdate(requete);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finnaly();
	}
}