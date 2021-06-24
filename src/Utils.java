import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

public class Utils {
	public Map<Integer, List<Employee>> find(List<Employee> emp) {
		calculateWorkDaysInProject(emp);
		//Create Map grouped by ProjectId as Key and Employees as value.
		Map<Integer, List<Employee>> empGroupedByProjectId = emp.stream()
				.collect(Collectors.groupingBy(e -> e.getProjectId()));
		//Sort Employees by workDaysInProject property.
		for (Entry<Integer, List<Employee>> entry : empGroupedByProjectId.entrySet()) {
			List<Employee> list = entry.getValue();
			Collections.sort(list, new CompareEmployeeByWorkDays());
		}
		return empGroupedByProjectId;
	}

	private void calculateWorkDaysInProject(List<Employee> emp) {
		//Calculates and set setWorkDaysInProject property for every Employee Object.
		for (Employee e : emp) {
			Long diffInDays = e.getDateTo().toEpochDay() - e.getDateFrom().toEpochDay();
			e.setWorkDaysInProject(diffInDays);
		}
	}

	public List<Employee> readFromCSV(String fileName) throws ParseException {
		List<Employee> employees = new ArrayList<>();
		Path pathToFile = Paths.get(fileName);

		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
			br.readLine(); // skip first line, because here are column names.
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
		//Creates Employee Objects from Csv Data
		int empId = Integer.parseInt(csvData[0]);
		int projectId = Integer.parseInt(csvData[1]);
		LocalDate dateFrom = findDateFormatAndParse(csvData[2]);
		LocalDate dateTo = findDateFormatAndParse(csvData[3]);
		
		return new Employee(empId, projectId, dateFrom, dateTo);
	}

	private LocalDate findDateFormatAndParse(String input) {
		if (input != null && input.length() != 0) {
			List<DateTimeFormatter>formats = new ArrayList<DateTimeFormatter>();
			//Here we have several date patterns. Also is possible to 
			//use Regex here to match more patterns.
			formats.add(DateTimeFormatter.ofPattern("yyyyMMdd"));
			formats.add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			formats.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalDate parsedDate = null;

			for (DateTimeFormatter format : formats) {
				try {
					//If the first pattern throws ParsingException we try 
					//with the next one.
					parsedDate = LocalDate.parse(input,format);
					return parsedDate;
				} catch (DateTimeParseException pe) {
					//If we have Null as input from csv file, we return current Date.
					if (pe.getMessage().equals("Text 'NULL' could not be parsed at index 0")) {
						return LocalDate.now();
					}
					continue;
				}
			}
		}
		return null;
	}
	public void print(Map<Integer, List<Employee>> map) {
		//Array to be used for visualization in Swing table.
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