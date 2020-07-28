package main.test;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import main.java.User;
import main.java.UserService;

public class UserServiceTest {
	private User user;
	private final UserService userService = new UserService();
	
	@Before
	public void setup(){
		user = new User(100, "ramesh");
	}
	
	@Test
	public void userServiceTest(){
		userService.createUser(user);
		List<User> users = userService.getUsers();
		assertEquals(1, users.size());
		userService.deleteUser(user);
		users = userService.getUsers();
		assertEquals(0, users.size());
	}
	
}