package vuesample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vuesample.model.UserVo;
import vuesample.service.UserService;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */

@RestController
@RequestMapping("vuesample/openapi/v1")
public class UserController {

	@Autowired
	UserService userService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(value = "/users/query")
	@CrossOrigin
	public ResponseEntity getAllConfig(@RequestBody UserVo criteria,
			@PageableDefault(value = 200, sort = { "surname" }, direction = Sort.Direction.ASC) Pageable pageable) {
		return new ResponseEntity(userService.findAll(criteria, pageable), HttpStatus.OK);
	}

}
