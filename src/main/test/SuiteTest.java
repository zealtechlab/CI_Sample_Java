import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  LoginServiceTest.class,
  UserServiceTest.class,
  // TestLogin.class,   // commented for later use
})
public class SuiteTest {
// This class remains empty, it is used only as a holder for the above annotations
}