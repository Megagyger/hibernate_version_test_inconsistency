package test;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
@Transactional
@ContextConfiguration(classes = {RepoConfig.class})
public abstract class BaseTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    protected PlatformTransactionManager platformTransactionManager;
}
