/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.adminFrame;

/**
 *
 * @author Pritam Bera
 */
class Teachers {
    private String name,position,email;
    
    public Teachers(String name,String position,String email){
        this.name = name;
        this.position = position;
        this.email = email;
    }
    
    public String getName(){
        return name;
    }
    
    public String getPosition(){
        return position;
    }
    
    public String getEmail(){
        return email;
    }
}
