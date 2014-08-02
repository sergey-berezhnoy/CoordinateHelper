// set the name of the project
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
"com.typesafe" % "config" % "1.2.1"
)

resolvers += "Java.net Maven 2 Repository" at "http://download.java.net/maven/2"
