/**
 * @author pranshu.shrivastava
 * 
 */
package com.programs.challenges.util;

import java.io.Serializable;


/**
 * A POJO representing relation of one channel request, with other channel initiated requests.
 */
public class ChannelRequestRelation implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2458122495747607882L;

	/** The type of the relation with other channel request. edit-source - indicates the related request is the one, which is taken as source of input for editing */
	protected String relType;
	
	/** The id of related channel request. */
	protected String channelSeqId;

	
	/**
	 * Instantiates empty object.
	 */
	public ChannelRequestRelation() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}


	/**
	 * Instantiates a new channel request relation.
	 *
	 * @param relType the rel type
	 * @param channelSeqId the channel seq id
	 */
	public ChannelRequestRelation(final String relType, final String channelSeqId) {
		super();
		this.relType = relType;
		this.channelSeqId = channelSeqId;
	}


	/**
	 * Gets the relation type.
	 *
	 * @return the relation type
	 */
	public String getRelType() {
		return relType;
	}

	/**
	 * Sets the relation type.
	 *
	 * @param relType the new relation type
	 */
	public void setRelType(final String relType) {
		this.relType = relType;
	}

	/**
	 * Gets the channel seq id.
	 *
	 * @return the channel seq id
	 */
	public String getChannelSeqId() {
		return channelSeqId;
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
	 * Provides custom string representation of this object, by overriding toString() method of Object class.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ChannelRequestRelation [relType=" + relType + ", channelSeqId=" + channelSeqId + "]";
	}
	
}
