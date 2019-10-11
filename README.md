# Supl Client

This is not an official Google product.

Suplclient is sample code to access supl.google.com

The SuplTester class provides an example on how to use the SUPL Client Project.
The SuplTester sets up the SUPL TCP connection specifications, then at a given latitude and 
longitude sends an LPP SUPL request and prints-out the SUPL server response. 

## Requirement
Make sure protoc binary is installed at /usr/local/bin/protoc
Can be installed easily by the following command on Linux:\
sudo apt-get install libprotobuf-java protobuf-compiler\

For MacOS and Windows, we might need to change the pom.xml or manually complie the proto object file.


## Basic Usage
git clone this repo
use Intellij to import pom.xml and run this project.
main function can be found in SuplTester.java

## License

Suplclient is licensed under the open-source [Apache 2.0 license](LICENSE).

## Contributing

Please [see the guidelines for contributing](CONTRIBUTING.md) before creating
pull requests.

# OSX Setup Guide
This guide is to help the first time java dev get set up on Mac OSX.

## Install JDK 
> Download the installer [from here](https://www.oracle.com/technetwork/java/javase/downloads/index.html). 
>
> Grab the `.dmg` file for easy GUI-based installation. Then just click through the instructions. Then, add the `$JAVA_HOME` variable:
```bash
# probably want to add this to your profile file too (.bashrc or .zshrc etc.)
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-13.jdk/Contents/Home
```

## Download and Install Maven
> Download it [from here](https://maven.apache.org/download.cgi). I downloaded version `3.6.2`.
> 
> #### Verify the Signature of Maven (optional)
> Grab [the keyfile](https://www.apache.org/dist/maven/KEYS) and save it as `apache-maven-keys`
> 
> Download the [signature file](https://www.apache.org/dist/maven/maven-3/3.6.2/binaries/apache-maven-3.6.2-bin.tar.gz.asc) and save it as `apache-maven-3.6.2-bin.tar.gz.asc`

```bash
# install gpg (if necessary)
brew install gnupg

# add the keyfile to your keyring
gpg --import apache-maven-keys

# verify the signature
gpg --verify apache-maven-3.6.2-bin.tar.gz.asc apache-maven-3.6.2-bin.tar.gz

# move it to your home dir an decompress
cp ~/Downloads/apache-maven-3.6.2-bin.tar.gz ~/
cd ~
tar xzvf apache-maven-3.6.2-bin.tar.gz

# link bin dir to your path (probably want to add this to your profile file too (.bashrc or .zshrc etc.)
export PATH=/Users/<username>/apache-maven-3.6.2/bin:$PATH 

# test it out
mvn --version
```

## Install Protobuf
```bash
brew install protobuf@3.6

# add to path
echo 'export PATH="/usr/local/opt/protobuf@3.6/bin:$PATH"' >> ~/.zshrc
```

## Clone the Repo
```bash
git clone https://github.com/coleHafner/supl-client-1.git
```

## Point to the Correct Installation Path of Protobuf
> Edit `./pom.xml`. Change lines 23 - 29 to contain the correct installation of protobuf
>
> From:
```xml
<plugin>
    <groupId>org.xolstice.maven.plugins</groupId>
    <artifactId>protobuf-maven-plugin</artifactId>
    <version>0.6.1</version>
    <configuration>
        <protocExecutable>/usr/local/bin/protoc</protocExecutable> <!-- change this line -->
    </configuration>
```

> To:
```xml
    <plugin>
        <groupId>org.xolstice.maven.plugins</groupId>
        <artifactId>protobuf-maven-plugin</artifactId>
        <version>0.6.1</version>
        <configuration>
          <protocExecutable>/usr/local/opt/protobuf@3.6/bin/protoc</protocExecutable> <!-- change this line -->
        </configuration>
```

## Configure Dependencies and Specify the Main Class
> Add the following 2 plugins to your `pom.xml` file:
```xml
<!-- specify main class for .jar file -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <archive>
        <manifest>
            <addClasspath>true</addClasspath>
            <mainClass>com.google.location.suplclient.supl.SuplTester</mainClass>
            <classpathPrefix>dependency-jars/</classpathPrefix>
        </manifest>
        </archive>
    </configuration>
</plugin>

<!-- ensure .jar file can find dependencies -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>2.5.1</version>
    <executions>
        <execution>
        <id>copy-dependencies</id>
        <phase>package</phase>
        <goals>
            <goal>copy-dependencies</goal>
        </goals>
        <configuration>
            <outputDirectory> ${project.build.directory}/dependency-jars/</outputDirectory>
        </configuration>
        </execution>
    </executions>
</plugin>
```

## Build and run the .jar file
```bash
# build the package
cd supl-client-1
mvn clean package

# send a request the Google SUPL server
java -jar target/SuplClient-1.0-SNAPSHOT.jar
```

# Troubleshooting
A handy thing to do to see if everything went to plan is to unpack the `.jar` file. You can do this like so: 

```bash
jar xf target/SuplClient-1.0-SNAPSHOT.jar
```

This will create a structure like this:

```bash
|META-INF
|-- maven
|---- MANIFEST.MF # this is the file you should look at
|---- gps-measurement-tools
|------ SuplClient
|-------- pom.properties
|-------- pom.xml
```

Your `MANIFEST.MF` file should look like this:
```bash
Manifest-Version: 1.0
Created-By: Maven Archiver 3.4.0
Build-Jdk-Spec: 13
Main-Class: com.google.location.suplclient.supl.SuplTester # this is the main file
Class-Path: protobuf-java-3.4.0.jar protobuf-maven-plugin-0.6.1.jar mave
 n-plugin-api-3.0.jar maven-model-3.0.jar sisu-inject-plexus-1.4.2.jar s
 isu-inject-bean-1.4.2.jar sisu-guice-2.1.7-noaop.jar maven-plugin-annot
 ations-3.5.2.jar plexus-utils-3.1.0.jar plexus-component-annotations-1.
 7.1.jar plexus-build-api-0.0.7.jar maven-core-3.0.jar maven-settings-3.
 0.jar maven-settings-builder-3.0.jar maven-repository-metadata-3.0.jar 
 maven-model-builder-3.0.jar maven-aether-provider-3.0.jar aether-impl-1
 .7.jar aether-spi-1.7.jar aether-api-1.7.jar aether-util-1.7.jar plexus
 -interpolation-1.14.jar plexus-classworlds-2.2.3.jar plexus-sec-dispatc
 her-1.3.jar plexus-cipher-1.4.jar maven-artifact-3.0.jar maven-compat-3
 .0.jar wagon-provider-api-1.0-beta-6.jar guava-19.0.jar joda-time-2.3.j
 ar core-1.47.1.jar google-oauth-client-jetty-1.11.0-beta.jar google-oau
 th-client-java6-1.11.0-beta.jar google-oauth-client-1.11.0-beta.jar goo
 gle-http-client-1.11.0-beta.jar httpclient-4.0.3.jar httpcore-4.0.1.jar
  commons-logging-1.1.1.jar commons-codec-1.3.jar xpp3-1.1.4c.jar jetty-
 6.1.26.jar jetty-util-6.1.26.jar servlet-api-2.5-20081211.jar jsr305-1.
 3.7.jar mail-1.4.jar activation-1.1.jar # these are the dependencies

```
