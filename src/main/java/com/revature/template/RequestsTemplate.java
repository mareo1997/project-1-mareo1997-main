package com.revature.template;

public class RequestsTemplate {

	private String employee, manager;

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
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
		RequestsTemplate other = (RequestsTemplate) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestsTemplate [employee=" + employee + ", manager=" + manager + "]";
	}

	public RequestsTemplate(String employee, String manager) {
		super();
		this.employee = employee;
		this.manager = manager;
	}

	public RequestsTemplate() {
		// TODO Auto-generated constructor stub
	}
}
