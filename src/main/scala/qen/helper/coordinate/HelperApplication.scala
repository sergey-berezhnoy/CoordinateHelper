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
    println(absoluteConfig.getString("kml"))
    val engineAccessor : EngineAccessor = new EngineAccessor(
        absoluteConfig.getString("en.game.domain"),
        absoluteConfig.getString("en.game.id"),
        absoluteConfig.getString("en.user.login"),
        absoluteConfig.getString("en.user.password"));
    val levelText : String = engineAccessor.enterCurrentGame();
    val coordinateList = new CoordinateParser(levelText).getCoordinates
    new KMLBuilder(coordinateList).buildKML;
  }
}
