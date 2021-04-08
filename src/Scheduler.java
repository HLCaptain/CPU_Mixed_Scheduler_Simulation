import java.util.ArrayList;

abstract public class Scheduler {
	public Scheduler() { }
	abstract public ArrayList<Task> schedule(ArrayList<Task> tasks);
	abstract public void inicialize(ArrayList<Task> tasks);
}
