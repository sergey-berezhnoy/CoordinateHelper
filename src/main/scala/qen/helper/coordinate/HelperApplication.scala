package qen.helper.coordinate

import java.io.File

import com.typesafe.config._


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
    val dropBoxSyncronizer = new DropBoxSyncronizer(absoluteConfig)
    val kmlFileName = absoluteConfig.getString("kml.filename")
    dropBoxSyncronizer.refreshFile(kmlBuilder.buildKML(kmlFileName,0, List((42.98423,98.98234))))
    while (currentLevel<maximalLevel){
      engineAccessor.refreshCurrentLevel
      if(engineAccessor.isCurrentLevelContentChanged){
    	  val coordinateList = coordinateParser.getCoordinates(engineAccessor.getCurrentLevelContent)
    	   dropBoxSyncronizer.refreshFile(kmlBuilder.buildKML(kmlFileName,currentLevel,coordinateList))
    	  engineAccessor.saveCurrentLevelContent
      }
      currentLevel = engineAccessor.getCurrentLevelNumber
    }
  }
}
