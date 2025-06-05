package core;

public class Player {
    private double money;

    public Player(double initialMoney) {
        this.money = initialMoney;
    }

    public double getMoney() { return money; }

    public void addMoney(double amount) {
        money += amount;
    }

    public boolean spendMoney(double amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        }
        return false;
    }
}
