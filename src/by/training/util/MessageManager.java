package by.training.util;

import java.util.ResourceBundle;

import by.training.constants.Message;

public class MessageManager {
	private static MessageManager instance;
	private ResourceBundle bundle = ResourceBundle.getBundle("messages");
	
	
	public static MessageManager getInstance(){
		if(instance == null){
			instance = new MessageManager();
		}
		return instance;
	}

	public String getProperty(Message mess) {
		return bundle.getString(mess.toString());
	}

}
