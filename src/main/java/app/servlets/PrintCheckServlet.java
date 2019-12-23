package app.servlets;

import app.entity.Basket;
import app.utils.GoodsUtil;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import static app.servlets.GoodsAddServlet.BASKET;

public class PrintCheckServlet extends HttpServlet {
    private static final String USER_NAME = "name";
    Basket basket;

    /**
     * Handles {@link HttpServlet} POST Method. Creates an HTML page containing the completed order and its
     * price. In this method user's name gets from the session
     * @param request the {@link HttpServletRequest}
     * @param response the {@link HttpServletResponse}
     * @throws IOException thrown when occur exception in getting Writer object
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String userName = (String) request.getSession().getAttribute(USER_NAME);
        PrintWriter writer = response.getWriter();
        basket = (Basket) request.getSession().getAttribute(BASKET);
        writer.println(
                "<!DOCTYPE html>"
                        + " <html lang=\"en\">"
                        + "  <head>"
                        + "    <meta charset=\"UTF-8\">"
                        + "    <title>Online Shop</title>"
                        + "       <style type=\"text/css\">"
                        + "         #greetingStyle {"
                        + "           margin-left: 10%;"
                        + "           margin-right: 10%;"
                        + "           background: #fc0;"
                        + "           padding: 10px;"
                        + "           text-align: center;"
                        + "         }"
                        + "       </style>"
                        + "       <style type=\"text/css\">"
                        + "         #formStyle {"
                        + "           margin-left: 10%;"
                        + "           margin-right: 10%;"
                        + "           background: #01DF01;"
                        + "           padding: 10px;"
                        + "           text-align: center;"
                        + "         }"
                        + "       </style>"
                        + "   </head>"
                        + "   <body>"
                        + "     <div id=\"greetingStyle\">"
                        + "       <h3>Dear " + userName + ", your order:</h3>"
                        + "     </div>"
                        + "     <div id=\"formStyle\"> ");
        int i = 1;
        for (Map.Entry<String, Integer> pair : basket.getGoods().entrySet()) {
            writer.printf("<p>%d) %s x %d</p>\n", i, pair.getKey(), pair.getValue());
            i += 1;
        }
        writer.printf("<p>Total: $ %.2f </p>\n", + GoodsUtil.countTotalPrice(basket));

        writer.println(
                        "    </div>"
                        + "   </body>"
                        + " </html>");
    }

}
