name := """play-swagger-demo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice

libraryDependencies += "play-swagger-api" %% "play-swagger-api" % "0.1.0-SNAPSHOT" // 引入之前的 play-swagger-api 工具包
