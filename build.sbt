name := "labs-self-avoiding-walk"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "io.monix" %% "minitest" % "2.2.2" % "test"

testFrameworks += new TestFramework("minitest.runner.Framework")