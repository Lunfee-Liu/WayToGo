package grammar.fanxing;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        store.sell(new Banana());
        List<Banana> bananas = new ArrayList<>();
        List<Fruit> fruits = new ArrayList<>();
        store.batchSell(fruits);
//        store.batchSell(bananas);

        Fruit[] fruits1 = new Fruit[]{new Banana(), new Fruit()};
        store.batchSellFruits(fruits1);
        store.batchSellFruits1(bananas);
    }
}
