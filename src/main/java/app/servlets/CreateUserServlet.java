package app.servlets;

import app.page_path.PagePath;
import app.utils.GoodsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class CreateUserServlet extends HttpServlet {
    private static final String USER_NAME = "name";
    public static final String ALL_GOODS_LIST = "allGoodsList";

    private Map<String, Double> goodsMap;

    /**
     * Called by the servlet container to indicate to a servlet that the servlet is being placed
     * into service. It receives ServletConfig object from the servlet container for getting parameters.
     * @param config the <code>ServletConfig</code> object that contains configuration
     * information for this servlet
     * @throws ServletException if an exception occurs interrupts the servlet's normal operation
     */
    @Override
    public void init(final ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        goodsMap = GoodsUtil.buildGoodsMap(servletContext);
        servletContext.setAttribute(ALL_GOODS_LIST, goodsMap);
        super.init(config);
    }

    /**
     * Handles {@link HttpServlet} POST Method. Calls user creation if name correct, otherwise redirect to the error page
     * @param request  the {@link HttpServletRequest} contains user name as a parameter. User name
     * transferred from the start(default) HTML page
     * @param response the {@link HttpServletResponse}
     * @throws IOException      thrown when occur exception in redirecting
     * @throws ServletException thrown when occur exception in redirecting
     */
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {

        if (request.getParameter(USER_NAME).equals("")) {
            forwardTo(request, response, PagePath.TERMS_ERROR);

        } else {
            createUser(request.getSession(true), request.getParameter(USER_NAME));
            forwardTo(request, response, PagePath.ADD);
        }

    }

    /**
     * Redirect request to the transferred path
     * @param request  the {@link HttpServletRequest}
     * @param response the {@link HttpServletResponse}
     * @param path     the path for redirection
     * @throws IOException      thrown when occur exception in redirecting
     * @throws ServletException thrown when occur exception in redirecting
     */
    private void forwardTo(final HttpServletRequest request, final HttpServletResponse response, PagePath path) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path.getPath());
        requestDispatcher.forward(request, response);
    }

    /**
     * Create a new user setting the name as a session attribute
     * @param session the user's {@link HttpSession}
     * @param name    user's name
     */
    private void createUser(final HttpSession session, final String name) {
        session.setAttribute(USER_NAME, name);
    }

}
