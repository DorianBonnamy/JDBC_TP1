package jdbc.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import fr.pizzeria.model.Pizza;


public class SQL {

	private Connection connexion;
	private Properties prop = new Properties();
	OutputStream output = null;
	
	public SQL()
	{
		try {

			output = new FileOutputStream("jdbc.properties");

//			 set the properties value
			prop.setProperty("database", "jdbc:mysql://localhost:3306/tp_jdbc_1?useSSL=false");
//			prop.setProperty("database", "jdbc:mariadb://localhost:3306/tp_jdbc_1?useSSL=false");
			prop.setProperty("dbuser", "root");
			prop.setProperty("dbpassword", "");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	private Statement initconnexion() throws SQLException, ClassNotFoundException
	{		
			Class.forName("com.mysql.jdbc.Driver");
//			Class.forName("org.mariadb.jdbc.Driver");
			connexion = DriverManager.
					getConnection(prop.getProperty("database"), prop.getProperty("dbuser"),prop.getProperty( "dbpassword"));
			return connexion.createStatement();
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
		try {
			Statement statement = initconnexion();
			nbPizza = statement.executeUpdate(requete);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finnaly();
		return nbPizza;
	}

	public int insert(Pizza requete)
	{
		int nbPizza = 0;
		
		String chaine = "INSERT INTO pizza (pizza_code, pizza_libelle, pizza_price)"
				+ " values ('"
				+requete.getCode()+"','"
				+requete.getLibelle()+"','"
				+requete.getPrix()+"')";
		System.out.println(chaine);
		
		try {
			Statement statement = initconnexion();
			nbPizza += statement.executeUpdate(chaine);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finnaly();
		return nbPizza;
	}

	public int insert (ArrayList<Pizza> requete)
	{
		int nbPizza = 0;

		Statement statement = null;
		try {
			statement = initconnexion();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i = 0 ; i < requete.size() ; i ++)
		{
			String chaine = "INSERT INTO pizza (pizza_code, pizza_libelle, pizza_price, pizza_categorie)"
					+ " values ('"
					+requete.get(i).getCode()+"','"// Code de la pizza
					+requete.get(i).getLibelle()+"','" //Nom de la pizza
					+requete.get(i).getPrix()+"')";
			try {
				
				nbPizza += statement.executeUpdate(chaine);
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
		try {
			Statement statement = initconnexion();
			ResultSet resultat = statement.executeQuery(requete);
			while(resultat.next())
			{
				Integer id = resultat.getInt("ID");
				String name = resultat.getString("NAME");
				BigDecimal price = resultat.getBigDecimal("PRICE");
				System.out.println("[id=" + id + " name=" + name + " price=" + price + "]");
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		finnaly();
	}

	public ArrayList select_list(String requete)
	{
		ArrayList<Pizza>pizza = new ArrayList<Pizza>();
		
		try {
			Statement statement = initconnexion();
			ResultSet resultat = statement.executeQuery(requete);
			while(resultat.next())
			{
				
				Integer id = resultat.getInt("id_pizza");
				String code = resultat.getString("pizza_code");
				String name = resultat.getString("pizza_libelle");
				BigDecimal price = resultat.getBigDecimal("pizza_price");
				pizza.add(new Pizza(id, code, name,price.doubleValue()));
				System.out.println("[id=" + id + " name=" + name + " price=" + price + "]");
			}

		} catch (SQLException | ClassNotFoundException e) {
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

		try {
			Statement statement = initconnexion();
			nbPizzasMaj = statement.executeUpdate(requete);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finnaly();
		return nbPizzasMaj;
	}
	
	public void delete(String requete)
	{
		try {
			Statement statement = initconnexion();
			statement.executeUpdate(requete);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finnaly();
	}
}