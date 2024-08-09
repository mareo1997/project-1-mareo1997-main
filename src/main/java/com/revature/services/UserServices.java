package com.revature.services;

import java.util.List;

import com.revature.model.Role;
import com.revature.model.User;

public interface UserServices {

	public User insert(User u);

	public void insert(Role r);

	public User profileHQL(User u);

	public List<User> allEmplHQL();

	public User updateHQL(User u, String fname, String lname, String email, String username, String password, String repassword);

	public Role roleHQL();
	
	public User useridHQL(int i);
	
}
/*	public void profile(User u);

	public void allEmpl();
}*/
