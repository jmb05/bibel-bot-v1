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

import java.awt.Color;
import jmb05.bibel.bot.util.SendTemplates;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 *
 * @author Jared
 */
public class HelpCommand {
    
    public static void helpMessageReceived(GuildMessageReceivedEvent evt, String[] args){
        try{
            if(args[1].equals("über")){
                SendTemplates.sendHilfeAbout(evt.getChannel());
            }else if(args[1].equals("kommandos")){
                SendTemplates.sendHilfeKommandos(evt.getChannel());
            }
        }catch(ArrayIndexOutOfBoundsException aioobe){
            EmbedBuilder losung = new EmbedBuilder();
            losung.setTitle("Bibel Bot - Hilfe");
            losung.addField("", "Das Kommando \'!hilfe\' macht alleine nichts ... hänge noch \'über\' oder \'kommandos\' hinten ran um die gewünschte Hilfestellung anzuzeigen! :wink:", false);
            losung.setColor(Color.green);

            evt.getChannel().sendTyping().queue();
            evt.getChannel().sendMessage(losung.build()).queue();
            losung.clear();
        }
    }
    
}
