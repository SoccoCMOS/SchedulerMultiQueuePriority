import java.sql.Date;
import org.jfree.data.gantt.Task;

public class TaskNumeric extends Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String  st;

	public TaskNumeric(String description, long start, long end, String st) {
		super(description, new Date(start), new Date(end));
		this.st = st;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public static TaskNumeric duration(String description, long start,
			long duration,String st) {
		System.out.println( " time start= "+start);
		System.out.println( " time start ="+duration);
		return new TaskNumeric(description, start, start + duration, st);
	}

}