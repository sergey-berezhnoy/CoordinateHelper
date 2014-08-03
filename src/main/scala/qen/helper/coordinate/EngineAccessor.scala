package qen.helper.coordinate

import org.openqa.selenium._
import org.openqa.selenium.htmlunit._
import scala.util.matching._

class EngineAccessor (val domain: String, val gameId: String, val username: String, val password: String) {
	var levelContent: String = "";
	val driver = new HtmlUnitDriver;
	val maxLevelNumberRegex = new Regex("(\\d{2}):");
	def enterCurrentGame(): Unit = {
	  println(s"Attempting login in on domain $domain with username $username")
      driver.get(s"http://$domain/Login.aspx?return=/gameengines/encounter/play/$gameId");
      println(driver.getCurrentUrl());
      val loginField = driver.findElement(By.id("txtLogin"));
      val passwordField = driver.findElement(By.id("txtPassword"));
      loginField.sendKeys(username);
      Thread.sleep(1000)
      passwordField.sendKeys(password);
      Thread.sleep(1000)
      val loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
      loginButton.click();
      println("Login attempted. Must be entering the game.")
      println("Current URL: "+driver.getCurrentUrl());
      val gameUrl = s"http://$domain/gameengines/encounter/play/$gameId"
      if(gameUrl.compareTo(driver.getCurrentUrl())!=0){
        println("Something went wrong: you were not returned to the correct game, check game id or credentials.")
      } else {
        println("You have successfully logged in and entered the game")
        println("Game title:"+driver.getTitle());
      }
	}
	def getCurrentLevelContent(): String = {
	  driver.get(s"http://$domain/gameengines/encounter/play/$gameId");
      driver.findElementByXPath("//div[@class='content']").getText()
	}
	def saveCurrentLevelContent(): Unit = {
	  this.levelContent  = this.getCurrentLevelContent
	}
	def isCurrentLevelContentChanged(): Boolean = {
	  this.levelContent.compareTo(this.getCurrentLevelContent)!=0
	}
	def refreshCurrentLevel(): Unit = {
	  val randomValue : Double = Math.random()
	  driver.get(s"http://$domain/gameengines/encounter/play/$gameId/?rnd=$randomValue");
	}
	def getCurrentLevelNumber(): Int = {
	  driver.findElementByXPath("//div[@class='content']/h2/span").getText().toInt
	}
	
	def getMaximalLevelNumber(): Int = {
	  maxLevelNumberRegex.findFirstIn(driver.findElementByXPath("//div[@class='content']/h2").getText()).get.replace(":", "").toInt
	}
}