package johnstweaks.decaliumtweaks.Handlers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import johnstweaks.decaliumtweaks.Configs.Configuration;
import johnstweaks.decaliumtweaks.Configs.MainConfiguration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

public class CreatePath implements Listener {

    private final Configuration<MainConfiguration> conf;

    public CreatePath (Configuration<MainConfiguration> conf) {
        this.conf = conf;
    }

    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

    @EventHandler
    public void changeBlockUnderFeet(PlayerMoveEvent event) {
        Block block = event.getTo().getBlock().getRelative(BlockFace.DOWN);

        //WG protect checker
        if(conf.getConfigData().otherSettings().wgSettings().enabled()) {

            RegionManager regions = container.get(BukkitAdapter.adapt(event.getPlayer().getWorld()));
            for (ProtectedRegion protectedRegion : regions.getRegions().values()) {
                if (protectedRegion.contains(block.getX(), block.getY(), block.getZ()) &&
                                !protectedRegion.getType().getName().equalsIgnoreCase("global")) return;
            }
        }

        if (
                event.getFrom().getBlock().equals(event.getTo().getBlock()) ||
                        event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                                event.getFrom().getBlockY() == event.getTo().getBlockY() &&
                                event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;


        if (block.getType().equals(Material.GRASS))
            if (new Random().nextInt(100 / conf.getConfigData().tweaksSettings().createPath().grassToDirtChance())+1 == 1) {
                block.setType(Material.DIRT);
            }

        if (block.getType().equals(Material.DIRT))
            if (new Random().nextInt(100 / conf.getConfigData().tweaksSettings().createPath().dirtToPathChance())+1 == 1) {
                block.setType(Material.GRASS_PATH);
            }

    }
}
