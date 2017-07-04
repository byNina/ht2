package by.training.commands.phone;

import javax.servlet.http.HttpServletRequest;

import by.training.beans.Person;
import by.training.commands.BaseCommand;

public class DeletePhoneCommand extends BaseCommand {

	public DeletePhoneCommand() {
		this.page = bundle.getString("path.page.manageperson");
	}

	@Override
	public String execute(HttpServletRequest req) {
		Person person = (Person) session.getAttribute("person");
		int id = Integer.parseInt(req.getParameter("phoneid"));
		person.getPhones().remove(id);
		session.setAttribute("person", person);
		return this.page;
	}

}
