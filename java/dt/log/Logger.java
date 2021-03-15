/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dt.log;

import dt.config.ConfigManager;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Rajendra
 * 
 *  NOTE: this is duplicate..., DTLogger has the same thing..
 */
class Logger {

    public enum Actor {

        STUDENT, TUTOR, SYSTEM, NONE
    };

    public enum Level {

        ONE, TWO, THREE
    };
    String htmlHeader = null;
    String htmlFooter = "\n</body>\n\n</html>";
    StringBuilder allLogs = null;
    String logName;

    public Logger(String _logName) {
        allLogs = new StringBuilder();

        logName = _logName;

        htmlHeader = "<html>\n\n<head><title>Log</title></head>"
                + "\n<body>"
                + //"\n\t<b>Session</b> : 23452<br/>"+
                "\n\t<b>StudentID-Date(MMDDYY)-IP_ADDRESS</b> : " + logName + "<br/>"
                + //"\n\t<b>Task</b> : demo1<br/>"+
                "---------------------------------------<br/>";
    }

    void log(Actor actor, Level level, String message) {
        System.out.println((level == Level.TWO ? "\t" : (level == Level.THREE ? "\t\t" : "")) + (actor == Actor.NONE ? "" : actor + ": ") + message);
        String level1 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String level2 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        String level3 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        switch (actor) {
            case STUDENT:
                allLogs.append("\n\t<b>" + actor + "</b>: <font color=\"#FF0080\">" + message + "</font><br/>");
                break;
            case TUTOR:
                allLogs.append("\n\t<b>" + "DEEP" + actor + "</b>: <font color=\"#0000FF\">" + message + "</font><br/>");
                break;
            case SYSTEM:
                allLogs.append("\n\t<b>" + actor + "</b>: <font color=\"#008000\">" + message + "</font><br/>");
                break;
            case NONE:
                if (level == Level.ONE) {
                    allLogs.append("\n\t" + level1 + message + "<br/>");
                } else if (level == Level.TWO) {
                    allLogs.append("\n\t" + level2 + message + "<br/>");
                } else {
                    allLogs.append("\n\t" + level3 + message + "<br/>");
                }
        }
    }

    void saveLogInHTML() {

        String filename = ConfigManager.getLogPath() + "Log-" + logName + ".html";
        File f = new File(filename);

        try {
            RandomAccessFile raf = null;
            if (f.exists()) {
                raf = new RandomAccessFile(filename, "rw");
                raf.seek(raf.length() - htmlFooter.length());
            } else {
                raf = new RandomAccessFile(filename, "rw");
                raf.writeBytes(htmlHeader);
            }
            raf.writeBytes(allLogs.toString());
            raf.writeBytes(htmlFooter);
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        //DTLogger.log(Actor.TUTOR, Level.ONE, "Hey start answering question");
        //DTLogger.log(Actor.STUDENT, Level.ONE, "Hey start answering question");
        //DTLogger.log(Actor.SYSTEM, Level.ONE, "Hey start answering question");
        //DTLogger.log(Actor.NONE, Level.ONE, "Hey start answering question");
        //DTLogger.log(Actor.NONE, Level.TWO, "Hey start answering question");
        //DTLogger.log(Actor.NONE, Level.THREE, "Hey start answering question");
        //DTLogger.log(Actor.NONE, Level.ONE, "Hey start answering question");
        //DTLogger.saveLogInHTML("src/log.html");
    }
}
