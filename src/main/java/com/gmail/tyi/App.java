package com.gmail.tyi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class App {

	public static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Restaurant");
    
    public static void main(String[] args) {
    	
    	addMenuItem("Nisuaz", 150, 150, true);
    	addMenuItem("Ribay", 350, 700, false);
    	addMenuItem("KievChiken", 250, 200, false);
    	addMenuItem("T-Bone", 500, 1000, false);
    	addMenuItem("Chease Cake", 150, 100, true);
    	
    	Consumer<MenuItem> menuItemSup = menuItem -> System.out.println(menuItem);
    	
    	List<MenuItem> menuItems = getMenuItems();
    	menuItems.forEach(menuItemSup);
    	System.out.println("Menu items with price between 150 and 900");
    	List<MenuItem> menuItemsWithPrice = getMenuItemsWithPrice(150, 900);
    	menuItemsWithPrice.forEach(menuItemSup);
    	System.out.println("Menu items with discount:");
    	List<MenuItem> menuItemWithDiscount = getMenuItemsWithDiscount();
    	menuItemWithDiscount.forEach(menuItemSup);
    	System.out.println("Menu items with weight under 1 kg:");
    	List<MenuItem> menuItemsUnderOneKg = getMenuItemsUnderOneKg();
    	menuItemsUnderOneKg.forEach(menuItemSup);
    }
    public static void addMenuItem(String dishName, int dishWeight, int dishPrice, boolean discount) {
		
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
    	EntityTransaction entityTransaction = null;
		
    	try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			MenuItem menuItem = new MenuItem();
			menuItem.setDishName(dishName);
			menuItem.setDishPrice(dishPrice);
			menuItem.setDishWeight(dishWeight);
			menuItem.setDiscount(discount);
			entityManager.persist(menuItem);
			entityTransaction.commit();
		} catch (Exception e) {
			if (entityTransaction != null) {
				entityTransaction.rollback();
			}
			e.printStackTrace();
		} 
    	finally {
			entityManager.close();
		}
	}
    
    public static List<MenuItem> getMenuItems() {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
		String query = "SELECT m FROM MenuItem m WHERE m.id IS NOT NULL";
		TypedQuery<MenuItem> tq = entityManager.createQuery(query, MenuItem.class);
		
		List<MenuItem> menuItems = new ArrayList<>();
		try {
			menuItems = tq.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		
		return menuItems;
		
	}
    public static List<MenuItem> getMenuItemsWithPrice(int priceFrom, int priceTo) {
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
		String query = "SELECT m FROM MenuItem m WHERE m.dishPrice >= :priceFrom and m.dishPrice <= :priceTo";
		TypedQuery<MenuItem> tq = entityManager.createQuery(query, MenuItem.class);
		tq.setParameter("priceFrom", priceFrom);
		tq.setParameter("priceTo", priceTo);
		
		List<MenuItem> menuItems = new ArrayList<>();
		try {
			menuItems = tq.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		
		return menuItems;
    }
    public static List<MenuItem> getMenuItemsWithDiscount() {
    	List<MenuItem> menuItems = getMenuItems();
    	return menuItems.stream().filter(menuItem -> menuItem.isDiscount()).collect(Collectors.toList());
    }
    public static List<MenuItem> getMenuItemsUnderOneKg() {
    	List<MenuItem> menuItems = getMenuItems();
    	List<MenuItem> menuItemsUnderOneKg = new ArrayList<>();
    	int sum = 0;
    	for (int i = 0; i < menuItems.size(); i++) {
			sum += menuItems.get(i).getDishWeight();
			if (sum < 1000) {
				menuItemsUnderOneKg.add(menuItems.get(i));
			} 
			
		}
    	return menuItemsUnderOneKg;
    }
}
