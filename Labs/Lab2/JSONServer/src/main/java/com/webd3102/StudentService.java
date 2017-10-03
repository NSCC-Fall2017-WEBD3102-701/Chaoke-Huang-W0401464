package com.webd3102;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("students")
public class StudentService {
    private static ArrayList<Student> students = new ArrayList<>();

    private static ArrayList<Student> getStudents() {
        if (students.size() == 0) {
            students.add(new Student("Chaoke", "Huang", "W0401464"));
            students.add(new Student("Derrick", "Ma", "W0401544"));
            students.add(new Student("Ronan", "O'Driscoll", "W0401544"));
        }
        return students;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public static ArrayList<Student> getAllStudents() {
        students = getStudents();
        return students;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addStudent(Student student) {
        student = new Student("I am", "New", "W04052546");
        students.add(student);
        return Response.status(201).entity(student.toString()).build();
    }

}

