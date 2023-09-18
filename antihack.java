import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Pattern;

public class AntiHackPlugin extends JavaPlugin implements Listener {

    private final String[] bannedChars = {"\\d", "[!@#$%^&*()+=_-]", "[\\\\\\/<>\"']"};
    private final String[] bannedWords = {"log4shell", "взлом"};

    @Override
    public void onEnable() {
        getLogger().info("AntiHack loaded");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (isNameBanned(playerName)) {
            player.kickPlayer(ChatColor.RED + "Ваш ник заблокирован по причине использования недопустимых символов.");
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (isMessageBanned(message)) {
            player.kickPlayer(ChatColor.RED + "Ваш ник заблокирован по причине отправки сообщения с запрещенными словами.");
            event.setCancelled(true);
        }
    }

    private boolean isNameBanned(String name) {
        for (String bannedChar : bannedChars) {
            if (Pattern.compile(bannedChar).matcher(name).find()) {
                return true;
            }
        }
        return false;
    }

    private boolean isMessageBanned(String message) {
        for (String bannedWord : bannedWords) {
            if (message.toLowerCase().contains(bannedWord)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Пользователь " + ChatColor.YELLOW + "Ник" + ChatColor.RED + " заблокирован по причине Взлома сервера.");
                return true;
            }
        }
        return false;
    }
}
