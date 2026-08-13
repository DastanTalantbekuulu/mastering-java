/**
 *    These materials are confidential and proprietary to Intellect Design Arena Ltd. and no part of these materials
 * should be reproduced, published, transmitted or distributed in any form or by any means, electronic, mechanical,
 * photocopying, recording or otherwise, or stored in any information storage or retrieval system of any nature nor
 * should the materials be disclosed to third parties or used in any other manner for which this is not authorized,
 * without the prior express written authorization of Intellect Design Arena Ltd.
 *
 * <p>Title             : Action API SDK </p>
 * <p>Description       : Part of Action API Commons Module - Core package. </p>
 * <p>Tag No.           : DAAS-4 </p>
 * <p>Copyright         : Copyright 2017 Intellect Design Arena Limited. All rights reserved.</p>
 * <p>Company           : Intellect Design Arena Limited</p>
 * <p>Date of Creation  : Jul 2, 2017 </p>
 * <p>Source            : StandardRequest.java </p>
 * <p>Package           : com.igtb.api.action.commons.models.request </p>
 * 
 * @author Hiren
 * 
 * @version 1.0
 * 
 * <p>------------------------------------------------------------------------------------</p>
 * <p>MODIFICATION HISTORY:</p>
 * <p>------------------------------------------------------------------------------------</p>
 * <p>SERIAL    AUTHOR      DATE              TAG            DESCRIPTION</p>
 * <p>   1.0    Hiren       Jul 2, 2017       DAAS-4         First Version.
 * <p>------------------------------------------------------------------------------------</p>
 */
package com.programs.challenges.util;

import java.io.Serializable;
import java.util.List;



/**
 * A POJO representing API Requests using the generic type &lt;T&gt;, where T represents payload POJO - example, StandardRequest&lt;SweepStructure&gt;, StandardRequest&lt;LoanAgreement&gt;.
 *
 * @param <T> the generic type representing Resource POJO, e.g. SweepStructure, LoanAgreement
 */
public class StandardRequest<T> implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3127346496908420896L;
	
	/** The request header object, to hold Http header specific information. */
	protected StandardRequestHeader requestHeader;
	
	/** The request context object, to hold contextual information for API channel. */
	protected StandardRequestContext context;
	
	/**  The actual resource payload (SweepStructure, LoanAgreement POJOs). */
	protected T payload;
	
	/**
	 * Instantiates a new empty object.
	 */
	public StandardRequest(){
		requestHeader = new StandardRequestHeader();
		context = new StandardRequestContext();
	}

	/**
	 * Instantiates a new standard API request object, with given parameters.
	 *
	 * @param requestHeader the request header object
	 * @param context the request context object
	 * @param payload the resource payload object
	 */
	public StandardRequest(final StandardRequestHeader requestHeader,
			final StandardRequestContext context, final T payload) {
		super();
		this.requestHeader = requestHeader;
		this.context = context;
		this.payload = payload;
	}

	/**
	 * Gets the request header object, which contains Http header specific information.
	 *
	 * @return the request header
	 */
	public StandardRequestHeader getRequestHeader() {
		return requestHeader;
	}

	/**
	 * Gets the context object, which contains contextual information for API channel.
	 *
	 * @return the context
	 */
	public StandardRequestContext getContext() {
		return context;
	}

	/**
	 * Gets the payload object, which represents resource specific POJOs (e.g. SweepStructure, LoanAgreement, FundTransfer...).
	 *
	 * @return the payload
	 */
	public T getPayload() {
		return payload;
	}

	/**
	 * Sets the request header.
	 *
	 * @param requestHeader the new request header
	 */
	public void setRequestHeader(final StandardRequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	public void setContext(final StandardRequestContext context) {
		this.context = context;
	}

	/**
	 * Sets the payload.
	 *
	 * @param payload the new payload
	 */
	public void setPayload(final T payload) {
		this.payload = payload;
	}

	/**
	 *  (non-Javadoc).
	 *
	 * @return the string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StandardRequest [requestHeader=" + requestHeader + ", context="
				+ context + ", payload=" + payload + "]";
	}

	
	/**
	 * Additional shorthand functions.
	 *
	 * @return the string
	 */

	/**
	 * Shorthand method to get organisation Id, from context object.
	 *
	 * @return the organisationId
	 */
	public String organisationId() {
		return (context==null) ? null : context.getOrganisationId();
	}

	/**
	 * Shorthand method to get corporate domain, from requestHeader object.
	 *
	 * @return the domainId
	 */
	public String domainId() {
		return (requestHeader==null) ? null : requestHeader.getDomainId();
	}

	/**
	 * Shorthand method to get channel id, from context object.
	 *
	 * @return the channelId
	 */
	public String channelId() {
		return (context==null) ? null : context.getChannelId();
	}

	/**
	 * Shorthand method to get channel sequence id, from context object.
	 *
	 * @return the channelSeqId
	 */
	public String channelSeqId() {
		return (context==null) ? null : context.getChannelSeqId();
	}

	/**
	 * Shorthand method to get action, from context object.
	 *
	 * @return the action
	 */
	public String action() {
		return (context==null) ? null : context.getAction();
	}

	/**
	 * Shorthand method to get type of the request, from context object.
	 *
	 * @return the type
	 */
	public String type() {
		return (context==null) ? null : context.getType();
	}

	/**
	 * Shorthand method to get remarks, from context object.
	 *
	 * @return the remarks
	 */
	public String remarks() {
		return (context==null) ? null : context.getRemarks();
	}

	/**
	 * Shorthand method to get userId, from context object.
	 *
	 * @return the userId
	 */
	public String userId() {
		return (context==null) ? null : context.getUserId();
	}

	/**
	 * Shorthand method to get IDP (auth0) userId, from context object.
	 *
	 * @return the idpUserId
	 */
	public String idpUserId() {
		return (context==null) ? null : context.getIdpUserId();
	}

	/**
	 * Shorthand method to get serviceKey, from context object.
	 *
	 * @return the serviceKey
	 */
	public String serviceKey() {
		return (context==null) ? null : context.getServiceKey();
	}

	/**
	 * Shorthand method to get userAction, from context object.
	 *
	 * @return the userAction
	 */
	public String userAction() {
		return (context==null) ? null : context.getUserAction();
	}

	/**
	 * Shorthand method to get Domain Sequence Id, from context.DomainResource object. 
	 *
	 * @return the domainSeqId
	 */
	public String domainSeqId() {
		return (context==null || context.getDomainResource()==null) ? null : context.getDomainResource().getSeqId();
	}

	/**
	 * Shorthand method to get Domain Effective Date, from context.DomainResource object.
	 *
	 * @return the domainEffDate
	 */
	public String domainEffDate() {
		return (context==null || context.getDomainResource()==null) ? null : context.getDomainResource().getEffDate();
	}

	/**
	 * Shorthand method to get list of related channel requests, from context object.
	 *
	 * @return the list of related channel requests
	 */
	public List<ChannelRequestRelation> relatedChnlRequests() {
		return (context==null) ? null : context.getRelatedChnlRequests();
	}

}
