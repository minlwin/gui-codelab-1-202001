package com.jdc.jpa.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REMOVE;

@Entity
public class Sale implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;
	@ManyToOne(cascade = { PERSIST, MERGE })
	private Customer customer;
	private LocalDate saleDate;
	private LocalTime saleTime;
	private int subTotal;
	private int tax;
	private int deliveryFees;
	private int total;

	@OneToMany(mappedBy = "sale", cascade = { PERSIST, MERGE, REMOVE })
	private List<SaleDetails> orders;
	
	public Sale() {
		orders = new ArrayList<>();
	}
	
	public void addDetaills(SaleDetails sd) {
		sd.setSale(this);
		orders.add(sd);
		calculate();
	}
	
	private void calculate() {
		subTotal = orders.stream().mapToInt(a  -> a.getPrice() * a.getQuantity()).sum();
		tax = subTotal / 100  * 5;
		total = subTotal + tax + deliveryFees;
	}

	public List<SaleDetails> getOrders() {
		return orders;
	}

	public void setOrders(List<SaleDetails> orders) {
		this.orders = orders;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public LocalTime getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(LocalTime saleTime) {
		this.saleTime = saleTime;
	}

	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public int getDeliveryFees() {
		return deliveryFees;
	}

	public void setDeliveryFees(int deliveryFees) {
		this.deliveryFees = deliveryFees;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
