package com.qc.springbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.qc.springbatch.entity.User;
import com.qc.springbatch.rowmapper.UserRowMapper;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	
	@Autowired
	DataSource datasource;
	
	@Autowired
	UserRowMapper userRowMapper;
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			org.springframework.batch.item.ItemReader<User> itemReader,
			org.springframework.batch.item.ItemProcessor<User, User> itemProcessor,
			org.springframework.batch.item.ItemWriter<User> itemWriter) {
		
		Step step = stepBuilderFactory.get("WRITE-CSV")
				.<User,User>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
			
		
		return jobBuilderFactory.get("CSV-WRITE")
		.incrementer(new RunIdIncrementer())
		.start(step)
		.build();
	}
	
	
	@Bean
	public JdbcCursorItemReader<User> itemReader(){
		JdbcCursorItemReader<User> itemReader = new JdbcCursorItemReader<>();
		itemReader.setDataSource(datasource);
		itemReader.setSql("SELECT id,name FROM USER");
		itemReader.setRowMapper(userRowMapper);
		return itemReader;
	}
	
	
	@Bean
	public DataSource datasource() {
		final DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("org.h2.Driver");
		datasource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
		datasource.setUsername("sa");
		return datasource;
	}
	
	@Bean
    public FlatFileItemWriter<User> itemWriter()
    {
     
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();
        writer.setResource(new ClassPathResource("user.csv"));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<User>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<User>() {
                    {
                        setNames(new String[] { "id", "name"});
                    }
                });
            }
        });
        return writer;
    }
}
