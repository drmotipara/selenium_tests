package mytestpack;


import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BuyAndVerifyProduct {
	
	private static WebDriver driver;
	private String baseUrl;
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://store.demoqa.com/";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static void login() throws InterruptedException {
		 
		driver.findElement(By.id("log")).sendKeys("drmotipara");
		driver.findElement(By.id("pwd")).sendKeys("juckB0x!43");
		
		WebElement login = driver.findElement(By.id("login"));
		login.click();
		Thread.sleep(5000);		
	}
	
	public static void updateAccount() throws InterruptedException {
		
		driver.findElement(By.linkText("Your Details")).click();
		Thread.sleep(1000);

		WebElement lastName = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[1]/article/div/div/form/table[1]/tbody/tr[3]/td[2]/input"));		                                                 
		String value = lastName.getAttribute("value");
		System.out.println(value);
		if (value.equals("R. Motipara")) {
			lastName.clear();
			lastName.sendKeys("Motipara");
		} else {
			Assert.assertNotEquals("expected last name is not found.", lastName);
		}		
		 
		Thread.sleep(1000);
	}
	public static void verifyLastName() throws InterruptedException {
		
		WebElement lastName = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[1]/article/div/div/form/table[1]/tbody/tr[3]/td[2]/input"));		                                
		String value = lastName.getAttribute("value");
		if (value.equalsIgnoreCase("Motipara")){
			System.out.println("Last Name updated successfully in my Profile.");
			lastName.clear();
			lastName.sendKeys("R. Motipara");
			Thread.sleep(1000);
			
			driver.findElement(By.name("submit")).click();
			Thread.sleep(1000);
			
		} else {
			System.out.println("Last Name NOT updated successfully in my Profile.");
		}
	}
	@Test
	public void purchaseOrder() throws Exception {
		driver.get(baseUrl);
		
		WebElement mnEle;
		WebElement sbEle;
		mnEle = driver.findElement(By.linkText("Product Category"));
		sbEle = driver.findElement(By.id("menu-item-37"));
		
		Actions builder = new Actions(driver);
		builder.moveToElement(mnEle).perform();
		Thread.sleep(1000L);
		sbEle.click();		
		Thread.sleep(5000);
		
		
		WebElement product1;
		product1 = driver.findElement(By.partialLinkText("Apple iPhone 4S 16GB SIM-Free "));		
		Actions builder2 = new Actions(driver);
		builder2.moveToElement(product1).click().build().perform();	
		Thread.sleep(5000);
		
		WebElement addToCart;
		addToCart = driver.findElement(By.className("wpsc_buy_button"));
		addToCart.click();		
		Thread.sleep(5000);
	
		WebElement element = null;		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		element = wait.until(ExpectedConditions.elementToBeClickable(By.className("go_to_checkout")));
		element.click();		
		Thread.sleep(5000);
		
		WebElement table_element = driver.findElement(By.className("checkout_cart"));
		List<WebElement> tr_collection =  table_element.findElements(By.tagName("tr"));
		
		System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
	    int row_num,col_num;
	    row_num=1;
	    for(WebElement trElement : tr_collection)
	    {
	        List<WebElement> td_collection=trElement.findElements(By.tagName("td"));
	        System.out.println("NUMBER OF COLUMNS="+td_collection.size());
	        col_num=1;
	        for(WebElement tdElement : td_collection)
	        {
	            System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
	            col_num++;	             
	         }
	         row_num++;
	            
	            
	     } 
	    
	    WebElement yourPrice = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/article/div/div[2]/div[1]/table/tbody/tr[2]/td[4]/span"));
	    WebElement totalPrice = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/article/div/div[2]/div[1]/table/tbody/tr[2]/td[5]/span/span"));
	    
	    if (yourPrice.getText().equals(totalPrice.getText())) {
	    	System.out.println("price is matched with total price.");
          	
        }
        else {
          	System.out.println("price is not matched with total price.");
	    }
	        
	     WebElement cont = driver.findElement(By.className("step2"));
         cont.click();
         Thread.sleep(5000);
           
	     Select drpCountry = new Select(driver.findElement(By.name("country")));
	     drpCountry.selectByVisibleText("USA");
	        
	     WebElement calculate = driver.findElement(By.name("wpsc_submit_zipcode"));
	     calculate.click();
	     Thread.sleep(5000);       
	        
	        
	     login(); 
	        
	     WebElement isChecked = driver.findElement(By.name("shippingSameBilling"));
	     isChecked.click();
	     Thread.sleep(5000);
	        
	     WebElement purchase = driver.findElement(By.className("input-button-buy"));
	     purchase.click();
	     Thread.sleep(5000);
	
	}
	
	@Test
	public void verifyAccount() throws Exception {
		
		driver.get(baseUrl);
		
		WebElement account = driver.findElement(By.className("account_icon"));
		account.click();
		Thread.sleep(5000);
		
		login();
		
		updateAccount();
		
		driver.findElement(By.name("submit")).click();
		Thread.sleep(1000);
		
		WebElement logout = driver.findElement(By.linkText("(Logout)"));
		logout.click();
		Thread.sleep(5000);
		
		login();
		
		driver.findElement(By.linkText("Your Details")).click();
		Thread.sleep(1000);
		
		verifyLastName();		
		
	}
	
	@Test
	public void removeCart() throws Exception {
		driver.get(baseUrl);
		
		WebElement mnEle;
		WebElement sbEle;
		mnEle = driver.findElement(By.linkText("Product Category"));
		sbEle = driver.findElement(By.id("menu-item-37"));
		
		Actions builder = new Actions(driver);
		builder.moveToElement(mnEle).perform();
		Thread.sleep(1000L);
		sbEle.click();		
		Thread.sleep(5000);		
		
		WebElement product1;
		product1 = driver.findElement(By.partialLinkText("Apple iPhone 4S 16GB SIM-Free "));		
		Actions builder2 = new Actions(driver);
		builder2.moveToElement(product1).click().build().perform();	
		Thread.sleep(5000);
		
		WebElement addToCart;
		addToCart = driver.findElement(By.className("wpsc_buy_button"));
		addToCart.click();		
		Thread.sleep(5000);
	
		WebElement element = null;		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		element = wait.until(ExpectedConditions.elementToBeClickable(By.className("go_to_checkout")));
		element.click();		
		Thread.sleep(5000);
		 
	    WebElement remove = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/article/div/div[2]/div[1]/table/tbody/tr[2]/td[6]/form/input[4]"));
	    remove.click();
	    Thread.sleep(5000);
	        
	    WebElement cartMsg = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/article/div"));
	    String text = cartMsg.getText();
	    System.out.println(text);
	        
	        
	    if (text.equals("Oops, there is nothing in your cart.")){
	      	System.out.println("cart is empty.");
	    } else {
	       	System.out.println("remove message not found.");
	       	//Assert.assertNotNull(text);	        	
	    }
		
	}
	
	
	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	
	public  void main(String[] args) throws InterruptedException {
		
		
	}

}
