name := "labs-self-avoiding-walk"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "io.monix" %% "minitest" % "2.2.2" % "test"
libraryDependencies += "io.monix" %% "minitest-laws" % "2.2.2" % "test"
libraryDependencies += "org.typelevel" %% "cats-effect" % "1.0.0"

testFrameworks += new TestFramework("minitest.runner.Framework")