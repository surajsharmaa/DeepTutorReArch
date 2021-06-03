/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dt.actions.admin;

import com.opensymphony.xwork2.ActionSupport;
import dt.config.ConfigManager;
import dt.constants.Result;
import dt.entities.database.TodoItem;
import dt.persistent.database.DerbyConnector;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author sharmas
 */
public class TodoListManager extends ActionSupport implements SessionAware {
    
     private Map<String, Object> session;
     
    @Override
    public String execute() throws Exception {
        
       HttpServletRequest request = ServletActionContext.getRequest();
       HttpServletResponse response = ServletActionContext.getResponse();
       ConfigManager.init(ServletActionContext.getServletContext());

       HttpSession session = request.getSession();
		String command = (String)request.getParameter("command");
		
		if (command == null || command.equals(""))
		{
			System.out.println("Loading todo list...");

			//first time, load the todo items
			session.setAttribute("feedback", "Loading tasks.");
                        DerbyConnector.getConnection();
			session.setAttribute("todoItems", DerbyConnector.getInstance().GetTodoItems());
		}
		else{
			if (command.equals("create"))
			{
				System.out.println("Creating todo...");

				String creator = request.getParameter("creator");
				String assignee = request.getParameter("assignee");
				String text = request.getParameter("textItem");
				                        TodoItem item = new TodoItem();
				item.setCreator(creator);
				item.setAssignee(assignee);
				item.setText(text.replaceAll("'", "`"));
				int itemID = DerbyConnector.getInstance().CreateTodoItem(item);
				session.setAttribute("feedback", "ToDo task #"+itemID+" succesfully created.");
				session.setAttribute("todoItems", DerbyConnector.getInstance().GetTodoItems());
			}
			else
			{
				int itemID = Integer.parseInt(request.getParameter("itemID"));
				boolean success = false;
				if (command.equals("update"))
				{
					String responseItem = request.getParameter("responseItem").replaceAll("'", "`");
					success = DerbyConnector.getInstance().UpdateTodoItem(itemID, responseItem);

					session.setAttribute("todoItems", DerbyConnector.getInstance().GetTodoItems());
				}
				if (command.equals("close"))
				{
					String creatorKey = request.getParameter("creator");
					success = DerbyConnector.getInstance().CloseTodoItem(itemID, creatorKey);
				}
				
				if (success)
				{
					session.setAttribute("feedback", "Database succesfully updated");
					session.setAttribute("todoItems", DerbyConnector.getInstance().GetTodoItems());
				}
				else session.setAttribute("feedback", "Problem encountered when updating the database.");
			}
			
		}
		
		return Result.SUCCESS;
        
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session = map;
    }
    
}
