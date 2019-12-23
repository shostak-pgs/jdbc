package app.utils;

import app.entity.Basket;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GoodsUtil {

    /**
     * Return the price of the transferred item.
     * @param item String representation of item position, contains name and price like"The Lord of the Rings (3.5 $)"
     * @return the price of item
     */
    private static double getPrice(String item) {
        if(item == null) {
            throw new NullPointerException("One argument is null! Check out web.xml parameters!");
        }
        Pattern pricePattern = Pattern.compile("\\(([0-9]+\\.[0-9]+)\\s\\$\\)");
        Matcher matcher = pricePattern.matcher(item);
        double price = 0.0;
        while (matcher.find()) {
            price = Double.parseDouble(matcher.group(1));
        }
        return price;
    }

    /**
     * The method creates a {@link Map} for storing the list of goods and their prices
     * in a key-value format. The list is built from the {@link ServletContext} object passed to the method
     * @param servletContext ServletContext object in which the necessary data is stored
     * @return the list of goods and their prices storied in map
     */
    public static Map<String, Double> buildGoodsMap(final ServletContext servletContext) {
        Map<String, Double> goodsMap = new HashMap<>();
        Enumeration<String> goods = servletContext.getInitParameterNames();
        while (goods.hasMoreElements()) {
            String name = goods.nextElement();
            goodsMap.put(name, Double.parseDouble(servletContext.getInitParameter(name)));
        }
        return goodsMap;
    }

    /**
     * Calculates the amount of the completed order
     * @param basket {@link Map} contains chosen goods
     * @return calculated price
     */
    public static double countTotalPrice(Basket basket) {
        if(basket == null) {
            throw new NullPointerException("Basket is empty!");
        }
        double totalPrice = 0.0;
        Map<String, Integer> order = basket.getGoods();
        double cost;
        for (Map.Entry<String, Integer> pair : order.entrySet()) {
            cost = getPrice(pair.getKey()) * pair.getValue();
            totalPrice += cost;
        }
        return totalPrice;
    }
}
