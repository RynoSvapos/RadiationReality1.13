package com.rynodevelops.radiationreality.Utils;



import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Array;
import java.util.*;

public class Utils {


    static CustomItemManager ItemCreator = new CustomItemManager();

    public static HashMap<Player, Integer> playerTimer = new HashMap<Player, Integer>();
    public static HashMap<Player, Integer> playerTimerSecondsElapsing = new HashMap<Player, Integer>();

    public static HashMap<Player, Boolean> playerInDanger = new HashMap<Player, Boolean>();

    public static HashMap<Player, Boolean> hasGGUIOpened = new HashMap<Player, Boolean>();
    public static HashMap<Player, Float> playerRadLevel = new HashMap<Player, Float>();
    public static HashMap<Player, Integer> isPlayerHealing = new HashMap<Player, Integer>();

    // HASHMAPS MENO IMPORTANTI
    public static HashMap<Player, Integer> breathe = new HashMap<Player, Integer>();

    public static int gasFilterLength = 10;


    public static boolean isPlayerInCold(Player player) {

        int checkValue = 0;

        switch(player.getLocation().getBlock().getBiome()) {
            case SNOWY_BEACH: checkValue++; break;
            case SNOWY_TUNDRA: checkValue++; break;
            case SNOWY_MOUNTAINS: checkValue++; break;
            case SNOWY_TAIGA: checkValue++; break;
            case SNOWY_TAIGA_HILLS: checkValue++; break;
            case SNOWY_TAIGA_MOUNTAINS: checkValue++; break;
            case ICE_SPIKES: checkValue++; break;
            case FROZEN_OCEAN: checkValue++; break;
            case FROZEN_RIVER: checkValue++; break;
            case DEEP_FROZEN_OCEAN: checkValue++; break;
            default: checkValue = 0; break;
        }

        return checkValue > 0;

    }

    public static void isPlayerInDanger(Player player) {
        if (player.getLocation().getBlock().getLightFromSky() <= 1) {
            if (player.hasPotionEffect(PotionEffectType.POISON) || player.hasPotionEffect(PotionEffectType.WITHER) || player.hasPotionEffect(PotionEffectType.HARM) || !player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
                Utils.playerInDanger.replace(player, false);
            }



        } else {
            if (player.getLocation().getBlock().getLightFromSky() > 1) {
                if (player.hasPotionEffect(PotionEffectType.POISON) ||
                        player.hasPotionEffect(PotionEffectType.WITHER) ||
                        player.hasPotionEffect(PotionEffectType.HARM) || player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
                    Utils.playerInDanger.replace(player, true);
                }
            }
        }
    }

    public static boolean isLitAir(Entity entity) {

        Location entityLocation = entity.getLocation();
        entityLocation.add(0, 1.5, 0);
        if (entityLocation.getBlock().getLightFromSky() > 1) {
            return false;
        }


        return true;

    }

    public static float radLevel(Player player) {
        float valore = 0.00301f; // millisievert

        if (player.hasPotionEffect(PotionEffectType.POISON)) {
            valore = 0.09f;
        }
        if (player.hasPotionEffect(PotionEffectType.WITHER)) {
            valore = 0.178f;
        }
        if (player.hasPotionEffect(PotionEffectType.HARM)) {
            valore = 0.5f;
        }
        return valore;
    }

    public static void addFilterTime(Player player) {
        String filterComparator = ChatColor.GOLD + "Filter";
        String substring; // Utile per formattare questa parte di testo;
        ChatColor finalChatColor;
        int maxFiltersTime, amount = 0, rawTime;


        for (ItemStack is : player.getInventory().getContents()) {
            if (is != null) {
                if (Objects.requireNonNull(is.getItemMeta()).getDisplayName().equals(filterComparator)) {
                    amount = is.getAmount();
                }
            }
        }
        rawTime = amount * 600;
        maxFiltersTime = rawTime / 60;


        if (maxFiltersTime >= 1) {
            finalChatColor = ChatColor.YELLOW;
        } else {
            finalChatColor = ChatColor.RED;
        }

        if (maxFiltersTime > 1) {
            substring = finalChatColor + String.valueOf(maxFiltersTime) + ChatColor.YELLOW + " minutes left";
        } else {
            substring = finalChatColor + String.valueOf(maxFiltersTime) + ChatColor.YELLOW + " seconds left";
        }

        player.sendMessage(ChatColor.GOLD + "Life time: " + substring);

        playerTimer.replace(player, gasFilterLength);
        playerTimerSecondsElapsing.replace(player, 0);

    }

