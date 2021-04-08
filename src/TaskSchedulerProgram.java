import java.util.ArrayList;
import java.util.Scanner;

public class TaskSchedulerProgram {
	private RRScheduler rr;
	private SJFScheduler sjf;
	public ArrayList<Task> beginTasks;
	public ArrayList<Task> tasks;
	private ArrayList<Task> sortedTasks;
	private String line;
	private Task prev;
	private Task current;
	public int time;
	public ArrayList<Task> prio0;
	public ArrayList<Task> prio1;

	public TaskSchedulerProgram() {
		rr = new RRScheduler();
		sjf = new SJFScheduler();
		tasks = new ArrayList<>();
		sortedTasks = new ArrayList<>();
		beginTasks = new ArrayList<>();
		time = 0;
	}

	public void run() {
		iniTasks();

		while (!tasks.isEmpty()) {
			cycle();
			time++;
		}
		System.out.println();
		end();
		System.out.println();
	}

	private void cycle() {
		prio1 = new ArrayList<>();
		for (Task t : tasks) { if (t.getPriority() == 1 && t.getStartTime() <= time) prio1.add(t); }
		prio0 = new ArrayList<>();
		for (Task t : tasks) { if (t.getPriority() == 0 && t.getStartTime() <= time) prio0.add(t); }

		prio0 = rr.schedule(prio0);
		 // setting current task
		prio1 = sjf.schedule(prio1);
		if (prio1.isEmpty()) {
			rr.tick(this);
		}
		sortedTasks.addAll(prio1);
		sortedTasks.addAll(prio0);
		//rr.tick(time);
		if (!sortedTasks.isEmpty()) {
			current = sortedTasks.get(0);
			sjf.current = current;
		} else return;

		if (prio1.isEmpty()) {

			sortedTasks.get(0).step(this, rr);
		} else sortedTasks.get(0).step(this, null);

		if (prev != current) {
			System.out.print(current.getId());
			prev = current;
		}
		sortedTasks.clear();
		for (Task t : tasks) {
			if (current != t) t.waitTask(time);
		}
	}

	public void end() {
		for (Task t : beginTasks) {
			System.out.print(t.getId() + ":" + t.getWaitTime());
			if (t != beginTasks.get(beginTasks.size() - 1)) System.out.print(",");
		}
	}

	/**
	 * Pain in the 4ss input handling.
	 */
	private void iniTasks() {
		Scanner scanner = new Scanner(System.in);
		try {
			while ((line = scanner.nextLine()).length() != 0) {
				line = line.replaceAll(",", " ");
				String id = line.substring(0, 1);
				line = line.substring(2);
				int priority = Integer.parseInt(parseVar());
				int startTime = Integer.parseInt(parseVar());
				int cpuTime = Integer.parseInt(line);
				tasks.add(new Task(id, priority, startTime, cpuTime));
				//System.out.println(tasks.get(tasks.size() - 1).toString());
			}
		} catch (Exception ignored) { }

		prio1 = new ArrayList<>();
		for (Task t : tasks) { if (t.getPriority() == 1) prio1.add(t); }
		prio0 = new ArrayList<>();
		for (Task t : tasks) { if (t.getPriority() == 0) prio0.add(t); }

		rr.inicialize(prio0);
		sjf.inicialize(prio1);
		beginTasks.addAll(tasks);
	}

	/**
	 * R3tarded parsing
	 * @return single variable as a string.
	 */
	private String parseVar() {
		int endPosition = 1;
		while (line.charAt(endPosition) != ' ' && line.charAt(endPosition) != '\n') {
			endPosition++;
		}
		String parsed = line.substring(0, endPosition);
		line = line.substring(Math.min(endPosition + 1, line.length() - 1));
		return parsed;
	}
}
