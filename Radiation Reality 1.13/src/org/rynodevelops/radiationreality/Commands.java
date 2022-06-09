package org.rynodevelops.radiationreality;

import java.util.ArrayList;
import java.util.List;

import com.rynodevelops.radiationreality.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;

import com.rynodevelops.radiationreality.Utils.CustomItemManager;


public class Commands implements Listener, CommandExecutor {

    CustomItemManager cmanager = new CustomItemManager();

    public String comando1 = "givemask";
    public String comando2 = "givefilter";
    public String comando3 = "getvaluesdebug";
    public String comando4 = "getgaiger";
    public String comando5 = "getfialetta";
    public String comando6 = "getradioprot";
    public String comando7 = "testthermalrad";
    public String comando8 = "startwind";
    public String comando9 = "stopwind";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            // PER CHI INVIA I MESSAGGI DALLA CHAT
            Player p = ((Player) sender).getPlayer();


            if (cmd.getName().equalsIgnoreCase(comando1)) {
                if (p.isOp() || p.hasPermission("radiationReality.givemask")) {
                    Inventory inv = ((Player) sender).getInventory();
                    inv.addItem(cmanager.gasmask());
                }
            } else if (cmd.getName().equalsIgnoreCase(comando2)) {
                if (p.isOp() || p.hasPermission("radiationReality.givefilter")) {
                    Inventory inv = ((Player) sender).getInventory();
                    inv.addItem(cmanager.filter());
                }
            } else if (cmd.getName().equalsIgnoreCase(comando3)) {
                if (p.isOp() || p.hasPermission("radiationReality.debug")) {

                    p.sendMessage("Timer filtro: " + String.valueOf(Utils.playerTimer.get((p))) + " InDanger?: " + String.valueOf(Utils.playerInDanger.get(p)) + " maskGUI: " + String.valueOf(Utils.hasGGUIOpened.get(p)) + " radLevel: " + String.valueOf(Utils.playerRadLevel.get(p)) + " isHealing: " + Utils.isPlayerHealing.get(p));
                }
            } else if (cmd.getName().equalsIgnoreCase(comando4)) {
                if (p.isOp() || p.hasPermission("radiationReality.getgaiger")) {
                    Inventory inv = ((Player) sender).getInventory();
                    inv.addItem(cmanager.gaiger());
                }
            } else if (cmd.getName().equalsIgnoreCase(comando5)) {
                if (p.isOp()|| p.hasPermission("radiationReality.getFialetta")) {
                    Inventory inv = ((Player) sender).getInventory();
                    inv.addItem(cmanager.fialetta());
                }
            } else if (cmd.getName().equalsIgnoreCase(comando6)) {
                if (p.isOp() || p.hasPermission("radiationReality.getradioprot")) {
                    Inventory inv = ((Player) sender).getInventory();
                    inv.addItem(cmanager.radioProtettore());
                }
            } else if (cmd.getName().equalsIgnoreCase(comando7)) {
                if (p.isOp() || p.hasPermission("radiationReality.testthermalrad")) {
                    Block currBlock, relative, relativeUpperFace;
                    int radius = 5;

                    currBlock = ((Entity) sender).getWorld().getBlockAt(((Entity) sender).getLocation());

                    for (int x = -radius; x <= radius; x++) {
                        for (int y = -1; y <= radius; y++) {
                            for (int z = -radius; z <= radius; z++) {
                                relative = currBlock.getRelative(x, y, z);
                                relativeUpperFace = relative.getRelative(BlockFace.UP);
                                if (relativeUpperFace.getType() == Material.AIR) {
                                    relativeUpperFace.setType(Material.FIRE);
                                }

                            }
                        }
                    }
                }
            }


        } else {
            if (cmd.getName().equalsIgnoreCase(comando8)) {
                Main.istanza.startWindEngine();
            } else if (cmd.getName().equalsIgnoreCase(comando9)) {
                Main.istanza.stopWindEngine();
            }
            // PER CHI INVIA I MESSAGGI DALLA CONSOLE
        }


        return false;
    }

}
