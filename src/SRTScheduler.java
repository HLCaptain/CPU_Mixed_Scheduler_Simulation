import java.util.ArrayList;

public class SRTScheduler extends Scheduler {
	@Override
	public ArrayList<Task> schedule(ArrayList<Task> tasks) {
		tasks.sort((o1, o2) -> {
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