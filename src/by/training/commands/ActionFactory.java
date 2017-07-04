package by.training.commands;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

	public Command detectCommand(HttpServletRequest req) {
		Command command = null;
		String commandFromReq = req.getParameter("command");
		
		if (commandFromReq != null) {
			try {
				command = EnumCommands.valueOf(commandFromReq.toUpperCase()).getCommand();
			} catch (IllegalArgumentException e) {
				System.out.println("Such command doesn't exist :" + commandFromReq);
			}
		}
		return command;
	}
}
