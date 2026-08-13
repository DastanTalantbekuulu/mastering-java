/**
 * @author pranshu.shrivastava
 *  
 */
package com.programs.challenges.util;

import org.apache.camel.component.jackson.JacksonDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The utility class, having functions that can convert JSON data into POJO and vice versa.
 * 
 * It uses Fasterxml Jackson library for JSON handling.
 */
public final class JSONHandler {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(JSONHandler.class);
	
	/** The object mapper, used later for JSON conversion. */
	private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();
	
	/** The JsonDataFormat, used in Camel route for JSON conversion. */
	private static final JacksonDataFormat JKSN_DATA_FMT = new JacksonDataFormat();
	
	
	static {
		OBJ_MAPPER.configure(Feature.IGNORE_UNDEFINED, true);
		OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OBJ_MAPPER.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		//OBJ_MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		OBJ_MAPPER.setSerializationInclusion(Include.NON_NULL);
		
		JKSN_DATA_FMT.disableFeature(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		JKSN_DATA_FMT.disableFeature(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
		//JKSN_DATA_FMT.disableFeature(SerializationFeature.WRITE_NULL_MAP_VALUES);
		JKSN_DATA_FMT.setInclude("NON_NULL");
	}
	
	/**
	 * Instantiates a new JSON handler.
	 */
	private JSONHandler() {
		// constructor intentionally kept empty
	}
	
	/**
	 * Converts given POJO into JSON String.
	 * 
	 * Example,
	 * <pre><code>
	 * 	// Create POJO
	 * 	StandardResponse resObj = new StandardResponse();
	 * 	resObj.setResCode(CommonsConstants.ERR_CODE_401_UNAUTH);
	 * 	resObj.setResMsg(CommonsConstants.ERR_STR_401_UNAUTH);
	 *  
	 * 	// Convert POJO to JSON String
	 * 	String jsonStr = JSONHandler.<b>toJson</b>(jsonObj);
	 * </code></pre>
	 *
	 * @param object to be transformed to JSON string
	 * @return the JSON string, or null in case of exception
	 */
	public static String toJson(final Object object) {
		String payloadJson = null;
		try {
			payloadJson = OBJ_MAPPER.writeValueAsString(object);
		} catch (Exception ex) {
			payloadJson = null;
			logger.error("{} - Exception converting Object to Json {}", "SYS_UNSUP_FUNC", object);
			logger.error(ex.getMessage(), ex);
		}
		return payloadJson;
	}
	
	
	/**
	 * Converts JSON string to Object of type specified as argument.
	 * 
	 * Example,
	 * <pre><code>
	 * 	// Create JSON String
	 * 	String payloadJson = "{ 'name' : 'John', 'surname' : 'Smith' }";
	 *  
	 * 	// Convert JSON String to POJO of type User
	 * 	User user = JSONHandler.<b>fromJson</b>(payloadJson, User.class);
	 * </code></pre>
	 *
	 * @param <T> the generic type
	 * @param payloadJson the JSON string data to be transformed to POJO
	 * @param type the type of object to be returned
	 * @return the transformed object of type T, or null in case of exception
	 */
	public static <T> T fromJson(final String payloadJson, final Class<T> type) {
		T payloadObj = null;
		try {
			payloadObj = OBJ_MAPPER.readValue(payloadJson, type);
		} catch (Exception ex) {
			payloadObj = null;
			logger.error("{} - Exception converting Json to Object of type {} - {}", "SYS_UNSUP_FUNC", type, payloadJson);
			logger.error(ex.getMessage(), ex);
		}
		return payloadObj;
	}
	
	
	
	/**
	 * Converts JSON string to Object of type specified as argument. This is useful when type refers to generic, e.g. User&lt;Guest&gt; (instead of simple User.class).
	 * 
	 * Example,
	 * <pre><code>
	 * 	// Create JSON String
	 * 	String payloadJson = "{ 'name' : 'John', 'surname' : 'Smith' }";
	 *  
	 * 	// Convert JSON String to POJO of type User&lt;Guest&gt;
	 * 	TypeReference&lt;User&lt;Guest&gt;&gt; ref = new TypeReference&lt;User&lt;Guest&gt;&gt;() {};
	 * 	User&lt;Guest&gt; guestUser = JSONHandler.<b>fromJson</b>(payloadJson, ref);
	 * </code></pre>
	 *
	 * @param <T> the generic type
	 * @param payloadJson the JSON string data to be transformed to POJO
	 * @param ref the type of object to be returned
	 * @return the transformed object of type T
	 */
	public static <T> T fromJson(final String payloadJson, final TypeReference<T> ref) {
		T payloadObj = null;
		try {
			payloadObj = OBJ_MAPPER.readValue(payloadJson, ref);
		} catch (Exception ex) {
			payloadObj = null;
			logger.error("{} - Exception converting Json to Object of type {} - {}", "SYS_UNSUP_FUNC",ref.getClass().getComponentType(), payloadJson);
			logger.error(ex.getMessage(), ex);
		}
		return payloadObj;
	}
	
	
	
	/**
	 * Return OBJ_MAPPER object instance.
	 *
	 * @return the object mapper
	 */
	public static ObjectMapper getObjectMapper() {
		return OBJ_MAPPER;
	}

	/**
	 * return data in jackson data format.
	 *
	 * @return the jackson data format
	 */
	public static JacksonDataFormat jdf() {
		return JKSN_DATA_FMT;
	}

}
