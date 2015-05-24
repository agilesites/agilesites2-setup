val v = scala.io.Source.fromFile(file("version.txt")).getLines.next.trim

val tomcatVersion = "7.0.59"

val libDeps = Seq(
  "org.apache.tomcat.embed" % "tomcat-embed-core" % tomcatVersion,
  "org.apache.tomcat.embed" % "tomcat-embed-logging-juli" % tomcatVersion,
  "org.apache.tomcat.embed" % "tomcat-embed-jasper" % tomcatVersion,
  "org.apache.tomcat" % "tomcat-jasper" % tomcatVersion,
  "org.apache.tomcat" % "tomcat-jasper-el" % tomcatVersion,
  "org.apache.tomcat" % "tomcat-jsp-api" % tomcatVersion,
  "org.apache.tomcat" % "tomcat-dbcp" % tomcatVersion,
  "org.hsqldb" % "hsqldb" % "1.8.0.10", // database
  "org.apache.httpcomponents" % "httpclient" % "4.3.4")

val btSettings = bintrayPublishSettings ++ Seq(
	bintray.Keys.bintrayOrganization in bintray.Keys.bintray := Some("sciabarra"),
	bintray.Keys.repository in bintray.Keys.bintray := "maven",
	licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html")),
	publishMavenStyle := true,
	publishArtifact in packageDoc := false,
	publishArtifact in Test := false)

/*val publishSttings = Seq(
  publishMavenStyle := true,
  pomIncludeRepository := { _ => false },
  publishTo := {
    val nexus = "http://nexus.sciabarra.com/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "content/repositories/releases")
  },
  publishArtifact in packageDoc := false,
  publishArtifact in Test := false,
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html")),
  credentials += Credentials(Path.userHome / ".ivy2" / "credentials")
)*/

val mySettings = Seq(name := "agilesites2-setup",
	organization := "com.sciabarra",
	version := v,
	crossPaths := false,
	autoScalaLibrary := false,
	scalacOptions ++= Seq("-deprecation", "-feature"),
	libraryDependencies ++= libDeps )

val setup = project.in(file(".")).
  settings(btSettings: _*).
  settings(mySettings : _*)

resolvers += Resolver.sonatypeRepo("releases")

net.virtualvoid.sbt.graph.Plugin.graphSettings

scalacOptions += "-target:jvm-1.6"

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")
