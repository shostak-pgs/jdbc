package app.dao;

public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();

    public static DAOProvider getInstance() {
        return instance;
    }

    private DAOProvider(){};

    private UserDao userDao = new UserDao();

    public UserDao getUserDao(){
        return userDao;
    }
}
