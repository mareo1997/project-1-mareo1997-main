package com.revature.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Reimbursement")
public class Reimbursement {

	@Id
	@Column(name = "Reimbursementid")
	@GeneratedValue(strategy = GenerationType.AUTO) // this acts like the SERIAL datatype in SQL
	private int Reimbursementid;

	@Column(name = "amount", nullable = false)
	private double amt;

	@Column(name = "submitted", nullable = false)
	private Timestamp submitted;

	@Column(name = "resolved")
	private Timestamp resolved;

	@Column(name = "description")
	private String description;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "AuthorFK")
	private User author;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ResolveFK")
	private User resolver;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Status status;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Type type;

	public Reimbursement() {
		// TODO Auto-generated constructor stub
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Reimbursement(int ersid, User author, double amt, String description, Timestamp submitted, User resolver,
			Timestamp resolved, Status status, Type type) {
		super();
		this.Reimbursementid = ersid;
		this.author = author;
		this.resolver = resolver;
		this.amt = amt;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.status = status;
		this.type = type;
	}

	public Reimbursement(User author, double amt, String description, Timestamp submitted, Status status, Type type) {
		super();
		this.author = author;
		this.amt = amt;
		this.submitted = submitted;
		this.description = description;
		this.status = status;
		this.type = type;
	}

	public Reimbursement(int ersid, User resolver, Timestamp resolved, Status status) {
		super();
		this.Reimbursementid = ersid;
		this.resolver = resolver;
		this.resolved = resolved;
		this.status = status;
	}

	@Override
	public String toString() {
		String result = "ReimbursementID: " + Reimbursementid + "\tAmount: $" + amt + "\n" + "Description: "
				+ description + "\n" + "Author: " + author.getUsername() + " \t\tSubmitted: " + submitted + "\n"
				+ "Resolver: " + resolver + " \t\tResolved: " + resolved + "\n" + "Status: " + status.getStatus()
				+ "\tType: " + type.getType() + "\n";
		return result;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getErsid() {
		return Reimbursementid;
	}

	public void setErsid(int ersid) {
		this.Reimbursementid = ersid;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public User getResolve() {
		return resolver;
	}

	public void setResolve(User resolve) {
		this.resolver = resolve;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Reimbursementid;
		result = prime * result + ((resolver == null) ? 0 : resolver.hashCode());
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amt) != Double.doubleToLongBits(other.amt))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Reimbursementid != other.Reimbursementid)
			return false;
		if (resolver == null) {
			if (other.resolver != null)
				return false;
		} else if (!resolver.equals(other.resolver))
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