    public static boolean isDay(Player player) {
        return (player.getWorld().getTime() >= 0 && player.getWorld().getTime() < 14000);
    }

    public static void damageMask(Player player) {
        // SORRY FOR THIS SPAGHETTI CODE :(((((
        if (player.getInventory().getHelmet().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Gas" + ChatColor.YELLOW + " Mask")) {
            player.playSound(player.getLocation(), Sound.ENTITY_GUARDIAN_HURT, 10, 1f);
            player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 10, 0.1f);

            ItemStack item = player.getInventory().getHelmet();
            ItemMeta itemMeta = item.getItemMeta();
            List<String> lore = itemMeta.getLore();


            if (lore.size() > 1) {
                if (lore.get(1).contains("Very Damaged")) {
                    destroyMask(player);
                }
            }

            if (lore.size() == 1) {
                lore.add(ChatColor.GREEN + "Slightly Damaged");
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                player.getInventory().setHelmet(item);
            }
            else if (lore.get(1).contains("Slightly Damaged")) {
                lore.set(1, ChatColor.YELLOW + "Damaged");
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                player.getInventory().setHelmet(item);

            } else if (lore.get(1).contains("Damaged") && !lore.get(1).contains("Very")) {
                lore.set(1, ChatColor.RED + "Very Damaged");
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                player.getInventory().setHelmet(item);

            }





        }
    }

    public static void destroyMask(Player player) {
        if(hasMask(player)) {

            ItemStack item = player.getInventory().getHelmet();
            ItemMeta itemMeta = item.getItemMeta();
            List<String> lore = itemMeta.getLore();

            lore.set(1, ChatColor.DARK_RED + "Destroyed");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);

            player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 10, 0.4f);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BREATH, 5, (float) 1);
            player.getInventory().setHelmet(item);
        }
    }

    public static void updateGasMaskUI(Player player) {
        String filterComparator = ChatColor.GOLD + "Filter";
        String clockStringFilter;
        float clockValueMinutes, clockValueSeconds;

        clockValueMinutes = playerTimer.get(player);
        clockValueSeconds = playerTimerSecondsElapsing.get(player);

        if (clockValueMinutes < 10 && clockValueSeconds < 10) {
            clockStringFilter = "0" + String.valueOf((int) clockValueMinutes) + ":" + "0" + String.valueOf((int) clockValueSeconds);
        } else if (clockValueMinutes < 10 && clockValueSeconds > 9) {
            clockStringFilter = "0" + String.valueOf((int) clockValueMinutes) + ":" + String.valueOf((int) clockValueSeconds);
        } else if (clockValueMinutes > 9 && clockValueSeconds < 10) {
            clockStringFilter = String.valueOf((int) clockValueMinutes) + ":" + "0" + String.valueOf((int) clockValueSeconds);
        } else {
            clockStringFilter = String.valueOf((int) clockValueMinutes) + ":" + String.valueOf((int) clockValueSeconds);
        }


        if (Utils.hasGGUIOpened.get(player) == true) {
            if (playerTimer.get(player) > 0) {
                ActionBarAPI.sendActionBar(player, ChatColor.GOLD + "Time left > " + ChatColor.GREEN + clockStringFilter);
            } else {
                ActionBarAPI.sendActionBar(player, ChatColor.GOLD + "Time left > " + ChatColor.RED + clockStringFilter);
            }

        }


    }


    public static boolean hasMask(Entity entity) {
        String gasComparator = ChatColor.GOLD + "Gas" + ChatColor.YELLOW + " Mask";
        if (entity instanceof Player) {
            Player p = ((Player) entity).getPlayer();
            ItemStack item = ItemCreator.gasmask();
            assert p != null;
            if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().hasItemMeta()) {
                if (p.getInventory().getHelmet().getItemMeta().getLore().size() > 1 && !p.getInventory().getHelmet().getItemMeta().getLore().get(1).contains("Destroyed") || p.getInventory().getHelmet().getItemMeta().getLore().size() == 1) {
                    return p.getInventory().getHelmet().getItemMeta().getDisplayName().equals(gasComparator);
                }
            }
        }
        return false;
    }

    public static boolean hasFilterActive(Entity entity) {
        int value = 0, value2 = 0;
        if (entity instanceof Player) {
            value = playerTimer.get((Player) entity);
            value2 = playerTimerSecondsElapsing.get((Player) entity);
        }
        return (value > 0 || value2 > 0);


    }


}
