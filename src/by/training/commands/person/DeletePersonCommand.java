package by.training.commands.person;

import javax.servlet.http.HttpServletRequest;

import by.training.commands.BaseCommand;
import by.training.constants.Message;

public class DeletePersonCommand extends BaseCommand {

	public DeletePersonCommand() {
		this.page = bundle.getString("path.page.main");
	}

	@Override
	public String execute(HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));
		if (dao.deletePerson(id)) {
			req.setAttribute("successmessage", Message.SUCCESS_DELETING_PERSON);
		} else {
			req.setAttribute("errormessage", Message.ERROR_ADDING_PERSON);
		}
		return this.page;
	}
}
