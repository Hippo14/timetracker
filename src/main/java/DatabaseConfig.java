import org.h2.tools.DeleteDbFiles;
import java.sql.*;

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
//        DeleteDbFiles.execute("~", DB_NAME, true);
        prepare();
    }

    private void prepare() {
        Connection connection = getConnection();
        Statement statement = null;

        String createUser = "CREATE TABLE IF NOT EXISTS USER(id int auto_increment primary key, name varchar(255))";
        String createApplication = "CREATE TABLE IF NOT EXISTS APPLICATION(id int auto_increment primary key, name varchar(255), seconds real, userId int, title varchar(255))";
//        String insertUser = "INSERT INTO USER(id, name) VALUES(0, 'Hippo')";
        String insertUser = "INSERT INTO USER(id, name) SELECT 0, 'Hippo' WHERE NOT EXISTS(SELECT 1 FROM USER WHERE id = 0 AND name = 'Hippo');";
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.execute(createUser);
            statement.execute(createApplication);
            statement.execute(insertUser);

            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
