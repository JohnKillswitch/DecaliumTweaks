package johnstweaks.decaliumtweaks.Configs;

import space.arim.dazzleconf.annote.*;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

@ConfHeader({"Main configuration file."})
public interface MainConfiguration {

    interface TweaksSettings {
        interface CreatePath {
            @AnnotationBasedSorter.Order(1)
            @ConfDefault.DefaultBoolean(true)
            @ConfKey("enabled")
            Boolean enabled();

            @ConfComments("Must be a percent value from 1 to 100")
            @ConfDefault.DefaultInteger(20)
            @ConfKey("grassToDirtChance")
            int grassToDirtChance();

            @ConfComments("Must be a percent value from 1 to 100")
            @ConfDefault.DefaultInteger(20)
            @ConfKey("dirtToPathChance")
            int dirtToPathChance();
        }
        @ConfComments("CreatePath tweak changing block under player from grass to dirt and from dirt to grass_path")
        @ConfKey("CreatePath")
        @SubSection
        CreatePath createPath();


        interface FallEffects {
            @AnnotationBasedSorter.Order(1)
            @ConfDefault.DefaultBoolean(true)
            @ConfKey("enabled")
            Boolean enabled();

            @ConfComments("How long player getting blindness effect (in seconds)")
            @ConfDefault.DefaultInteger(3)
            @ConfKey("blindnessTime")
            int blindnessTime();

            @ConfComments("How long player getting slowness effect (in seconds)")
            @ConfDefault.DefaultInteger(3)
            @ConfKey("slownessTime")
            int slownessTime();

            @ConfComments("How powerful slowness player get (number of level)")
            @ConfDefault.DefaultInteger(5)
            @ConfKey("slownessPower")
            int slownessPower();
        }
        @ConfComments("FallEffects give to player a blindness and slowness effect for few seconds after he is fall")
        @ConfKey("FallEffects")
        @SubSection
        FallEffects fallEffects();


        interface ArrowFire {
            @AnnotationBasedSorter.Order(1)
            @ConfDefault.DefaultBoolean(true)
            @ConfKey("enabled")
            Boolean enabled();

            @ConfComments("Does player`s arrows can invoke fire?")
            @ConfDefault.DefaultBoolean(true)
            @ConfKey("playerCanTrigger")
            Boolean playerCanTrigger();

            @ConfComments("Does mob`s arrows can invoke fire?")
            @ConfDefault.DefaultBoolean(true)
            @ConfKey("mobsCanTrigger")
            Boolean mobsCanTrigger();

            @ConfComments("Must be a percent value from 1 to 100")
            @ConfDefault.DefaultInteger(20)
            @ConfKey("igniteChance")
            Integer igniteChance();

        }
        @ConfComments("After entity shoot on burnable block that block can be igniting")
        @ConfKey("ArrowFire")
        @SubSection
        ArrowFire arrowFire();
    }
    @ConfKey("Tweaks-Settings")
    @SubSection
    TweaksSettings tweaksSettings();


    interface OtherSettings {
        interface WGSettings {
            @ConfComments("WorldGuard MUST BE installed for this")
            @ConfDefault.DefaultBoolean(false)
            @ConfKey("enabled")
            Boolean enabled();
        }
        @SubSection
        @ConfKey("WorldGuardSupportSettings")
        WGSettings wgSettings();
    }
    @ConfKey("Other-Settings")
    @SubSection
    OtherSettings otherSettings();





}