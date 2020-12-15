package com.gmail.tyi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "MenuItems")
public class MenuItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "dish_name")
	private String dishName;
	
	@Column(name = "dish_price")
	private int dishPrice;
	
	@Column(name = "dish_weight")
	private int dishWeight;
	
	@Column(name = "dish_discount")
	private boolean discount;
	

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", dishName=" + dishName + ", dishPrice=" + dishPrice + ", dishWeight="
				+ dishWeight + ", discount=" + discount + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public int getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(int dishPrice) {
		this.dishPrice = dishPrice;
	}

	public int getDishWeight() {
		return dishWeight;
	}

	public void setDishWeight(int dishWeight) {
		this.dishWeight = dishWeight;
	}
	
	

}
