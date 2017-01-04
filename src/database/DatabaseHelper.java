package database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by 张启 on 2016/4/14.
 * database helper
 */
public class DatabaseHelper {

    public static Connection getConnection() throws NamingException, SQLException {
        Context context = new InitialContext();
        DataSource source = (DataSource) context.lookup("java:comp/env/jndi/here");
        return source.getConnection();
    }

    public static ResultSet executeQuery(String sql) throws SQLException {
        Connection connection = null;
        Statement  statement  = null;
        ResultSet  resultSet  = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (NamingException e) {
            e.printStackTrace();
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return resultSet;
    }

    public static int executeQueryForCount(String sql) {
        ResultSet rs = null;
        try {
            rs = executeQuery(sql);
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            release(rs);
        }
    }

    public static int executeUpdate(String sql) throws SQLException {
        Connection connection = null;
        Statement  statement  = null;
        int result = -1;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            result = statement.executeUpdate(sql);
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public static boolean executeSingleUpdate(String sql) {
        try {
            int result = executeUpdate(sql);
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean executeTransactionForUpdate(String... sqls) {
        Connection connection;
        try {
            connection = getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            return false;
        }

        Statement statement = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for (String sql : sqls) {
                statement.executeUpdate(sql);
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
            return false;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void release(ResultSet resultSet) {
        if (resultSet == null) {
            return;
        }
        try {
            Statement statement = resultSet.getStatement();
            Connection connection = statement.getConnection();
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
