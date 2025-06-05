package core;

public class Player {
    private int money;
    private final Inventory inventory;

    public Player(int startingMoney) {
        this.money = startingMoney;
        this.inventory = new Inventory();
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public boolean spendMoney(int amount) {
        if (money < amount) return false;
        money -= amount;
        return true;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
