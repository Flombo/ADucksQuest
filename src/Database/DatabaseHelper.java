package Database;

import java.sql.*;

public class DatabaseHelper {

    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://127.0.0.1/game";

    public void selectAll() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = this.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM level;");
            ResultSet resultSet = stmt.getResultSet();
            System.out.println("select");
            stmt.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeConnection(stmt, conn);
        }
    }

    private synchronized void closeConnection(PreparedStatement statement, Connection connection){
        try {
            if (statement != null && connection != null) {
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertLevel(String levelname, String tags, String levelCode){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = this.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO level(levelname, tags, levelcode) values (?,?,?)"
            );
            preparedStatement.setString(1, levelname);
            preparedStatement.setString(2, tags);
            preparedStatement.setString(3, levelCode);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection(preparedStatement, connection);
        }
    }

    private Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(
                    DB_URL, "root", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
