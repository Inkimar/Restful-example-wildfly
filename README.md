# Restful-example-wildfly
Using maven.
Testing @Get and @Post ( coming from the example of mjtoolbox, going to tweak that ).

The configuration differs between wildfly and glassfish.

(1) @GET
curl http://localhost:8080/RestWebServiceTest/rs/json/students

(2) @POST
curl -v -F "uploadedFile=@sorg.jpg"  http://localhost:8080/RestWebServiceTest/rs/json/add

(3)  @POST
curl -v -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"testUUID":"863ec044-17cf-4c87-81cc-783ab13230ae"}'  http://localhost:8080/RestWebServiceTest/rs/json/ingimar
