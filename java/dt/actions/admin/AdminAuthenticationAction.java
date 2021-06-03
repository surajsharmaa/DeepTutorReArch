/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dt.actions.admin;

import dt.constants.Result;
import java.io.InputStream;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Suraj Sharma
 */
public class AdminAuthenticationAction implements SessionAware {
    private String userName;
    private String password;
    private String device;
    private InputStream inputStream;
    private String message;
    private Map<String, Object> session;
    
    public String execute() throws Exception {
        session.clear();
        System.out.println("Username: " + getUserName()+ " Password: "+ getPassword());
        if(getUserName()!=null && getUserName().trim().equals("dtadmin") && getPassword()!=null && getPassword().trim().equals("dtauthor310")){
            session.put("adminLoginSuccesfull", "true");
            return Result.SUCCESS;
        } else {
            session.put("adminLoginSuccesfull", "false");
            setMessage("Login failed: Incorrect user name and/or password");
            return Result.ERROR;
        }
        
    }
    
     public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
     public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getDevice() {
        return device;
    }
    
    public void setDevice(String device) {
        this.device = device;
    }
    
    public InputStream getInputStream() {
        return inputStream;
    }
    
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
     public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
    
}
