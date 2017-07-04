package by.training.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import by.training.beans.Person;
import by.training.constants.SqlQuery;

public class Dao {
	private static Connection connection;
	private static ResourceBundle dbBundle = ResourceBundle.getBundle("database");

	private static String database = "";
	private static String user = "";
	private static String password = "";
	private static String otherSettings = "";// "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&connectionCollation=utf8_general_ci";

	private static Map<Integer, Person> persons;

	// Количество рядов таблицы, затронутых последним запросом.
	private Integer affected_rows = 0;

	// Значение автоинкрементируемого первичного ключа, полученное после
	// добавления новой записи.
	private Integer last_insert_id = 0;

	// Указатель на экземпляр класса.
	private static Dao instance = null;

	private static void initSettings() {
		Enumeration<String> e = dbBundle.getKeys();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			switch (key) {
			case "database":
				database = dbBundle.getString(key);
				break;
			case "user":
				user = dbBundle.getString(key);
				break;
			case "password":
				password = dbBundle.getString(key);
				break;
			default:
				otherSettings += "&" + key + "=" + dbBundle.getString(key);
				break;
			}
		}
		System.out.println("jdbc:mysql://localhost/" + database + "?user=" + user + "&password="
				+ password + otherSettings);

	}

	// Метод для получения экземпляра класса (реализован Singleton).
	public static Dao getInstance() {
		if (instance == null) {
			instance = new Dao();
			try {
				initSettings();
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				connection = DriverManager.getConnection("jdbc:mysql://localhost/" + database
						+ "?user=" + user + "&password=" + password + otherSettings);
				System.out.println("Got connection to DB");
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				System.out.println("Can't load driver");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("Error during connection to db");
				e.printStackTrace();
			}
		}
		return instance;
	}

	// "Заглушка", чтобы экземпляр класса нельзя было получить напрямую.
	private Dao() {
		// Просто "заглушка".
	}

	// ***********************************
	// Common requests

	// Выполнение запросов на выборку всех персонов.
	public Map<Integer, Person> getAllPersons() {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(SqlQuery.GET_ALL_PERSONS);
			ResultSet resultSet = statement.executeQuery();
			initPersons(resultSet);
			return persons;
		} catch (SQLException e) {
			System.out.println("SQL Error during \"getAllPersons\"");
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(statement);
		}
		System.out.println("Null on \"getAllPersons\"");
		return null;
	}

	// ***********************************
	// Person requests

	public Person getPersonById(int id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			Person person = null;
			statement = connection.prepareStatement(SqlQuery.GET_PERSON_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				person = initPersonFromDb(resultSet);
			}
			return person;
		} catch (SQLException e) {
			System.out.println("SQL Error during \"getPersonById\"");
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(statement);
		}
		System.out.println("Null on \"getPersonById\"");
		return null;
	}

	// Выполнение запросов на модификацию данных.
	public boolean editPerson(Person person) {
		PreparedStatement statement = null;
		try {
			// start transation
			connection.setAutoCommit(false);
			boolean transactionFail = false; // indicate transaction success

			statement = connection.prepareStatement(SqlQuery.UPDATE_PERSON_INFO);
			statement.setString(1, person.getName());
			statement.setString(2, person.getSurname());
			statement.setString(3, person.getMiddlename());
			statement.setInt(4, person.getId());
			this.affected_rows = statement.executeUpdate();
			if (affected_rows > 0) {
				statement = connection.prepareStatement(SqlQuery.CLEAN_PHONE);
				statement.setInt(1, person.getId());

				for (Map.Entry<Integer, String> phone : person.getPhones().entrySet()) {
					if (phone.getKey() < 0) {
						statement = connection.prepareStatement(SqlQuery.ADD_PHONE);
						statement.setInt(1, person.getId());
						statement.setString(2, phone.getValue());
					} else {
						statement = connection.prepareStatement(SqlQuery.UPDATE_PHONE_INFO);
						statement.setString(1, phone.getValue());
						statement.setInt(2, phone.getKey());
					}
					this.affected_rows = statement.executeUpdate();
					if (affected_rows == 0) {
						transactionFail = true;
						break;
					}
					statement = connection.prepareStatement(SqlQuery.DELETE_PHONE);
					statement.setInt(1, person.getId());
				}
			} else {
				transactionFail = true;
			}
			if (!transactionFail) {
				connection.commit();
				return true;
			} else {
				connection.rollback();
			}
		} catch (SQLException e) {
			System.out.println("SQL Error during \"editPerson\"");
			e.printStackTrace();
		} finally {
//			closeStatement(statement);
			setAutotransaction();
		}
		System.out.println("Null on \"editPerson\"");
		return false;
	}

	// Выполнение запроса на добавление персона
	public boolean addPerson(Person person) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(SqlQuery.ADD_NEW_PERSONS,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, person.getName());
			statement.setString(2, person.getSurname());
			statement.setString(3, person.getMiddlename());
			this.affected_rows = statement.executeUpdate();
			// Получаем last_insert_id() для операции вставки.

			// Если добавление прошло успешно...
			if (affected_rows > 0) {
				rs = statement.getGeneratedKeys();
				if (rs.next()) {
					this.last_insert_id = rs.getInt(1);
				}
				return true;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error during \"addPerson\"");
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(statement);
		}
		System.out.println("Null on \"addPerson\"");
		return false;
	}

	// Выполнение запросов на удаление персона.
	public boolean deletePerson(int id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(SqlQuery.DELETE_PERSON);
			statement.setInt(1, id);
			this.affected_rows = statement.executeUpdate();

			// Если удаление прошло успешно...
			if (affected_rows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.out.println("SQL Error during \"deletePerson\"");
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(statement);
		}

		System.out.println("Null on \"deletePerson\"");
		return false;
	}

	// ***********************************
	// Phone requests

	private Map<Integer, String> getPersonPhones(int id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(SqlQuery.GET_PERSON_PHONES);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			Map<Integer, String> phones = initPhones(resultSet);
			return phones;
		} catch (SQLException e) {
			System.out.println("SQL Error during \"getPersonPhones\"");
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(statement);
		}
		System.out.println("Null on \"getPersonPhones\"");
		return null;
	}

	// ***********************************
	// service methods

	private void initPersons(ResultSet resultSet) throws SQLException {
		persons = new LinkedHashMap<Integer, Person>();
		while (resultSet.next()) {
			Person person = initPersonFromDb(resultSet);
			persons.put(person.getId(), person);
		}
	}

	private Person initPersonFromDb(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String surname = resultSet.getString("surname");
		String name = resultSet.getString("name");
		String middlename = resultSet.getString("middlename");
		Map<Integer, String> phones = getPersonPhones(resultSet.getInt("id"));
		Person person = new Person(id, name, surname, middlename, phones);
		return person;
	}

	private Map<Integer, String> initPhones(ResultSet resultSet) throws SQLException {
		Map<Integer, String> phones = new HashMap<Integer, String>();
		while (resultSet.next()) {
			Integer key = resultSet.getInt("id");
			phones.put(key, resultSet.getString("number"));
		}
		return phones;
	}

	private void setAutotransaction() {
		if (connection != null) {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Error during set autocommit true!");
				e.printStackTrace();
			}
		}
	}

	// +++++++++++++++++++++++++
	// resource safety methods
	private void closeStatement(PreparedStatement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			System.out.println("Error during close statement!");
		}
	}

	private void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.out.println("Error during close resultset!");
		}
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++
	// Геттеры и сеттеры.
	public Integer getAffectedRowsCount() {
		return this.affected_rows;
	}

	public Integer getLastInsertId() {
		return this.last_insert_id;
	}

	public Map<Integer, Person> getPersons() {
		return persons;
	}

	public void setPersons(Map<Integer, Person> persons) {
		Dao.persons = persons;
	}

	// Геттеры и сеттеры.
	// -------------------------------------------------

}
