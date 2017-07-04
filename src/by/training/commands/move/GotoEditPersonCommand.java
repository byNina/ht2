package by.training.commands.move;

import javax.servlet.http.HttpServletRequest;

import by.training.beans.Person;
import by.training.commands.BaseCommand;

public class GotoEditPersonCommand extends BaseCommand {

	public GotoEditPersonCommand() {
		this.page = bundle.getString("path.page.manageperson");
	}

	@Override
	public String execute(HttpServletRequest req) {
		session = req.getSession();
		int id = Integer.parseInt(req.getParameter("id"));
		Person person = dao.getPersonById(id);
		session.setAttribute("person", person);
		return this.page;
	}

}
