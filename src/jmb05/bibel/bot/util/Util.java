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
package jmb05.bibel.bot.util;

import jmb05.bibel.bot.scheduler.Time;
import jmb05.bibel.bot.excel.XLSXReaderExample;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmb05.bibel.bot.Main;
import jmb05.bibel.bot.database.SQLiteManager;
import jmb05.bibel.bot.scheduler.LosungScheduler;

/**
 *
 * @author Jared
 */
public class Util {
    
    
    public static int getDayOfYear(){
        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }
    
    public static String getDate(){
        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        return cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR);
    }
    
    public static String getYear(){
        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        return cal.get(Calendar.YEAR)+"";
    }
    
    public static String getLosung(int dayofyear){
        List<String> parts = new ArrayList();
        for(int i=3;i<7;i++){
            parts.add(XLSXReaderExample.ReadCellData(Main.losungenFile, dayofyear, i));
        }
        return "**Losungstext:**\n"+parts.get(1)+" ("+parts.get(0)+")\n\n**Lehrtext:**\n"+parts.get(3)+" ("+parts.get(2)+")\n";
    }
    
    public static String getJahresLosung(){
        List<String> parts = new ArrayList();
        parts.add(XLSXReaderExample.ReadCellData(Main.jahreslosungenFile, 0, 1));
        parts.add(XLSXReaderExample.ReadCellData(Main.jahreslosungenFile, 0, 2));
        return ""+parts.get(1)+" ("+parts.get(0)+")";
    }
    
    public static String readFromConsole(String prefix) throws IOException { 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(prefix);
        String s = br.readLine();
        return s;
    }
    
    //get methods
    public static String getPrefix(long guildId){
        System.out.println("Searching for prefix...");
        try(Connection conn = SQLiteManager.connect(VariableManager.DATABASE_FILE); PreparedStatement prepStmt = conn.prepareStatement("SELECT prefix FROM guild_settings WHERE guild_id = ?")){
            prepStmt.setString(1, String.valueOf(guildId));

            final ResultSet resultSet = prepStmt.executeQuery();
            if(resultSet.next()){
                String out = resultSet.getString("prefix");
                System.out.println("Returning prefix");
                System.out.println("Closing Database...");
                return  out;
            }
            try(final PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")){
                insertStmt.setString(1, String.valueOf(guildId));
                insertStmt.execute();
            }
        }catch(SQLException e){
            java.util.logging.Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "", e);
        }
        System.out.println("Closed Database");
        return VariableManager.DEAFULT_PREFIX;
    }
    
    public static String getLosungenChannelName(long guildId){
        System.out.println("getLosungenChannelName");
        try{
            try(Connection conn = SQLiteManager.connect(VariableManager.DATABASE_FILE); PreparedStatement prepStmt = conn.prepareStatement("SELECT losungen_channel_name FROM guild_settings WHERE guild_id = ?")){
                prepStmt.setString(1, String.valueOf(guildId));
                final ResultSet resultSet = prepStmt.executeQuery();
                if(resultSet.next()){
                    String out = resultSet.getString("losungen_channel_name");
                    System.out.println("Closing Database...");
                    return  out;
                }
                try(final PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")){
                    insertStmt.setString(1, String.valueOf(guildId));
                    insertStmt.execute();
                }
            }
        }catch(SQLException ex){
            java.util.logging.Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "", ex);
        }
        System.out.println("Closed Database");
        return VariableManager.DEAFULT_CHANNEL;
    }
    
    public static Time getTime(long guildId){
        System.out.println("Searching for time...");
        try(Connection conn = SQLiteManager.connect(VariableManager.DATABASE_FILE); PreparedStatement prepStmt = conn.prepareStatement("SELECT time FROM guild_settings WHERE guild_id = ?")){
            prepStmt.setString(1, String.valueOf(guildId));
            
            
            
            final ResultSet resultSet = prepStmt.executeQuery();
            if(resultSet.next()){
                String[] parts = resultSet.getString("time").split(":");
                System.out.println("Returning time...");
                System.out.println("Closing Database...");
                return  new Time(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            }
            try(final PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")){
                insertStmt.setString(1, String.valueOf(guildId));
                insertStmt.execute();
            }
        }catch(SQLException ex){
            java.util.logging.Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "", ex);
        }
        System.out.println("Closed Database");
        return VariableManager.DEFAULT_TIME;
    }
    
    public static boolean getIfShow(long guildId){
        System.out.println("getPIfShow");
        try(Connection conn = SQLiteManager.connect(VariableManager.DATABASE_FILE); PreparedStatement prepStmt = conn.prepareStatement("SELECT showLosung FROM guild_settings WHERE guild_id = ?")) {
            
            prepStmt.setString(1, String.valueOf(guildId));
            
            final ResultSet resultSet = prepStmt.executeQuery();
            if(resultSet.next()){
                int out = resultSet.getInt("showLosung");
                System.out.println("Closing Database...");
                return out != 0;
            }
            try(final PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO guild_settings(guild_id) VALUES(?)")){
                insertStmt.setString(1, String.valueOf(guildId));
                insertStmt.execute();
            }
        }catch(SQLException e){
            java.util.logging.Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "", e);
        }
        System.out.println("Closed Database");
        return true;
        
    }
    
    
    //set methods
    public static boolean setPrefix(long guildId, String newPrefix){
        System.out.println("setPrefix");
        try(Connection conn = SQLiteManager.connect(VariableManager.DATABASE_FILE); PreparedStatement prepStmt = conn.prepareStatement("UPDATE guild_settings SET prefix = ? WHERE guild_id = ?")){
            prepStmt.setString(1, newPrefix);
            prepStmt.setString(2, String.valueOf(guildId));
            prepStmt.execute();
        }catch(SQLException e){
            java.util.logging.Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "", e);
            System.out.println("Closing Database... fail");
            return false;
        }
        System.out.println("Closed Database");
        return true;
    }
    
    public static boolean setLosungenChannelName(long guildId, String newLosungenChannel){
        System.out.println("setLosungenChannelName");
        try(Connection conn = SQLiteManager.connect(VariableManager.DATABASE_FILE); PreparedStatement prepStmt = conn.prepareStatement("UPDATE guild_settings SET losungen_channel_name = ? WHERE guild_id = ?")){
            prepStmt.setString(1, newLosungenChannel);
            prepStmt.setString(2, String.valueOf(guildId));
            prepStmt.execute();
        }catch(SQLException e){
            java.util.logging.Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "", e);
            System.out.println("Closing Database... fail");
            return false;
        }
        System.out.println("Closed Database");
        return true;
    }
    
    public static boolean setTime(long guildId, Time newTime){
        System.out.println("setTime");
        try(Connection conn = SQLiteManager.connect(VariableManager.DATABASE_FILE); PreparedStatement prepStmt = conn.prepareStatement("UPDATE guild_settings SET time = ? WHERE guild_id = ?")){
            prepStmt.setString(1, newTime.getHour()+":"+newTime.getMinute()+":"+newTime.getSecond());
            prepStmt.setString(2, String.valueOf(guildId));
            prepStmt.execute();
        }catch(SQLException e){
            java.util.logging.Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "", e);
            System.out.println("Closing Database... fail");
            return false;
        }
        
        LosungScheduler.restart(guildId);
        System.out.println("Closed Database");
        return true;
    }
    
    public static boolean setIfShow(long guildId, boolean show){
        System.out.println("setIfShow");
        try(Connection conn = SQLiteManager.connect(VariableManager.DATABASE_FILE); PreparedStatement prepStmt = conn.prepareStatement("UPDATE guild_settings SET showLosung = ? WHERE guild_id = ?")){
            if(show){
                prepStmt.setInt(1, 1);
            }else{
                prepStmt.setInt(1, 0);
            }
            prepStmt.setString(2, String.valueOf(guildId));
            prepStmt.execute();
        }catch(SQLException e){
            java.util.logging.Logger.getLogger(Util.class.getName()).log(Level.SEVERE, "", e);
            System.out.println("Closing Database... fail");
            return false;
        }
        System.out.println("Closed Database");
        return true;
    }
    
    public static String loadToken(){
        String token = "";
        try {
            File file = new File("secret.token");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            token = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return token;
    }
}