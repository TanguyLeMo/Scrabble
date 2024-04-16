ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(
    name := "Scrabble"
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"
  )
