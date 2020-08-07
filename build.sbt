import sbt.Keys.libraryDependencies

concurrentRestrictions in Global := Seq(
  Tags.limit(Tags.Test, 1)
)


/*
 * RESOLVER
 ********************************************************/

val sonatypeReleases = Resolver.sonatypeRepo("releases")
val sonatypeSnapshots = Resolver.sonatypeRepo("snapshots")
val resolverSeebergerJson = Resolver.bintrayRepo("hseeberger", "maven")
val resolverElasticsearch = "elasticsearch-releases" at "https://artifacts.elastic.co/maven"

val ubirchUtilGroup = "com.ubirch.util"

val commonSettings = Seq(

  scalaVersion := "2.11.12",
  scalacOptions ++= Seq(
    "-feature"
  ),

  organization := ubirchUtilGroup,

  homepage := Some(url("http://ubirch.com")),
  scmInfo := Some(ScmInfo(
    url("https://github.com/ubirch/ubirch-scala-utils"),
    "https://github.com/ubirch/ubirch-scala-utils.git"
  )),
  (sys.env.get("CLOUDREPO_USER"), sys.env.get("CLOUDREPO_PW")) match {
    case (Some(username), Some(password)) =>
      println("USERNAME and/or PASSWORD found.")
      credentials += Credentials("ubirch.mycloudrepo.io", "ubirch.mycloudrepo.io", username, password)
    case _ =>
      println("USERNAME and/or PASSWORD is taken from /.sbt/.credentials.")
      credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
  },
  resolvers ++= Seq(
    sonatypeReleases,
    sonatypeSnapshots),
  publishTo := Some("io.cloudrepo" at "https://ubirch.mycloudrepo.io/repositories/trackle-mvn"),
  publishMavenStyle := true
  //  https://www.scala-lang.org/2019/10/17/dependency-management.html
  //  , conflictManager := ConflictManager.strict

)

/*
 * MODULES
 ********************************************************/


lazy val crypto = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "ubirch-crypto-utils",
    description := "ubirch util with crypto related code",
    version := "0.5.2",
    libraryDependencies ++= Seq(
      roundeightsHasher,
      apacheCommonsCodec,
      ubirchUtilConfig,
      eddsa,
      scalaTest % "test",
      jodaTime % "test",
      jodaConvert % "test"
    ) ++ depSlf4jLogging
  )


/*
 * DEPENDENCIES
 ********************************************************/


lazy val roundeightsHasher = "com.roundeights" %% "hasher" % "1.2.0"
lazy val apacheCommonsCodec = "commons-codec" % "commons-codec" % "1.11"
lazy val ubirchUtilConfig = ubirchUtilGroup %% "config" % "0.2.3"
// https://mvnrepository.com/artifact/net.i2p.crypto/eddsa
val eddsa = "net.i2p.crypto" % "eddsa" % "0.3.0"
val scalaTestV = "3.0.5"
lazy val scalaTest = "org.scalatest" %% "scalatest" % scalaTestV
val jodaTime = "joda-time" % "joda-time" % "2.10"
val jodaConvert = "org.joda" % "joda-convert" % "2.1.1"
lazy val scalaLoggingSlf4j = "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2"
lazy val slf4j = "org.slf4j" % "slf4j-api" % "1.7.21"
lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.1.7"

lazy val depSlf4jLogging = Seq(
  scalaLoggingSlf4j,
  slf4j,
  logbackClassic
)



