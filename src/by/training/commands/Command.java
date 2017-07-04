package by.training.commands;

import javax.servlet.http.HttpServletRequest;

public interface Command {
	
	String execute(HttpServletRequest req);

}
