import org.h2.tools.DeleteDbFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by MSI on 2016-10-01.
 */
public class DatabaseConfig {

    private static final String DB_NAME = "timetracker";

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/" + DB_NAME;
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    private DatabaseConfig() {
        DeleteDbFiles.execute("~", DB_NAME, true);
    }

    private final static class SingletonHolder {
        private final static DatabaseConfig instance = new DatabaseConfig();
    }

    public final static DatabaseConfig getInstance() {
        return SingletonHolder.instance;
    }

    public static Connection getConnection() {
        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbConnection;
    }

}
