public class DungeonInstance {
    private int id;
    private boolean active;
    private int partiesServed;
    private int totalTime;

    public DungeonInstance(int id) {
        this.id = id;
        this.active = false;
        this.partiesServed = 0;
        this.totalTime = 0;
    }

    public int getId() {
        return id;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public synchronized void updateInstance(int runTime) {
        partiesServed++;
        totalTime += runTime;
    }

    public int getPartiesServed() {
        return partiesServed;
    }

    public int getTotalTime() {
        return totalTime;
    }

    @Override
    public String toString() {
        String res;
        if (active) 
            res = "Instance " + id + ": active"; 
        else
            res = "Instance " + id + ": empty"; 
        return res;
    }
}