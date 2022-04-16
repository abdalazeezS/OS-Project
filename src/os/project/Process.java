package os.project;

public class Process {
    private int processId;
    private int arrivalTime;
    private int cpuBurst;
    private int sizeIntBytes;

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
