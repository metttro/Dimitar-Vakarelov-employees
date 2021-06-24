import java.time.LocalDate;

public class Employee {
	private int empId;
	private int projectId;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private Long workDaysInProject;

	public Employee(int empId, int projectId, LocalDate dateFrom, LocalDate dateTo) {
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

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public Long getWorkDaysInProject() {
		return workDaysInProject;
	}

	public void setWorkDaysInProject(Long workDaysInProject) {
		this.workDaysInProject = workDaysInProject;
	}

}
