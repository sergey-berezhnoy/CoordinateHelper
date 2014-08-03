package qen.helper.coordinate

import java.io.File
import de.micromata.opengis.kml.v_2_2_0.Kml
import scala.io.Source
import com.typesafe.config._
import de.micromata.opengis.kml.v_2_2_0.KmlFactory
import de.micromata.opengis.kml.v_2_2_0.Document


object HelperApplication extends App {
  override def main(args: Array[String]): Unit = {
    val configurationFilePath : String = args(0)
    val absoluteConfig: Config = ConfigFactory.parseFile(new File(configurationFilePath))
    val engineAccessor : EngineAccessor = new EngineAccessor(
        absoluteConfig.getString("en.game.domain"),
        absoluteConfig.getString("en.game.id"),
        absoluteConfig.getString("en.user.login"),
        absoluteConfig.getString("en.user.password"));
    engineAccessor.enterCurrentGame();
    val maximalLevel = engineAccessor.getMaximalLevelNumber;
    var currentLevel = engineAccessor.getCurrentLevelNumber;
    val coordinateParser = new CoordinateParser
    val kmlBuilder: KMLBuilder = new KMLBuilder
    while (currentLevel<maximalLevel){
      engineAccessor.refreshCurrentLevel
      if(engineAccessor.isCurrentLevelContentChanged){
    	  val coordinateList = coordinateParser.getCoordinates(engineAccessor.getCurrentLevelContent)
    	  kmlBuilder.buildKML(coordinateList)
    	  engineAccessor.saveCurrentLevelContent
      }
      currentLevel = engineAccessor.getCurrentLevelNumber
    }
  }
}
