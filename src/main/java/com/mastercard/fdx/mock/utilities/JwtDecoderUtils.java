package com.mastercard.fdx.mock.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
public class JwtDecoderUtils {

	private JwtDecoderUtils(){}

	private static final String ACCOUNT_ID = "account_id";

	public static JSONArray getAccountIdsFromToken(String jwtToken) {
		try {
			jwtToken = jwtToken.replace("Bearer ", "");
			log.info("------------ Decode JWT ------------");
		        String[] split_string = jwtToken.split("\\.");
		        String base64EncodedBody = split_string[1];
		        Base64 base64Url = new Base64(true);
		       
		        log.info("~~~~~~~~~ JWT Body ~~~~~~~");
		        String payload = new String(base64Url.decode(base64EncodedBody));
		        return getJsonField(payload, ACCOUNT_ID);
		}
		catch (Exception e) {
			log.error("No Account Ids found from Token or Invalid Token..."+e.getMessage());
			return null;
		}
	}
	
	 private static JSONArray getJsonField(String json, String jsonPath) {
		 JSONObject jsonObject = new JSONObject(json);
		 return  jsonObject.getJSONArray(jsonPath);
	    }

}
