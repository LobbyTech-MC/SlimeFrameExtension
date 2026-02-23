package io.github.acdeasdff.SlimeFrameExtension.Listeners;

import io.github.acdeasdff.SlimeFrameExtension.ItemMetaRelated.Keys;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

import static io.github.mooy1.infinitylib.core.AbstractAddon.instance;

public class PlayerKillEntityListener implements Listener {

    Random random = new Random();

    @EventHandler
    public void EndoListener(EntityDamageByEntityEvent e) {
        // 1. Ensure the target is a LivingEntity (mobs/animals) but NOT a Player
        // 2. Ensure the target is NOT an ItemFrame or other non-living entities
        if (!(e.getEntity() instanceof LivingEntity victim) || victim instanceof Player) {
            return; 
        }

        Player attacker = null;

        // Determine if the damager is a player or a projectile shot by a player
        if (e.getDamager() instanceof Player p) {
            attacker = p;
        } else if (e.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player p) {
            attacker = p;
        }

        // If we found a player attacker, process the Endo logic
        if (attacker != null) {
            if (random.nextInt(50) <= 1) { // 4% chance (approx 1 in 50 is 2%, but logic says <= 1)
                
                // Check if the hit will kill the entity
                if (victim.getHealth() <= e.getDamage()) {
                    var maxHealthAttr = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    
                    if (maxHealthAttr != null) {
                        int currentEndos = PersistentDataAPI.getInt(attacker, Keys.ENDOS_OWNED, 0);
                        
                        double healthValue = maxHealthAttr.getValue();
                        // Apply your 600 cap logic if it's a ranged attack or keep it consistent
                        double rewardBase = (e.getDamager() instanceof Projectile) ? Math.min(healthValue, 600) : healthValue;
                        
                        int gainedEndos = (int) rewardBase + random.nextInt(10);
                        PersistentDataAPI.setInt(attacker, Keys.ENDOS_OWNED, currentEndos + gainedEndos);
                    }
                }
            }
        }
    

        long currentTime = System.currentTimeMillis();
        Entity entity1 = e.getEntity();
        if (entity1.getMetadata("sfe_viral").size() > 0) {
            long viral_time = entity1.getMetadata("sfe_viral_time").get(0).asLong();
            int viral_level = entity1.getMetadata("sfe_viral_level").get(0).asInt();

            if (entity1.getMetadata("sfe_viral_lasts").get(0).asLong()
                    < currentTime - viral_time) {
                entity1.removeMetadata("sfe_viral_lasts", instance());
                entity1.removeMetadata("sfe_viral_time", instance());
                entity1.removeMetadata("sfe_viral_first_time", instance());
                entity1.removeMetadata("sfe_viral_level", instance());
                entity1.removeMetadata("sfe_viral", instance());
            } else {
                e.setDamage(e.getDamage() * (viral_level * 0.25 + 0.75));
            }
        }

    }


}
