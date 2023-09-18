import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class AuthPlugin extends JavaPlugin implements Listener {

    private Map<String, String> users;
    private Map<String, Integer> captchaCodes;

    @Override
    public void onEnable() {
        users = new HashMap<>();
        captchaCodes = new HashMap<>();

        File userDataFile = new File(getDataFolder(), "users.txt");
        if (!userDataFile.exists()) {
            try {
                userDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadUserData(userDataFile);
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (!users.containsKey(playerName)) {
            player.sendMessage(ChatColor.YELLOW + "Для регистрации введите пароль:");
        } else {
            player.sendMessage(ChatColor.YELLOW + "Для авторизации введите пароль:");
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String password = event.getMessage();

        if (!users.containsKey(playerName)) {
            registerPlayer(playerName, password);
            event.setCancelled(true);
            player.sendMessage(ChatColor.GREEN + "Вы успешно зарегистрированы!");
            player.sendMessage(ChatColor.YELLOW + "Введите капчу: " + generateCaptcha());
        } else {
            if (users.get(playerName).equals(password)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.GREEN + "Вы успешно авторизованы!");
                player.sendMessage(ChatColor.YELLOW + "Введите капчу: " + generateCaptcha());
            } else {
                event.setCancelled(true);
                player.kickPlayer(ChatColor.RED + "Неверный пароль! Попробуйте еще раз.");
            }
        }
    }

    private void registerPlayer(String playerName, String password) {
        users.put(playerName, password);

        File userDataFile = new File(getDataFolder(), "users.txt");
        try {
            FileWriter writer = new FileWriter(userDataFile, true);
            writer.write(playerName + ":" + password + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserData(File userDataFile) {
        try {
            Scanner scanner = new Scanner(userDataFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(":");
                if (data.length == 2) {
                    users.put(data[0], data[1]);
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateCaptcha() {
        Random random = new Random();
        int captcha = random.nextInt(10000);
        captchaCodes.put(captcha, captcha);
        return String.valueOf(captcha);
    }
}
