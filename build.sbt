val ScalatraVersion = "2.6.3"

name := "sorders"

version := "1.0"

scalaVersion := "2.12.4"

resolvers += Classpaths.typesafeReleases + "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.json4s" %% "json4s-jackson" % "3.5.2",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
  "mysql" % "mysql-connector-java" % "5.1.42",
  "com.typesafe.akka" %% "akka-actor" % "2.5.3",
  "net.databinder.dispatch" %% "dispatch-core" % "0.13.1",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.8.v20171121" % "container",
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-specs2" % "2.6.5" % "test",
  "org.scalatest" %% "scalatest" % "3.2.15" % "test"
)

enablePlugins(ScalatraPlugin)
