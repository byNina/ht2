package by.training.commands.person;

import javax.servlet.http.HttpServletRequest;

import by.training.beans.Person;
import by.training.commands.BaseCommand;
import by.training.constants.Message;

public class AddPersonCommand extends BaseCommand {

	public AddPersonCommand() {
		this.page = bundle.getString("path.page.manageperson");
	}

	@Override
	public String execute(HttpServletRequest req) {

		Person person = initPersonFromReq(req);
		if (validatePersonFMLName(person).equals("")) {
			if (dao.addPerson(person)) {
				person.setId(dao.getLastInsertId());
				session.setAttribute("person", person);
				req.setAttribute("successmessage", mm.getProperty(Message.SUCCESS_ADDING_PERSON));
			} else {
				req.setAttribute("errormessage", mm.getProperty(Message.ERROR_ADDING_PERSON));
			}
		}else{
			req.setAttribute("errormessage", validatePersonFMLName(person));
		}
		return this.page;
	}

}
