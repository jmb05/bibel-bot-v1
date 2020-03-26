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

import java.awt.Color;
import static jmb05.bibel.bot.util.Util.getLosungenChannelName;
import static jmb05.bibel.bot.util.Util.getPrefix;
import static jmb05.bibel.bot.util.Util.getTime;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 *
 * @author Jared
 */
public class SendTemplates {
    
    public static void sendLosungHeute(TextChannel channel){
        EmbedBuilder losung = new EmbedBuilder();
        losung.setTitle("Bibel Bot - Die Losung");
        losung.setDescription("Die heutigen Losung:");
        losung.addField(jmb05.bibel.bot.util.Util.getDate(), Util.getLosung(Util.getDayOfYear()), false);
        losung.setFooter("© Evangelische Brüder-Unität – Herrnhuter Brüdergemeine (www.herrnhuter.de)\nWeitere Informationen finden sie hier: www.losungen.de");
        losung.setColor(Color.orange);

        channel.sendTyping().queue();
        channel.sendMessage(losung.build()).queue();
        losung.clear();
    }
    
    public static void sendLosungJahr(TextChannel channel){
        EmbedBuilder losung = new EmbedBuilder();
        losung.setTitle("Bibel Bot - Die Losung");
        losung.setDescription("Die Jahreslosung:");
        losung.addField(Util.getYear(), Util.getJahresLosung(), false);
        losung.setFooter("© Evangelische Brüder-Unität – Herrnhuter Brüdergemeine (www.herrnhuter.de)\nWeitere Informationen finden sie hier: www.losungen.de");
        losung.setColor(Color.orange);

        channel.sendTyping().queue();
        channel.sendMessage(losung.build()).queue();
        losung.clear();
    }
    
    public static void sendHilfeKommandos(TextChannel channel){
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("Bibel Bot - Hilfe - Kommandos");
        help.setDescription("Alle Kommandos auf die ich höre!");
        help.addField("Kommandos:", "\'!hilfe [über/kommandos]\'\n"
                + "\'!losung [heute/jahr]\'\n"
                + "\'!admin [\n   get-prefix/\n   set-prefix [prefix]/\n   get-time/\n   set-time [time]/\n   get-channel-name/\n   set-channel-name [channelname]/\n   get-show-losung/\n   set-show-losung ['true'/'false']\n]\' \n(Für dieses Kommando werden Administratorrechte benötigt)", false);
        help.setColor(Color.green);

        channel.sendTyping().queue();
        channel.sendMessage(help.build()).queue();
        help.clear();
    }
    
    public static void sendHilfeAbout(TextChannel channel){
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("Bibel Bot - Hilfe - Über");
        help.setDescription("Das sind meine specs!");
        help.addField("", "**Bibel Bot**\nVersion: 1.2\nEntwickler: @jmb05", false);
        help.setColor(Color.green);

        channel.sendTyping().queue();
        channel.sendMessage(help.build()).queue();
        help.clear();
    }
    
    public static void sendNoAdmin(TextChannel channel){
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("Bibel Bot");
        help.addField("", "Sorry ... für dieses Kommando musst du Administrator sein...", false);
        help.setColor(Color.red);

        channel.sendTyping().queue();
        channel.sendMessage(help.build()).queue();
        help.clear();
    }
    
    public static void sendPrefix(TextChannel channel){
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("Bibel Bot - Admin");
        help.addField("", "Der Prefix des Servers ist: \'"+getPrefix(channel.getGuild().getIdLong())+"\'", false);
        help.setColor(Color.red);

        channel.sendTyping().queue();
        channel.sendMessage(help.build()).queue();
        help.clear();
    }
    
    public static void sendTime(TextChannel channel){
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("Bibel Bot - Admin");
        help.addField("", "Die Zeit für die automatische Losung des Servers ist auf: "+getTime(channel.getGuild().getIdLong()).toString()+" gesetzt.", false);
        help.setColor(Color.red);

        channel.sendTyping().queue();
        channel.sendMessage(help.build()).queue();
        help.clear();
    }
    
    public static void sendLosungenChannelName(TextChannel channel){
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("Bibel Bot - Admin");
        help.addField("", "Der Name des Losungskanals für diesen Server ist: '"+getLosungenChannelName(channel.getGuild().getIdLong())+"'.", false);
        help.setColor(Color.red);

        channel.sendTyping().queue();
        channel.sendMessage(help.build()).queue();
        help.clear();
    }
    
    public static void sendIfShow(TextChannel channel){
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("Bibel Bot - Admin");
        if(Util.getIfShow(channel.getGuild().getIdLong())){
            help.addField("", "Die Losung wird automatisch angezeigt.", false);
        }else{
            help.addField("", "Die Losung wird nicht automatisch angezeigt.", false);
        }
        help.setColor(Color.red);

        channel.sendTyping().queue();
        channel.sendMessage(help.build()).queue();
        help.clear();
    }
}
