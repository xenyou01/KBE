package de.htwBerlin.ai.kbe.storage;

import de.htwBerlin.ai.kbe.bean.User;

public interface UsersDAO {
	
	public User findUserByUserId(String user_id);

}
