import java.util.ArrayList;

public class SJFScheduler extends Scheduler {
	public Task current;

	@Override
	public ArrayList<Task> schedule(ArrayList<Task> tasks) {
		tasks.sort((o1, o2) -> {
			if (o1 == current) return -1;
			if (o2 == current) return 1;
			if (o1.getCpuTimeLeft() < o2.getCpuTimeLeft()) return -1;
			if (o1.getCpuTimeLeft() == o2.getCpuTimeLeft()) {
				return o1.getId().compareTo(o2.getId());
			}
			return 1;
		});
		return tasks;
	}

	@Override
	public void inicialize(ArrayList<Task> tasks) {
		// empty
	}
}
