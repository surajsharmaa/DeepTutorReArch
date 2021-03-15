package dt.actions.admin;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;

//import com.sun.grizzly.websockets.WebSocketEngine;
import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;
import dt.config.ConfigManager;
import dt.constants.Result;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

//import memphis.deeptutor.main.WebSocketHandler;
//import memphis.deeptutor.main.WizardofOzServiceHandler;
//import memphis.deeptutor.singleton.ConfigManager;

public class InitDTServlet extends ActionSupport implements SessionAware {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2454863708244770534L; 
	//public static WizardofOzServiceHandler wozHandler = new WizardofOzServiceHandler();
	
	//public static WebSocketHandler app = new WebSocketHandler();
	public static SpellChecker spellCheck = null;
        
        private Map<String, Object> session;
        
        public void setSession(Map<String, Object> map) {
            this.session = map;
        }

    @Override
    public String execute () throws Exception{
    	
    	if (ConfigManager.init(ServletActionContext.getServletContext())) System.out.print("Web App properties ... loaded.");
    	else System.out.print("Web App properties... failed to load.");
    	
    	try {
    		SpellDictionary dictionary = new SpellDictionaryHashMap(new File(ConfigManager.getDataPath()+ "\\dict\\english.0"), null);
    		spellCheck = new SpellChecker(dictionary);
                return Result.SUCCESS;
    	}catch (Exception e) {
                e.printStackTrace();
                System.out.print("SpellChecker dictionary... failed to load.");
                return Result.ERROR;
	}
    }
    
//    @Override
//    public void destroy()
//    {
//    	System.out.print("Unregistering WebSocket WoZ Service Handler...");
//    	//WebSocketEngine.getEngine().unregister(app);
//    }
}
