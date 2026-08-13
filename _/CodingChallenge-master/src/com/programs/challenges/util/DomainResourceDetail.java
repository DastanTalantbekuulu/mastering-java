/**
 * @author pranshu.shrivastava
 */
package com.programs.challenges.util;

import java.io.Serializable;


/**
 * A POJO representing details of a Domain Resource (example SweepStructure, LoanAgreement, FundTransfer).
 */
public class DomainResourceDetail implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2672585954436088438L;
	
	/** The unique sequence id, that represents Domain Resource instance. */
	protected String seqId;
	
	/** The effective date, when state of Domain Resource was last created/modified. */
	protected String effDate;

	
	/**
	 * Instantiates empty object.
	 */
	public DomainResourceDetail() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}
	
	/**
	 * Instantiates a new domain resource detail, with given parameters.
	 *
	 * @param seqId the unique sequence id representing Domain Resource instance
	 * @param effDate the effective date, when resource was created/updated
	 */
	public DomainResourceDetail(final String seqId, final String effDate) {
		super();
		this.seqId = seqId;
		this.effDate = effDate;
	}

	
	/**
	 * Gets the unique sequence id, that represents Domain Resource instance.
	 *
	 * @return the sequence id
	 */
	public String getSeqId() {
		return seqId;
	}
	
	/**
	 * Gets the effective date, when state of Domain Resource was last created/modified.
	 *
	 * @return the effective date
	 */
	public String getEffDate() {
		return effDate;
	}
	
	/**
	 * Sets the Resource sequence id.
	 *
	 * @param seqId the new sequence id
	 */
	public void setSeqId(final String seqId) {
		this.seqId = seqId;
	}
	
	/**
	 * Sets the effective date.
	 *
	 * @param effDate the new effective date
	 */
	public void setEffDate(final String effDate) {
		this.effDate = effDate;
	}
	
	
	/**
	 *  (non-Javadoc).
	 *
	 * @return the string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DomainResourceDetail [seqId=" + seqId + ", effDate=" + effDate + "]";
	}

	
}
