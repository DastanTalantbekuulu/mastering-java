package com.mastering.concurrency.patterns.guarded_suspension.logistic;

import java.util.ArrayList;
import java.util.List;

import static com.mastering.concurrency.patterns.guarded_suspension.logistic.LoadingBay.EMPTY;

public class LogisticDemo {

    private static final String RESET = "\u001B[0m";
    private static final String BG_BLUE = "\u001B[44m\u001B[37m";
    private static final String BG_GREEN = "\u001B[42m\u001B[30m";
    private static final String YELLOW_BOLD = "\u001B[1;33m";

    static void main() throws InterruptedException {
        int dockCount = 3;
        int truckCount = 8;

        System.out.println("LOGISTICS CENTER SIMULATION");
        System.out.println("Docks: " + dockCount + ", Trucks: " + truckCount);
        System.out.println("---------------------------------------------------------------");

        LoadingBay bay = createLoadingBay(dockCount);
        List<Thread> trucks = new ArrayList<>();

        for (int i = 1; i <= truckCount; i++) {
            trucks.add(new Thread(new Truck(i, bay)));
        }

        for (Thread t : trucks) t.start();
        for (Thread t : trucks) t.join();

        System.out.println("---------------------------------------------------------------");
        System.out.println("All trucks serviced");
    }

    private static LoadingBay createLoadingBay(int dockCount) {
        BayLogger logger = (event, docks, waitingCount) -> {
            StringBuilder sb = new StringBuilder();

            sb.append(String.format("%-12s | ", event));

            for (int dock : docks) {
                String visual;
                if (dock == EMPTY) {
                    visual = BG_GREEN + " [ FREE ] " + RESET;
                } else {
                    visual = BG_BLUE + String.format(" [TRK-%02d] ", dock) + RESET;
                }
                sb.append(visual).append(" ");
            }

            sb.append("| Queue: ");
            if (waitingCount > 0) {
                sb.append(YELLOW_BOLD);
                sb.append("🚛 ".repeat(waitingCount));
                sb.append(RESET);
            } else {
                sb.append("Empty");
            }

            System.out.println(sb);
        };

        return new LoadingBay(dockCount, logger);
    }
}