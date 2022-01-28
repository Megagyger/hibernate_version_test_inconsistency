package test;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class HibernateTest extends BaseTest{

    @Autowired
    private SessionFactory sessionFactory;

    @Test(expected = IllegalStateException.class)
    public void test1(){
        Customer customer = new Customer();
        sessionFactory.getCurrentSession().persist(customer);

        AccountingCustomer accountingCustomer = new AccountingCustomer();
        accountingCustomer.setCustomer(customer);
        customer.setAccountingCustomer(accountingCustomer);

        sessionFactory.getCurrentSession().flush();
    }

    @Test
    public void test2(){
        Customer customer = new Customer();
        sessionFactory.getCurrentSession().persist(customer);

        AccountingCustomerOther accountingCustomerOther = new AccountingCustomerOther();
        customer.setAccountingCustomerOther(accountingCustomerOther);

        sessionFactory.getCurrentSession().persist(customer);
        sessionFactory.getCurrentSession().flush();
    }

}
