package ers.dao;

import ers.domain.Users;

public interface UsersDao {
	
	public int getUserRoleId(String username, String password);
	
	public boolean isValidUsername(String username);
	
	public boolean isValidPassword(String username, String password);
	
	public Users createUser(String username, String password);
	
	public void updateUsers(String username, String col, String newVal);
}
