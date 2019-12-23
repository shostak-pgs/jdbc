package app.main;

import app.entity.User;
import java.sql.*;
import java.util.Arrays;

public class Main {

    private static final String CREATE_USERS_TABLE_SQL_STATEMENT = "CREATE TABLE users (" +
            "id INTEGER not null," +
            "username VARCHAR(255)," +
            "password VARCHAR(255)," +
            "PRIMARY KEY(id))";

    private static final String INSERT_USERS_SQL_STATEMENT = "INSERT INTO users values (?,?,?)";
    private static final String SELECT_ALL_USERS_SQL_STATEMENT = "SELECT * FROM users";
    private static final String UPDATE_USERS_USERNAME_SQL_STATEMENT = "UPDATE users SET username = ? WHERE id = ?";
    private static final String DELETE_USER_SQL_STATEMENT = "DELETE FROM users WHERE id = ?";

    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")) {

            createUsersTable(conn);

//            insertUsers(conn);
            batchInsertUsers(conn);

            selectAndLogUsers(conn);

            System.out.println("----------------------------UPDATE----------------------------");
            updateUsersUsername(conn, 1L, "new_name");
            selectAndLogUsers(conn);

            System.out.println("----------------------------DELETE----------------------------");
            deleteUser(conn, 3L);
            selectAndLogUsers(conn);
        }
    }

    private static void createUsersTable(Connection conn) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(CREATE_USERS_TABLE_SQL_STATEMENT)) {
            System.out.println("CREATE TABLE: rows updated - " + st.executeUpdate());
        }
    }

    private static void insertUsers(Connection conn) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(INSERT_USERS_SQL_STATEMENT)) {
            for (int i = 0; i < 10; ++i) {
                st.setLong(1, i);
                st.setString(2, "usr" + i);
                st.setString(3, "pass" + i);
                System.out.println("INSERT: rows updated - " + st.executeUpdate());
            }
        }
    }

    private static void batchInsertUsers(Connection conn) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(INSERT_USERS_SQL_STATEMENT)) {
            for (int i = 0; i < 10; ++i) {
                st.setLong(1, i);
                st.setString(2, "usr" + i);
                st.setString(3, "pass" + i);
                st.addBatch();
            }
            System.out.println("BATCH INSERT: rows updated - " + Arrays.toString(st.executeBatch()));
        }
    }

    private static void selectAndLogUsers(Connection conn) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(SELECT_ALL_USERS_SQL_STATEMENT );
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                System.out.println(new User(rs.getLong("id"), rs.getString("username"),
                        rs.getString("password")));
            }
        }
    }

    private static void updateUsersUsername(Connection conn, Long userId, String username) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(UPDATE_USERS_USERNAME_SQL_STATEMENT)) {
            st.setString(1, username);
            st.setLong(2, userId);
            System.out.println("UPDATE: rows updated - " + st.executeUpdate());
        }
    }

    private static void deleteUser(Connection conn, Long userId) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement(DELETE_USER_SQL_STATEMENT)) {
            st.setLong(1, userId);
            System.out.println("DELETE: rows updated - " + st.executeUpdate());
        }
    }
}
