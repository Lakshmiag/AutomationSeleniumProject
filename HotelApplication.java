package apachePoi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HotelApplication {
	WebDriver driver = null;
	WebDriverWait driverWait = null;
	WebElement indate, outdate;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFCell cell;
	
	String selectedvalue = null;
	long noofdays = 0;

    By Location_element=By.xpath("//select[@id='location']");
    By No_of_Rooms = By.xpath("//select[@id='room_nos']");
    By Adults_Per_Room = By.xpath("//select[@id='adult_room']");
    By Check_In_Date = By.xpath("//input[@id='datepick_in']");
    By Check_Out_Date = By.xpath("//input[@id='datepick_out']");

	@BeforeTest
	public void initialization(){
		// To set the path of the Chrome driver.
		System.setProperty("webdriver.chrome.driver","/Users/omangpoddar/Automation/BasicSelenium/driver_exe/chromedriver");
		driver = new ChromeDriver();

		// To launch facebook
		driver.get("http://www.adactin.com/HotelAppBuild2/index.php");
		// To maximize the browser
		driver.manage().window().maximize();
		// implicit wait
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driverWait = new WebDriverWait(driver, 20);
	}

	@Test(priority = 0)
	public void ReadData() throws IOException, Exception{		

		// Import excel sheet.
		File src=new File("/Users/omangpoddar/Desktop/Data.xlsx");		  
		
		// Load the file.
		FileInputStream fis = new FileInputStream(src);
		
		// Load the workbook.
		workbook = new XSSFWorkbook(fis);
		// Load the sheet in which data is stored.
		sheet= workbook.getSheet("Sheet1");

		for(int i=1; i<=sheet.getLastRowNum(); i++){
			// Import data for Email.
			cell = sheet.getRow(i).getCell(0);
			cell.setCellType(CellType.STRING);
			driver.findElement(By.xpath("//input[@id='username']")).clear();
			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(cell.getStringCellValue());

			// Import data for password.
			cell = sheet.getRow(i).getCell(1);
			cell.setCellType(CellType.STRING);
			driver.findElement(By.xpath("//input[@id='password']")).clear();	         
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(cell.getStringCellValue());
			//To write data in the excel
			FileOutputStream fos=new FileOutputStream(src);
			
			// Message to be written in the excel sheet
			String message = "FAIL";
			// Create cell where data needs to be written.
			sheet.getRow(i).createCell(2).setCellValue(message);
			
			if(sheet.getLastRowNum() != 0) {
				String message1="PASS";
				sheet.getRow(5).createCell(2).setCellValue(message1);
				// To click on Login button
				driver.findElement(By.xpath("//input[@id='login']")).click();
		}
		
			// finally write content
			workbook.write(fos);
			//driver.navigate().back();
		}
		
	}
	
	@Test
	public void verifyPageTitle() {
		//String expectedTitle = "AdactIn.com - Hotel Reservation System";
		String actualTitle = driver.getTitle();
		Assert.assertTrue(actualTitle.contains("AdactIn.com"));
		System.out.println("Test completed - Success");
	}	
		
	@Test(priority = 1)
	public void Select_DropDowns() throws InterruptedException 
	{
		if(Location_element == null) {
			System.out.println("Please Select a Location");
		}
		if(No_of_Rooms == null) {
			System.out.println("Please Select No. Of. Rooms");
		}
		if(Adults_Per_Room == null) {
			System.out.println("Please Select No. of. Adults per room");
		}
		if(Check_In_Date == null) {
			System.out.println("Please Select Check In Date");
		}
		if(Check_Out_Date == null) {
			System.out.println("Please Select Check Out Date");
		}
		
		//For Location Field
			WebElement Location_DropDown_Element = null;
			Location_DropDown_Element = driverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(Location_element)));
		    Select Location_DropDown = new Select(Location_DropDown_Element);
		    Location_DropDown.selectByIndex(2);
		    List<WebElement> Location_DropDownList = Location_DropDown.getOptions();
		    for(WebElement s : Location_DropDownList)
		    	  		System.out.println(s.getText());
		
		 //For Rooms Field
			WebElement Rooms = null;
			Rooms = driver.findElement(No_of_Rooms);
			Select NumRooms = new Select(Rooms);
			NumRooms.selectByIndex(2);
			selectedvalue = NumRooms.getFirstSelectedOption().getText();
			System.out.println("Selected value is"+selectedvalue);
			List<WebElement> nr = NumRooms.getOptions();
		    for(WebElement s1 : nr)
		    	  		System.out.println(s1.getText());
		    
		//For Adults per room Field
		    WebElement Adult_Room = null;
		    Adult_Room = driver.findElement(Adults_Per_Room);
		    Select PerRoom = new Select(Adult_Room);
		    PerRoom.selectByIndex(1);
		    List<WebElement> adpr = PerRoom.getOptions();
		    for(WebElement s2 : adpr)
		    	  		System.out.println(s2.getText());
		    
		    //indate
		    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		    Date dt = new Date();
		    Calendar cl = Calendar.getInstance();
		    cl.setTime(dt);
		    Date dt1=cl.getTime();
		    String str = df.format(dt1);
		        System.out.println("the date today is " + str);
		    
		    //outdate
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date tomorrow = calendar.getTime();
			String str1 = dateFormat.format(tomorrow);
			System.out.println("the date tomorrow is " + str1);
			noofdays = ((calendar.getTime().getTime() - cl.getTime().getTime()) / (1000 * 60 * 60 * 24));
			System.out.println("Days="+ noofdays);
			    
		// For Dates Field
		    indate = driver.findElement(By.xpath("//input[@id='datepick_in']"));
		    indate.clear();
		    indate.sendKeys(str);
		    
		    outdate = driver.findElement(By.xpath("//input[@id='datepick_out']"));
		    outdate.clear();
		    outdate.sendKeys(str1);
		
		//Fields should not be null and are mandatory fields
		    if(Location_element != null && No_of_Rooms != null && Adults_Per_Room != null && Check_In_Date != null && Check_Out_Date != null) {
			driver.findElement(By.id("Submit")).click();
		}
		    
		CalTotalPrice();
	}
	
	public String CalTotalPrice() {
		
		String ppern = null;

		WebElement pricepernight = driver.findElement(By.xpath("//input[@id='price_night_1']"));
		ppern = pricepernight.getAttribute("value");
		System.out.println("Price Per Night ="+ppern);
		
		String t2=null;
		
		double value = Double.parseDouble(selectedvalue.replaceAll("[^0-9\\.]+", ""));
		double price = Double.parseDouble(ppern.replaceAll("[^0-9\\.]+", ""));
		
		double result = Double.parseDouble(Long.toString(noofdays)) * value * price;
		t2 = Double.toString(result); 

	
		System.out.println("Total Price =" +t2);
		return t2;					
}

	public void main(String args[]) throws InterruptedException
	{
		Select_DropDowns();
	}
	
	@AfterTest
	public void ExitApp(){
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
	}

}

