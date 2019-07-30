package com.qc.mongo.batch.config;

import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.qc.mongo.batch.model.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	@Autowired
	private MongoTemplate template;
	
	@Bean
	public MongoItemReader<User> itemReader(){
		MongoItemReader<User> itemReader = new MongoItemReader<>();
		itemReader.setTemplate(template);
		itemReader.setQuery("{}");
		itemReader.setTargetType(User.class);
		itemReader.setSort(new HashMap<String, Sort.Direction>(){
			private static final long serialVersionUID = 1L;
		{			
			put("name",Direction.DESC);
		}
		});
		return itemReader;
	}
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			org.springframework.batch.item.ItemReader<User> itemReader,
			org.springframework.batch.item.ItemProcessor<User, User> itemProcessor,
			org.springframework.batch.item.ItemWriter<User> itemWriter) {
		
		Step step = stepBuilderFactory.get("CSV-FILE-LOAD")
				.<User,User>chunk(100)
				.reader(itemReader)
				//.processor(itemProcessor)
				.writer(itemWriter)
				.build();
			
		
		return jobBuilderFactory.get("CSV-LOAD")
		.incrementer(new RunIdIncrementer())
		.start(step)
		.build();
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
						setNames(new String[] { "name"});
					}
				});
			}
		});
		return writer;
	}
	
}
