package main.java;

public class LoginService {
    public boolean authenticate(final String userName, final String password) {
    if("demo".equals(userName) && "demo".equals(password)){
    return true;
    }
    return false;
}
}