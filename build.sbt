val v = scala.io.Source.fromFile(file("version.txt")).getLines.next.trim

val jfx = config("jfx")

val jfxJar = file(System.getProperty("java.home")) / "lib" / "jfxrt.jar"

val tomcatVersion = "7.0.52"

val libDeps = Seq(
   "org.scalafx"             %% "scalafx"        % "2.2.76-R11",
   "org.scalafx"             %% "scalafxml-core" % "0.2.1",
    "org.apache.httpcomponents" % "httpclient" % "4.3.4",
    "javax.servlet" % "servlet-api" % "2.5",
    "org.apache.tomcat" % "tomcat-catalina" % tomcatVersion,
    "org.apache.tomcat.embed" % "tomcat-embed-core" % tomcatVersion ,
    "org.apache.tomcat.embed" % "tomcat-embed-logging-juli" % tomcatVersion 
   )

val btSettings = bintrayPublishSettings ++ Seq(
	bintray.Keys.bintrayOrganization in bintray.Keys.bintray := Some("sciabarra"),
	bintray.Keys.repository in bintray.Keys.bintray := "maven",
	licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html")),
	publishMavenStyle := true,
	publishArtifact in packageDoc := false,
	publishArtifact in Test := false)

val mySettings = Seq(name := "agilesites2-setup",
	organization := "com.sciabarra",
	sbtPlugin := true,
	version := v,
	scalaVersion := "2.10.4",
	scalacOptions ++= Seq("-deprecation", "-feature"),
	libraryDependencies ++= libDeps )

val guiSettings = Seq(
    fork in jfx := true,
    mainClass in jfx := Some("agilesites.gui.Main"),
    unmanagedJars in jfx <<= unmanagedJars in Compile,
    unmanagedJars in Compile += Attributed.blank(jfxJar) )

val setup = project.in(file(".")).
  settings(btSettings: _*).
  settings(mySettings : _*).
  settings(guiSettings: _*)

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.1" cross CrossVersion.full)

net.virtualvoid.sbt.graph.Plugin.graphSettings

scalacOptions += "-target:jvm-1.6"

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

