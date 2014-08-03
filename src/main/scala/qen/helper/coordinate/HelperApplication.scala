package qen.helper.coordinate

import java.io.File

import com.typesafe.config._


object HelperApplication extends App {
  override def main(args: Array[String]): Unit = {
    val configurationFilePath : String = args(0)
    val absoluteConfig: Config = ConfigFactory.parseFile(new File(configurationFilePath))
    val dropBoxSyncronizer = new DropBoxSyncronizer(absoluteConfig)
    val engineAccessor : EngineAccessor = new EngineAccessor(
        absoluteConfig.getString("en.game.domain"),
        absoluteConfig.getString("en.game.id"),
        absoluteConfig.getString("en.user.login"),
        absoluteConfig.getString("en.user.password"));
    engineAccessor.enterCurrentGame();
    var currentLevel = engineAccessor.getCurrentLevelNumber;
    val coordinateParser = new CoordinateParser
    val kmlBuilder: KMLBuilder = new KMLBuilder
    val kmlFileName = absoluteConfig.getString("kml.filename")
    val coordinateList = coordinateParser.getCoordinates(engineAccessor.getCurrentLevelContent)
    dropBoxSyncronizer.refreshFile(kmlBuilder.buildKML(kmlFileName,currentLevel,coordinateList))
    engineAccessor.saveCurrentLevelContent
  }
}
