package qen.helper.coordinate
import java.util.ArrayList
import scala.util.matching._
import scala.util.matching.Regex.MatchIterator
import scala.collection.mutable.ListBuffer
class CoordinateParser (val levelText: String){
	def getCoordinates() :List[Tuple2[Double, Double]] = {
	  val result: ListBuffer[Tuple2[Double, Double]] = ListBuffer[Tuple2[Double, Double]]()
	  val regularExpression : Regex = new Regex("(\\d{2}[,.]\\d{3,5})")
	  val matchIterator: MatchIterator = regularExpression.findAllIn(levelText)
	  val coordinatePairList: List[Seq[Double]] = matchIterator.map(x=>x.toDouble).grouped(2).withPartial(false).toList
	  //.map(x=>(x(0),x(1)))
	  
	  for(coordinatePair <- coordinatePairList) {
	    result.append((coordinatePair(0),coordinatePair(1)))
	  }
	  return result.toList;
	}
}