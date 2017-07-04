package by.training.commands.person;

import javax.servlet.http.HttpServletRequest;

import by.training.beans.Person;
import by.training.commands.BaseCommand;
import by.training.constants.Message;

public class EditPersonCommand extends BaseCommand {

	public EditPersonCommand() {

		this.page = bundle.getString("path.page.manageperson");
	}

	@Override
	public String execute(HttpServletRequest req) {
		Person person = ((Person) session.getAttribute("person")).clone();
		person = initExistingPersonFromReq(person, req);
		if (validatePersonFMLName(person).equals("")) {
			if (dao.editPerson(person)) {
				session.setAttribute("person", person);
				req.setAttribute("successmessage",  mm.getProperty(Message.SUCCESS_EDIT_PERSON));
			} else {
				req.setAttribute("errormessage",  mm.getProperty(Message.ERROR_EDIT_PERSON));
			}
		} else {
			req.setAttribute("errormessage", validatePersonFMLName(person));
		}
		return this.page;
	}

}
