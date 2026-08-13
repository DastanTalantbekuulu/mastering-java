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
 * <p>Source            : StandardRequestContext.java </p>
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
 * A POJO representing contextual information sent as part of API request or State Update Event.
 */
public class StandardRequestContext implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1920963939011788823L;
	
	/** The organisation id this API request is raised for. */
	protected String organisationId;
	
	/** The channel Id, representing platform/system this API request is originated from. */
	protected String channelId;
	
	/** The unique id representing an Action API request persisted in State Store. */
	protected String channelSeqId;

	/** The action requested, supported - {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_DRAFT},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_INITIATE},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_VALIDATE},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_REJECT},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_CANCEL},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_RELEASE},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_BE_ACK_SUCCESS},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_BE_REJECTED},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_PROCESSED},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_SA_REVALIDATE}. */
	protected String action;
	
	/** The key indicating what prod/subprod, this action API request is invoked for. */
	protected String serviceKey;
	
	/** The key indicating what userAction (hold/unhold/suspend/revoke_suspension), this action API request is invoked for. */
	protected String userAction;
	
	/** The type of the request, supported - {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_TYPE_CREATE}, {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_TYPE_UPDATE}, {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_TYPE_DELETE}. */
	protected String type;
	
	/** Object containing information about specific Domain Resource e.g. SweepStructure, LoanAgreement, FundTransfer. */
	protected DomainResourceDetail domainResource;
	
	/** The remarks received as part of request (when request is rejected / cancelled). */
	protected String remarks;
	
	/** The user id received as part of request, who raised this request. */
	protected String userId;
	
	/** List of relations for this request, with other channel initiated requests. */
	protected List<ChannelRequestRelation> relatedChnlRequests;
	
	/** The IDP (auth0) user id received as part of request, who raised this request. */
	protected String idpUserId;
	
	
	/**
	 * Instantiates a new empty object.
	 */
	public StandardRequestContext(){
		domainResource = new DomainResourceDetail();
	}

	/**
	 * Instantiates a new object, based on parameters passed.
	 *
	 * @param organisationId the organisation id
	 * @param channelId the channel id
	 * @param channelSeqId the channel seq id
	 * @param action the action
	 * @param serviceKey the service key
	 * @param userAction the userAction
	 * @param type the type
	 * @param domainResource the domain resource
	 * @param remarks the remarks
	 * @param userId the user id
	 * @param relatedChnlRequests the list of related channel requests
	 * @param idpUserId the IDP (auth0) user id
	 */
	public StandardRequestContext(final String organisationId, final String channelId, final String channelSeqId, final String action,
			final String serviceKey, final String userAction, final String type, final DomainResourceDetail domainResource,
			final String remarks, final String userId, final List<ChannelRequestRelation> relatedChnlRequests,
			final String idpUserId) {
		super();
		this.organisationId = organisationId;
		this.channelId = channelId;
		this.channelSeqId = channelSeqId;
		this.action = action;
		this.serviceKey = serviceKey;
		this.userAction = userAction;
		this.type = type;
		this.domainResource = domainResource;
		this.remarks = remarks;
		this.userId = userId;
		this.relatedChnlRequests = relatedChnlRequests;
		this.idpUserId = idpUserId;
	}

	/**
	 * Gets the organisation Id this API request is raised for.
	 *
	 * @return the organisation id
	 */
	public String getOrganisationId() {
		return organisationId;
	}

	/**
	 * Gets the channel Id - representing platform/system this API request is originated from.
	 * 
	 * @return the channel Id
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * Gets the channel seq id - unique id representing an Action API request persisted in State Store.
	 *
	 * @return the channel seq id
	 */
	public String getChannelSeqId() {
		return channelSeqId;
	}

	/**
	 * Gets the action, supported - {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_DRAFT},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_INITIATE},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_VALIDATE},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_REJECT},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_CANCEL},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_RELEASE},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_BE_ACK_SUCCESS},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_BE_REJECTED},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_PROCESSED},
	 *  {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_STATE_SA_REVALIDATE}.
	 *
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Gets the type of request, supported - {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_TYPE_CREATE}, {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_TYPE_UPDATE}, {@value com.igtb.api.action.commons.util.CommonsConstants#REQUEST_TYPE_DELETE}.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the domain resource object.
	 *
	 * @return the domain resource object
	 */
	public DomainResourceDetail getDomainResource() {
		return domainResource;
	}

	/**
	 * Gets the remarks.
	 *
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the organisation Id.
	 * 
	 * @param organisationId organisation Id
	 */
	public void setOrganisationId(final String organisationId) {
		this.organisationId = organisationId;
	}

	/**
	 * Sets the channel Id.
	 * 
	 * @param channelId the channel Id - CBX
	 */
	public void setChannelId(final String channelId) {
		this.channelId = channelId;
	}

	/**
	 * Sets the channel seq id.
	 *
	 * @param channelSeqId the new channel seq id
	 */
	public void setChannelSeqId(final String channelSeqId) {
		this.channelSeqId = channelSeqId;
	}

	/**
	 * Sets the action.
	 *
	 * @param action the new action
	 */
	public void setAction(final String action) {
		this.action = action;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Sets the domain resource.
	 *
	 * @param domainResource the new domain resource
	 */
	public void setDomainResource(final DomainResourceDetail domainResource) {
		this.domainResource = domainResource;
	}

	/**
	 * Sets the remarks.
	 *
	 * @param remarks the new remarks
	 */
	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the service key.
	 *
	 * @return the service key
	 */
	public String getServiceKey() {
		return serviceKey;
	}

	/**
	 * Sets the service key.
	 *
	 * @param serviceKey the new service key
	 */
	public void setServiceKey(final String serviceKey) {
		this.serviceKey = serviceKey;
	}

	/**
	 * Gets the user action.
	 *
	 * @return the user action
	 */
	public String getUserAction() {
		return userAction;
	}

	/**
	 * Sets the user action.
	 *
	 * @param userAction the new user action
	 */
	public void setUserAction(final String userAction) {
		this.userAction = userAction;
	}
	
	/**
	 * Gets the list of related chnl requests.
	 *
	 * @return the list of related chnl requests
	 */
	public List<ChannelRequestRelation> getRelatedChnlRequests() {
		return relatedChnlRequests;
	}

	/**
	 * Sets the list of related chnl requests.
	 *
	 * @param relatedChnlRequests the new list of related chnl requests
	 */
	public void setRelatedChnlRequests(final List<ChannelRequestRelation> relatedChnlRequests) {
		this.relatedChnlRequests = relatedChnlRequests;
	}

	/**
	 * Gets the IDP (auth0) user id.
	 *
	 * @return the IDP (auth0) user id
	 */
	public String getIdpUserId() {
		return idpUserId;
	}

	/**
	 * Sets the IDP (auth0) user id.
	 *
	 * @param idpUserId the new IDP (auth0) user id
	 */
	public void setIdpUserId(final String idpUserId) {
		this.idpUserId = idpUserId;
	}

	/**
	 * Provides custom string representation of this object, by overriding toString() method of Object class.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "StandardRequestContext [organisationId=" + organisationId + ", channelId=" + channelId
				+ ", channelSeqId=" + channelSeqId + ", action=" + action + ", serviceKey=" + serviceKey
				+ ", userAction=" + userAction + ", type=" + type + ", domainResource=" + domainResource + ", remarks="
				+ remarks + ", userId=" + userId + ", relatedChnlRequests=" + relatedChnlRequests + ", idpUserId="
				+ idpUserId + "]";
	}

}
