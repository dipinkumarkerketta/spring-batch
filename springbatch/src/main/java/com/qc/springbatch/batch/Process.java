package com.qc.springbatch.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.qc.springbatch.entity.User;

@Component
public class Process implements ItemProcessor<User, User> {

	@Override
	public User process(User user) throws Exception {
		String username = user.getName();
		user.setName(username.toUpperCase());
		return user;
	}

}
