package johnstweaks.decaliumtweaks;

import johnstweaks.decaliumtweaks.Configs.Configuration;
import johnstweaks.decaliumtweaks.Handlers.AnvilRepair;
import johnstweaks.decaliumtweaks.Handlers.ArrowFire;
import johnstweaks.decaliumtweaks.Handlers.CreatePath;
import johnstweaks.decaliumtweaks.Configs.MainConfiguration;
import johnstweaks.decaliumtweaks.Handlers.FallEffects;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DecaliumTweaks extends JavaPlugin {

    @Override
    public void onEnable() {

        int pluginId = 16926;
        Metrics metrics = new Metrics(this, pluginId);

        Configuration<MainConfiguration> conf = Configuration.create(
                this.getDataFolder().toPath(),
                "config.yml",
                MainConfiguration.class);


        conf.reloadConfig();

        if (conf.getConfigData().tweaksSettings().createPath().enabled()) {
            getServer().getPluginManager().registerEvents(new CreatePath(conf), this);
        }

        if (conf.getConfigData().tweaksSettings().fallEffects().enabled()) {
            getServer().getPluginManager().registerEvents(new FallEffects(conf), this);
        }
        if (conf.getConfigData().tweaksSettings().anvilRepair().enabled()) {
            getServer().getPluginManager().registerEvents(new AnvilRepair(conf), this);
        }


        if (conf.getConfigData().tweaksSettings().arrowFire().enabled()) {
            if(conf.getConfigData().tweaksSettings().arrowFire().mobsCanTrigger() ||
                            conf.getConfigData().tweaksSettings().arrowFire().mobsCanTrigger())
                getServer().getPluginManager().registerEvents(new ArrowFire(conf), this);
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
