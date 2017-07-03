package com.djrapitops.genie.wishes.other;

import com.djrapitops.genie.Genie;
import com.djrapitops.genie.wishes.Wish;
import com.djrapitops.javaplugin.api.ColorScheme;
import com.djrapitops.javaplugin.task.RslBukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Rsl1122
 */
public class FlyingWish extends Wish {

    public FlyingWish() {
        super("Fly", "Could, fly", "flying", "gliding");
    }

    @Override
    public boolean fulfillWish(Player p) {
        boolean allowFlightPrior = p.getAllowFlight();
        p.setAllowFlight(true);
        p.setFlying(true);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*20, 0));
        ColorScheme color = Genie.getInstance().getColorScheme();
        p.sendMessage(color.getMainColor() + "[Genie] " + color.getSecondColor() + "You have been granted the ability to fly for 1 minute");
        new RslBukkitRunnable<Genie>("EndFlyTask") {
            @Override
            public void run() {
                this.cancel();
            }

            @Override
            public void cancel() {
                p.sendMessage(color.getSecondColor() + "The Effect wore off.");
                p.setFlying(false);
                p.setAllowFlight(allowFlightPrior);
                super.cancel();
            }
        }.runTaskLater(20 * 60);
        return true;
    }

}
