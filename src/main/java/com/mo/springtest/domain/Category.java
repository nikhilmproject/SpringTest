/**
 * 
 */
package com.mo.springtest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="CATEGORY_ID", nullable=false)
	private long id;
	private String name;
	private String salesTax;
	
	
	public Category(String name, String salesTax) {
		this.name = name;
		this.salesTax = salesTax;
	}
	
	public Category() { }
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalesTax() {
		return Double.valueOf(salesTax.replace("%", "")) / 100;
	}
	public String getSalesTaxString() {
		return salesTax;
	}
	public void setSalesTax(String salesTax) {
		this.salesTax = salesTax;
	}
	

}
