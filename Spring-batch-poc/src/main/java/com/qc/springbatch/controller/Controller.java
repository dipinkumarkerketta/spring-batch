package com.qc.springbatch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@GetMapping("/load")
	public BatchStatus uploadData() {
		BatchStatus status = null;
		try {
			Map<String, JobParameter> param = new HashMap<>();
			param.put("time", new JobParameter(System.currentTimeMillis()));
			JobParameters jobParam = new JobParameters(param);
			JobExecution jobExecution = jobLauncher.run(job, jobParam);			
			
			while(jobExecution.isRunning()) {
				System.out.println("...");
			}
			
			status = jobExecution.getStatus();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
}
