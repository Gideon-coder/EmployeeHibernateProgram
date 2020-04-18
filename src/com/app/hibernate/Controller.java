package com.app.hibernate;

import com.app.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;


public class Controller {
//    private static final Scanner input = new Scanner(System.in);
    private static SessionFactory factory = new Configuration()
                                             .configure("hibernate.cfg.xml")
                                             .addAnnotatedClass(Employee.class)
                                             .buildSessionFactory();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Please type in the action you wish to perform \nAdd, Query, Delete");
        String ans = input.next();

        if(ans.equalsIgnoreCase("Add")) {
            System.out.println("Enter Employee first name");
            String employeeFirstName = input.next();
            System.out.println("Enter Employee last name");
            String employeeLastName = input.next();
            System.out.println("Enter Employee company's name");
            String employeeCompanyName = input.next();

            saveEmployee(employeeFirstName, employeeLastName, employeeCompanyName);
        } else if(ans.equalsIgnoreCase("Query")) {
            System.out.println("Enter employee id to get details");
            int id = input.nextInt();
            queryEmployee(id);
        } else if(ans.equalsIgnoreCase("Delete")) {
            System.out.println("Enter employee id to delete employee");
        }
    }

    public static void queryEmployee(int id) {
        try {
            //getting a session from the session factory
            Session session = factory.getCurrentSession();

            //starting transaction on the section collected from the session factory
            session.beginTransaction();

            //query for the employee based on the id
            Employee employee = session.get(Employee.class, id);

            //check if employee exist by checking if employee is null if null display employee does not exist else display employee details
            if(employee == null) {
                System.out.println("Employee does not exist");
            } else {
                System.out.println(employee);
            }
        } finally {
            factory.close();
        }
    }

    public static void saveEmployee(String employeeFirstName, String employeeLastName, String employeeCompanyName) {



        try {
            //getting a session from the session factory
            Session session = factory.getCurrentSession();

            //starting transaction on the section collected from the session factory
            session.beginTransaction();

            //create and employee object based on users input
            System.out.println("Creating object");
            Employee employee = new Employee(employeeFirstName, employeeLastName, employeeCompanyName);

            //save input
            System.out.println("Saving object to database");
            session.save(employee);

            //commit transaction
            session.getTransaction().commit();

            System.out.println("Done");




        } finally {
            factory.close();
        }
    }
}
