import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RRScheduler extends Scheduler {
	int baseTime;
	HashMap<Task, Integer> leftTime;
	int lastStepTime;
	public ArrayList<Task> sortedTasks;

	public RRScheduler() {
		baseTime = 2;
		leftTime = new HashMap<>();
		lastStepTime = 0;
		sortedTasks = new ArrayList<>();
	}

	@Override
	public ArrayList<Task> schedule(ArrayList<Task> tasks) {
		sortedTasks = new ArrayList<>(tasks);
//		sortedTasks.sort((o1, o2) -> {
//			if (leftTime.get(o1) == 0) return -1;
//			if (o1.getCpuTimeLeft() < o2.getCpuTimeLeft()) return 1;
//			if (o1.getCpuTimeLeft() == o2.getCpuTimeLeft()) {
//				return o1.getId().compareTo(o2.getId()) * -1;
//			}
//			return -1;
//		});
//		sortedTasks.sort((o1, o2) -> {
//			if (leftTime.get(o1) == 0) return 1;
//			if (leftTime.get(o2) == 0) return -1;
//			if (leftTime.get(o1) > leftTime.get(o2)) {
//				return 1;
//			}
//			if (o1.getStartTime() == o2.getStartTime()) {
//				return o1.getId().compareTo(o2.getId());
//			}
//			return Integer.compare(o1.getStartTime(), o2.getStartTime());
//		});
		sortedTasks.sort((o1, o2) -> {
			if (o1.getStartTime() == o2.getStartTime()) {
				if (o1.getStartTime() == o2.getStartTime()) {
					return o1.getId().compareTo(o2.getId());
				}
			}
			return Integer.compare(o1.getStartTime(), o2.getStartTime());
		});
		int delta = 0;
		for (int i = 0; i < sortedTasks.size(); i++) {
			if (leftTime.get(sortedTasks.get(i)) > 0) {
				delta = i;
				break;
			}
		}
		ArrayList<Task> tmp = new ArrayList<>();
		tmp.addAll(sortedTasks.subList(delta, sortedTasks.size()));
		tmp.addAll(sortedTasks.subList(0, delta));
		return tmp;
	}

	@Override
	public void inicialize(ArrayList<Task> tasks) {
		leftTime = new HashMap<>();
		for (Task t : tasks) {
			leftTime.put(t, baseTime);
		}
	}

	public void stepped(Task task, ArrayList<Task> tasks) {
		resetIfZero(tasks);
		int value;
		if ((value = leftTime.get(task)) > 0) {
			leftTime.replace(task, value - 1);
		}
//		allZero = true;
//		for (Task t : tasks) {
//			if (leftTime.get(t) > 0) {
//				allZero = false;
//			}
//		}
//		if (allZero) {
//			for (Task t : tasks) {
//				leftTime.replace(t, baseTime);
//			}
//		}
	}

	public void tick(TaskSchedulerProgram tsp) {
		if (tsp.time > 0 && tsp.time > lastStepTime + 1) {
			for (int i = 0; i < sortedTasks.size(); i++) {
				if (leftTime.get(sortedTasks.get(i)) == 1) {
					leftTime.replace(sortedTasks.get(i), 0);
					int delta = 0;
					for (int j = 0; j < sortedTasks.size(); j++) {
						if (leftTime.get(sortedTasks.get(j)) > 0) {
							delta = j;
							break;
						}
					}
					ArrayList<Task> tmp = new ArrayList<>();
					tmp.addAll(sortedTasks.subList(delta, sortedTasks.size()));
					tmp.addAll(sortedTasks.subList(0, delta));
					sortedTasks = tmp;
					tsp.prio0 = sortedTasks;
					break;
				}
			}
		}
		lastStepTime = tsp.time;
		resetIfZero(sortedTasks);
	}

	private void resetIfZero(ArrayList<Task> sortedTasks) {
		boolean allZero = true;
		for (Task t : sortedTasks) {
			if (leftTime.get(t) > 0) {
				allZero = false;
			}
		}
		if (allZero) {
			for (Task t : sortedTasks) {
				leftTime.replace(t, baseTime);
			}
		}
	}
}
