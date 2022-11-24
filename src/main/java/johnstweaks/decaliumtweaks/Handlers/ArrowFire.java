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
import org.bukkit.block.data.type.Fire;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Random;

public class ArrowFire implements Listener {

    private final Configuration<MainConfiguration> conf;

    public ArrowFire (Configuration<MainConfiguration> conf) {
        this.conf = conf;
    }


    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

    @EventHandler
    public void checkEvent(ProjectileHitEvent event) {
        Block block = event.getHitBlock();

        if (!block.getType().isBurnable()) return;


        //Check if player can trigger
        if (conf.getConfigData().tweaksSettings().arrowFire().playerCanTrigger()) {
            if (!(event.getEntity().getShooter() instanceof Player)) return;
            Player player = ((Player) event.getEntity().getShooter());
            if (

                    !player.getInventory().getItemInOffHand().containsEnchantment(Enchantment.ARROW_FIRE) &&
                            !player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.ARROW_FIRE))
                return;
        }

        //Check if livingEntity can trigger
        if (conf.getConfigData().tweaksSettings().arrowFire().mobsCanTrigger()) {
            if (!(event.getEntity().getShooter() instanceof LivingEntity)) return;
            LivingEntity entity = (LivingEntity) event.getEntity().getShooter();
            if (
                    !entity.getEquipment().getItemInMainHand().containsEnchantment(Enchantment.ARROW_FIRE) &&
                            !entity.getEquipment().getItemInOffHand().containsEnchantment(Enchantment.ARROW_FIRE))
                return;
        }

        Block firedBlock = event.getHitBlock().getRelative(event.getHitBlockFace());

        //WG protect checker
        if(conf.getConfigData().otherSettings().wgSettings().enabled()) {
            RegionManager regions = container.get(BukkitAdapter.adapt(block.getWorld()));
            for (ProtectedRegion protectedRegion : regions.getRegions().values()) {
                if (protectedRegion.contains(block.getX(), block.getY(), block.getZ()) &&
                        !protectedRegion.getType().getName().equalsIgnoreCase("global")) return;
            }
        }

        if (new Random().nextInt(100 / conf.getConfigData().tweaksSettings().createPath().grassToDirtChance())+1 == 1) {
            if (firedBlock.getType().equals(Material.AIR)) {
                firedBlock.setType(Material.FIRE);
                Fire data = (Fire)firedBlock.getBlockData();

                if (event.getHitBlockFace().getOppositeFace().equals(BlockFace.DOWN)) return;

                data.setFace(event.getHitBlockFace().getOppositeFace(),true);
                firedBlock.setBlockData(data);

            }

        }

    }
}
