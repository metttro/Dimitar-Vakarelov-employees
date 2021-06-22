import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class EmployeeFinder {
	public static Map<Integer, List<Employee>> find(List<Employee> emp) {
		calculateWorkDaysInProject(emp);
		Map<Integer, List<Employee>> empGroupedByProjectId =
				emp.stream().collect(Collectors.groupingBy(e -> e.getProjectId()));
	   for (Entry<Integer,List<Employee>> entry : empGroupedByProjectId.entrySet()) {
		 List<Employee> list = entry.getValue();
	     Collections.sort(list, new CompareEmployeeByWorkDays());
	   }
	   return empGroupedByProjectId;
	}
	private static void calculateWorkDaysInProject(List<Employee> emp) {
		for(Employee e : emp) {
			Long diffInSec = e.getDateTo().getTime() - e.getDateFrom().getTime();
			Long diffInDays = (diffInSec / (24 * 60 * 60 * 1000));
			e.setWorkDaysInProject(diffInDays);
		}
		
	}
}
class CompareEmployeeByWorkDays implements Comparator<Employee> {

	  @Override
	  public int compare(Employee e1, Employee e2) {
	    return e2.getWorkDaysInProject().compareTo(e1.getWorkDaysInProject());
	  } 
}