package vuesample.service.impl;

import java.io.InputStream;
import java.util.Arrays;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import vuesample.entity.User;
import vuesample.service.ImportTestDataService;
import vuesample.service.UserService;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
@Service
@Slf4j
public class ImportTestDataServiceImpl implements ImportTestDataService {

	@Autowired
	UserService userService;

	@Override
	public void importTestData() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream is = Test.class.getClassLoader().getResourceAsStream("testdata/people.json");
			User[] userlist = mapper.readValue(is, User[].class);
			userService.saveAll(Arrays.asList(userlist));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

}
