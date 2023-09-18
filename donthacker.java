import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HackKickPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("hack").setExecutor(new HackCommandExecutor());
    }

    private class HackCommandExecutor implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String playerName = player.getName();

                player.kickPlayer(ChatColor.RED + "Пытаешься взломать? Ничего не получится, сопляк!");
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Сопляк " + playerName + " пытался взломать сервер и был кикнут за Hack.");

                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Эту команду можно использовать только в игре.");
                return false;
            }
        }
    }
}
