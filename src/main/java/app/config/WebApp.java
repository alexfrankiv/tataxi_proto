package app.config;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.enterprise.inject.Model;
import javax.sql.DataSource;

/**
 * Created by Alex_Frankiv on 14.02.2017.
 */
@Configuration
@ComponentScan(basePackages = "app")
@PropertySources({
        @PropertySource("classpath:google_api.properties"),
        @PropertySource("classpath:db.properties")
})
@EnableWebMvc
@EnableTransactionManagement
@EnableCaching
public class WebApp extends WebMvcConfigurerAdapter{

    @Autowired
    Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/presentation/**").addResourceLocations("/presentation/");
    }

    @Bean
    public ViewResolver jspViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/presentation/view/");
        resolver.setSuffix(".jsp");
        resolver.setContentType("text/html; charset=UTF-8");
        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public DataSource dataSource(){
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setServerName(env.getProperty("db.server.name"));
        dataSource.setDatabaseName(env.getProperty("db.name"));
        dataSource.setUser(env.getProperty("db.login"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setMaxConnections(Integer.valueOf(env.getProperty("db.connections")));
        return dataSource;
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager txManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public Md5PasswordEncoder passwordEncoder(){
        return new Md5PasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
