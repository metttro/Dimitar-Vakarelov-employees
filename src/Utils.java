import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

public class Utils {
	public Map<Integer, List<Employee>> find(List<Employee> emp) {
		calculateWorkDaysInProject(emp);
		Map<Integer, List<Employee>> empGroupedByProjectId = emp.stream()
				.collect(Collectors.groupingBy(e -> e.getProjectId()));
		for (Entry<Integer, List<Employee>> entry : empGroupedByProjectId.entrySet()) {
			List<Employee> list = entry.getValue();
			Collections.sort(list, new CompareEmployeeByWorkDays());
		}
		return empGroupedByProjectId;
	}

	private void calculateWorkDaysInProject(List<Employee> emp) {
		for (Employee e : emp) {
			Long diffInSec = e.getDateTo().getTime() - e.getDateFrom().getTime();
			Long diffInDays = (diffInSec / (24 * 60 * 60 * 1000));
			e.setWorkDaysInProject(diffInDays);
		}
	}

	public List<Employee> readFromCSV(String fileName) throws ParseException {
		List<Employee> employees = new ArrayList<>();
		Path pathToFile = Paths.get(fileName);

		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			br.readLine(); // skip first line
			String line = br.readLine();
			while (line != null) {
				String[] attributes = line.split(",");
				Employee emp = createEmpFromCsv(attributes);
				employees.add(emp);
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return employees;
	}

	private Employee createEmpFromCsv(String[] csvData) throws ParseException {
		int empId = Integer.parseInt(csvData[0]);
		int projectId = Integer.parseInt(csvData[1]);
		Date dateFrom = findDateFormatAndParse(csvData[2]);
		Date dateTo = findDateFormatAndParse(csvData[3]);
		
		return new Employee(empId, projectId, dateFrom, dateTo);
	}

	private Date findDateFormatAndParse(String input) {
		if (input != null && input.length() != 0) {
			List<SimpleDateFormat> formats = new ArrayList<SimpleDateFormat>();
			formats.add(new SimpleDateFormat("yyyyMMdd"));
			formats.add(new SimpleDateFormat("yyyy/MM/dd"));
			formats.add(new SimpleDateFormat("yyyy-MM-dd"));
			Date parsedDate = null;
			
			for (SimpleDateFormat format : formats) {
				try {
					parsedDate = format.parse(input);
					return parsedDate;
				} catch (ParseException pe) {
					if (pe.getMessage().equals("Unparseable date: \"NULL\"")) {
						return new Date();
					}
					continue;
				}
			}
		}
		return null;
	}
	public void print(Map<Integer, List<Employee>> map) {
		Object[][] data = new Object[map.entrySet().size() * 2][3];
		int i = 0;
		for (Entry<Integer, List<Employee>> entry : map.entrySet()) {
			Employee e1 = entry.getValue().get(0);
			Employee e2 = entry.getValue().get(1);
			Object[] e1Arr = new Object[] { e1.getProjectId(), e1.getEmpId(), e1.getWorkDaysInProject() };
			Object[] e2Arr = new Object[] { e2.getProjectId(), e2.getEmpId(), e2.getWorkDaysInProject() };
			data[i] = e1Arr;
			data[++i] = e2Arr;
			System.out.println("ProjectId: " + e1.getProjectId() + " EmpId: " + e1.getEmpId()  + " Days in project: " + e1.getWorkDaysInProject());
			System.out.println("ProjectId: " + e2.getProjectId() + " EmpId: " + e2.getEmpId()  + " Days in project: " + e2.getWorkDaysInProject());
			i++;
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main(data);
			}
		});
	}
}

class CompareEmployeeByWorkDays implements Comparator<Employee> {
	@Override
	public int compare(Employee e1, Employee e2) {
		return e2.getWorkDaysInProject().compareTo(e1.getWorkDaysInProject());
	}
}