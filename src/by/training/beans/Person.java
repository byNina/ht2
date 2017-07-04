package by.training.beans;

import java.util.HashMap;
import java.util.Map;

public class Person implements Cloneable {

	// Данные записи о человеке.
	private int id = 0;
	private String name = "";
	private String surname = "";
	private String middlename = "";
	private Map<Integer, String> phones = new HashMap<Integer, String>();

	// Конструктор для создания записи о человеке на основе данных из БД.
	public Person(int id, String name, String surname, String middlename,
			Map<Integer, String> phones) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.middlename = middlename;
		this.phones = phones;
	}

	// Конструктор для создания пустой записи о человеке.
	public Person() {
		this.id = 0;
		this.name = "";
		this.surname = "";
		this.middlename = "";
	}

	// Конструктор для создания записи, предназначенной для добавления в БД.
	public Person(String name, String surname, String middlename) {
		this.id = 0;
		this.name = name;
		this.surname = surname;
		this.middlename = middlename;
	}

	public Person clone() {
		try {
			return (Person) super.clone();
		} catch (Exception e) {
			return null;
		}
	}

	// ++++++++++++++++++++++++++++++++++++++
	// Геттеры и сеттеры
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getMiddlename() {
		if ((this.middlename != null) && (!this.middlename.equals("null"))) {
			return this.middlename;
		} else {
			return "";
		}
	}

	public Map<Integer, String> getPhones() {
		return this.phones;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public void setPhones(Map<Integer, String> phones) {
		this.phones = phones;
	}
	// Геттеры и сеттеры
	// --------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return surname + " " + name + " " + middlename;
	}

}
