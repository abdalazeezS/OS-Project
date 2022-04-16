package os.project;

public class Process {
    private int processId;
    private int arrivalTime;
    private int cpuBurst;
    private int sizeIntBytes;
    private int waitingTime;
    private int turnAroundTime;
    private int finishTime;

    public Process(int waitingTime, int turnAroundTime, int finishTime) {
        this.waitingTime = waitingTime;
        this.turnAroundTime = turnAroundTime;
        this.finishTime = finishTime;
    }

    
    public Process(int processId, int arrivalTime, int cpuBurst, int sizeIntBytes) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurst;
        this.sizeIntBytes = sizeIntBytes;
    }

    public int getProcessId() {
        return processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getCpuBurst() {
        return cpuBurst;
    }

    public int getSizeIntBytes() {
        return sizeIntBytes;
    }
    
}
