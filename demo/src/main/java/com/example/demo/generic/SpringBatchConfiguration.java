package com.example.demo.generic;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JpaBatchConfigurer;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@CommonsLog
public class SpringBatchConfiguration {

    static class JpaBatchConfiguration {

        /*@Bean
        JpaBatchConfigurer batchConfigurer(BatchProperties properties, DataSource dataSource,
                                           @BatchDataSource ObjectProvider<DataSource> batchDataSource,
                                           ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers,
                                           EntityManagerFactory entityManagerFactory) {
            return new CustomBasicBatchConfigurer(properties, batchDataSource.getIfAvailable(() -> dataSource),
                    transactionManagerCustomizers.getIfAvailable(), entityManagerFactory);
        }*/

    }

}
