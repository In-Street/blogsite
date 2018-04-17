package cyf.blog.dao.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 数据源配置
 */
@Configuration
//为了支持注解事务，增加了@EnableTransactionManagement注解，并且反回了一个PlatformTransactionManagerBean
@EnableTransactionManagement
@MapperScan(basePackages ="cyf.blog.dao.mapper",sqlSessionFactoryRef = "primarySqlSessionFactory")
@Slf4j
@Component
public class DataSourceConfiguration {

    @Bean(name="primaryDataSource",destroyMethod = "close")
    @ConfigurationProperties(prefix="hikari.datasource.primary")
    @Primary
    //设置 @Configuration 中@Bean 的加载顺序 或者通过参数调用的方式决定谁先加载
//    @Order(value = )
    public DataSource primaryDataSource() {
        log.info("-------------------- primaryDataSource init ---------------------");
        return new HikariDataSource();
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public PlatformTransactionManager primaryTransactionManager() {
        log.info("-------------------- primaryTransactionManager init ---------------------");
        return new DataSourceTransactionManager(primaryDataSource());
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
        sessionFactory.setTypeAliasesPackage("cyf.blog.dao.model");

        log.info("-------------------- primarySqlSessionFactory init ---------------------");
        return sessionFactory.getObject();
    }

    @Bean(name="primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        log.info("-------------------- primarySqlSessionTemplate init ---------------------");
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}