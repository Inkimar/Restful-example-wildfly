package se.nrm.bio;

import java.io.Serializable;
import javax.ws.rs.FormParam;

/**
 *
 * @author ingimar
 */
public class Form implements Serializable{

    private static final long serialVersionUID = 1303297878875818134L;
    
    @FormParam("testUUID")
    private String testUUID;
    
    
    @FormParam("base64")
    private String base64;

    public Form() {
    }
    
    public String getTestUUID() {
        return testUUID;
    }

    public void setTestUUID(String testUUID) {
        this.testUUID = testUUID;
    }
    
    
}
