/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dt.persistent;

import dt.entities.database.Student;
import dt.entities.xml.FCIQuestions;
import dt.persistent.database.Students;


/**
 *
 * @author Rajendra
 */
public class DataManager {

    public static void insertNewStudent(Student s) {
        Students.insertNewStudent(s);
    }

 
    public void createTables() {

    }

    /**
     * Gives Student if matching student id and password.
     *
     * @param sudentId
     * @param password
     * @return
     */
    public static Student getStudent(String sudentId, String password) {
        return Students.getStudent(sudentId, password);
    }

    /**
     *
     * @param sudentId
     * @return
     */
    public static Student getStudent(String sudentId) {
        return Students.getStudent(sudentId);
    }

    /**
     * Updates student data in the database.
     *
     */
    public static void updateStudent(Student s) {
        if (s.isIsSpecialStudent()) {
            return;
        }
        Students.updateStudent(s);
    }

    /**
     *
     * @param s - student
     * @param taskId -task id
     * @return A task object.
     */
//    public static DTTask getTask(Student s, String taskId) {
//        return DTTasks.getTask(s, taskId);
//    }
    /**
     * TODO: working copy.. use xml handling module and cache..
     *
     * @param type
     * @return
     */
    public static FCIQuestions getPretestQuestions(String type) {

        String fciQuestionFile = " "; //get that....from configuration manager

        //replaces all but the alphabets and digits from the file path
        // Search in Cache and return if found. If it fails, load from the file
        return null;
    }

    public static void main(String[] args) {

        DataManager dbManager = new DataManager();
        //dbManager.createTables();

        Student demoStudent = new Student();
        demoStudent.setStudentId("user");
        demoStudent.setPassword("pass");
        demoStudent.setAssignedTasks("FM_LV03_PR06,FM_LV03_PR07,FM_LV03_PR08");
        Students.insertNewStudent(demoStudent);

        System.out.println("Done!");

    }
}
