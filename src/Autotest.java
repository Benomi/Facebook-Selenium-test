import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
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
    private static List<WebElement> ffriendList;
    private static WebDriver CWD;
    private static JavascriptExecutor JSE;
    private static String profName;
    private static ArrayList<String> finalFriends = new ArrayList<String> (Arrays.asList("List of facebook Friends"));
    private static ArrayList<String> finalFFriends = new ArrayList<String> (Arrays.asList("List of facebook Friends"));
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
        CWD = WDSetup();
        JSE = JSESetup(CWD);
        facebook(email, pass, CWD, JSE);
    }
    
    public static WebDriver WDSetup(){
        System.setProperty("webdriver.chrome.driver", "chromedriver");       
        //ChromeOptions options = new ChromeOptions();
        //options.addExtensions(new File("/home/benomi/NetBeansProjects/Autotest/UltraSurf-Security,-Privacy-&-Unblock-VPN_v1.3.0.crx"));
        //DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        //WebDriver driver = new ChromeDriver(capabilities);
        WebDriver driver = new ChromeDriver();

        
        return driver;
    }
    
    public static JavascriptExecutor JSESetup(WebDriver driver){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        
        return js;
    }
    
    public static void facebook(String email, String pass, WebDriver driver, JavascriptExecutor js){        
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
            Thread.sleep(2000);
        } catch (Exception e) {
            
        }
        
        String fLinkContent = fLink.getText().toString();
        int nFriends = Integer.parseInt(fLinkContent.substring(7).replaceAll(",", ""));
        String uName = driver.findElement(By.id("fb-timeline-cover-name")).getText().toString();
        
        System.out.println("You have " + nFriends + " facebook friends.");
        
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            
        }
        
        int current = 0;
        
        for (int i = 0; i < nFriends/18; i++) {
            js.executeScript("scroll(0, 10000000000000);");
            try {
                Thread.sleep(1000);
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
            writeExcel(finalFriends, uName);
        } catch (Exception e) {
            
        }
        
        for (int x = 0; x < finalFriends.size(); x++){
            friendsOfFriends(finalFriends, driver, js);
        }       
        
        driver.close();
    }
    
    public static void writeExcel(List names, String uName) throws IOException{
        HSSFWorkbook excel = new HSSFWorkbook();
        HSSFSheet list = excel.createSheet("Facebook Friends");
        
        for (int i=0; i < names.size(); i++) {
            Row r = list.createRow(i);
            r.createCell(0).setCellValue(names.get(i).toString());
         }
         
        try (FileOutputStream outputStream = new FileOutputStream("Friends of " + uName + ".xlsx")) {
            excel.write(outputStream);
        }
    }
    
    public static void friendsOfFriends(List names, WebDriver driver, JavascriptExecutor js){
        WebElement sBar = driver.findElement(By.name("q"));
        for (int x = 2; x < names.size(); x++){            
            String name = (String) names.get(x);
            sBar.sendKeys(name);
            sBar.sendKeys(Keys.ENTER);
            
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            }

            
            WebElement fName = driver.findElement(By.xpath("//a[contains(@class,'1ii5 _2yez')]"));
            fName.click();

            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            }

            WebElement fLink = driver.findElement(By.xpath("//*[@data-tab-key=\"friends\"]"));
            fLink.click();


            try {
                Thread.sleep(8000);
            } catch (Exception e) {

            }
            
            String fLinkContent = fLink.getText().toString();
            //int nFriends = Integer.parseInt(fLinkContent.substring(7).replaceAll(",", ""));
            String uName = driver.findElement(By.id("fb-timeline-cover-name")).getText().toString();
            //System.out.println(uName + nFriends + " facebook friends.");
        
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }

            int current = 0;

            for (int i = 0; i <= 100; i++) {
                js.executeScript("scroll(0, 10000000000000);");
                try {
                    Thread.sleep(2500);
                } catch (Exception e) {

                }

                ffriendList = driver.findElements(By.xpath("//div[@class='fsl fwb fcb']/a"));
                current = ffriendList.size();
                System.out.println("Found " + current + "Friends!");
            }

            try {
                Thread.sleep(8000);
            } catch (Exception e) {

            }
            
            System.out.println("Friends of " + profName + ".");
            for (int j = 0; j < ffriendList.size(); j++)  {
                System.out.println(ffriendList.get(j).getText().toString());            
                finalFFriends.add(ffriendList.get(j).getText().toString());
                
                try {
                    writeExcel(finalFFriends, uName);
                } catch (Exception e) {

                }
            }       

            //driver.close();
        }
        
    }
}
