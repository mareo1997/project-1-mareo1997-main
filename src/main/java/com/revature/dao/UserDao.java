package com.revature.dao;

import java.util.List;

import com.revature.model.Role;
import com.revature.model.User;

public interface UserDao {

	/*public void profile(User u);

	public void allEmpl();*/

	public User insert(User u);

	public void insert(Role r);

	public User updateHQL(User u, String fname, String lname, String email, String username, String password, String repassword);

	public List<User> allEmplHQL();

	public Role roleHQL();
	
	public User profileHQL(User u);
	
	public User useridHQL(int i);

}
