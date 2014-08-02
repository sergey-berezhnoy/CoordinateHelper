package qen.helper.coordinate

import de.micromata.opengis.kml.v_2_2_0.Kml
import de.micromata.opengis.kml.v_2_2_0.KmlFactory
import de.micromata.opengis.kml.v_2_2_0.Document
import java.io.File

class KMLBuilder(val coordinateList: List[Tuple2[Double, Double]]) {
  def buildKML(): Boolean = {
    val kml: Kml = KmlFactory.createKml();
    val document: Document = kml.createAndSetDocument().withName("just a test");
    coordinateList.foreach(x => {
      document.createAndAddPlacemark().withName("just a layer").withOpen(true).createAndSetPoint().addToCoordinates(x._1, x._2)
    })
    kml.marshal(new File("HelloKml.kml"));
  }
}