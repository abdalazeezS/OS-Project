package os.project;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Execution {

    private LinkedList<Process> list;
    private int contextSwitch;

    public Execution(LinkedList<Process> list, int contextSwitch) {
        this.list = list;
        this.contextSwitch = contextSwitch;
    }

    // list contains the finish time for each process
    LinkedList<Integer> finishTime;

    /*
    list contians all numbers needed to display on the Grant Chart like thie initial time (start time),
    finish time for each process and context swith intervals
     */
    LinkedList<Integer> grantChartTimeLine;

    // list contains the start time for each process
    LinkedList<Integer> startTime;

    /* 
    List contains waiting time for each process computed using the equation
    waiting time = start time - arrival time
     */
    LinkedList<Integer> waitingTime;

    /* 
    List contains trun around time for each process computed using the equation
    turn aroun time = finish time - arrival time
     */
    LinkedList<Integer> turnArounTime;

    void FCFC() {
        System.out.println("First Come First Serve Shceduling Algorithm:");

        //sort the processes based on the Arrival Time
        Collections.sort(list, new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                if (o1.getArrivalTime() < o2.getArrivalTime()) {
                    return -1;
                }
                if (o1.getArrivalTime() > o2.getArrivalTime()) {
                    return 1;
                }
                return 0;
            }

        });

        finishTime = new LinkedList<>();
        grantChartTimeLine = new LinkedList<>();
        startTime = new LinkedList<>();

        // currentTime used to check if the process is arrived or not in order to schedule it
        int currentTime = 0;

        for (int i = 0; i < list.size(); i++) {
            // using p just to make it easier to access the Process object instead of writing list.get(i) every time
            Process p = list.get(i);

            if (i == 0) {
                //System.out.print(p.getArrivalTime() + " " + p.getCpuBurstTime() + " " + (p.getCpuBurstTime() + this.contextSwitch) + " ");

                startTime.add(p.getArrivalTime());

                finishTime.add(p.getCpuBurstTime() + p.getArrivalTime());

                grantChartTimeLine.add(p.getArrivalTime());
                grantChartTimeLine.add(p.getCpuBurstTime() + p.getArrivalTime());
                grantChartTimeLine.add(p.getCpuBurstTime() + this.contextSwitch);
                currentTime += (p.getCpuBurstTime() + this.contextSwitch);

            } else if (p.getArrivalTime() < currentTime) {
                startTime.add(finishTime.get(i - 1) + this.contextSwitch);
                finishTime.add(startTime.get(i) + p.getCpuBurstTime());

            } else {
                startTime.add(p.getArrivalTime());
                finishTime.add(startTime.get(i) + p.getCpuBurstTime());

            }
            /* 
                when we reach the last process there is no need to display the context swithc after that
                so will display only the finish time
             */
            if (i == list.size() - 1) {
                grantChartTimeLine.add(finishTime.get(i));

            } else {
                grantChartTimeLine.add(finishTime.get(i));
                grantChartTimeLine.add(finishTime.get(i) + this.contextSwitch);
            }
        }

        // calculating waiting time for each process
        waitingTime = new LinkedList<>();
        for (int i = 0; i < startTime.size(); i++) {
            waitingTime.add(startTime.get(i) - list.get(i).getArrivalTime());
        }

        // calculating turn around time for each process
        turnArounTime = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            turnArounTime.add(finishTime.get(i) - list.get(i).getArrivalTime());
        }

        System.out.println("");

        System.out.println("Processes Arrangement in the grant chart: ");
        for (int i = 0; i < list.size() - 1; i++) {
            System.out.print("p" + list.get(i).getProcessId() + " ");
        }
        System.out.print(" (with context switch between processes = " + this.contextSwitch + ")");
        System.out.println();

        // drawing the Grant Chart containing time, not a perfect rectangle but it is good :)
        print_rectangle(3, list.size() * 16);

        System.out.print("\n\n");

        System.out.println("Process ID      Waiting Time        Turn Around Time        Finish Time");
        /* here I used a map data structure just to collect the processes with their information together and arrange them
           appropriately, because the output will be like this;
        
        Process ID      Waiting Time        Turn Aroung Time        Finish Time
            1           0                   12                      12
            2           12                  15                      16
            0           14                  24                      27
            3           23                  44                      49
            4           41                  48                      57
        
        it is correct but they are not sorted depending the process id :)
        
        The map has a key which is the process id
        and a value which is an integer array contains WT, TAT and FT
         */
        Map<Integer, int[]> map = new HashMap<Integer, int[]>();

        for (int i = 0; i < list.size(); i++) {
            /* 
            in each iteration we initilize the array with WT, TAT and FT 
            then we add the process id with the associated values to the map
             */
            int[] arr = {waitingTime.get(i), turnArounTime.get(i), finishTime.get(i)};
            map.put(list.get(i).getProcessId(), arr);

            // in case you want to see how the output will be without the map
            //System.out.println(list.get(i).getProcessId() + "     " + waitingTime.get(i) + "      " + turnArounTime.get(i) + "        " + finishTime.get(i));
        }

        // printing the map elements
        map.forEach((processId, info) -> {
            System.out.print(processId + "\t\t");
            for (int i = 0; i < 3; i++) {
                System.out.print(info[i] + "\t\t\t");
            }
            System.out.println("");
        });
        System.out.println("");

        /*
        **********************
        1) Averages Calculation for Finish Time, Waiting time, Turnaround time
        2) CPU utilization
         */
        double sumFinishTime = 0;
        for (int ft : finishTime) {
            sumFinishTime += ft;
        }
        double avgFinishTime = sumFinishTime / list.size();
        System.out.println("Average Finish Time: " + avgFinishTime + " time unit.");

        double sumWainting = 0;
        for (int wt : waitingTime) {
            sumWainting += wt;
        }
        double avgWaitingTime = sumWainting / list.size();
        System.out.println("Average Waiting Time: " + avgWaitingTime + " time unit.");

        double sumTurnAroundTime = 0;
        for (int tat : turnArounTime) {
            sumTurnAroundTime += tat;
        }
        double avgTurnArounTime = sumTurnAroundTime / list.size();
        System.out.println("Average Turnaround Time: " + avgTurnArounTime + " time unit.");

        double sumBurstTime = 0;
        for (Process process : list) {
            sumBurstTime += process.getCpuBurstTime();
        }
        double cpuUtilization = (sumBurstTime / (sumBurstTime + this.contextSwitch * (list.size() - 1))) * 100;

        // just for formating the CPU utilization for 2 decmial digits
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        System.out.println("CPU Utilization: " + decimalFormat.format(cpuUtilization) + " %");

        // clear the common lists so the other Algorithms can use them
        grantChartTimeLine.clear();
        finishTime.clear();

    }

    void SJF() {
        System.out.println("Shortest Job First (non-preemptive) Shceduling Algorithm:\n");

        //sort the processes based on the CPU burst Time
        Collections.sort(list, new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                if (o1.getArrivalTime() < o2.getArrivalTime()) {
                    return -1;
                }
                if (o1.getArrivalTime() > o2.getArrivalTime()) {
                    return 1;
                }
                return 0;
            }
        });

        finishTime = new LinkedList<>();
        grantChartTimeLine = new LinkedList<>();
        startTime = new LinkedList<>();

        // currentTime used to check if the process is arrived or not in order to schedule it
        int currentTime = 0;

        startTime.add(list.get(0).getArrivalTime());

        finishTime.add(list.get(0).getCpuBurstTime() + list.get(0).getArrivalTime());

        grantChartTimeLine.add(list.get(0).getArrivalTime());
        grantChartTimeLine.add(list.get(0).getCpuBurstTime() + list.get(0).getArrivalTime());
        grantChartTimeLine.add(list.get(0).getCpuBurstTime() + this.contextSwitch);
        currentTime += (list.get(0).getCpuBurstTime() + this.contextSwitch);

        LinkedList<Process> arrivedProcesses = new LinkedList<>();

        for (int i = 1; i < list.size(); i++) {

            // using p just to make it easier to access the Process object instead of writing list.get(i) every time
            Process p = list.get(i);

            // get the processes based on arrival time
            if (p.getArrivalTime() < currentTime) {
                arrivedProcesses.add(p);
                currentTime += p.getCpuBurstTime() + this.contextSwitch;
            }
//            if ((p.getArrivalTime() < currentTime && list.get(i + 1).getArrivalTime() < currentTime)
//                    && (p.getCpuBurstTime() < list.get(i + 1).getCpuBurstTime())) {
//                startTime.add(finishTime.get(i - 1) + this.contextSwitch);
//                System.out.println(startTime.get(i));
//                finishTime.add(startTime.get(i) + p.getCpuBurstTime());
//                System.out.println(finishTime.get(i));
//                currentTime+=p.getCpuBurstTime()+this.contextSwitch;
//            }
        }

        // sort the processes based on the CPU Burst
        Collections.sort(arrivedProcesses, new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                if (o1.getCpuBurstTime() < o2.getCpuBurstTime()) {
                    return -1;
                }
                if (o1.getCpuBurstTime() > o2.getCpuBurstTime()) {
                    return 1;
                }
                return 0;
            }
        });

