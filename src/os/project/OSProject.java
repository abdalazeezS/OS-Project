package os.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class OSProject {

    public static void main(String[] args) {

        LinkedList<Process> processesList = new LinkedList();
        File processFile = new File("processes.txt");
        int memorySize, pageSize, roundRoubinQ, contextSwitch;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(processFile));
            String st;

            while ((st = bufferedReader.readLine()) != null) {
                memorySize = Integer.parseInt(st);

                pageSize = Integer.parseInt(bufferedReader.readLine());

                roundRoubinQ = Integer.parseInt(bufferedReader.readLine());

                contextSwitch = Integer.parseInt(bufferedReader.readLine());

                while ((st = bufferedReader.readLine()) != null) {
                    String[] processData = st.split(" ");
                    int processId = Integer.parseInt(processData[0]);
                    int arrivalTime = Integer.parseInt(processData[1]);
                    int cpuBurst = Integer.parseInt(processData[2]);
                    int sizeInBytes = Integer.parseInt(processData[3]);

                    Process process = new Process(processId, arrivalTime, cpuBurst, sizeInBytes);
                    processesList.add(process);

                }

                if (st == null) {
                    ProcessesScheduling execution = new ProcessesScheduling(processesList, contextSwitch);
                    
                    execution.FCFC();
                    
                    System.out.println("\n###############################################################\n");
                    
                    execution.SJF();
                    
                    System.out.println("\n###############################################################\n");
                    
                    execution.RR(roundRoubinQ);
                }

            }

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }

}
