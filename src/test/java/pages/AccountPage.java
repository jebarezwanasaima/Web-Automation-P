package pages;

import org.openqa.selenium.By;

public class AccountPage extends BasePage{
    public By account_text = By.xpath("//h1[normalize-space()='Account']");

    public By welcome_text = By.xpath("//p[normalize-space()='Welcome to OpenCart!']");
    public By account_btn = By.xpath("//a[@class='btn btn-link navbar-btn']");

    public By logout_btn = By.xpath("//a[@class='btn btn-black navbar-btn']");


    public void doLogout(){
        if (getElement(logout_btn).isDisplayed()){
            clickOnElement(logout_btn);
        }
    }


}
