package qen.helper.coordinate

import de.micromata.opengis.kml.v_2_2_0.Kml
import de.micromata.opengis.kml.v_2_2_0.KmlFactory
import de.micromata.opengis.kml.v_2_2_0.Document
import java.io.File

class KMLBuilder() {
  def buildKML(fileName: String,levelNumber: Int,coordinateList: List[Tuple2[Double, Double]]): File = {
    val kml: Kml = KmlFactory.createKml();
    val document: Document = kml.createAndSetDocument().withName(s"Level $levelNumber");
    coordinateList.foreach(x => {
      document.createAndAddPlacemark().withName("point").withOpen(true).createAndSetPoint().addToCoordinates(x._1, x._2)
    })
    val kmlFile = new File(s"$fileName.kml")
    kml.marshal(kmlFile);
    return kmlFile;
  }
}