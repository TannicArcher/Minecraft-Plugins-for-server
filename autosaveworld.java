import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldSavePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().savePlayers();
                Bukkit.getServer().saveData();
                Bukkit.getWorlds().forEach(world -> world.save());
                getLogger().info("World saved.");
            }
        }, 0, 12000); // 10 minutes = 10 * 60 * 20 (ticks)

        getLogger().info("WorldSavePlugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("WorldSavePlugin disabled.");
    }
}
