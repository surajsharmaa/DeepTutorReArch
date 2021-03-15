package dt.config;

import dt.entities.database.Student;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.servlet.ServletContext;

public class ConfigManager {

    static Properties properties = null;
    static String webResourcePath = null;

    /**
     * This function returns the local root folder path specified in the
     * dtconfig
     *
     * @return
     */
    public static String getLocalRoot() {
        return properties.getProperty("dt.localroot");
    }

    /**
     * Returns the path of dtconfig file
     *
     * @return
     */
    public static String getConfigPath() {
        return webResourcePath;
    }

    /**
     * Loads the property file
     *
     * @param servletContextPath
     * @return
     */
    public static boolean init(ServletContext servletContext) {
        String contextPath = servletContext.getRealPath(File.separator);
        webResourcePath = contextPath + "WEB-INF/";
        String filename = webResourcePath + "dtconfig";
        FileInputStream in;
        try {
            in = new FileInputStream(filename);
            properties = System.getProperties();
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String getLogPath() {
        String logPath = getLocalRoot() + properties.getProperty("dt.resource.logloc");
        java.io.File logDir = new File(logPath);
        if (!logDir.exists()) {
            logDir.mkdir();
        }

        return logPath + "/";
    }

    public static String getMediaWebPath() {
        return "/DeepTutorAppReArch/images/tasks/";
    }

    public static String getMediaPath() {

        String datapath = getLocalRoot();

        String mediaPath = datapath + properties.getProperty("dt.resource.media");
        java.io.File mediaDir = new File(mediaPath);
        if (!mediaDir.exists()) {
            mediaDir.mkdir();
        }

        return mediaPath + "/";
    }

    public static String getTasksPath() {
        return getLocalRoot() + properties.getProperty("dt.resource.demo.tasks");
    }

    public static String getDataPath(){
        return getLocalRoot() + properties.getProperty("dt.resource.demo.datapath");
    }
    /**
     * Dictionary file for spell checking.
     *
     * @return
     */
    public static String getDictionaryFile() {
        return getLocalRoot() + properties.getProperty("dt.resource.dictionary");
    }

    public static String getDemoTaskFolder() {
        return getLocalRoot() + properties.getProperty("dt.resource.demo.tasks");
    }

    public static String GetEditedTasksPath() {

        String datapath = getLocalRoot();

        String logPath = datapath + "/EditedTasks";
        java.io.File logDir = new File(logPath);
        if (!logDir.exists()) {
            logDir.mkdir();
        }

        return logPath + "/";
    }

    /*
     *  Gives the folder containing the tasks, that user will see the answers of them.
     */
    public String GetShowAnswerTasksPath() {

        String dataPath = getLocalRoot();

        dataPath = dataPath + "ShowAnswerTasks";
        java.io.File logDir = new File(dataPath);
        if (!logDir.exists()) {
            logDir.mkdir();
        }
        return dataPath + "/";
    }

    public static String GetTaskFileName(String taskID) {
        return taskID + ".xml";
    }

    /**
     * Gives the FCI files path (ending /), pretest question, post test question
     * etc.
     *
     * @return
     * @author Rajendra 1/26/2013
     */
    public static String getFciFilePath() {
        return getLocalRoot() + properties.getProperty("dt.resource.test") + File.separator;
    }

    /**
     * Gives the pretest file, given type (A, B etc). if group is null or
     * something unknown null is returned.
     *
     */
    public static String getFciFileName(String type) {
        if (type == null || type.length() == 0) {
            return null;
        }
        if (type.equalsIgnoreCase("A")) {
            return getFciFilePath() + properties.getProperty("dt.resource.test.pretest-a");
        } else if (type.equalsIgnoreCase("B")) {
            return getFciFilePath() + properties.getProperty("dt.resource.test.pretest-b");
        }
        return null;
    }

    public static String getLogName(Student s) {
        DateFormat df = new SimpleDateFormat("MMddyy");
        String logFileName = s.getStudentId() + "-" + df.format(Calendar.getInstance().getTime());
        return logFileName;
    }

    /*
     * Get the LP file.. (LP-Physics file..)
     */
    public static String getLpFileAbsPath(Student s) {
        return "foo-lpphysics path.. file path";
    }

    public static String getDebugLogConfigFile() {
        //
        return "src\\java\\dt\\config\\log4jconfigl.xml";
    }

    public static String getScriptFilePath() {
        return getLocalRoot() + properties.getProperty("dt.resource.scripts");
    }

    public static String getLpFilePath() {
        return getLocalRoot() + properties.getProperty("dt.resource.lp");
    }

    public static String getDialogPolicyFilePath() {
        return getLocalRoot() + properties.getProperty("dt.resource.dialogpolicy");
    }
}
