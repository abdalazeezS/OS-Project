package os.project;

public class Process {

    private int processId;
    private int arrivalTime;
    private int cpuBurstTime;
    private int sizeIntBytes;

    public Process(int processId, int arrivalTime, int cpuBurstTime, int sizeIntBytes) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.cpuBurstTime = cpuBurstTime;
        this.sizeIntBytes = sizeIntBytes;
    }

    public int getProcessId() {
        return processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getCpuBurstTime() {
        return cpuBurstTime;
    }

    public int getSizeIntBytes() {
        return sizeIntBytes;
    }

}
