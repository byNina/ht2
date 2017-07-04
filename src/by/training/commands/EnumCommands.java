package by.training.commands;

import by.training.commands.common.ShowAllCommand;
import by.training.commands.move.GotoAddPersonCommand;
import by.training.commands.move.GotoEditPersonCommand;
import by.training.commands.move.GotoPhoneManagerCommand;
import by.training.commands.person.AddPersonCommand;
import by.training.commands.person.DeletePersonCommand;
import by.training.commands.person.EditPersonCommand;
import by.training.commands.phone.AddPhoneCommand;
import by.training.commands.phone.DeletePhoneCommand;
import by.training.commands.phone.EditPhoneCommand;

public enum EnumCommands {

	// *****************************
	// Common commands

	SHOWALL {
		{
			command = new ShowAllCommand();
		}
	},

	// Move commands

	GOTOADDPERSON {
		{
			command = new GotoAddPersonCommand();
		}
	},

	GOTOEDITPERSON {
		{
			command = new GotoEditPersonCommand();
		}
	},

	GOTOPHONEMANAGER {
		{
			command = new GotoPhoneManagerCommand();
		}
	},

	// Person manipulation commands

	ADDPERSON {
		{
			command = new AddPersonCommand();
		}
	},

	EDITPERSON {
		{
			command = new EditPersonCommand();
		}
	},

	DELETEPERSON {
		{
			command = new DeletePersonCommand();
		}
	},

	// Phone manipulation commands

	ADDPHONE {
		{
			command = new AddPhoneCommand();
		}
	},

	EDITPHONE {
		{
			command = new EditPhoneCommand();
		}
	},

	DELETEPHONE {
		{
			command = new DeletePhoneCommand();
		}
	},

	;

	Command command;

	public Command getCommand() {
		return command;
	}

}
