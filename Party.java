import java.util.Random;

public class Party implements Runnable {
    private int partyId;
    private int t1;
    private int t2;
    private DungeonManager manager;

    public Party(int partyId, int t1, int t2, DungeonManager manager) {
        this.partyId = partyId;
        this.t1 = t1;
        this.t2 = t2;
        this.manager = manager;
    }

    @Override
    public void run() {
        DungeonInstance instance = manager.acquireInstance();
        if (instance == null) {
            System.out.println("Error: No available instance found for Party " + partyId + ".");
            return;
        }

        System.out.println("[START] Party " + partyId + " has been assigned to Instance " + instance.getId() + " and is now active.");

        // Simulate the dungeon run with a random sleep time between t1 and t2 seconds.
        int runTime = new Random().nextInt(t2 - t1 + 1) + t1;
        try {
            Thread.sleep(runTime * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        manager.updateInstance(instance, runTime);
        System.out.println("[END] Party " + partyId + " finished in Instance " + instance.getId() + " (Run time: " + runTime + "s).");
        manager.releaseInstance(instance);
    }
}