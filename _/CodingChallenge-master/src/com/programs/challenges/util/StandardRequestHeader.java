/**
 * @author pranshu.shrivastava
 */
package com.programs.challenges.util;

import java.io.Serializable;


/**
 * A POJO used for holding API Request header information.
 */
public class StandardRequestHeader implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3642303191562327259L;
	
	/** The request id that comes in as header parameter, helps preventing duplicate request from API consumer. */
	protected String requestId;
	
	/** The corporate domain this API request belongs to. */
	protected String domainId;


	/**
	 * Instantiates a new empty object.
	 */
	public StandardRequestHeader() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	/**
	 * Instantiates a new object with given parameter.
	 *
	 * @param requestId the request id
	 * @param domainId the corporate domain
	 */
	public StandardRequestHeader(final String requestId, final String domainId) {
		super();
		this.requestId = requestId;
		this.domainId = domainId;
	}

	/**
	 * Gets the request id that comes in as header parameter, helps preventing duplicate request from API consumer.
	 *
	 * @return the request id
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * Gets the corporate domain this API request belongs to.
	 *
	 * @return the corporate domain id
	 */
	public String getDomainId() {
		return domainId;
	}

	/**
	 * Sets the request id.
	 *
	 * @param requestId the new request id
	 */
	public void setRequestId(final String requestId) {
		this.requestId = requestId;
	}

	/**
	 * Sets the corporate domain.
	 * 
	 * @param domainId corporate domain
	 */
	public void setDomainId(final String domainId) {
		this.domainId = domainId;
	}

	/**
	 * Override toString() method of object class.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "StandardRequestHeader [requestId=" + requestId + ", domainId=" + domainId + "]";
	}
	
}
