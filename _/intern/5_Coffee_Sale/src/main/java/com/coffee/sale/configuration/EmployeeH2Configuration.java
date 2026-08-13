package com.coffee.sale.configuration;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages ="com.coffee.sale.repository.jpa.employee",
        entityManagerFactoryRef = "employeeEntityManagerFactory",
        transactionManagerRef = "employeeTransactionManager"
)
public class EmployeeH2Configuration {

    @Value("${spring.employee-datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.employee-datasource.h2.url}")
    private String url;
    @Value("${spring.employee-datasource.h2.username}")
    private String username;
    @Value("${spring.employee-datasource.h2.password}")
    private String password;

    @Bean
    public LocalContainerEntityManagerFactoryBean employeeEntityManagerFactory() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(employeeDataSource());
        em.setPackagesToScan("com.coffee.sale.entity.employee");
        em.setJpaVendorAdapter(adapter);
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource employeeDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager employeeTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(employeeEntityManagerFactory().getObject());
        return transactionManager;
    }
}
