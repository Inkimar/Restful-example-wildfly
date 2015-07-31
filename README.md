# Restful-example-wildfly
Using maven.
Testing @Get and @Post ( coming from the example of mjtoolbox, going to tweak that ).

The configuration differs between wildfly and glassfish.

curl http://localhost:8080/RestWebServiceTest/rs/json/students
curl -v -F "uploadedFile=@sorg.jpg"  http://localhost:8080/RestWebServiceTest/rs/json/add
