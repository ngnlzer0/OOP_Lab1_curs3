package train.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // URL: має вказувати на новостворену базу даних
    private static final String URL = "jdbc:postgresql://localhost:5432/my_app_db_test1_1_1";

    // USER: має бути користувач, створений для додатку
    private static final String USER = "my_app_user";

    // PASSWORD: має бути пароль користувача додатку
    private static final String PASSWORD = "1234qwer";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
