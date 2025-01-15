package testcases;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utilities.DataSet;
import utilities.DriverSetup;

public class TestLoginPage extends DriverSetup {
    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();


    @BeforeMethod
    public void setup_class(){
        loginPage.navigateToLoginPage();
    }

    @AfterMethod
    public void addTestScreenshot(){
        loginPage.addScreenshot("After test");
    }

    @Test
    public void testLoginWithValidCredentials(){
        loginPage.writeOnElement(loginPage.email_input_box, "fameloh253@chysir.com");
        loginPage.writeOnElement(loginPage.password_input_box, "Pass&Pass!");
        loginPage.clickOnElement(loginPage.login_btn);
        Assert.assertTrue(homePage.is_element_visible(homePage.logout_btn));
        Assert.assertFalse(loginPage.is_element_visible(loginPage.login_btn));
    }

    @Test(description = "Test with invalid password")
    @Description("Test user is trying to login with invalid password")
    public void testLoginWithInvalidPassword(){
        Allure.label("severity", "critical");
        loginPage.writeOnElement(loginPage.email_input_box, "fameloh253@chysir.com");
        loginPage.writeOnElement(loginPage.password_input_box, "Pass&Pas");
        loginPage.clickOnElement(loginPage.login_btn);
        Assert.assertEquals(loginPage.getElement(loginPage.error_msg).getText(), "Your email or password is incorrect!");
        Assert.assertTrue(loginPage.is_element_visible(loginPage.login_btn));
    }

    @Test
    public void testLoginWithInvalidEmailAndPassword(){
        loginPage.writeOnElement(loginPage.email_input_box, "fameloh253@chys.com");
        loginPage.writeOnElement(loginPage.password_input_box, "Pass&Pas");
        loginPage.clickOnElement(loginPage.login_btn);
        Assert.assertEquals(loginPage.getElement(loginPage.error_msg).getText(), "Your email or password is incorrect!");
        Assert.assertTrue(loginPage.is_element_visible(loginPage.login_btn));
    }

    @Test
    public void testLoginWithInvalidEmailAndValidPassword(){
        loginPage.writeOnElement(loginPage.email_input_box, "fameloh253@chys.com");
        loginPage.writeOnElement(loginPage.password_input_box, "Pass&Pass!");
        loginPage.clickOnElement(loginPage.login_btn);
        Assert.assertEquals(loginPage.getElement(loginPage.error_msg).getText(), "Your email or password is incorrect!");
        Assert.assertTrue(loginPage.is_element_visible(loginPage.login_btn));
    }

    @Test
    public void testLoginWithoutEmailAndPassword(){
        loginPage.writeOnElement(loginPage.email_input_box, "");
        loginPage.writeOnElement(loginPage.password_input_box, "");
        loginPage.clickOnElement(loginPage.login_btn);
        Assert.assertEquals(loginPage.getElement(loginPage.email_input_box).getAttribute("validationMessage"), "Please fill out this field.");
        Assert.assertTrue(loginPage.is_element_visible(loginPage.login_btn));
    }

    @Test
    public void testLoginEmailAndWithoutPassword(){
        loginPage.writeOnElement(loginPage.email_input_box, "fameloh253@chysir.com");
        loginPage.writeOnElement(loginPage.password_input_box, "");
        loginPage.clickOnElement(loginPage.login_btn);
        Assert.assertEquals(loginPage.getElement(loginPage.password_input_box).getAttribute("validationMessage"), "Please fill out this field.");
        Assert.assertTrue(loginPage.is_element_visible(loginPage.login_btn));
    }

    @Test(dataProvider = "invalidUserCredentials", dataProviderClass = DataSet.class)
    public void testLoginWithInvalidCredentials(String email, String password, String error_msg, String validation_msg){
        loginPage.writeOnElement(loginPage.email_input_box, email);
        loginPage.writeOnElement(loginPage.password_input_box, password);
        loginPage.clickOnElement(loginPage.login_btn);
        Assert.assertEquals(loginPage.getElement(loginPage.email_input_box).getAttribute("validationMessage"), validation_msg);
        Assert.assertEquals(loginPage.getElement(loginPage.password_input_box).getAttribute("validationMessage"), validation_msg);
        if (loginPage.is_element_visible(loginPage.error_msg))
            Assert.assertEquals(loginPage.getElement(loginPage.error_msg).getText(), error_msg);
        Assert.assertTrue(loginPage.is_element_visible(loginPage.login_btn));
    }
}
