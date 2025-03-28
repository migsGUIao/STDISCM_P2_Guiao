import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class STDISCM_P2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nInstances = getValidatedInput(scanner, "Enter maximum number of concurrent instances (n): ", 1, Integer.MAX_VALUE, "Number of instances must be greater than 0.");
        int tankCount = getValidatedInput(scanner, "Enter number of tank players in the queue (t): ", 0, Integer.MAX_VALUE, "Number of tank players cannot be negative.");
        int healerCount = getValidatedInput(scanner, "Enter number of healer players in the queue (h): ", 0, Integer.MAX_VALUE, "Number of healer players cannot be negative.");
        int dpsCount = getValidatedInput(scanner, "Enter number of DPS players in the queue (d): ", 0, Integer.MAX_VALUE, "Number of DPS players cannot be negative.");
        int t1 = getValidatedInput(scanner, "Enter minimum clear time (t1 seconds): ", 0, Integer.MAX_VALUE, "Minimum clear time must be greater than 0.");
        int t2 = getValidatedInput(scanner, "Enter maximum clear time (t2 seconds, <=15): ", t1, 15 , "Maximum clear time must be at least t1 and no more than 15 seconds.");
        
        DungeonManager manager = new DungeonManager(nInstances);
        PlayerQueue playerQueue = new PlayerQueue(tankCount, healerCount, dpsCount);

        List<Thread> partyThreads = new ArrayList<>();
        int partyCounter = 0;

        // Form parties while there are enough players.
        while (playerQueue.canFormParty()) {
            if (playerQueue.formParty()) {
                partyCounter++;
                Party party = new Party(partyCounter, t1, t2, manager);
                Thread partyThread = new Thread(party);
                partyThreads.add(partyThread);
                partyThread.start();
                // Print instance status after scheduling a party.
                manager.printStatus();
            }
        }

        // Wait for all party threads to complete.
        for (Thread t : partyThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Print the final summary.
        manager.printFinalSummary();
        scanner.close();
    }
    
    public static int getValidatedInput(Scanner scanner, 
                                        String prompt, 
                                        int minVal,
                                        int maxVal, 
                                        String errorMessage) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= minVal && value <= maxVal) {
                    break;
                } else {
                    System.out.println("Invalid input: " + errorMessage);
                    System.out.println("Value must be between " + minVal + " and " + maxVal + ". Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // discard the invalid input
            }
        }
        return value;
    }
}

