/**
 * 
 */
package com.prodyna.pac.conference.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

/**
 * Handler for exceptions in REST interface methods.
 * 
 * @author Andreas Heizenreder (andreas.heizenreder@prodyna.com)
 * 
 */
public class RestUnknowExceptionHandler {

	public static Response.ResponseBuilder handleUnknowException(Exception e) {
		Response.ResponseBuilder builder;

		Map<String, String> responseObj = new HashMap<String, String>();
		responseObj.put("error", e.getMessage());
		builder = Response.status(Response.Status.BAD_REQUEST).entity(
				responseObj);

		return builder;
	}
}
