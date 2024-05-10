package org.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

class Process {
    int priority;
    String pid;
    int burstTime;
    int arrivalTime;

    public Process(int priority, String pid, int burstTime, int arrivalTime) {
        this.priority = priority;
        this.pid = pid;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }
}

public class PriorityScheduling {
    public static List<String> gantt = new ArrayList<>();
    public  static Map<String, int[]> completed = new HashMap<>();
    public static void priority(List<Process> processList) {

        int t = 0;
        int totaltt=0,totalwt = 0,totalct = 0;
        while (!processList.isEmpty()) {
            List<Process> available = new ArrayList<>();
            for (Process p : processList) {
                if (p.arrivalTime <= t) {
                    available.add(p);
                }
            }

            if (available.isEmpty()) {
                gantt.add("Idle");
                t += 1;
                continue;
            } else {
                available.sort(Comparator.comparingInt(p -> p.priority));
                Process process = available.get(0);

                // Service the process
                // 1. Remove the process
                processList.remove(process);
                // 2. Add to gantt chart
                String pid = process.pid;
                gantt.add(pid);
                // 3. Update the time
                int burstTime = process.burstTime;
                t += burstTime;
                // Create an entry in the completed map
                // Calculate ct, tt, wt
                int ct = t;
                totalct+=ct;
                int arrivalTime = process.arrivalTime;
                int tt = ct - arrivalTime;
                totaltt+=tt;
                int wt = tt - burstTime;
                totalwt+=wt;
                completed.put(pid, new int[]{ct, tt, wt});
            }
        }

        System.out.println(gantt);
        for (Map.Entry<String, int[]> entry: completed.entrySet()){
            String pid = entry.getKey();
            int[] details = entry.getValue();
            System.out.println(pid + ": " + Arrays.toString(details));
        }
//        System.out.println(completed);

    }

    public static void main(String[] args) {
        List<Process> processList = new ArrayList<>();
        processList.add(new Process(3, "p1", 8, 0));
        processList.add(new Process(4, "p2", 2, 1));
        processList.add(new Process(4, "p3", 4, 3));
        processList.add(new Process(5, "p4", 1, 4));
        processList.add(new Process(2, "p5", 6, 5));
        processList.add(new Process(6, "p5", 5, 6));
        processList.add(new Process(1, "p7", 1, 10));

        priority(processList);
    }
}
