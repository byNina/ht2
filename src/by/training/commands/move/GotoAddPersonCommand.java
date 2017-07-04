package by.training.commands.move;

import javax.servlet.http.HttpServletRequest;

import by.training.commands.BaseCommand;

public class GotoAddPersonCommand extends BaseCommand {

	public GotoAddPersonCommand() {
		this.page = bundle.getString("path.page.manageperson");
	}

	@Override
	public String execute(HttpServletRequest req) {
		session = req.getSession();
		return this.page;
	}

}
