public class PlayerQueue {
    private int tankCount;
    private int healerCount;
    private int dpsCount;

    public PlayerQueue(int tankCount, int healerCount, int dpsCount) {
        this.tankCount = tankCount;
        this.healerCount = healerCount;
        this.dpsCount = dpsCount;
    }

    public synchronized boolean formParty() {
        if (tankCount >= 1 && healerCount >= 1 && dpsCount >= 3) {
            tankCount--;
            healerCount--;
            dpsCount -= 3;
            return true;
        }
        return false;
    }

    public synchronized boolean canFormParty() {
        return (tankCount >= 1 && healerCount >= 1 && dpsCount >= 3);
    }
}