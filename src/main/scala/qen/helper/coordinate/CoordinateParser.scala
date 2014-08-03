package qen.helper.coordinate
import java.util.ArrayList
import scala.util.matching._
import scala.util.matching.Regex.MatchIterator
import scala.collection.mutable.ListBuffer
class CoordinateParser (){
	val coordinateRegEx = new Regex("(\\d{2}[,.]\\d{3,5})")
	def getCoordinates(levelText: String): List[Tuple2[Double, Double]] = {
	  return coordinateRegEx
	  .findAllIn(levelText)
	  .map(x=>x.toDouble)
	  .grouped(2).withPartial(false).toList
	  .map(x=>(x(0),x(1)))
	}
}