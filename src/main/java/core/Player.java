package core;

public class Player {
    private int money;

    public Player(int initialMoney) {
        this.money = initialMoney;
    }

    public int getMoney() { return money; }

    public void addMoney(int amount) {
        money += amount;
    }

    public boolean spendMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        }
        return false;
    }
}
