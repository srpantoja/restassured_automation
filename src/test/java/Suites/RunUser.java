package Suites;

import Controller.User.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CreateUser.class,DeleteUser.class,EditUser.class, FindUser.class, GetAllUsers.class})


public class RunUser {
}
