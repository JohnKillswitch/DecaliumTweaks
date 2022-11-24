package johnstweaks.decaliumtweaks.Handlers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import johnstweaks.decaliumtweaks.Configs.Configuration;
import johnstweaks.decaliumtweaks.Configs.MainConfiguration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class AnvilRepair implements Listener {


    private final Configuration<MainConfiguration> conf;

    public AnvilRepair(Configuration<MainConfiguration> conf) {
        this.conf = conf;
    }

    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

    @EventHandler
    public void AnvilClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (
                !event.getClickedBlock().getType().equals(Material.ANVIL) &&
                        !event.getClickedBlock().getType().equals(Material.CHIPPED_ANVIL) &&
                        !event.getClickedBlock().getType().equals(Material.DAMAGED_ANVIL))
            return;
        if (!player
                .getInventory()
                .getItemInMainHand()
                .getType()
                .equals(Material
                        .getMaterial(
                                conf.getConfigData().tweaksSettings().anvilRepair().repairMaterial())))
            return;

        switch (block.getType()) {
            case DAMAGED_ANVIL:
                block.setType(Material.CHIPPED_ANVIL);
                break;
            case CHIPPED_ANVIL:
                block.setType(Material.ANVIL);
                break;
            default:
                return;
        }
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);

            }
}