//        for (Process p : arrivedProcesses) {
//            System.out.println(p.getProcessId() + " " + p.getCpuBurstTime());
//        }
        for (int i = 0; i < arrivedProcesses.size(); i++) {
            if (i == 0) {
                startTime.add(finishTime.get(0) + this.contextSwitch);
                finishTime.add(startTime.get(1) + arrivedProcesses.get(i).getCpuBurstTime());
                grantChartTimeLine.add(finishTime.get(1));
            } else {
                startTime.add(finishTime.get(i) + this.contextSwitch);
                grantChartTimeLine.add(startTime.get(i + 1));
                finishTime.add(finishTime.get(i) + arrivedProcesses.get(i).getCpuBurstTime() + this.contextSwitch);
                grantChartTimeLine.add(finishTime.get(i + 1));
            }
        }

//        System.out.println("start times: ");
//        for (int st : startTime) {
//            System.out.print(st + " ");
//        }
//        System.out.println("");
//
//        System.out.println("finish times: ");
//        for (int ft : finishTime) {
//            System.out.print(ft + " ");
//        }
//        System.out.println("");
//
//        System.out.println("grant chart times: ");
//        for (int gt : grantChartTimeLine) {
//            System.out.print(gt + " ");
//        }
//        System.out.println("");

        arrivedProcesses.addFirst(list.get(0));
        System.out.println("Processes Arrangement in the grant chart: ");
        for (Process p : arrivedProcesses) {
            System.out.print("p" + p.getProcessId() + "  ");
        }
        System.out.print("(with context switch between processes = " + this.contextSwitch + ")");

        System.out.println("");
        print_rectangle(3, list.size() * 16);
        System.out.print("\n\n");
        
        // calculating waiting time for each process
        waitingTime = new LinkedList<>();
        for (int i = 0; i < startTime.size(); i++) {
            waitingTime.add(startTime.get(i) - list.get(i).getArrivalTime());
        }

        // calculating turn around time for each process
        turnArounTime = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            turnArounTime.add(finishTime.get(i) - list.get(i).getArrivalTime());
        }
        
        double sumFinishTime = 0;
        for (int ft : finishTime) {
            sumFinishTime += ft;
        }
        double avgFinishTime = sumFinishTime / list.size();
        System.out.println("Average Finish Time: " + avgFinishTime + " time unit.");

        double sumWainting = 0;
        for (int wt : waitingTime) {
            sumWainting += wt;
        }
        double avgWaitingTime = sumWainting / list.size();
        System.out.println("Average Waiting Time: " + avgWaitingTime + " time unit.");

        double sumTurnAroundTime = 0;
        for (int tat : turnArounTime) {
            sumTurnAroundTime += tat;
        }
        double avgTurnArounTime = sumTurnAroundTime / list.size();
        System.out.println("Average Turnaround Time: " + avgTurnArounTime + " time unit.");

        double sumBurstTime = 0;
        for (Process process : list) {
            sumBurstTime += process.getCpuBurstTime();
        }
        double cpuUtilization = (sumBurstTime / (sumBurstTime + this.contextSwitch * (list.size() - 1))) * 100;

        // just for formating the CPU utilization for 2 decmial digits
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        System.out.println("CPU Utilization: " + decimalFormat.format(cpuUtilization) + " %");

        // clear the common lists so the other Algorithms can use them
        grantChartTimeLine.clear();
        finishTime.clear();
    }

    void RR(int q) {

    }

    private void print_rectangle(int n, int m) {
        System.out.println("\nGrant Chart (present time):\n");
        int i, j;
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= m; j++) {
                if (i == 1 || i == n
                        || j == 1) {
                    System.out.print("*");
                } else if (grantChartTimeLine.contains(j)) {
                    if (j == grantChartTimeLine.getLast()) {
                        System.out.print(j + "  *");
                    } else {
                        System.out.print(j + " ");
                    }

                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }

}
