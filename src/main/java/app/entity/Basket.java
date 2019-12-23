package app.entity;

import java.util.HashMap;
import java.util.Map;

public class Basket {

    private Map<String, Integer> basket;
    private static Basket instance;

    private Basket() {
        basket = new HashMap<>();
    }

    /**
     * @return the basket of current user. Is the basket is empty, creates new basket
     */
    public static Basket getBasket() {
        if (instance == null) {
            instance = new Basket();
        }
        return instance;
    }

    /**
     * Adds the selected item to the basket
     * @param item chosen product
     */
    public void toBasket(String item) {
        int value = 1;
        if (basket.containsKey(item)) {
            value = basket.get(item) + 1;
            basket.remove(item);
        }
        basket.put(item, value);
    }

    /**
     * @return the map contains chosen goods
     */
    public Map<String, Integer> getGoods() {
        return basket;
    }
}
