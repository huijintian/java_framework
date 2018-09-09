package cn.tian.spring.jdbc;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by mengtian on 2018/9/9
 */
public class JDBCTest {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext();
        DataSource dataSource = new DriverManagerDataSource();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        int order = DataSourceUtils.CONNECTION_SYNCHRONIZATION_ORDER;
    }
}
