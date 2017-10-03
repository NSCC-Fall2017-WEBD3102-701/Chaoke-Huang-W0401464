package com.webd3102;

public class Student {
    private String firstName;
    private String lastName;
    private String studentNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    Student(String firstName,String lastName,String studentNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        return;
    }
    public Student(){
        super();
    }
    @Override
    public String toString(){

       return "First Name: " + this.getFirstName() + ", Last Name: " + this.getLastName() + ", Student Number: "+this.getStudentNumber();

    }


}
