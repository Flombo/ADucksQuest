package Database;

import GameObjects.Entities.Level;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {

    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://127.0.0.1/game";

    public ArrayList<Level> selectAll() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Level> levels = new ArrayList<>();
        try {
            conn = this.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM level;");
            stmt.execute();
            ResultSet resultSet = stmt.getResultSet();
            while (resultSet.next()){
                String name = resultSet.getString("levelname");
                String tags = resultSet.getString("tags");
                String levelcode = resultSet.getString("levelcode");
                Image thumbnail = new Image(new ByteArrayInputStream(resultSet.getBytes("thumbnail")));
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                levels.add(new Level(name, tags, levelcode, thumbnail, x, y));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeConnection(stmt, conn);
        }
        return levels;
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

    public void insertLevel(String levelname, String tags, String levelCode, byte[] thumbnail, int x, int y){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = this.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO level(levelname, tags, levelcode, thumbnail, x, y) values (?,?,?,?,?,?)"
            );
            preparedStatement.setString(1, levelname);
            preparedStatement.setString(2, tags);
            preparedStatement.setString(3, levelCode);
            preparedStatement.setBytes(4, thumbnail);
            preparedStatement.setInt(5, x);
            preparedStatement.setInt(6, y);
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
