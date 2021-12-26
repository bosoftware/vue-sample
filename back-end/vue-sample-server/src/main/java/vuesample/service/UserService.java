package vuesample.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vuesample.entity.User;
import vuesample.model.UserVo;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
public interface UserService {
	Page<User> findAll(UserVo criteria, Pageable pageable);

	List<User> saveAll(Iterable<User> entities);
}
