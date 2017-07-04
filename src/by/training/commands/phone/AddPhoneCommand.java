package by.training.commands.phone;

import javax.servlet.http.HttpServletRequest;

import by.training.beans.Person;
import by.training.commands.BaseCommand;

public class AddPhoneCommand extends BaseCommand {
	private String errorpage = bundle.getString("path.page.managephone");

	public AddPhoneCommand() {
		this.page = bundle.getString("path.page.manageperson");
	}

	@Override
	public String execute(HttpServletRequest req) {
		if (validatePhone(req.getParameter("phone")) == "") {
			Person person = (Person) session.getAttribute("person");
			int index = -1;
			while (person.getPhones().containsKey(index)) {
				index--;
			}
			person.getPhones().put(index, req.getParameter("phone"));
			return this.page;
		}
		req.setAttribute("errormessage", validatePhone(req.getParameter("phone")));
		return errorpage;
	}
}
