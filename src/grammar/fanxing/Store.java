package grammar.fanxing;

import java.util.List;

public class Store {
    public void sell(Fruit fruit) {
        System.out.println(fruit.show());
    }

    public void batchSell(List<Fruit> fruits) {
        for (Fruit fruit : fruits) {
            System.out.println(fruit.show());
        }
    }

    public void batchSellFruits(Fruit[] fruits) {
        for (Fruit fruit : fruits) {
            System.out.println(fruit.show());
        }
    }

    public void batchSellFruits1(List<? extends Fruit> fruits) {
        for (Fruit fruit : fruits) {
            System.out.println(fruit.show());
        }
    }
}
