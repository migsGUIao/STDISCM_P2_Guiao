import java.util.*;
import java.util.concurrent.Semaphore;

public class DungeonManager {
    private List<DungeonInstance> instances;
    private Semaphore instanceSemaphore;
    private final Object instanceLock = new Object();

    public DungeonManager(int nInstances) {
        instances = new ArrayList<>();
        for (int i = 0; i < nInstances; i++) {
            instances.add(new DungeonInstance(i));
        }
        instanceSemaphore = new Semaphore(nInstances);
    }

    public Semaphore getSemaphore() {
        return instanceSemaphore;
    }

    // acquires an available instance 
    public DungeonInstance acquireInstance() {
        try {
            instanceSemaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
        synchronized (instanceLock) {
            for (DungeonInstance inst : instances) {
                if (!inst.isActive()) {
                    inst.setActive(true);
                    return inst;
                }
            }
        }
        return null; 
    }

    // after the dungeon run, release instance
    public void releaseInstance(DungeonInstance inst) {
        synchronized (instanceLock) {
            inst.setActive(false);
        }
        instanceSemaphore.release();
    }

    // after a run, instance counters are updateed
    public void updateInstance(DungeonInstance inst, int runTime) {
        synchronized (instanceLock) {
            inst.updateInstance(runTime);
        }
    }

    public void printStatus() {
        synchronized (instanceLock) {
            System.out.println("Current Instance Status:");
            for (DungeonInstance inst : instances) {
                System.out.println(inst);
            }
            System.out.println("----------------------------------");
        }
    }

    public void printFinalSummary() {
        synchronized (instanceLock) {
            System.out.println("\n--- Final Summary ---");
            for (DungeonInstance inst : instances) {
                System.out.println("Instance " + inst.getId() + ": Served " + inst.getPartiesServed() + " parties, Total time served: " + inst.getTotalTime() + " seconds.");
            }
        }
    }
}