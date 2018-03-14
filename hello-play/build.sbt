name := """hello-play"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies += guice
// https://mvnrepository.com/artifact/org.mongodb.morphia/morphia
libraryDependencies += "org.mongodb.morphia" % "morphia" % "1.3.2"
