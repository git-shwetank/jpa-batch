package com.example.demo.generic;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.Jackson2ExecutionContextStringSerializer;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JpaBatchConfigurer;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.PropertyMapper;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

public class CustomBasicBatchConfigurer extends JpaBatchConfigurer {

    private final BatchProperties properties;

    private final DataSource dataSource;

    private ExecutionContextSerializer serializer;

    protected CustomBasicBatchConfigurer(BatchProperties properties,
                                         DataSource dataSource,
                                         TransactionManagerCustomizers transactionManagerCustomizers,
                                         EntityManagerFactory entityManagerFactory) {
        super(properties, dataSource, transactionManagerCustomizers, entityManagerFactory);
        this.properties = properties;
        this.dataSource = dataSource;
        this.serializer = getSerializer();
    }


    public ExecutionContextSerializer getSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JodaModule());

        Jackson2ExecutionContextStringSerializer serializer = new Jackson2ExecutionContextStringSerializer();
        serializer.setObjectMapper(objectMapper);

        return serializer;
    }

    @Override
    protected JobExplorer createJobExplorer() throws Exception {
        PropertyMapper map = PropertyMapper.get();
        JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
        factory.setDataSource(this.dataSource);
        map.from(this.properties::getTablePrefix).whenHasText().to(factory::setTablePrefix);
        factory.afterPropertiesSet();
        factory.setSerializer(this.serializer);
        return factory.getObject();
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        PropertyMapper map = PropertyMapper.get();
        map.from(this.dataSource).to(factory::setDataSource);
        map.from(this::determineIsolationLevel).whenNonNull().to(factory::setIsolationLevelForCreate);
        map.from(this.properties::getTablePrefix).whenHasText().to(factory::setTablePrefix);
        map.from(this::getTransactionManager).to(factory::setTransactionManager);
        factory.setSerializer(this.serializer);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

}
