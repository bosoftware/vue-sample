package vuesample.util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Bo Wang
 *
 *         vue-sample-server 25 Dec 2021
 */
public class JsonUtils {

	static ObjectMapper objectMapper = new ObjectMapper();
	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
	}

	public static String getString(Object obj) throws JsonProcessingException {

		return objectMapper.writeValueAsString(obj);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getObject(String jsonString, Class cls) throws IOException {
		return objectMapper.readValue(jsonString, cls);
	}

}
