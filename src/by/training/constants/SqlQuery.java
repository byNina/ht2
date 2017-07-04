package by.training.constants;

public class SqlQuery {

	public static final String GET_ALL_PERSONS = "SELECT * FROM `person` ORDER BY `surname` ASC";

	// person manipulate queries
	public static final String ADD_NEW_PERSONS = "INSERT INTO `person` (`name`, `surname`, `middlename`) VALUES (?, ?, ?)";
	public static final String GET_PERSON_BY_ID = "SELECT * FROM `person` WHERE `id`=?";
	public static final String GET_PERSON_PHONES = "SELECT * FROM `phone` WHERE `owner`=?";
	public static final String UPDATE_PERSON_INFO = "UPDATE `person` SET `name` = ?, `surname` = ?, `middlename` = ? WHERE `id` = ?";
	public static final String DELETE_PERSON = "DELETE FROM `person` WHERE `id`= ?";

	// phone manipulate queries
	public static final String ADD_PHONE = "INSERT INTO `phone` (`owner`, `number`) VALUES (?, ?)";
	public static final String UPDATE_PHONE_INFO = "UPDATE `phone` SET `number` = ? WHERE `id` = ?";
	public static final String DELETE_PHONE = "DELETE FROM `phone` WHERE `owner`= ? AND `number`= ''";

	public static final String CLEAN_PHONE = "UPDATE `phone` SET `number` = '' WHERE `owner` = ?";

}
