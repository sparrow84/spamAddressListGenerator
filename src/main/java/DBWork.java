import org.sqlite.JDBC;
import java.io.File;
import java.sql.*;



public class DBWork {
    private static Connection connection;


    /**
     * Подключение к базе данных
     *
     * @throws Exception
     */
    public static void connect () throws Exception {
        //регистрация драйвера JDBC поставщика СУБД
        DriverManager.registerDriver(new JDBC());
        //получение соединеня с СУБД
        connection = DriverManager.getConnection(createUrlJdbc(getResource("main2.db")));
        connection.setAutoCommit(false);
    }

    /**
     * Отключение от БД
     */
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запись в базу нового объекта
     *
     * @param name
     * @throws SQLException
     */
    public void insertName(String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO my_table(name) VALUES(?);")) {
            statement.setString(1, name);
            statement.execute();
            connection.commit();
        } catch (Throwable t) {
            connection.rollback();
        }
    }
/*
    protected static ResultObject createResult(ResultSet resultSet) throws SQLException {
        return new ResultObject(resultSet.getInt("id"), resultSet.getString("name"));
    }
*/
    /**
     * Создание адреса для соединения с JDBC
     *
     * @param dbFile путь к файлу бд
     * @return адрес бд (строка)
     */
    protected static String createUrlJdbc(File dbFile) {
        return String.format("jdbc:sqlite:%s", dbFile.getAbsolutePath());
    }

    /**
     * Получение ресурса по его имени
     *
     * @param resourceName имя ресура
     * @return файл ресурса
     */
    protected static File getResource(String resourceName) {
        return new File(Main.class.getClassLoader().getResource(resourceName).getFile());
    }

}
