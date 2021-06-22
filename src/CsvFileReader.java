import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CsvFileReader {

	public static List<Employee> readFromCSV(String fileName) throws ParseException {
		 List<Employee> employees = new ArrayList<>();
	        Path pathToFile = Paths.get(fileName);

	        try (BufferedReader br = Files.newBufferedReader(pathToFile,
	                StandardCharsets.US_ASCII)) {
	        	br.readLine(); //skip first line
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
	private static Employee createEmpFromCsv(String[] csvData) throws ParseException {

		int empId = Integer.parseInt(csvData[0]);
		int projectId = Integer.parseInt(csvData[1]);
		Date dateFrom = findDateFormatAndParse(csvData[2]);
		Date dateTo = findDateFormatAndParse(csvData[3]);
		return new Employee(empId, projectId, dateFrom, dateTo);
    }
	private static Date findDateFormatAndParse(String input) {
		 if (input != null && input.length() != 0)
		    {
		        List<SimpleDateFormat> formats = new ArrayList<SimpleDateFormat>();
		        formats.add(new SimpleDateFormat("yyyyMMdd"));
		        formats.add(new SimpleDateFormat("yyyy/MM/dd"));
		        formats.add(new SimpleDateFormat("yyyy-MM-dd"));

		        Date parsedDate = null;
		        for (SimpleDateFormat format : formats)
		        {
		            try
		            {
		                parsedDate = format.parse(input);
		                return parsedDate;
		            }
		            catch (ParseException pe)
		            { 
		            	if( pe.getMessage().equals("Unparseable date: \"NULL\"")) {
		            	  return new Date();		    
		                }
		            	  continue;
		            }
		        }
		    }
		return null;
	}

}

