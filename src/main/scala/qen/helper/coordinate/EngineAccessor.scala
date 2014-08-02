package qen.helper.coordinate

import org.openqa.selenium._
import org.openqa.selenium.htmlunit._

class EngineAccessor (val domain: String, val gameId: String, val username: String, val password: String) {
	def enterCurrentGame(): String = {
	  val driver = new HtmlUnitDriver
      driver.get(s"http://$domain/Login.aspx?return=/Default.aspx");
      println(driver.getCurrentUrl());
      val loginField = driver.findElement(By.id("txtLogin"));
      loginField.sendKeys(username);
      val passwordField = driver.findElement(By.id("txtPassword"));
      passwordField.sendKeys(password);
      val loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
      loginButton.click();
      println(driver.getCurrentUrl());
      driver.get(s"http://$domain/gameengines/encounter/play/$gameId");
      println(driver.getCurrentUrl());
      println(driver.getTitle());
      val levelContent = driver.findElementByXPath("//div[@class='content']")
      levelContent.getText()
	}
}