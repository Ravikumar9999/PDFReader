package pdfReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PDFReaderTest01 {

	WebDriver driver;
	String url = "C:\\Users\\RAVI KUMAR\\Downloads\\google_infrastructure_whitepaper_fa.pdf";
	
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium Drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		driver.get(url);
	}
	
	
	@Test
	public void pdfReaderTest() throws IOException {
		
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);
		
		URL pdfUrl = new URL(currentUrl);
		InputStream ip = pdfUrl.openStream();
		BufferedInputStream bf = new BufferedInputStream(ip);
		PDDocument pdDocument = PDDocument.load(bf);
		
		//page count
		int pageCount = pdDocument.getNumberOfPages();
		System.out.println("PDF no of pages : " + pageCount);
		Assert.assertEquals(pageCount, 14);
		
		System.out.println("====================pdf content========================");
		
		//full pdf page content/text:
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String pdfText = pdfStripper.getText(pdDocument);
		System.out.println(pdfText);
		
		Assert.assertTrue(pdfText.contains("Introduction"));
		Assert.assertTrue(pdfText.contains("Secure boot stack and machine identity"));
		Assert.assertTrue(pdfText.contains("Access management of end-user data in Google Workspace"));
		
		
		//set the page number and get the text:
//		pdfStripper.setStartPage(4);
//		pdfStripper.setEndPage(4);
//		String pdfText = pdfStripper.getText(pdDocument);
//		System.out.println(pdfText);
//		Assert.assertTrue(pdfText.contains("Hardware design and provenance"));
		
	}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
