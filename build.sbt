name := """play-swagger-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice

libraryDependencies += "play-swagger-api" %% "play-swagger-api" % "0.1.0-SNAPSHOT"

libraryDependencies += "play-swagger-core" %% "play-swagger-core" % "0.1.0-SNAPSHOT"
