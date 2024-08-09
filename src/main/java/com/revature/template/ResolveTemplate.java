package com.revature.template;

public class ResolveTemplate {

	private String username;

	private int reimid, status;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getReimid() {
		return reimid;
	}

	public void setReimid(int reimid) {
		this.reimid = reimid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + reimid;
		result = prime * result + status;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		ResolveTemplate other = (ResolveTemplate) obj;
		if (reimid != other.reimid)
			return false;
		if (status != other.status)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResolveTemplate [username=" + username + ", reimid=" + reimid + ", status=" + status + "]";
	}

	public ResolveTemplate(String username, int reimid, int status) {
		super();
		this.username = username;
		this.reimid = reimid;
		this.status = status;
	}

	public ResolveTemplate() {
		// TODO Auto-generated constructor stub
	}
}
