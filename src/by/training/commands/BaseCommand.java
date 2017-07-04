package by.training.commands;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.training.beans.Person;
import by.training.constants.Message;
import by.training.dao.Dao;
import by.training.util.MessageManager;

public abstract class BaseCommand implements Command {
	protected ResourceBundle bundle = ResourceBundle.getBundle("path");
	protected MessageManager mm = MessageManager.getInstance();
	protected Dao dao = Dao.getInstance();
	protected static HttpSession session;
	protected String page;

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	public BaseCommand() {

	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	protected Person initPersonFromReq(HttpServletRequest req) {
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String middlename = req.getParameter("middlename");
		return new Person(name, surname, middlename);
	}

	// Валидация ФИО и генерация сообщения об ошибке в случае невалидных данных.
	protected String validatePersonFMLName(Person person) {
		String error_message = "";

		if (!validateFMLNamePart(person.getName(), false)) {
			error_message += mm.getProperty(Message.ERROR_NAME_VALIDATION); //"Имя должно быть строкой от 1 до 150 символов из букв, цифр, знаков подчёркивания и знаков минус.<br />";
		}

		if (!validateFMLNamePart(person.getSurname(), false)) {
			error_message += mm.getProperty(Message.ERROR_SURNAME_VALIDATION); // "Фамилия должна быть строкой от 1 до 150 символов из букв, цифр, знаков подчёркивания и знаков минус.<br />";
		}

		if (!validateFMLNamePart(person.getMiddlename(), true)) {
			error_message +=  mm.getProperty(Message.ERROR_MIDDLENAME_VALIDATION); // "Отчество должно быть строкой от 0 до 150 символов из букв, цифр, знаков подчёркивания и знаков минус.<br />";
		}

		return error_message;
	}

	// Валидация частей ФИО. Для отчества можно передать второй параметр ==
	// true,
	// тогда допускается пустое значение.
	protected boolean validateFMLNamePart(String fml_name_part, boolean empty_allowed) {
		if (empty_allowed) {
			Matcher matcher = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ\\d-_]{0,150}$")
					.matcher(fml_name_part);
			return matcher.matches();
		} else {
			Matcher matcher = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ\\d-_]{1,150}$")
					.matcher(fml_name_part);
			return matcher.matches();
		}
	}

	// Phone validation. Total 2-50 symbols: numbers, +, -, #
	protected String validatePhone(String phone) {
		String error_message = "";
		if (phone != null && !phone.isEmpty()) {
			Matcher matcher = Pattern.compile("^[\\d-+#]{2,50}$").matcher(phone);
			if (!matcher.matches()) {
				error_message += mm.getProperty(Message.ERROR_PHONE_VALIDATION);
			}
		}else{
			error_message += mm.getProperty(Message.ERROR_PHONE_VALIDATION);
		}
		return error_message;
	}

	protected Person initExistingPersonFromReq(Person person, HttpServletRequest req) {
		person.setName(req.getParameter("name"));
		person.setSurname(req.getParameter("surname"));
		person.setMiddlename(req.getParameter("middlename"));
		return person;
	}

	protected void throwAttribute(HttpServletRequest req, String attr) {
		req.setAttribute(attr, req.getAttribute(attr));
	}

}
