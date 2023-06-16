val scala3Version = "3.3.0"
val http4sVersion = "0.23.20"

lazy val root = project
  .in(file("."))
  .settings(
    name := "http4s-revolver-fast",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion
    )
  )
