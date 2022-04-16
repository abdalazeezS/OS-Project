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
                //System.out.println(memorySize);

                pageSize = Integer.parseInt(bufferedReader.readLine());
                //System.out.println(pageSize);

                roundRoubinQ = Integer.parseInt(bufferedReader.readLine());
                //System.out.println(roundRoubinQ);

                contextSwitch = Integer.parseInt(bufferedReader.readLine());
                //System.out.println(contextSwitch);

                while ((st = bufferedReader.readLine()) != null) {
                    String[] processData = st.split(" ");
                    int processId = Integer.parseInt(processData[0]);
                    int arrivalTime = Integer.parseInt(processData[1]);
                    int cpuBurst = Integer.parseInt(processData[2]);
                    int sizeInBytes = Integer.parseInt(processData[3]);

                    Process process = new Process(processId, arrivalTime, cpuBurst, sizeInBytes);
                    processesList.add(process);

//                    for (String data : processData) {
//                        System.out.print(data + " ");
//                    }
//                    System.out.println();
                }

                if (st == null) {
                    Execution execution = new Execution(processesList, contextSwitch);
                    execution.FCFC();
                }

            }

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }

}
