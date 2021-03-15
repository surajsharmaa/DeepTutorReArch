/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dt.persistent.database;

import dt.entities.database.Student;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Rajendra
 */
public class Students {

    public static boolean updateStudent(Student student) {

        Statement stmt = null;
        Connection conn = DerbyConnector.getConnection();
        try {
            stmt = conn.createStatement();
            String query = "UPDATE student SET "
                    + " hasacceptedtermsandconditions =" + (student.isHasAcceptedTermsAndConditions() ? 1 : 0)  
                    + " , assignedtasks =" + (student.getAssignedTasks() != null ? "'" + student.getAssignedTasks() + "'" : "null")
                    + " , finishedtasks =" + (student.getFinishedTasks() != null ? "'" + student.getFinishedTasks() + "'" : "null")
                    //+ (student.getFinishedTasks() != null ? (" , finishedtasks ='" + student.getFinishedTasks()  + "'" : "null")) : "")
                    + " WHERE studentid ='" + student.getStudentId() + "'";
            //TODO: need more fields to be updated?

            stmt.execute(query);
            stmt.close();
        } catch (SQLException ex) {
            System.err.println("Error.. inserting student information in the database!!");
            ex.printStackTrace();
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException ex1) {
                System.err.println("Error closing database connections..");
                ex1.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public static Student getStudent(String studentId) {
        Student s = new Student();
        s.setStudentId(studentId);
        return getStudent(s);
    }

    /**
     *
     * @param studentId
     * @param password
     * @return
     */
    public static Student getStudent(String studentId, String password) {
        Student s = new Student();
        s.setStudentId(studentId);
        s.setPassword(password);
        Student result = getStudent(s);
        if (result != null) {
            // if password is not null, try matching password.
            if (result.getPassword() != null) {
                if (result.getPassword().equals(s.getPassword())) {
                    return result;
                } else {
                    return null;
                }
            }
            return result;
        }
        return null;
    }

    /**
     * Returns student given its id
     *
     * @param student
     * @return
     */
    public static Student getStudent(Student student) {
        String studentId = student.getStudentId();
        Student s1 = null;
        Statement stmt = null;
        Connection conn = DerbyConnector.getConnection();
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM STUDENT WHERE studentid='" + studentId + "'";
            ResultSet results = stmt.executeQuery(query);
            //There should be only one result but we just need to iterate.
            while (results.next()) {
                s1 = new Student();
                s1.setStudentId(studentId);
                String val = results.getString("ISSPECIALSTUDENT");
                boolean bol = false;
                if (val != null) {
                    if (Integer.parseInt(val) > 0) {
                        bol = true;
                    }
                }
                s1.setIsSpecialStudent(bol);
                //
                val = results.getString("HASACCEPTEDTERMSANDCONDITIONS");
                bol = false;
                if (val != null) {
                    if (Integer.parseInt(val) > 0) {
                        bol = true;
                    }
                }
                s1.setHasAcceptedTermsAndConditions(bol);
                //
                s1.setPassword(results.getString("PASSWORD"));
                //
                s1.setPreTest(results.getString("PRETEST"));
                s1.setPostTest(results.getString("POSTTEST"));
                //
                s1.setAssignedTasks(results.getString("ASSIGNEDTASKS"));
                s1.setFinishedTasks(results.getString("FINISHEDTASKS"));
                //TODO: add more fields. if needed.
            }

        } catch (SQLException ex) {
            System.err.println("Error.. getting student information from database!!");
            s1 = null;
        }

        return s1;
    }

    /*
     * Insert's a demo user..
     * 
     */
    public static boolean insertNewStudent(Student student) {
        Statement stmt = null;
        Connection conn = DerbyConnector.getConnection();
        try {
            stmt = conn.createStatement();
            String query = "INSERT INTO STUDENT (studentid, password, isspecialstudent, hasacceptedtermsandconditions, pretest, posttest, assignedtasks) VALUES "
                    + "('"
                    + student.getStudentId()
                    + "', '"
                    + student.getPassword()
                    + "', "
                    + (student.isIsSpecialStudent() ? 1 : 0)
                    + ","
                    + (student.isHasAcceptedTermsAndConditions() ? 1 : 0)
                    + ",'"
                    + student.getPreTest()
                    + "','"
                    + student.getPostTest()
                    + "','"
                    + student.getAssignedTasks()
                    + "')";  

            stmt.execute(query);
            stmt.close();
        } catch (SQLException ex) {
            System.err.println("Error.. inserting student information in the database!!");
            ex.printStackTrace();
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException ex1) {
                System.err.println("Error closing database connections..");
                ex1.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
