import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(
    name := "Scrabble",
    libraryDependencies += "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0" % "test" ,
    libraryDependencies += "org.scalatestplus" %% "mockito-5-10" % "3.2.18.0" % "test",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test",
  )