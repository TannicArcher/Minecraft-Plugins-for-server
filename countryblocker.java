import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class UkraineOnlyPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        try {
            InetAddress address = event.getAddress();
            String country = getCountryFromIP(address.getHostAddress());

            if (!country.equalsIgnoreCase("UA")) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You are not allowed to join this server.");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private String getCountryFromIP(String ipAddress) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(ipAddress);
        String country = address.getCountry().toUpperCase();

        return country;
    }
}
