package vuesample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vuesample.entity.User;
import vuesample.model.UserVo;
import vuesample.repository.UserRepository;
import vuesample.service.UserService;
import vuesample.util.QueryHelp;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@SuppressWarnings("unchecked")
	@Override
	public Page<User> findAll(UserVo criteria, Pageable pageable) {
		return userRepository.findAll(QueryHelp.query(criteria), pageable);
	}

	@Override
	public List<User> saveAll(Iterable<User> entities) {
		return userRepository.saveAll(entities);
	}
}
