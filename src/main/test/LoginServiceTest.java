package main.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import main.java.LoginService;

public class LoginServiceTest {
 @Test
 public void authenticateSuccessTest(){
   final LoginService login = new LoginService();
   assertTrue("authenticate failed", login.authenticate("demo", "demo"));
 }

 @Test
 public void authenticateFailTest() {
   final LoginService login = new LoginService();
  assertFalse("authenticate failed", login.authenticate("demo1", "demo"));
 }
}