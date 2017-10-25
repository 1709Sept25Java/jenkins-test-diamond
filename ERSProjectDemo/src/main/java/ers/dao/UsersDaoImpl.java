package ers.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.driver.OracleDriver;
import ers.domain.Users;
import ers.util.ConnectionUtil;

public class UsersDaoImpl implements UsersDao {

	@Override
	public int getUserRoleId(String username, String password) {
		
		PreparedStatement pstmt = null;
		int id = 0;
		
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			ConnectionUtil obj = ConnectionUtil.getinstance();
			String sql = "SELECT UR_ID FROM ERS_USERS "
					+ "WHERE U_USERNAME = ? AND U_PASSWORD = ?";
			pstmt = obj.establishedConnection().prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				id = rs.getInt("UR_ID");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return id;
	}

	@Override
	public boolean isValidUsername(String username) {
		PreparedStatement pstmt = null;
		try {
			ConnectionUtil obj = ConnectionUtil.getinstance();
			String sql = "SELECT COUNT(U_USERNAME) FROM ERS_USERS WHERE U_USERNAME = ?";
			pstmt = obj.establishedConnection().prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			int count_un = 0;
			while(rs.next()) {
				count_un = rs.getInt("COUNT(U_USERNAME)");
			}
			if(count_un == 1) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean isValidPassword(String username, String password) {
		PreparedStatement pstmt = null;
		try {
			ConnectionUtil obj = ConnectionUtil.getinstance();
			String sql = "SELECT U_PASSWORD FROM ERS_USERS WHERE U_USERNAME = ?";
			pstmt = obj.establishedConnection().prepareStatement(sql);
			pstmt.setString(1, password);
			ResultSet rs = pstmt.executeQuery();
			String pw = "";
			while(rs.next()) {
				pw = rs.getString("U_PASSWORD");
			}
			if(pw.equals(password)) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Users createUser(String username, String password) {
		
		Users u = new Users();
		PreparedStatement pstmt = null;
		
		try {
			ConnectionUtil obj = ConnectionUtil.getinstance();
			String sql = "SELECT U_ID, U_FIRSTNAME, U_LASTNAME, U_EMAIL "
					+ "FROM ERS_USERS WHERE U_USERNAME = ?";
			pstmt = obj.establishedConnection().prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			int u_id = 0;
			String fname = "";
			String lname = "";
			String email = "";
			while(rs.next()) {
				u_id = rs.getInt("U_ID");
				fname = rs.getString("U_FIRSTNAME");
				lname = rs.getString("U_LASTNAME");
				email = rs.getString("U_EMAIL");
			}
			u.setU_id(u_id);
			u.setUsername(username);
			u.setPassword(password);
			u.setFirstname(fname);
			u.setLastname(lname);
			u.setEmail(email);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return u; 
	}
	
	public void updateUsers(String username, String col, String newVal) {
		
		CallableStatement stmt = null;
		
		try {
			ConnectionUtil obj = ConnectionUtil.getinstance();
			stmt = obj.establishedConnection().prepareCall("{call UPDATE_USER(?, ?, ?)}");
			stmt.setString(1, username);
			stmt.setString(2, col);
			stmt.setString(3, newVal);
			stmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
