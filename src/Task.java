public class Task {
	private final String id;
	private final int priority;
	private final int startTime;
	private final int cpuTime;
	private int cpuTimeLeft;
	private int waitTime;

	public Task(String id, int priority, int startTime, int cpuTime) {
		this.id = id;
		this.priority = priority;
		this.startTime = startTime;
		this.cpuTime = cpuTime;
		this.cpuTimeLeft = cpuTime;
		this.waitTime = 0;
	}

	@Override
	public String toString() {
		return "id: " + id +
				", priority: " + priority +
				", start: " + startTime +
				", cpu time: " + cpuTime +
				", cpu time left: " + cpuTimeLeft;
	}

	public int getPriority() {
		return priority;
	}

	public int getCpuTimeLeft() {
		return cpuTimeLeft;
	}

	public String getId() {
		return id;
	}

	public void step(TaskSchedulerProgram tsp, RRScheduler rr) {
		if (rr != null) {
			rr.stepped(this, tsp.prio0);
		}
		if (cpuTimeLeft > 0) {
			cpuTimeLeft--;
		}
		if (cpuTimeLeft == 0) {
			tsp.tasks.remove(this);
		}
	}

	public int getCpuTime() {
		return cpuTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void waitTask(int time) {
		if (startTime <= time)
			waitTime++;
	}

	public int getWaitTime() {
		return waitTime;
	}
}
