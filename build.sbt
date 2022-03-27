val scala3Version = "3.1.1"

ThisBuild / scalaVersion := scala3Version
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "io.mbrc"
ThisBuild / organizationName := "mbrc"

lazy val core = project
  .in(file("core"))
  .settings(
    name := "ezl",
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test

  )
