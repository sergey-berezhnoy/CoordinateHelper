package qen.helper.coordinate
import org.openqa.selenium._
import org.openqa.selenium.htmlunit._
import java.io.File
import de.micromata.opengis.kml.v_2_2_0.Kml


object HelperApplication extends App {

//    val driver = new HtmlUnitDriver
//    driver.get("http://quest.ua/Login.aspx?return=/Default.aspx");
//    println(driver.getCurrentUrl());
//    val loginField = driver.findElement(By.id("txtLogin"));
//    loginField.sendKeys("COFEINE");
//    val passwordField = driver.findElement(By.id("txtPassword"));
//    val loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
//    loginButton.click();
//    println(driver.getCurrentUrl());
//    val enterGameButtom = driver.findElement(By.xpath("//a[@class='glassbutton ']"));
//    enterGameButtom.click();
//    println(driver.getCurrentUrl());
    
    val kml: Kml = new Kml();
    kml.createAndSetPlacemark().withName("London, UK").withOpen(true).createAndSetPoint().addToCoordinates(-0.126236, 51.500152);
    kml.marshal(new File("HelloKml.kml"));
    
}
