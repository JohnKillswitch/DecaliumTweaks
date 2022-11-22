package johnstweaks.decaliumtweaks.Handlers;

import johnstweaks.decaliumtweaks.Configs.Configuration;
import johnstweaks.decaliumtweaks.Configs.MainConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FallEffects implements Listener {

    private final Configuration<MainConfiguration> conf;

    public FallEffects (Configuration<MainConfiguration> conf) {
        this.conf = conf;
    }


    @EventHandler
    public void checkEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;

        Player player = ((Player) event.getEntity()).getPlayer();
        if (!(event.getFinalDamage() < player.getHealth())) return;


        player.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.BLINDNESS,
                        conf.getConfigData().tweaksSettings().fallEffects().blindnessTime()*20,
                        1));

        player.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.SLOW,
                        conf.getConfigData().tweaksSettings().fallEffects().slownessTime()*20,
                        conf.getConfigData().tweaksSettings().fallEffects().slownessPower()*1));

    }
}
