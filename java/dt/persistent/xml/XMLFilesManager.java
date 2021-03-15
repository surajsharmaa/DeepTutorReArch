 package dt.persistent.xml;

import dt.config.ConfigManager;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLFilesManager {
	private static XMLFilesManager instance = null;

	private Document docTasks = null;
	private Document docScripts = null;
	private Document docLPModel = null;
	
	Random randomGenerator = new Random();
	 
    public static XMLFilesManager getInstance() {
    	if (instance == null) 
    	{
    		try{
    			instance = new XMLFilesManager();
		    }catch (SAXParseException err) {
		    	System.out.println ("** Parsing error" + ", line " 
		    			+ err.getLineNumber () + ", uri " + err.getSystemId ());
		    	System.out.println(" " + err.getMessage ());
		    	return null;
		    }catch (SAXException e) {
		    	Exception x = e.getException ();
		    	((x == null) ? e : x).printStackTrace ();
		    }catch (Throwable t) {
		    	t.printStackTrace ();
		    }
    	}
        return instance;
    }
    
    private XMLFilesManager() throws Throwable 
    {
    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        
        //docTasks = docBuilder.parse(new File(ConfigManager.GetResourcePath() + ConfigManager.GetTaskFileName()));
        docScripts = docBuilder.parse(new File(ConfigManager.getScriptFilePath()));
        docLPModel = docBuilder.parse(new File(ConfigManager.getLpFilePath()));

        // normalize text representation
        //docTasks.getDocumentElement().normalize();
        docScripts.getDocumentElement().normalize();
        docLPModel.getDocumentElement().normalize();
   }

    public Element GetEditedTaskElement(String taskID)
    {
    	//this is a temporary fix for loading the task file on every request; it definitely makes it easier for developing the tasks
    	try{
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    		docTasks = docBuilder.parse(new File(ConfigManager.GetEditedTasksPath() + ConfigManager.GetTaskFileName(taskID)));
    		docTasks.getDocumentElement().normalize();
    	}
    	catch(Exception e){e.printStackTrace();return null;}
       
        NodeList listOfAnswerTasks = docTasks.getElementsByTagName("Task");
        
        return (Element)listOfAnswerTasks.item(0);
    }
    
    
    /*
     * Gets the task of which student will just see the answer. 
     * No more instructions to enter the answer...etc 
     */
//    public Element GetShowAnswerTaskElement(String taskID)
//    {
//    	try{
//    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//    		docTasks = docBuilder.parse(new File(ConfigManager.GetShowAnswerTasksPath() + ConfigManager.GetTaskFileName(taskID)));
//    		docTasks.getDocumentElement().normalize();
//    	}
//    	catch(Exception e){e.printStackTrace();return null;}
//       
//        NodeList listOfAnswerTasks = docTasks.getElementsByTagName("Task");
//        
//        return (Element)listOfAnswerTasks.item(0);
//    }

    public Element GetTaskElement(String taskID)
    {
    	//this is a temporary fix for loading the task file on every request; it definitely makes it easier for developing the tasks
    	try{
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                //modified by rajendra: 2/8/2013, to fix some utf-8 related issue
                String taskFileName = ConfigManager.getTasksPath() + "\\" + ConfigManager.GetTaskFileName(taskID);
                ByteArrayInputStream bis = new  ByteArrayInputStream(getTextFromFileAsALine(taskFileName).getBytes("UTF8"));
                docTasks = docBuilder.parse(bis);
    		docTasks.getDocumentElement().normalize();
    	}
    	catch(Exception e){e.printStackTrace();return null;}
       
        NodeList listOfAnswerTasks = docTasks.getElementsByTagName("Task");
        
        return (Element)listOfAnswerTasks.item(0);
        /*
        int totalAnswerTasks = listOfAnswerTasks.getLength();
           	
        Element myTask = null;
        for(int s=0; s<totalAnswerTasks ; s++){

     	   Element answerTaskNode = (Element)listOfAnswerTasks.item(s);
     	   if (answerTaskNode.getAttribute("id").equals(taskID))
     	   {
     		   //LMC - I want to clone this to make sure we are not using the same node for all requests
     		   myTask = (Element)answerTaskNode.cloneNode(true);
     	   }
        }
        
        return myTask;
        */
    }

    public Element GetLPRoot() //
    {
    	return (Element)docLPModel.getDocumentElement();
    }

    public String GetSomeFeedback(String type) //
    {
 	   Element feedback = (Element)docScripts.getElementsByTagName(type).item(0);

 	   NodeList choices = feedback.getElementsByTagName("Text");

 	   return choices.item(randomGenerator.nextInt(choices.getLength())).getTextContent();
    }
    
    public static Element[] GetXMLChildrenElements(Element e, String nodeName)
    {
    	NodeList children = e.getChildNodes();
    	ArrayList<Element> el = new ArrayList<Element>();
    	for (int i=0;i<children.getLength();i++)
    	{
    		if (children.item(i).getNodeName().equalsIgnoreCase(nodeName)) el.add((Element)children.item(i));
    	}
    	
    	Element[] result = new Element[el.size()];
    	return el.toArray(result);
    }
    
    
    
    /**
     * Rajendra 2/8/2013: I have added this function just to fix the UTF-8 encoding.. error.
     * Don't know why this that is not working.. now, but it has working before.
     * @param fileName
     * @return 
     */
    	private String getTextFromFileAsALine(String fileName) {
                StringBuilder sb = new StringBuilder();
		try {
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				sb.append(strLine);
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return sb.toString();
	}
}
