package dt.actions.admin;

import static com.opensymphony.xwork2.Action.ERROR;
import com.opensymphony.xwork2.ActionSupport;
import dt.config.ConfigManager;
import dt.constants.Result;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

public class TaskSequenceUploader extends ActionSupport implements SessionAware {
	  
    private String message;

    private Map<String, Object> session;
        
    private File myFile;
    private String myFileContentType;
    private String myFileFileName;
    private File taskSeqDestinationFolder;
    
    public File getMyFile() {
      return myFile;
   }
   
   public void setMyFile(File myFile) {
      this.myFile = myFile;
   }
   
   public String getMyFileContentType() {
      return myFileContentType;
   }
   
   public void setMyFileContentType(String myFileContentType) {
      this.myFileContentType = myFileContentType;
   }
   
   public String getMyFileFileName() {
      return myFileFileName;
   }
   
   public void setMyFileFileName(String myFileFileName) {
      this.myFileFileName = myFileFileName;
   }
   
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }

    @Override
    public String execute () throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        System.out.println(request.getInputStream().toString());
//        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = request.getSession();

        try {
            System.out.println("Src File name: " + myFile);
            System.out.println("Dst File name: " + myFileFileName);
            ConfigManager.init(ServletActionContext.getServletContext());
            
            taskSeqDestinationFolder = new File(ConfigManager.getDataPath());
           System.out.println("Distination Folder: " + taskSeqDestinationFolder.getAbsolutePath());

            if (myFileFileName.endsWith(".xml")) {
                File destFile  = new File(taskSeqDestinationFolder, myFileFileName);
                FileUtils.copyFile(myFile, destFile);
            } else {
                session.setAttribute("status","Failed! It can only upload XML files.");
                return ERROR;
            }
        } catch(IOException e) {
            e.printStackTrace();
            return ERROR;
        }
        session.setAttribute("status", myFileFileName+ " is uploaded succesfully.");
        setMessage("Welcome to actionhome after upload button clicked");
        
        
        return Result.SUCCESS;
    }
    
    
}
