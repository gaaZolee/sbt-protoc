def grpcExeFileName = {
  val os = if (scala.util.Properties.isMac){
    "osx-x86_64"
  } else if (scala.util.Properties.isWin){
    "windows-x86_64"
  } else {
    "linux-x86_64"
  }
  s"${grpcArtifactId}-${grpcJavaVersion}-${os}.exe"
}

val grpcArtifactId = "protoc-gen-grpc-java"

val grpcJavaVersion = "1.9.0"

val grpcExeUrl =
  url(s"http://repo1.maven.org/maven2/io/grpc/${grpcArtifactId}/${grpcJavaVersion}/${grpcExeFileName}")

val grpcExePath = settingKey[xsbti.api.Lazy[File]]("grpcExePath")

libraryDependencies += "io.grpc" % "protoc-gen-grpc-java" % "1.19.0"

grpcExePath := xsbti.api.SafeLazyProxy {
  val exe: File = (baseDirectory in ThisBuild).value / ".bin" / grpcExeFileName
  exe.getParentFile().mkdirs()
  if (!exe.exists) {
    import scala.sys.process._
    println("grpc protoc plugin (for Java) does not exist. Downloading.")
    grpcExeUrl #> (exe) !

    exe.setExecutable(true)
  } else {
    println("grpc protoc plugin (for Java) exists.")
  }
  exe
}

PB.protocOptions in Compile ++= Seq(
  s"--plugin=protoc-gen-java_rpc=${grpcExePath.value.get}",
  s"--java_rpc_out=${((sourceManaged in Compile).value).getAbsolutePath}"
)

PB.targets in Compile := Seq(
  PB.gens.java -> (sourceManaged in Compile).value
)

libraryDependencies ++= Seq(
    "io.grpc" % "grpc-all" % grpcJavaVersion,
    "javax.annotation" % "javax.annotation-api" % "1.3.2"  // needed for grpc-java on JDK9
)

