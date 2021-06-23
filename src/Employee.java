import java.util.Date;

public class Employee {
	private int empId;
	private int projectId;
	private Date dateFrom;
	private Date dateTo;
	private Long workDaysInProject;

	public Employee(int empId, int projectId, Date dateFrom, Date dateTo) {
		super();
		this.empId = empId;
		this.projectId = projectId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Long getWorkDaysInProject() {
		return workDaysInProject;
	}

	public void setWorkDaysInProject(Long workDaysInProject) {
		this.workDaysInProject = workDaysInProject;
	}

}
