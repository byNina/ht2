package by.training.constants;

public enum Message {

	// Success Messages
	SUCCESS_ADDING_PERSON,
	SUCCESS_DELETING_PERSON,
	SUCCESS_LOADING_FROM_DATABASE,
	SUCCESS_DELETING_PHONE,
	SUCCESS_EDIT_PERSON,

	// Error Messages
	EDIT,
	ERROR_ADDING_PERSON,
	ERROR_DELETING_PERSON,
	ERROR_LOADING_FROM_DATABASE,
	ERROR_DELETING_PHONE,
	ERROR_PHONE_VALIDATION,
	ERROR_NAME_VALIDATION,
	ERROR_SURNAME_VALIDATION,
	ERROR_MIDDLENAME_VALIDATION,
	ERROR_EDIT_PERSON,

	//
	;

	public String toString() {
		return super.toString().toLowerCase().replaceAll("_", ".");
	}

}