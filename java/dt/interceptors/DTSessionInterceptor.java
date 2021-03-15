/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dt.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Map;

/**
 *
 * @author admin
 */
public class DTSessionInterceptor extends AbstractInterceptor {
  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
      Map<String,Object> session = invocation.getInvocationContext().getSession();
      if(session.isEmpty()) {
          System.out.println("Sessionn interceptor detected session time out!!!!!!!");
          return "sessiontimeout"; // session is empty/expired
      }
      return invocation.invoke();
  }
}