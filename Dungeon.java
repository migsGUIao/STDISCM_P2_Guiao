import java.util.*;
import java.util.concurrent.Semaphore;

public class Dungeon {

    static int nInstances;  
    static int tankCount;   
    static int healerCount; 
    static int dpsCount;    
    static int t1;          
    static int t2;          

    static Semaphore instanceSemaphore;
    static List<DungeonInstance> instances = new ArrayList<>();

    static final Object instanceLock = new Object();  
    static final Object playerLock = new Object();    

    static int partyCounter = 0;

    static class DungeonInstance {
        int id;
        boolean active;
        int partiesServed;
        int totalTime;

        DungeonInstance(int id) {
            this.id = id;
            this.active = false;
            this.partiesServed = 0;
            this.totalTime = 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter maximum number of concurrent instances (n): ");
            nInstances = scanner.nextInt();
            System.out.print("Enter number of tank players in the queue (t): ");
            tankCount = scanner.nextInt();
            System.out.print("Enter number of healer players in the queue (h): ");
            healerCount = scanner.nextInt();
            System.out.print("Enter number of DPS players in the queue (d): ");
            dpsCount = scanner.nextInt();
            System.out.print("Enter minimum clear time (t1 seconds): ");
            t1 = scanner.nextInt();
            System.out.print("Enter maximum clear time (t2 seconds, <=15): ");
            t2 = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter integer values.");
            scanner.close();
            return;
        }

        // Initialize dungeon instances and semaphore.
        for (int i = 0; i < nInstances; i++) {
            instances.add(new DungeonInstance(i));
        }
        instanceSemaphore = new Semaphore(nInstances);

    }
}