/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dt.actions.authentication;

import com.opensymphony.xwork2.ActionSupport;
import dt.config.ConfigManager;
import dt.constants.Result;
import dt.entities.database.Student;
import dt.persistent.DataManager;
import dt.persistent.database.Students;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Rajendra
 */
public class UserAuthenticationAction extends ActionSupport implements SessionAware {

    private String message; /* error message or so.., TODO: is there any elegant way to set error message?? */

    private String userName;
    private String password;
    private String device;
    private InputStream inputStream;

    public String getDevice() {
        return device;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setDevice(String device) {
        this.device = device;
    }
    private Map<String, Object> session;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserAuthenticationAction() {
    }

    /**
     * if authentication fails, set some message and stay on the same page. if authentication is successful, - set
     * student in the session and redirect to next page... (see struts config)
     *
     * @return
     */
    public String execute() throws UnsupportedEncodingException {
//        setDevice("android");
//        setUserName("myandroiddevice");
//        setPassword("IPT_M_APP");
        
        Student s = DataManager.getStudent(getUserName().trim(), getPassword());
        if (s == null) {
            if (device != null && device.equalsIgnoreCase("android")) {
                
                if (getPassword() != null && getPassword().equalsIgnoreCase("IPT_M_APP")) {
                    //register new student
                    s = new Student();
                    s.setStudentId(getUserName());
                    s.setPassword(getPassword());
                    s.setPreTest("A");
                    s.setPostTest("B");
                    String taskList="app.rocketMeteor,app.carMosquito,app.bicycleApple"; //Temoprarily removed
                    //String taskList="app.bicycleApple";
                    s.setAssignedTasks(taskList); //TODO: does it matter??
                    DataManager.insertNewStudent(s);
                    //Retrieve student the same student and work seamlessly
                    s = DataManager.getStudent(getUserName().trim(), getPassword());
                    if (s == null) {
                        this.message = "<response status=\"FAILED\">Login failed: Incorrect user name and/or password</response>";
                        sendResponse(this.message);
                        //rajendra: Note that this success doesn't mean that login successful, need to return success if writing to inputstream.
                        return Result.SUCCESS;
                    }
                } else {

                    this.message = "<response status=\"FAILED\">Login failed: Incorrect user name and/or password</response>";
                    sendResponse(this.message);
                    //rajendra: Note that this success doesn't mean that login successful, need to return success if writing to inputstream.
                    return Result.SUCCESS;
                }
                s.setCurrentUserClientType(device);
            } else {
                //set the error message.
                setMessage("Login failed: Incorrect user name and/or password");
                return Result.INPUT;
            }
        }
        //Temporary... initialize config manager
        ConfigManager.init(ServletActionContext.getServletContext());
        /* clear the session.. */
        session.clear();
        /* do some housekeeping and put the student object in session */
        s.setup();
        session.put("student", s);
        if (device != null && device.equalsIgnoreCase("android")) {
            this.message = "<response status=\"SUCCESS\">Please call dialogue action</response>";
            sendResponse(this.message);
            return Result.SUCCESS;
        }
        return Result.CONTINUE;
    }

    /**
     * Write response to stream.
     *
     * @param responseText
     * @throws UnsupportedEncodingException
     */
    private void sendResponse(String responseText) throws UnsupportedEncodingException {
        // Now the response is formed using the old DTResponseOld, and Components object..,
        // this function is not being used.
        //response.formXMLResponse();
        System.out.println("WRITING TO CLIENT: ");
        System.out.println(responseText);
        inputStream = new ByteArrayInputStream(responseText.getBytes("UTF8"));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
}
