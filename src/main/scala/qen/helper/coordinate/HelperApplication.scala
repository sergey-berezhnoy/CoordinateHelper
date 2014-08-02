package qen.helper.coordinate

import java.io.File
import de.micromata.opengis.kml.v_2_2_0.Kml
import scala.io.Source
import com.typesafe.config._


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
//    val kml: Kml = new Kml();
//    kml.createAndSetPlacemark().withName("London, UK").withOpen(true).createAndSetPoint().addToCoordinates(-0.126236, 51.500152);
//    kml.marshal(new File("HelloKml.kml"));
  override def main(args: Array[String]): Unit = {
    val configurationFilePath : String = args(0)
    val absoluteConfig: Config = ConfigFactory.parseFile(new File(configurationFilePath))
    println(absoluteConfig.getString("kml"))
    val engineAccessor : EngineAccessor = new EngineAccessor(
        absoluteConfig.getString("en.game.domain"),
        absoluteConfig.getString("en.game.id"),
        absoluteConfig.getString("en.user.login"),
        absoluteConfig.getString("en.user.password"));
    val levelText : String = engineAccessor.enterCurrentGame();
    val coordinateParser: CoordinateParser = new CoordinateParser(levelText)
    val coordinateList = coordinateParser.getCoordinates()
    println(coordinateList)
    val kml: Kml = new Kml();
    val coordinateTuple : Tuple2[Double, Double] = coordinateList(0)
    kml.createAndSetPlacemark().withName("London, UK").withOpen(true).createAndSetPoint().addToCoordinates(coordinateTuple._1 ,coordinateTuple._2);
    kml.marshal(new File("HelloKml.kml"));
  }
}
