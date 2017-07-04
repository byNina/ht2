package by.training.commands.phone;

import javax.servlet.http.HttpServletRequest;

import by.training.beans.Person;
import by.training.commands.BaseCommand;

public class EditPhoneCommand extends BaseCommand {
	private String errorpage = bundle.getString("path.page.managephone");

	public EditPhoneCommand() {
		this.page = bundle.getString("path.page.manageperson");
	}

	@Override
	public String execute(HttpServletRequest req) {
		String phone = (String) req.getParameter("phone").trim();

		if (validatePhone(phone) == "") {

			Person person = (Person) session.getAttribute("person");
			int id = -1;
			if (session.getAttribute("phoneid") == null) {
				while (person.getPhones().containsKey(id)) {
					id--; // not supported by database key to detect unsaved
							// phones
				}
			} else {
				id = (Integer) session.getAttribute("phoneid");
			}
			String.format("%.50s", phone);
			person.getPhones().put(id, phone);
			session.removeAttribute("phone");
			session.removeAttribute("phoneid");
			return this.page;
		}
		req.setAttribute("errormessage", validatePhone(phone));
		return errorpage;
	}

}
