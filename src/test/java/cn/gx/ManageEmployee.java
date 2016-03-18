package cn.gx;


import cn.gx.model.Employee;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by always on 16/3/18.
 */
public class ManageEmployee {

    private SessionFactory sessionFactory;

    @Before
    public void before(){

         sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    

    @Test
    public void testCURD(){

        
        Employee e1=new Employee("baijuan", "Ali", 1000);
        Employee e2=new Employee("shaonian", "Das", 5000);
        Employee e3=new Employee("guanxin", "Paul", 10000);
      /* Add few employee records in database */
        Integer empID1 = addEmployee(e1);
        Integer empID2 = addEmployee(e2);
        Integer empID3 = addEmployee(e3);

      /* List down all the employees */
        listEmployees();

      /* Update employee's records */
        updateEmployee(empID1, 5000);

      /* Delete an employee from the database */
        deleteEmployee(empID2);

      /* List down new list of the employees */
        listEmployees();
    }

    /* hod to CREATE an employee in the database */
    public Integer addEmployee(Employee employee){


        Session session=sessionFactory.openSession();
        Transaction tx=null;
        Integer id=null;
        try{
           tx=session.beginTransaction();
            id= (Integer) session.save(employee);
        }catch (HibernateException ex){
            if (tx!=null) tx.rollback();
            ex.printStackTrace();
        }finally {
            session.close();
        }

        return id;
    }


    /* hod to  READ all the employees */
    public void listEmployees( ){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List<Employee> employees = session.createQuery("FROM Employee").list();
             employees.forEach(employee->{
                 System.out.print("First Na " + employee.getFirstName());
                 System.out.print("  Last Na " + employee.getLastName());
                 System.out.println("  Salary: " + employee.getSalary());
             });
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* hod to UPDATE salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary ){
        Session session =sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Employee employee =
                    (Employee)session.get(Employee.class, EmployeeID);
            employee.setSalary( salary );
            session.update(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* hod to DELETE an employee from the records */
    public void deleteEmployee(Integer EmployeeID){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Employee employee =
                    (Employee)session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }




}