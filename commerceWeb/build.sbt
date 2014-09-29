name := """commerceWeb"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  filters,
  "org.webjars" % "angularjs" % "1.3.0-beta.2",
  "org.webjars" % "requirejs" % "2.1.11-1",
  "mysql" % "mysql-connector-java" % "5.1.27",
  "org.apache.httpcomponents" % "httpcore" % "4.2",
  "org.apache.httpcomponents" % "httpclient" % "4.2",
  "commons-lang" % "commons-lang" % "2.5",
  "commons-pool" % "commons-pool" % "1.6",
  "commons-codec" % "commons-codec" % "1.6",
  "commons-beanutils" % "commons-beanutils" % "1.8.3",
  "redis.clients" % "jedis" % "2.5.2"
)     

lazy val root = (project in file(".")).enablePlugins(PlayScala)

pipelineStages := Seq(rjs, digest, gzip)
