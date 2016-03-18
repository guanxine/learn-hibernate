package cn.gx;

import cn.gx.model.many2one.Address;
import cn.gx.model.many2one.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * Created by always on 16/3/18.
 */
public class Many2One {


    private SessionFactory factory;

    @Before
    public void before(){

        factory = new Configuration().configure().buildSessionFactory();
    }

    @Test
    public void testCURD(){


        Address a1=new Address("山西省","运城市","平陆县","西大街");
        Address a2=new Address("福建省","福州市","闽侯县","福建工程学院");

        Employee e1=new Employee("shaonian","guanx",100,a1);

        Employee e2=new Employee("guanxin","guanx",100,a1);
        Employee e3=new Employee("bai","guanx",100,a2);
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




    /* Method to add an employee record in the database */
    public Integer addEmployee(Employee employee){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try{
            tx = session.beginTransaction();
            session.save(employee.getAddress());
            employeeID = (Integer) session.save(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return employeeID;
    }

    /* Method to list all the employees detail */
    public void listEmployees( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List<Employee> employees = session.createQuery("FROM Employee ").list();
            employees.forEach(employee -> {
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.println("  Salary: " + employee.getSalary());
                Address add = employee.getAddress();
                System.out.println("Address ");
                System.out.println("\tStreet: " +  add.getStreet());
                System.out.println("\tCity: " + add.getCity());
                System.out.println("\tState: " + add.getState());
                System.out.println("\tZipcode: " + add.getZipcode());
            });
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to update salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary ){
        Session session = factory.openSession();
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
    /* Method to delete an employee from the records */
    public void deleteEmployee(Integer EmployeeID){
        Session session = factory.openSession();
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
