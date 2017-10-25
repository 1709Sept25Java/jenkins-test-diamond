package ers.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import ers.dao.*;
import ers.domain.Users;
import ers.util.ConnectionUtil;

public class Driver {

	public static void main(String[] args){
		
		UsersDao ud = new UsersDaoImpl();
		Users u = new Users();
		ud.updateUsers("SPONGEBOB", "U_EMAIL" , "bobpop123@gmail.com");
		
		
	}
}
