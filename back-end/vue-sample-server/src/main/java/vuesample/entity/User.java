package vuesample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
@Data
@Entity
public class User {

	@Id
	@Column(name = "uuid", nullable = false, length = 100)
	private String uuid;

	@Column(name = "surname", nullable = false, length = 100)
	private String surname;

	@Column(name = "givenname", nullable = false, length = 100)
	private String givenname;

	@Column(name = "email", nullable = true, length = 50)
	private String email;

	@Column(name = "gender", nullable = true, length = 50)
	private String gender;

	@Column(name = "address", nullable = true, length = 100)
	private String address;

	@Column(name = "state", nullable = true, length = 50)
	private String state;

	@Column(name = "postcode", nullable = true, length = 50)
	private String postcode;

}
