import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package autotest;

/**
 *
 * @author benomi
 */
public class Autotest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter email: ");
        String  email = reader.next();
        System.out.println("Enter password: ");
        String pass = reader.next();
        facebook(email, pass);
    }
    
    public static void facebook(String email, String pass){
        System.setProperty("webdriver.chrome.driver", "chromedriver");       
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("/home/benomi/NetBeansProjects/Autotest/UltraSurf-Security,-Privacy-&-Unblock-VPN_v1.3.0.crx"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        
        WebDriver driver = new ChromeDriver(capabilities);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
       
        
        driver.navigate().to("https://www.facebook.com");
        
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            
        }
        
        WebElement username = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("pass"));
        
        
        username.sendKeys(email);
        password.sendKeys(pass);
        password.sendKeys(Keys.ENTER);
        
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            
        }
        
        driver.navigate().to("https://www.facebook.com/" + email);
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            
        }
       
        WebElement fLink = driver.findElement(By.xpath("//*[@data-tab-key=\"friends\"]"));
        fLink.click();
        
        int nFriends = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"u_0_q\"]/div/a[3]/span[1]")).getText());
        
        System.out.println("You have " + nFriends + " facebook friends.");
        
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            
        }
        
        List<WebElement> friendList;
        int current;
        
        do {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                
            }
            
            friendList = driver.findElements(By.xpath("//*[@class=\"fsl fwb fcb\"]"));
            current = friendList.size();
            
            System.out.println(current);
            
            if (!driver.findElement(By.xpath("//*[@class=\"uiHeaderTitle\"]")).isDisplayed())
            {
                js.executeScript("scroll(0, 250);");  
            }
        }
        while (current < nFriends);
        
        //js.executeScript("scroll(0, 250);");        
        
        
        try {
            Thread.sleep(8000);
        } catch (Exception e) {
            
        }
        
        driver.close();
    }
}