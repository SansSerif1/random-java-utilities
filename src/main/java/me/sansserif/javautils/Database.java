package me.sansserif.javautils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

public class Database implements Runnable {
    private String url;
    private Duration timeout;
    private Thread renewer = new Thread(this);
    private LocalDateTime candie;
    private Connection current = null;

    public Database(String ip, int port, String username, String password, String dbname, String jdbc_type, Duration timeout) throws SQLException {
        this.url = "jdbc:" + jdbc_type + "://" + ip + ":" + port + "/" + dbname + "?user=" + username + "&password=" + password;
        this.timeout = timeout;

        current = DriverManager.getConnection(url);
        getConn();
        Runtime.getRuntime().addShutdownHook(new Thread(this::terminate));
    }
    public void terminate() {
        candie = LocalDateTime.MIN;
        renewer.interrupt();
    }
    public Connection getConn() throws SQLException {
        candie = LocalDateTime.now().plus(timeout);
        if (current.isClosed()) current = DriverManager.getConnection(url);
        if (!renewer.isAlive()) {
            if (renewer.getState().equals(Thread.State.TERMINATED))
                renewer = new Thread(this);
            renewer.start();
        }
        return current;
    }

    @Override
    public void run() {
        while (Duration.between(candie, LocalDateTime.now()).isNegative()) {
            try {
                Thread.sleep(Duration.between(LocalDateTime.now(), candie).toMillis());
            } catch (Exception ignored) {}
        }
        try {
            current.close();
        } catch (SQLException ignored) {}
    }
}