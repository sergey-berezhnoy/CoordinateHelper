// set the name of the project
import AssemblyKeys._

assemblySettings

jarName in assembly := "CoordinateHelper.jar"

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("javax","xml", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf" => MergeStrategy.concat
    case "unwanted.txt"     => MergeStrategy.discard
    case x => old(x)
  }
}

mainClass in assembly := Some("qen.helper.coordinate.HelperApplication")

name := "Coordinate Helper"

version := "1.0"

organization := "qen.helper"

// set the Scala version used for the project
scalaVersion := "2.11.0"

EclipseKeys.withSource := true

libraryDependencies ++= Seq( 
"org.seleniumhq.selenium" % "selenium-java" % "2.42.2" % "compile",
"org.scalatest" % "scalatest_2.11" % "2.2.0" % "test",
"de.micromata.jak" % "JavaAPIforKml" % "2.2.0",
"com.typesafe" % "config" % "1.2.1",
"com.dropbox.core" % "dropbox-core-sdk" % "[1.7,1.8)"
)

resolvers += "Java.net Maven 2 Repository" at "http://download.java.net/maven/2"
