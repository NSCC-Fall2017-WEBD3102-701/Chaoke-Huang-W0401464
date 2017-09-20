package com.nscc.webd3102;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Hello world!
 *
 */

public class App 
{
    public static void main(String[] args) {
        Employee JaneDoe = new Employee("Jane Doe","HR",45000);
        Employee JohnPublic = new Employee("John Public","IT",250000);
        Employee AlisonSmith = new Employee("Alison Smith","Accounting",1500000);
        Employee XavierJones = new Employee("Xavier Jones","CEO",200000);
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(JaneDoe);
        employees.add(JohnPublic);
        employees.add(AlisonSmith);
        employees.add(XavierJones);
        Collections.sort(employees);
        Collections.reverse(employees);

        System.out.println("Name----------------Department----------Salary--------------------");

        for (Employee o:employees) {
            System.out.println(o.toString());

        }


    }

}

