package fr.pizzeria.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.pizzeria.model.Pizza;
import jdbc.properties.SQL;

public class PizzaDbDao implements IPizzaDao {

	private List<Pizza> pizzas = new ArrayList<Pizza>();
	private SQL sql = new SQL();
	
	public PizzaDbDao(){
		pizzas.add(new Pizza("PEP", "Pépéroni", 12.50));
		pizzas.add(new Pizza("MAR", "Margherita", 14.00));
		pizzas.add(new Pizza("REIN", "La Reine", 11.50));
		pizzas.add(new Pizza("FRO", "La 4 fromages", 12.00));
		pizzas.add(new Pizza("CAN", "La cannibale", 12.50));
		pizzas.add(new Pizza("SAV", "La savoyarde", 13.00));
		pizzas.add(new Pizza("ORI", "L’orientale", 13.50));
		pizzas.add(new Pizza("IND", "L\"indienne", 14.00));
		sql.insert((ArrayList<Pizza>) pizzas);
	}

	@Override
	public List<Pizza> findAllPizzas() {
		return sql.select_list("Select * from pizza");
	}

	@Override
	public void saveNewPizza(Pizza pizza) {
		sql.insert(pizza);
	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) {
		sql.update("update pizza set pizza_code='"+pizza.getCode()+"', pizza_libelle ='"+pizza.getLibelle()
		+"',pizza_price='"+pizza.getPrix()+"' where pizza_code='"+codePizza+"'");
		for (Pizza p: pizzas){
			if (p.getCode().equals(codePizza)){
				p.setCode(pizza.getCode());
				p.setLibelle(pizza.getLibelle());
				p.setPrix(pizza.getPrix());
				return;
			}
		}
	}

	@Override
	public void deletePizza(String codePizza) {
		sql.delete("delete from pizza where pizza_code='"+codePizza+"'");
	}

	@Override
	public Pizza findPizzaByCode(String codePizza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean pizzaExists(String codePizza) {
		// TODO Auto-generated method stub
		return false;
	}

}
