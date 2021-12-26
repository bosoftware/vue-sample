package vuesample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vuesample.entity.User;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

}
