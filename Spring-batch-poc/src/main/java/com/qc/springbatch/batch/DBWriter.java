package com.qc.springbatch.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qc.springbatch.entity.User;
import com.qc.springbatch.repository.UserRepository;

@Component
public class DBWriter implements ItemWriter<User>{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public void write(List<? extends User> user) throws Exception {
		System.out.println("User : "+user);
		userRepo.saveAll(user);
	}

}
