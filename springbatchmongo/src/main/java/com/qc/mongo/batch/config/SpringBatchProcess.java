package com.qc.mongo.batch.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.qc.mongo.batch.model.User;

@Component
public class SpringBatchProcess implements ItemProcessor<User, User> {

	@Override
	public User process(User user) throws Exception {
		return user;
	}

}
