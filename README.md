# wolfWr-backend
Backend for WolfWR

### Running using JAR (without installation)

- Go to the project repository and run &quot;./mvnw clean install&quot;
  - This will generate the jar (wolfwr-1.0.0.jar) inside target folder
- Once you have the jar you can run the app using &quot;java -jar wolfwr-1.0.0.jar&quot;
- Now go to &quot;[http://localhost:8000/api/swagger-ui/#/](http://localhost:8000/api/swagger-ui/#/)&quot; to access the APIs

### Installation

- Clone the GitHub repository from [here](https://github.com/shahrk/wolfWr-backend). It is a private repository, if you don&#39;t have access contact [rshah28@ncsu.edu](mailto:rshah28@ncsu.edu).
- Import the project **as a maven project** in your choice of editor (we prefer [STS](https://spring.io/tools#eclipse))
- Download &amp; Install [lombok](https://projectlombok.org/download).
  - Run lombok using java -jar lombok.jar (make sure the version of java is the same as the one used in STS)
  - Select the location of your editor of choice.
  - For STS
    - On MAC
      - /Applications/SpringToolSuite4.app/Contents/Eclipse/SpringToolSuite4.ini
    - On Windows
      - Directory where STS is installed
      - e.g. C:\ProgramFiles\sts\sts-4.5.1.RELEASE
- Please go to Project -\&gt; Clean (Build Automatically) after doing this

### Running

- For running select the project in STS and right click → run/debug as → Spring Boot App
 ![](RackMultipart20210419-4-16z2o7m_html_692221e4703d6d8.png)
- The Application will start running at the port 8000 by default
- You can also open the project via terminal and run &quot;mvn clean install&quot; to generate a jar for the project.
  - This will generate a project jar in $Project\_Directory/target/
  - To run the jar simply run the command &quot;java -jar $path\_to\_generated\_project\_jar.jar&quot;

### Configurations

- You might want to use different ports/ database URLs to specify configurations relevant to your setup update $project\_directory/src/main/resources/application.yml
  - To change the port update the property _server.port_
  - To change the database url/username/password update _spring.datasource.url/username/password_
