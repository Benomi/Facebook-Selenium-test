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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
    private static List<WebElement> friendList;
    private static String profName;
    private static ArrayList<String> finalFriends = new ArrayList<String> (Arrays.asList("List of facebook Friends"));
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
            Thread.sleep(8000);
        } catch (Exception e) {
            
        }
        WebElement pLink = driver.findElement(By.xpath("//*[@data-testid=\"blue_bar_profile_link\"]"));
        pLink.click();
        
        try {
            Thread.sleep(8000);
        } catch (Exception e) {
            
        }
       
        WebElement fLink = driver.findElement(By.xpath("//*[@data-tab-key=\"friends\"]"));
        fLink.click();
        
        
        try {
            Thread.sleep(8000);
        } catch (Exception e) {
            
        }
        
        int nFriends = Integer.parseInt(driver.findElement(By.xpath("//*[@id=\"u_0_q\"]/div/a[3]/span[1]")).getText());
        profName = driver.findElement(By.id("fb-timeline-cover-name")).getText().toString();
        
        System.out.println("You have " + nFriends + " facebook friends.");
        
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            
        }
        
        int current = 0;
        
        for (int i = 0; i < nFriends/18; i++) {
            js.executeScript("scroll(0, 10000000000000);");
            try {
                Thread.sleep(2500);
            } catch (Exception e) {

            }
            
            friendList = driver.findElements(By.xpath("//div[@class='fsl fwb fcb']/a"));
            current = friendList.size();
            System.out.println("Found " + current + "Friends!");
        }
        
        try {
            Thread.sleep(8000);
        } catch (Exception e) {
            
        }
        
        for (int j = 0; j < friendList.size(); j++)  {
            System.out.println(friendList.get(j).getText().toString());            
            finalFriends.add(friendList.get(j).getText().toString());
        }
        
        try {
            writeExcel(finalFriends);
        } catch (Exception e) {
            
        }       
        
        driver.close();
    }
    
    public static void writeExcel(List names) throws IOException{
        HSSFWorkbook excel = new HSSFWorkbook();
        HSSFSheet list = excel.createSheet("Facebook Friends");
        
        for (int i=0; i < names.size(); i++) {
            Row r = list.createRow(i);
            r.createCell(0).setCellValue(names.get(i).toString());
         }
         
        try (FileOutputStream outputStream = new FileOutputStream("Friends of " + profName + ".xlsx")) {
            excel.write(outputStream);
        }
    }
}
