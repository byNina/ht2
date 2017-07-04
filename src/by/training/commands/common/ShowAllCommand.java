package by.training.commands.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import by.training.beans.Person;
import by.training.commands.BaseCommand;
import by.training.constants.Message;

public class ShowAllCommand extends BaseCommand {

	public ShowAllCommand() {
		page = bundle.getString("path.page.showall");
	}

	@Override
	public String execute(HttpServletRequest req) {
		session = req.getSession();
		if (session != null) {
			session.invalidate();
		}
		Map<Integer, Person> persons = dao.getAllPersons();
		if (persons != null) {
			req.setAttribute("persons", persons);
			String successmessage = "";
			if (req.getParameter("successmessage") != null) {
				successmessage += req.getParameter("successmessage");
			}
			successmessage += mm.getProperty(Message.SUCCESS_LOADING_FROM_DATABASE)
					+ persons.size();
			req.setAttribute("successmessage", successmessage);

		} else {
			req.setAttribute("errormessage", mm.getProperty(Message.ERROR_LOADING_FROM_DATABASE));
		}

		return page;
	}

}
