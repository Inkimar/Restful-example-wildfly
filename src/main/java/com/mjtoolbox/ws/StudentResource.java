package com.mjtoolbox.ws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import se.nrm.bio.Form;

/**
 * http://www.mkyong.com/webservices/jax-rs/file-upload-example-in-resteasy/
 *
 * http://localhost:8080/RestWebServiceTest/rs/json/students
 *
 * curl http://localhost:8080/RestWebServiceTest/rs/json/students
 *
 * @author ingimar
 */
@Path("/json")
public class StudentResource {

    private final String UPLOADED_FILE_PATH = "/home/ingimar/Downloads/tmp/";

    List<Student> students = new ArrayList<>();

    StringListConverter converter = new StringListConverter();

    public StudentResource() {
        // Populate test data. Later each WS method will retrieve data from DB.
        students.add(new Student(12344, "Mike", 17));
        students.add(new Student(12345, "Jane", 19));
        students.add(new Student(12346, "Bob", 19));
        students.add(new Student(12347, "Susan", 22));
        students.add(new Student(12348, "Daniel", 25));
        students.add(new Student(12349, "John", 26));
        students.add(new Student(12350, "Debbie", 28));
        students.add(new Student(12350, "anders", 61));
    }
    
    /**
     *  curl -v -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"testUUID":"w"}'  http://localhot:8080/RestWebServiceTest/rs/json/ingimar
     * 
     * @param form
     * @return 
     */
    @POST
    @Path("ingimar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response testAdd(@MultipartForm Form form) {
        String uuid = form.getTestUUID();
        
        return Response.status(200).entity(uuid).build();
    }

    /**
     * curl -v -F "uploadedFile=@sorg.jpg"  http://localhost:8080/RestWebServiceTest/rs/json/add
     * curl -v -F "legend=Göran Liljeberg" -F "sortOrder=1" -F  "comment=test"  -F "taxonpageId=2655" -F "uploadedFile=@sorg.jpg"  http://localhost:8080/RestWebServiceTest/rs/json/add
     */
    @POST
    @Path("add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response add(MultipartFormDataInput input) {
        String fileName = "";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        if (null == inputParts) {
            return Response.status(400).entity("not valid \n").build();
        }

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                //constructs upload file path
                fileName = UPLOADED_FILE_PATH + fileName;

                writeFile(bytes, fileName);

                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return Response.status(200).entity(" Uploaded file name : " + fileName).build();
    }

    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    //save to somewhere
    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }

    @GET
    @Path("students")
    @Produces("application/json")
    public Response getStudentList() {
        StudentWrapper wrapper = new StudentWrapper();

        wrapper.setList(students);

        return Response.status(200).entity(wrapper).build();
    }

    @GET
    @Path("overAge/{age}")
    @Produces("application/json")
    public Response getStudentOverAgeList(@PathParam("age") String anAge) {
        StudentWrapper wrapper = new StudentWrapper();

        List<Student> newList = new ArrayList<Student>();
        for (Student student : students) {
            if (student.getAge() > Integer.parseInt(anAge)) {
                newList.add(student);
            }
        }
        wrapper.setList(newList);
        return Response.status(200).entity(wrapper).build();
    }

    /**
     * Pass concatenated names as a single string
     *
     * @param namesString
     * @return
     */
    @GET
    @Path("studentsByName")
    @Produces("application/json")
    public Response getStudentsByNames(@QueryParam("names") String namesString) {
        StudentWrapper wrapper = new StudentWrapper();

        List<String> nameList = converter.fromString(namesString);
        List<Student> newList = new ArrayList<Student>();

        for (Student student : students) {
            if (nameList.contains(student.getName())) {
                newList.add(student);
            }
        }
        wrapper.setList(newList);
        return Response.status(200).entity(wrapper).build();
    }

    /**
     * Pass concatenated names as a single string
     *
     * @param namesString
     * @return
     */
    @PUT
    @Path("studentsByNameJson")
    @Produces("application/json")
    public Response getStudentsByNamesJson(String namesJson) {
        StudentWrapper wrapper = new StudentWrapper();

        List<String> nameList = converter.fromJson(namesJson);
        List<Student> newList = new ArrayList<Student>();

        for (Student student : students) {
            if (nameList.contains(student.getName())) {
                newList.add(student);
            }
        }
        wrapper.setList(newList);
        return Response.status(200).entity(wrapper).build();
    }

}
