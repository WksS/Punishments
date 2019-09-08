package br.com.wkss.punishments.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishCommand implements CommandExecutor {

    private String line = "\n";

    public enum Modes {
        BAN,
        MUTE;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("punir")) {
            if(args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Utilize: /punir <nick>");
                return true;
            }
            if(args.length == 1) {

                Player player = Bukkit.getPlayer(args[0]);
                Player staff = (Player) sender;

                if(player == null) {
                    sender.sendMessage(ChatColor.RED + "Jogador parceal ou completamente inexistente.");
                    return true;
                }
                staff.sendMessage(ChatColor.YELLOW + "Tipos de infrações disponíveis:");
                TextComponent text = new TextComponent();
                text.setText("Abuso de bugs");
                text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/punir " + player.getName() + " ABUSO_DE_BUGS"));
                text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.YELLOW + "Abuso de bugs:" + ChatColor.WHITE + line + line + "Algum player usufruiu de vantagens atráves de bugs dentro do servidor." + line + line + "Grupo mínimo: " + ChatColor.DARK_GREEN + "Moderador" + line + ChatColor.WHITE + "Redes: " + ChatColor.GRAY + "MINIGAMES; SURVIVAL" + line + ChatColor.YELLOW + "1°: " + ChatColor.WHITE + "[BAN] 15 dias" + line + ChatColor.YELLOW +  "2°: " + ChatColor.WHITE + "[BAN] " + ChatColor.RED + "Permanente").create()));
                player.spigot().sendMessage(text);
                }
        }
        return false;
    }
}
