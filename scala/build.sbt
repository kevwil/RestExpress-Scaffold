name := "restexpress-scaffold-scala"

version := "1.0"

scalaVersion := "2.10.2"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"
)

// libraryDependencies += "com.strategicgains" % "RestExpress" % "0.9.2"
libraryDependencies ++= Seq(
  "com.strategicgains" % "RestExpress" % "0.9.2",
  "com.strategicgains" % "HyperExpress" % "1.0.1",
  "com.strategicgains" % "Syntaxe" % "0.4.5",
  "com.strategicgains.repoexpress" % "repoexpress-mongodb" % "0.3.2",
  "com.strategicgains.domain-eventing" % "domain-eventing-core" % "0.4.3",
  "com.strategicgains.plugin-express" % "CacheControlPlugin" % "0.1.2",
  "com.strategicgains.plugin-express" % "MetricsPlugin" % "0.1.2",
  "com.strategicgains.plugin-express" % "RoutesMetadataPlugin" % "0.1.2",
  "com.yammer.metrics" % "metrics-graphite" % "2.2.0",
  "org.slf4j" % "slf4j-jcl" % "1.7.5"// ,
  // "org.specs2" %% "specs2" % "1.14" % "test"
)
