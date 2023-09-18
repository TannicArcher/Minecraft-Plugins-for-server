import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BedrockBreakPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("bedrockbreak")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ItemStack handItem = player.getInventory().getItemInMainHand();
                if (handItem.getType() == Material.DIAMOND_PICKAXE) {
                    Block targetBlock = player.getTargetBlock(null, 5);
                    if (targetBlock.getType() == Material.BEDROCK) {
                        player.sendMessage("Вы разрушили блок бедрока!");
                        targetBlock.setType(Material.AIR);
                        short durability = (short) (handItem.getDurability() + 1);
                        if (durability > handItem.getType().getMaxDurability()) {
                            player.getInventory().setItemInMainHand(null);
                        } else {
                            handItem.setDurability(durability);
                        }
                    } else {
                        player.sendMessage("Этот блок не является блоком бедрока!");
                    }
                } else {
                    player.sendMessage("Вы должны держать кирку алмазного уровня в руке!");
                }
            } else {
                sender.sendMessage("Эту команду могут использовать только игроки!");
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.BEDROCK) {
            Player player = event.getPlayer();
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem.getType() == Material.DIAMOND_PICKAXE) {
                player.sendMessage("Вы разрушили блок бедрока!");
                short durability = (short) (handItem.getDurability() + 1);
                if (durability > handItem.getType().getMaxDurability()) {
                    player.getInventory().setItemInMainHand(null);
                } else {
                    handItem.setDurability(durability);
                }
            }
        }
    }
}
