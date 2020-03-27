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
package jmb05.bibel.bot.commands;

import java.sql.SQLException;
import java.util.Arrays;
import jmb05.bibel.bot.util.Util;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author Jared
 */
public class Commands extends ListenerAdapter{
    
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent evt){
        System.out.println("Message received: " + evt.getMessage().toString());
        String[] args = evt.getMessage().getContentRaw().split("\\s+");
        System.out.println(Arrays.toString(args));
        String prefix = Util.getPrefix(evt.getGuild().getIdLong());
        System.out.println("Prefix is '"+prefix+"'");
        try{
            if(args[0].equals(prefix + "admin")){
                System.out.println("Detected '!admin' command");
                AdminCommand.adminMessageReceived(evt, args);
            }else if(args[0].equals(prefix + "losung")){
                System.out.println("Detected '!losung' command");
                LosungCommand.losungMessageReceived(evt, args);
            }else if(args[0].equals(prefix + "hilfe")){
                System.out.println("Detected !'hilfe' command");
                HelpCommand.helpMessageReceived(evt, args);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
}
