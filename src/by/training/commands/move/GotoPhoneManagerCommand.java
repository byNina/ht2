package by.training.commands.move;

import javax.servlet.http.HttpServletRequest;

import by.training.beans.Person;
import by.training.commands.BaseCommand;

public class GotoPhoneManagerCommand extends BaseCommand {

	public GotoPhoneManagerCommand() {
		this.page = bundle.getString("path.page.managephone");
	}

	@Override
	public String execute(HttpServletRequest req) {
		if (req.getParameter("phoneid") != null) {
			int phoneid = Integer.parseInt(req.getParameter("phoneid"));
			session.setAttribute("phoneid", phoneid);
			Person person = (Person) session.getAttribute("person");
			session.setAttribute("phone", person.getPhones().get(phoneid));
		}
		return this.page;
	}
}