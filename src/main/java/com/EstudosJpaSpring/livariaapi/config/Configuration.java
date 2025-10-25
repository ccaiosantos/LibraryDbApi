package com.EstudosJpaSpring.livariaapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;


    @Bean
    public DataSource datasource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setPassword(password);
        ds.setUsername(username);
        ds.setDriverClassName(driver);
        return ds;

    }
    //@Bean
    public DataSource hikariDatasource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setPassword(password);
        config.setUsername(username);
        config.setDriverClassName(driver);
        config.setMaximumPoolSize(10); //liberar 10 conexões para ter mais usuários
        config.setMinimumIdle(1); //minimo de conexões liberados, tamanho ninicial do pool
        config.setPoolName("Librarydb-pool"); // nome de quando rodar
        config.setMaxLifetime(600000); //tem que ser em milisegundos
        config.setConnectionTimeout(100000);//tempo que ele vai obeter tempo que vai gastar para obter, se passar mais que isso vai matar a conexão e vai tentar criar outra
        config.setConnectionTestQuery("select 1"); //query de teste
         return new HikariDataSource(config);

    }
}
