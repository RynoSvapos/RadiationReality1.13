package com.rynodevelops.radiationreality.Utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.*;

public class CustomItemManager {


    //lore = new ArrayList<>();
    //lore.add(ChatColor.GOLD + "- " + ChatColor.YELLOW + "Ti protegge dalla polvere radioattiva");





    // GAS MASK V
    // FILTRO
    // RADIO PROTETTORE


    public ItemStack gasmask() {

        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD + "- " + ChatColor.YELLOW + "It protects you from hazardous gas");


        ItemStack item;
        item = customItem(new ItemStack(Material.CARVED_PUMPKIN), ChatColor.GOLD + "Gas" + ChatColor.YELLOW + " Mask",
                lore);
        lore.clear();


        return item;

    }

    public ItemStack radioProtettore() {
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD + "- " + ChatColor.YELLOW + "It modulate the rate of");
        lore.add(ChatColor.YELLOW + "blood production and purifies you from radiations");
        lore.add (ChatColor.GOLD + "Rate: " + ChatColor.GREEN + "-0.027 Sievert/S" );
        ItemStack item;
        item = customItem(new ItemStack(Material.POTION), ChatColor.GREEN + "Green stuff", lore);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 6000, 1), false);
        item.setItemMeta(meta);

        lore.clear();
        return item;
    }


    public ItemStack fialetta() {
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD + "- " + ChatColor.YELLOW + "It can extract blood from you or from someone else");
        lore.add(ChatColor.YELLOW + "and tests for blood quality");
        ItemStack item;

        item = customItem(new ItemStack(Material.GLASS_BOTTLE), ChatColor.GREEN + "Flask with a syringe", lore);
        lore.clear();
        return item;
    }


    public ItemStack filter()
    {
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD + "- " + ChatColor.YELLOW + "Needed for your Gas mask, ");
        lore.add(ChatColor.YELLOW + "it just filters the air.");
        lore.add (ChatColor.GOLD + "Duration: " + ChatColor.YELLOW + "10:00 minutes");
        ItemStack item;
        item = customItem(new ItemStack(Material.FIREWORK_STAR), ChatColor.GOLD + "Filter", lore);
        lore.clear();


        return item;
    }

    public ItemStack gaiger() {
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD + "- "  + ChatColor.YELLOW + "It measures the level of radiations");
        ItemStack item;
        item = customItem(new ItemStack(Material.COMPASS), ChatColor.GOLD + "Gaiger Counter", lore);
        lore.clear();

        return item;
    }


    public ItemStack customItem(ItemStack item, String displayName, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }



}
