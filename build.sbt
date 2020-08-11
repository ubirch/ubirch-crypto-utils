
/*
 * BASIC INFORMATION
 ********************************************************/

name := "ubirch-crypto-utils"
version := "0.5.3"
description := "ubirch util with crypto related code"
organization := "com.ubirch.util"
homepage := Some(url("http://ubirch.com"))
scalaVersion := "2.11.12"
scalacOptions ++= Seq(
  "-feature"
)
scmInfo := Some(ScmInfo(
  url("https://github.com/ubirch/ubirch-crypto-utils"),
  "https://github.com/ubirch/ubirch-crypto-utils.git"
))

/*
 * CREDENTIALS
 ********************************************************/

(sys.env.get("CLOUDREPO_USER"), sys.env.get("CLOUDREPO_PW")) match {
  case (Some(username), Some(password)) =>
    println("USERNAME and/or PASSWORD found.")
    credentials += Credentials("ubirch.utils.cloudrepo", "ubirch.mycloudrepo.io", username, password)
  case _ =>
    println("USERNAME and/or PASSWORD is taken from /.sbt/.credentials-public")
    credentials += Credentials(Path.userHome / ".sbt" / ".credentials-public")
}


/*
 * RESOLVER
 ********************************************************/

val resolverUbirchUtils = "ubirch.utils.cloudrepo" at "https://ubirch.mycloudrepo.io/repositories/ubirch-utils-mvn"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  resolverUbirchUtils)


/*
 * PUBLISHING
 ********************************************************/

publishTo := Some(resolverUbirchUtils)
publishMavenStyle := true


/*
 * DEPENDENCIES
 ********************************************************/

// Ubirch
val ubirchUtilConfig = "com.ubirch.util" %% "ubirch-config-utils" % "0.2.4"

// Third parties
val roundeightsHasher = "com.roundeights" %% "hasher" % "1.2.0"
val apacheCommonsCodec = "commons-codec" % "commons-codec" % "1.11"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
val jodaTime = "joda-time" % "joda-time" % "2.10"
val jodaConvert = "org.joda" % "joda-convert" % "2.1.1"
val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
val slf4j = "org.slf4j" % "slf4j-api" % "1.7.21"
val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.1.7"

val logging = Seq(
  scalaLogging,
  slf4j,
  logbackClassic
)

libraryDependencies ++= Seq(
  roundeightsHasher,
  apacheCommonsCodec,
  ubirchUtilConfig,
  scalaTest % "test",
  jodaTime % "test",
  jodaConvert % "test"
) ++ logging


