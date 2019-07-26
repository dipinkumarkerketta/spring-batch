package com.qc.springbatch.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.qc.springbatch.entity.User;

@Component
public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Integer id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		User user = new User();
		user.setId(id);
		user.setName(name);
		return user;
	}

}
