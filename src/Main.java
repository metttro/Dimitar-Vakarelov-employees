import java.text.ParseException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;

public class Main extends JFrame {

	public static void main(String[] args) throws ParseException {
		//Init utils class. Here is all logic
		Utils utils = new Utils();
		//Modal file selection window.
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		String selectedFile = null;
		List<Employee> emp;
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = jfc.getSelectedFile().getPath();
		} else {
			System.exit(0);
		}
		//Read all employess from provided csv file.
		emp = utils.readFromCSV(selectedFile);
		utils.print(utils.find(emp));
	}

	public Main(Object[][] data) {
	    //Swing table for employees visualization.
		String[] columns = new String[] { "ProjectId", "EmployeeId", "DaysInProject" };
		JTable table = new JTable(data, columns);
		this.add(new JScrollPane(table));
		this.setTitle("Employees");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}
