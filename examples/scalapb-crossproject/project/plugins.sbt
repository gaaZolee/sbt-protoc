addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "0.6.0")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "0.6.0")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % "0.6.23")
addSbtPlugin("org.scala-native"   % "sbt-scala-native"              % "0.3.7")

addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.15")

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.8.0-RC1"
