package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by MSI on 2016-10-01.
 */
public class ApplicationDao {

    public ApplicationDao () {

    }


    public static void insert(Connection connection, String lastTitle, String lastProcess, long time) {
        PreparedStatement statement = null;
        String sql = "INSERT INTO APPLICATION(name, seconds, userId) VALUES(?, ?, ?)";

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setString(1, lastProcess);
            statement.setInt(2, (int) time);
            statement.setInt(3, 0);

            statement.executeUpdate();
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
