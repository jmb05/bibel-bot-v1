/*
 * Copyright (C) 2020 Jared
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmb05.bibel.bot.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import jmb05.bibel.bot.Main;

/**
 *
 * @author Jared
 */
public class SQLiteDataSource {
    
    private static final HikariConfig CONFIG = new HikariConfig();
    private static HikariDataSource ds;
    
    public SQLiteDataSource(){
        java.util.logging.Logger.getLogger(Main.class.getName()).info("Starting initialization...");
        try{
            final File dbFile = new File("database.db");
            if(!dbFile.exists()){
                if(dbFile.createNewFile()){
                    java.util.logging.Logger.getLogger(Main.class.getName()).info("Created database file");
                }else{
                    java.util.logging.Logger.getLogger(Main.class.getName()).info("Could not create database file.");
                }
            }
        }catch(IOException e){
            java.util.logging.Logger.getLogger(SQLException.class.getName()).log(Level.SEVERE, "", e);
        }
        
        CONFIG.setJdbcUrl("jdbc:sqlite:database.db");
        CONFIG.setConnectionTestQuery("SELECT 1");
        CONFIG.addDataSourceProperty("cachePrepStmts", "true");
        CONFIG.addDataSourceProperty("prepStmtCacheSize", "250");
        CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        CONFIG.setMaximumPoolSize(100);
        CONFIG.setConnectionTimeout(30000);
        CONFIG.setLeakDetectionThreshold(30000);
        ds = new HikariDataSource(CONFIG);
        
        try {
            final Statement statement = getConnection().createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS guild_settings("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "guild_id VARCHAR(20) NOT NULL, "
                    + "prefix VARCHAR(255) NOT NULL DEFAULT '!', "
                    + "losungen_channel_name VARCHAR(255) NOT NULL DEFAULT 'losungenz', "
                    + "showLosung INTEGER(1) DEFAULT 1,"
                    + "time VARCHAR(8) DEFAULT '08:00:00'"
                    + ");"); 
            java.util.logging.Logger.getLogger(Main.class.getName()).info("Table initialized.");
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SQLException.class.getName()).log(Level.SEVERE, "", ex);
        }
    }
    
    public Connection getConnection() throws SQLException{
        return ds.getConnection();
    }
}
