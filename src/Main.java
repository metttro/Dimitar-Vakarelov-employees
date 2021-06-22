import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

public class Main extends JFrame{

	public static void main(String[] args) throws ParseException {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        String selectedFile = null;
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile().getPath();
        }else {
        	System.exit(0);
        }
		List<Employee> emp = CsvFileReader.readFromCSV(selectedFile);
		print(EmployeeFinder.find(emp));
		 
	}
	private static void print(Map<Integer, List<Employee>> map) {
		Object[][] data =  new Object[map.entrySet().size()*2][3];
		int i = 0;
		for (Entry<Integer, List<Employee>> entry : map.entrySet()) {
			Employee e1 = entry.getValue().get(0);
			Employee e2 = entry.getValue().get(1);
			Object[] e1Arr = new Object[]{e1.getProjectId(), e1.getEmpId(), e1.getWorkDaysInProject()};
			Object[] e2Arr = new Object[]{e2.getProjectId(), e2.getEmpId(), e2.getWorkDaysInProject()};	
			data[i] = e1Arr;
			data[++i] = e2Arr;
			System.out.println(e1.getProjectId() + " project " + e1.getEmpId() + " empId " + e1.getWorkDaysInProject() + " days in project.");
			System.out.println(e2.getProjectId() + " project " + e2.getEmpId() + " empId " + e2.getWorkDaysInProject() + " days in project.");
			i++;
		}
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main(data);
            }
        });
	}
	public Main(Object[][] data)
    {
        String[] columns = new String[] {
            "ProjectId", "EmployeeId", "DaysInProject"
        };
        JTable table = new JTable(data, columns);
        this.add(new JScrollPane(table));
        this.setTitle("Table Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.pack();
        this.setVisible(true);
    }

}
