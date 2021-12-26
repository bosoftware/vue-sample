package vuesample.model;

import lombok.Data;
import vuesample.annotation.Query;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
@Data
public class UserVo {
	@Query(type = Query.Type.EQUAL)
	private String uuid;

	@Query(type = Query.Type.INNER_LIKE)
	private String surname;

	@Query(type = Query.Type.INNER_LIKE)
	private String givenname;

	@Query(type = Query.Type.EQUAL)
	private String email;

	@Query(type = Query.Type.EQUAL)
	private String gender;

	@Query(type = Query.Type.INNER_LIKE)
	private String address;

	@Query(type = Query.Type.EQUAL)
	private String state;

	@Query(type = Query.Type.EQUAL)
	private String postcode;

}
