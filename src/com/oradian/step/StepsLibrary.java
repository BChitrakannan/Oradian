package com.oradian.step;


import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.inject.Key;
import com.oradian.util.Utilities;
import com.qmetry.qaf.automation.core.MessageTypes;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
import com.qmetry.qaf.automation.util.Reporter;




public class StepsLibrary extends WebDriverTestCase  {
		
	public WebDriverWait exWait;
	public StepsLibrary()
	{
		
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	
	
	@QAFTestStep(description = "customer launches the session {0}")
	public void SiteNavigation(String url) 
	{
		getDriver().get(Utilities.readProp(url));
		getDriver().findElement("login.logo").isPresent();
		getDriver().findElement("login.welcome.msg").isPresent();
		Reporter.log("Navigated to the URL");

		
	}
	
	@QAFTestStep(description="customer logs in with {0} and {1}")
	public void login(String userName,String password){
		
		getDriver().findElement("login.username").sendKeys(userName);
		getDriver().findElement("login.password").sendKeys(password);
		getDriver().findElement("login.submit").click();
		Reporter.log("Login Attempt made");

	}
	
	@QAFTestStep(description="system verifies if the customer is logged in")
	public void loginVerification(){
		
		boolean accountDrop = getDriver().findElement("login.user.verification.hamburger").isPresent() ;
		boolean dashboard = getDriver().findElement("login.user.verification.dashboard").isPresent();
		boolean searchBox=getDriver().findElement("login.user.searchbox").isPresent();
		boolean activityStream = getDriver().findElement("homepage.activity.stream").isPresent();
		if(accountDrop && dashboard && searchBox && activityStream)	
		{
		Reporter.log("Customer navigated to home page successfullly", MessageTypes.Pass);

		}
		else
		{
			Reporter.log("Login Attempt failed");

			Reporter.log("Signed in resources not found", MessageTypes.Fail);
			closeBrowser();
		}
		
		
	}
	
	@QAFTestStep(description="Create Individual {0} client")
	public void createClient(String gender) throws InterruptedException {
		
		getDriver().findElement("create.client.menu").click();
		getDriver().findElement("create.client.individual").click();
		getDriver().findElement("create.client.label").verifyPresent("Verifying the successful navigation");
		WebElement genderBox=getDriver().findElement("create.client.gender");
		Actions actions = new Actions(getDriver());
		actions.moveToElement(genderBox);
		actions.click();
		actions.sendKeys(gender, Keys.ENTER );
		actions.build().perform();
		getDriver().findElement("create.client.firstName").sendKeys(Utilities.randomString("text",64));
		getDriver().findElement("create.client.lastName").sendKeys(Utilities.randomString("text",64));
		WebElement dob= getDriver().findElement("create.client.dob");
		dob.sendKeys("14");
		dob.sendKeys("04");
		dob.sendKeys("1990");
		getDriver().findElement("create.client.dobactual").click();
		System.out.println("DOB Clicked");
		getDriver().findElement("create.client.street").sendKeys(Utilities.randomString("text", 8));
		getDriver().findElement("create.client.city").sendKeys(Utilities.randomString("text",5));
		getDriver().findElement("create.client.post").sendKeys(Utilities.randomString("digit",6));
		getDriver().findElement("create.client.country").sendKeys(Utilities.randomString("text", 5));
		getDriver().findElement("create.client.phone").sendKeys(Utilities.randomString("digit",10));
		WebElement branch=getDriver().findElement("create.client.branch");
		actions.moveToElement(branch);
		actions.click();
		actions.sendKeys("Branch 1000", Keys.ENTER );
		actions.build().perform();
		
		getDriver().findElement("create.client.submit").click();
		System.out.println("Clicked Submit");
		getDriver().findElement("create.client.pending").assertPresent("Asserting the presence of Pending approval");
		
	}
	
	@QAFTestStep(description="system verifies error message for invalid credentials")
	public void invalidCredentials(){
		
		
		if(getDriver().findElement("login.user.errmsg").isDisplayed())
		{
			Reporter.log("User could not login with invalid credentials", MessageTypes.Pass);
		}
		else
		{
			Reporter.log("Test Case failed", MessageTypes.Fail);
			
		}	
		
	}

	@QAFTestStep(description="customer logs out")
	public void logout(){
		
		getDriver().findElement("login.user.verification.hamburger").click();
		getDriver().findElement("login.user.logout").click();
		Reporter.log("customer logged out");
		
	}
	
	@QAFTestStep(description="user clicks on the filter button")
	public void clickFilter(){

		QAFWebElement filterButton=getDriver().findElement("homepage.filter");
		filterButton.waitForEnabled(1000);
		
		Actions actions = new Actions(getDriver());
		
		actions.moveToElement(filterButton).click().perform();
		
		
	}
	
	@QAFTestStep(description="customer verifies the Report Generation for {0} and {1}")
	public void verifyReport(String loc, String nodeLevel) throws InterruptedException {
		WebElement dropdown1= getDriver().findElement("homepage.user.loc.type");
		Select location=new Select(dropdown1);
		location.selectByVisibleText(loc);
		

		WebElement nodeElement= getDriver().findElement("homepage.user.org.node");
		Select orgNode=new Select(nodeElement);
		orgNode.selectByVisibleText(nodeLevel);;
		JavascriptExecutor js = (JavascriptExecutor)getDriver();
		QAFWebElement submitButton = getDriver().findElement("homepage.modal.submit");
		js.executeScript("arguments[0].click();", submitButton);
		
		
		

	}
	
	@QAFTestStep(description="user clicks on submit without selecting the required field")
	public void modalSubmitErrValidation(){
		
		JavascriptExecutor js = (JavascriptExecutor)getDriver();
		QAFWebElement submitButton = getDriver().findElement("homepage.modal.submit");
		js.executeScript("arguments[0].click();", submitButton);
		Assert.assertTrue(getDriver().findElement("homepage.modal.form.err").isDisplayed(), "Asserting the presence of modal");
		
		
	}
	
	@QAFTestStep(description="user clicks on the cancel button")
	public void clickModalCancel(){
		

		
		JavascriptExecutor js = (JavascriptExecutor)getDriver();
		QAFWebElement cancelButton = getDriver().findElement("homepage.modal.cancel");
		js.executeScript("arguments[0].click();", cancelButton);
	
		Assert.assertTrue(getDriver().findElement("login.user.verification.hamburger").isDisplayed(), "Asserting the presence of modal");
		
		
	}
	

	@QAFTestStep(description="lets give it a try")
	public void givingReport(){
		
		WebElement dropdown1= getDriver().findElement("homepage.user.loc.type");
		Select location=new Select(dropdown1);
		location.selectByIndex(1);
		System.out.println(location.getOptions().get(1));
		
		
	}
	@QAFTestStep(description="customer locks the screen")
	public void lockScreen() throws InterruptedException{
		
		getDriver().findElement("login.user.verification.hamburger").click();
		getDriver().findElement("login.screen.lock").click();
		if(getDriver().findElement("login.screen.lock.welcomeback").isPresent())
		{
		Reporter.log("Screen Locked", MessageTypes.Pass);
		}
		else
		{
			Reporter.log("Screen not Locked", MessageTypes.Fail);
			closeBrowser();
		}
	}
	
	@QAFTestStep(description="customer unlocks the screen using {0} password {1}")
	public void unlockScreen(String validity, String password) {
		
		boolean valid = validity.equals("valid");
		QAFWebElement lockPassword = getDriver().findElement("screen.unlock.password");
		QAFWebElement lockSubmit = getDriver().findElement("screen.unlock.submit");
		lockPassword.sendKeys(password);
		lockSubmit.click();
		if(valid)
		{
			loginVerification();
			closeBrowser();
		}
		else
		{
			QAFWebElement errMsg=getDriver().findElement("screen.unlock.err");
			exWait = new WebDriverWait(getDriver(), 20);
			exWait.until(ExpectedConditions.visibilityOf(errMsg));
			Assert.assertEquals(true, errMsg.isPresent());
			closeBrowser();
		}
		
		
	}
	
	@QAFTestStep(description="user leaves the system idle")
	public void idleSys() throws InterruptedException {
		
		Thread.sleep(600000);
		System.out.println("Slept - more than 5 minutes");
	
	}
	
	@QAFTestStep(description="customer navigates to Profile Page")
	public void navigateToProfilePage(){
		
		getDriver().findElement("login.user.verification.hamburger").click();

		getDriver().findElement("profile.link.hamburger").click();
		
		boolean userDisplay= getDriver().findElement("profile.page.user.name").isDisplayed();
		boolean basicInfo= getDriver().findElement("profile.page.basicinfo").isDisplayed();
		boolean status =getDriver().findElement("profile.page.status").isDisplayed();
		boolean statusResult =getDriver().findElement("profile.page.status.result").isDisplayed();
		
		if(userDisplay && basicInfo && status && statusResult)
		{
			Reporter.log("User Navigated to Profile Page", MessageTypes.Pass);
		}
		else
		{
			Reporter.log("Navigation Failed",MessageTypes.Fail);
			closeBrowser();
		}
	}
	
	@QAFTestStep(description="The captcha should be displayed")
	public void verifyCaptcha(){
		
		if(getDriver().findElement("login.captcha").verifyPresent("Captcha displayed"))
		{
			Reporter.log("Captcha Present", MessageTypes.Pass);
		}
		else
		{
			Reporter.log("Captcha not displayed", MessageTypes.Info);
		}
	}
	@QAFTestStep(description="Test All the links")
	public void testFindLinks() {
				
		List<WebElement> linksList = Utilities.clickableLinks(getDriver());
		for (WebElement link : linksList) {
			String href = link.getAttribute("href");
			try {
				System.out.println("URL " + href + " returned " + Utilities.linkStatus(new URL(href)));
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	
	@QAFTestStep(description="customer closes the browser")
	public void closeBrowser(){
		
		WebDriverTestBase base=new WebDriverTestBase();
		base.tearDown();
			
		
	}
	
	
	

}
