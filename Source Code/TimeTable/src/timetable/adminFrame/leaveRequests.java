/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.adminFrame;

/**
 *
 * @author ASUS
 */
public class leaveRequests {
    private String teacher_name;
    private String start_date;
    private String end_date;
    private String substitute_teacher;
    
    public leaveRequests(String teacher_name, String start_date, String end_date, String substitute_teacher)
    {
        this.teacher_name = teacher_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.substitute_teacher = substitute_teacher;
    }
    
    public String getTeacherName()
    {
       return teacher_name;
    }
    
    public String getStartDate()
    {
       return start_date;
    }
    
    public String getEndDate()
    {
       return end_date;
    }
    
    public String getSubstituteTeacher()
    {
       return substitute_teacher;
    }
}
