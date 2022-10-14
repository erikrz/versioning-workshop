# Java Maven Versioning Workshop

The main goal of this project is to guide you to identify some Maven best practices.
In the last section you can find instructions on how to locally build and run this microservice.

# Sample Contacts Microservice

In the `/contacts-service-parent` folder, there there is a parent Maven Project, along with 4 childs:

* [contacts-service-parent](contacts-service-parent)
  * [contacts-api](contacts-service-parent/contacts-api)
    Defines the DTOs (Data Transfer Objects) used to communicate with the Contacts Service.
  * [contacts-client](contacts-service-parent/contacts-client)
    Defines a Feign client to easily communicate with the Contacts Service for Java Developers.
  * [contacts-client-spring-boot-starter](contacts-service-parent/contacts-client-spring-boot-starter)
    Defines a ready-to-use Feign client for JAva Developers using Spring Boot.
  * [contacts-service](contacts-service-parent/contacts-service)
    The actual microservice.


## Maven Versioning Best Practices 

See all these points implemented in 
* contacts-service-parent [pom.xml](contacts-service-parent/pom.xml).

### Manage versioning in the parent POM
See `<project><dependencyManagement>` and  section.
```xml
<project>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>io.swagger.core.v3</groupId>
                <artifactId>swagger-annotations</artifactId>
                <version>${swagger-annotations.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
  <build>
    <pluginManagement>
      <plugins>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>${maven-compiler-plugin.version}</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>
```

### Declare dependency versions on properties.
See `<project><properties>` section.
```xml
<project>
  <properties>
    <spring-boot.version>2.7.3</spring-boot.version>
  </properties>
</project>
```

### Declare all dependency versions in the dependency management section.
See `<project><dependencyManagement><dependencies><version>` section.
```xml
<project>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <version>${spring-boot.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

### Import Bill Of Materials (BOM) when possible.
See `<project><dependencyManagement><dependencies><type>` and `<import>` section.

```xml
<project>
    <dependencyManagement>
        <dependencies>
            <!-- Father Bill Of Materials (BOM) of Spring Boot. defines lots of versions for lots of dependencies that
            play well together. -->
            <dependency>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

### Use versions-maven-plugin to keep versions up to date.
See `<project><build><plugins>` section.
```xml
<project>
  <build>
    <plugins>
      <!--This plugin is defined in the parent pom to easily check for dependencies updates.-->
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>versions-maven-plugin</artifactId>
        <inherited>false</inherited>
        <configuration>
          <rulesUri>file://${project.basedir}/version-number-rules.xml</rulesUri>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

## Maven Scoping Best Practices
See all these points implemented in 
* contacts-api [pom.xml](contacts-service-parent/contacts-api/pom.xml)
* contacts-client [pom.xml](contacts-service-parent/contacts-client/pom.xml)
* contacts-client-spring-boot-starter [pom.xml](contacts-service-parent/contacts-client-spring-boot-starter/pom.xml)
* contacts-service [pom.xml](contacts-service-parent/contacts-service/pom.xml)

### Manage scoping per child POM
See that the parent pom does not have a single `<scope>` section in its dependencies.
See contacts-service [pom.xml](contacts-service-parent/contacts-service/pom.xml) `<scope>` sections.

### Declare only dependencies needed by the child module
See how all child projects defines their own set of needed dependencies.

### Declare scope (compile, provided, runtime, test) when needed

Open [contacts-service-parent](contacts-service-parent) and run `mvn clean install`. This should build the all the neccesary artifacts to run and consume this microservice. 
In this sample, we have a RESTFul microservice that accepts JSON payload. 


## Local Development

### Prerequisites

* **Open JDK 17**

  You can install any version, i.e. [Amazon Coretto](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html).

* **Maven**

  You can use the embedded Maven binaries that your IDE offers, or you can install your own one.
  
### Building all the components

We use Maven as our build tool. Here is the [Maven Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html) for reference.

Maven goals commonly used in this project:

| Goals                               | Description                                                                                                                                                                                |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `clean`                             | Removes all files generated by the previous build. Recommended to run first, before any build goal.                                                                                        |
| `versions:display-property-updates` | Scans the project and produces a report of those properties which are used to control artifact versions and which properties have newer versions available. Recommended to run standalone. |
| `compile`                           | Build goal to compile source code.                                                                                                                                                         |
| `test`                              | `compile` plus compile and run tests.                                                                                                                                                      |
| `package`                           | `test` plus package compiled code into a jar.                                                                                                                                              |
| `install`                           | `package` plus install in local maven repo for other local projects to reuse.                                                                                                              |

Sample maven command goal runs:

* `mvn clean test`
* `mvn clean install`
* `mvn versions:display-property-updates`

### Starting the Application

#### Locally

* Run `java -jar contacts-service/target/ContactsService.jar`
* Local swagger URL is available at [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)













