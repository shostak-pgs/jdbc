package app.utils;

import app.entity.Basket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GoodsUtilTest {
    Basket basket;

    @Before
    public void init(){
        basket = Basket.getBasket();
    }

    @After
    public void destroy(){ basket.getGoods().clear();
    }

    @Test
    public void testCountTotalPriceEmpty() {
        //Given
        basket = Basket.getBasket();
        Double expected = 0.0;
        //When
        Double actual = GoodsUtil.countTotalPrice(basket);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void testCountTotalPrice() {
        //Given
        basket.getGoods().put("War and Peace (5.5 $)", 1);
        basket.getGoods().put("The Great Gatsby (4.5 $)", 2);
        basket.getGoods().put("Hamlet by William Shakespeare (3.5 $)", 3);
        Double expected = 25.0;
        //When
        Double actual = GoodsUtil.countTotalPrice(basket);
        //Then
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testCountTotalPriceNullCase() {
        //Given
        basket.getGoods().put(null, 1);
        basket.getGoods().put("The Great Gatsby (4.5 $)", 2);
        //When
        Double actual = GoodsUtil.countTotalPrice(basket);
    }
    @Test
    public void testBuildGoodsMap() {
        //Given
        Vector<String> vector = new Vector<>();
        vector.add("War and Peace");
        vector.add("The Great Gatsby");
        Enumeration<String> goods = vector.elements();

        ServletContext contextMock = mock(ServletContext.class);
        when(contextMock.getInitParameterNames()).thenReturn(goods);
        when(contextMock.getInitParameter("The Great Gatsby")).thenReturn("4.5");
        when(contextMock.getInitParameter("War and Peace")).thenReturn("5.5");

        Map<String, Double> expected = new HashMap<>();
        expected.put("War and Peace", 5.5);
        expected.put("The Great Gatsby", 4.5);
        //When
        Map<String, Double> actual = GoodsUtil.buildGoodsMap(contextMock);
        //Then
        assertEquals(expected, actual);

    }

}