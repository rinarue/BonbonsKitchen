package core;

public class Player {
    private int money;
    private static int totalEarned = 0;

    public Player(int initialMoney) {
        this.money = initialMoney;
    }

    public int getMoney() { return money; }

    public void addMoney(int amount) {
        money += amount;
        totalEarned += amount;
    }

    public boolean spendMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        }
        return false;
    }

    public static int getTotalMoneyEarned() {
        return totalEarned;
    }
}
