package main.java.LoginWebApp;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertLinkPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertTitleEquals;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLink;
import static net.sourceforge.jwebunit.junit.JWebUnit.setBaseUrl;
import static net.sourceforge.jwebunit.junit.JWebUnit.setTestingEngineKey;
 
import org.junit.Before;
import org.junit.Test;
 
import net.sourceforge.jwebunit.util.TestingEngineRegistry;
 
 
public class JWebUnitTest {
    @Before
    public void prepare() {
        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT); 
        setBaseUrl("http://localhost:8081/LoginWebApp");
    }
 
    @Test
    public void testLoginPage() {
        beginAt("login.jsp"); 
        assertTitleEquals(" Java Simple Login Web App");
        assertLinkPresent("register");
        clickLink("register");
        assertTitleEquals("Registration");
    }
     
    // @Test
    // public void testHomePage() {
    //     beginAt("home.jsp"); 
    //     assertTitleEquals("Home");
    //     assertLinkPresent("login");
    //     clickLink("login");
    //     assertTitleEquals("Login");
    // }
}