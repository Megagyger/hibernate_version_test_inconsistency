package test;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.secure.internal.JaccPreDeleteEventListener;
import org.hibernate.secure.internal.JaccPreInsertEventListener;
import org.hibernate.secure.internal.JaccPreLoadEventListener;
import org.hibernate.secure.internal.JaccPreUpdateEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
@Transactional
public class RepoConfig {

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";


    @Autowired
    private SessionFactory sessionFactory;

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(sessionFactory);
        return hibernateTransactionManager;
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:mariadb://localhost:3308/test?useUnicode=true&characterEncoding=utf8", "root", "root");
    }

    @Bean
    @Primary
    public SessionFactory sessionFactory(DataSource dataSource) {
        ResourceLoader resourceLoader = new PathMatchingResourcePatternResolver();
        LocalSessionFactoryBuilder sfb = new LocalSessionFactoryBuilder(dataSource, resourceLoader,
                new MetadataSources((new BootstrapServiceRegistryBuilder())
                        .applyClassLoader(resourceLoader.getClassLoader()).build()));
        sfb.scanPackages(getScanPackages().toArray(new String[0]));
        sfb.addProperties(getHibernateProperties());

        SessionFactoryImpl sessionFactory = ((SessionFactoryImpl) sfb.buildSessionFactory());

        final EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);

        registry.prependListeners(EventType.PRE_INSERT, preInsertEventListener());
        registry.prependListeners(EventType.PRE_UPDATE, preUpdateEventListener());
        registry.prependListeners(EventType.PRE_LOAD, preLoadEventListener());
        registry.prependListeners(EventType.PRE_DELETE, preDeleteEventListener());

        return sessionFactory;
    }

    /**
     * To be transformed to abstract and reimplemented in each apps using
     */
    protected List<String> getScanPackages() {
        return Arrays.asList("test");
    }

    @Bean
    protected JaccPreInsertEventListener preInsertEventListener() {
        return new JaccPreInsertEventListener();
    }

    @Bean
    protected JaccPreUpdateEventListener preUpdateEventListener() {
        return new JaccPreUpdateEventListener();
    }

    @Bean
    protected JaccPreLoadEventListener preLoadEventListener() {
        return new JaccPreLoadEventListener();
    }


    @Bean
    protected JaccPreDeleteEventListener preDeleteEventListener() {
        return new JaccPreDeleteEventListener();
    }


    private Properties getHibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("max_fetch_depth", "2");
        hibernateProperties.setProperty(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "false");

        hibernateProperties.setProperty("hibernate.hbm2ddl.auto ", "create");

        hibernateProperties.setProperty("hibernate.query.substitutions", "true 'Y', false 'N'");
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "true");
        hibernateProperties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
        hibernateProperties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
        hibernateProperties.setProperty("hibernate.cache.use_structured_entries", "true");
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", "true");
        hibernateProperties.setProperty("hibernate.id.new_generator_mappings", "false");
        hibernateProperties.setProperty("org.hibernate.envers.audit_table_suffix", "_history");
        hibernateProperties.setProperty("org.hibernate.envers.revision_field_name", "history_id");
        hibernateProperties.setProperty("org.hibernate.envers.revision_type_field_name", "history_type");
        return hibernateProperties;
    }

}
