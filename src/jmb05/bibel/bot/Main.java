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
package jmb05.bibel.bot;

import javax.security.auth.login.LoginException;
import jmb05.bibel.bot.commands.AdminCommand;
import jmb05.bibel.bot.commands.HelpCommand;
import jmb05.bibel.bot.commands.LosungCommand;
import jmb05.bibel.bot.database.SQLiteDataSource;
import jmb05.bibel.bot.scheduler.LosungScheduler;
import jmb05.bibel.bot.util.Util;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

/**
 *
 * @author Jared
 */
public class Main {
    
    public static JDA jda;
    public static String losungenFile = "";
    public static String jahreslosungenFile = "";
    public static String dataBase = "";
    public static int HOUR = 0;
    public static int MINUTE = 0;
    public static int SECOND = 0;
    public static SQLiteDataSource sqliteDataSource;
    
    //Main method
    public static void main(String[] args) throws InterruptedException, LoginException{
        java.util.logging.Logger.getLogger(Main.class.getName()).info("Hi, I am the \"Bibel Bot\"...");
        java.util.logging.Logger.getLogger(Main.class.getName()).info("Initializing Variables ...");
        
        losungenFile = "losungen.xlsx";
        jahreslosungenFile = "jahreslosungen.xlsx";
        
        sqliteDataSource = new SQLiteDataSource();
        
        java.util.logging.Logger.getLogger(Main.class.getName()).info("Initializing and Starting JDA...");
        jda = JDABuilder.createDefault(Util.loadToken()).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.awaitReady();
        
        LosungScheduler.init();
        
        Main.jda.getGuilds().forEach((guild) -> {
            LosungScheduler.start(guild.getIdLong());
        });
        
        jda.addEventListener(new LosungCommand());
        jda.addEventListener(new HelpCommand());
        jda.addEventListener(new AdminCommand());
    }
    
    public static void close(){
        java.util.logging.Logger.getLogger(Main.class.getName()).info("Closing everything down ... bye!");
        jda.shutdown();
    }
    
}
