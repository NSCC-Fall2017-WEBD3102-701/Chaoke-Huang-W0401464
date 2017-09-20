package com.nscc.webd3102;

public class Employee implements Comparable<Employee> {
    private String name;
    private String department;
    private Double salary = null;

    public Employee(String name, String department, double salary) {

        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public Employee(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int compareTo(Employee o) {
        if (this.salary == null && o.salary != null) {
            return -1;
        } else if (o.salary == null && this.salary != null) {
            return 1;
        } else {

            if (this.salary == o.salary) {
                if (this.name.compareTo(o.name) <= 0) {
                    return 1;
                } else {
                    return -1;
                }

            } else if (this.salary > o.salary) {
                return 1;
            } else {
                return -1;
            }
        }
    }


    public String toString() {

        String nameStr = String.format("%1$-20s", this.name);
        String deptStr = String.format("%1$-20s", this.department);
        String salaryStr;
        if (this.salary == null) {
            salaryStr = String.format("%1$-20s", "null");
        } else {
            salaryStr = String.format("%,.0f", this.salary);

        }
        return nameStr + deptStr + salaryStr;

    }

}
