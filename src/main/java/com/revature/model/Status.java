package com.revature.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Status")
public class Status {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // this acts like the SERIAL datatype in SQL
	@Column(name = "statusid")
	private int statusid;

	@Column(name = "status", nullable = false)
	private String status; // Pending, Approved, Denied

	public Status() {
		// TODO Auto-generated constructor stub
	}

	public Status(int statusid, String status) {
		super();
		this.statusid = statusid;
		this.status = status;
	}

	public Status(String status) {
		super();
		this.status = status;
	}

	@Override
	public String toString() {
		String result = "StatusID: " + statusid + " \t\tStatus: " + status + "\n";
		return result;
	}

	public int getStatusid() {
		return statusid;
	}

	public void setStatusid(int statusid) {
		this.statusid = statusid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + statusid;
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
		Status other = (Status) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusid != other.statusid)
			return false;
		return true;
	}

}
