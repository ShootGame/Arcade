/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.menu.ClassSelectorMenu;
import pl.shg.arcade.api.menu.ServerPickerMenu;
import pl.shg.arcade.api.menu.TeamSelectorMenu;

/**
 *
 * @author Aleksander
 */
public class ObserverKitListeners implements Listener {
    private final ClassSelectorMenu classSelector = new ClassSelectorMenu();
    private final ServerPickerMenu picker = new ServerPickerMenu();
    private final TeamSelectorMenu selector = new TeamSelectorMenu();
    
    public ObserverKitListeners() {
        this.picker.register();
        this.selector.register();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getItem() == null) {
            return;
        }
        
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        MatchStatus status = Arcade.getMatches().getStatus();
        if (player.isObserver() || status != MatchStatus.PLAYING) {
            ItemStack item = e.getItem();
            switch (item.getType()) {
                case NETHER_STAR:
                    if (item.getItemMeta().getDisplayName().equals(Color.GOLD + "Wybierz druzyne")) {
                        this.handleTeamSelector(player, this.isRightClicked(e));
                    }
                    break;
                case SKULL_ITEM:
                    if (item.getItemMeta().getDisplayName().equals(Color.GOLD + "Zmien klase")) {
                        this.handleClassSelector(player, this.isRightClicked(e));
                    }
                    break;
                case BOOK: case WRITTEN_BOOK:
                    Tutorial tutorial = Arcade.getMaps().getCurrentMap().getTutorial();
                    if (item.getItemMeta().getDisplayName().equals(tutorial.getName())) {
                        this.handleTutorial(player, tutorial, this.isRightClicked(e));
                    }
                    break;
                case ENCHANTED_BOOK:
                    if (item.getItemMeta().getDisplayName().equals(Color.GOLD + "Teleport serwerowy")) {
                        this.handleServerTeleporter(player, this.isRightClicked(e));
                    }
                    break;
            }
        }
    }
    
    private boolean isRightClicked(PlayerInteractEvent e) {
        return e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK;
    }
    
    private void handleClassSelector(Player player, boolean rightClicked) {
        if (rightClicked) {
            this.classSelector.onCreate(player);
            ((org.bukkit.entity.Player) player.getPlayer()).openInventory((Inventory) this.classSelector.create());
        } else if (Arcade.getMaps().getCurrentMap().hasClasses()) {
            player.sendMessage(Color.GRAY + "Twoja obecna klasa to " + Color.DARK_AQUA + Color.BOLD + player.getArcadeClass().getName()
                    + Color.RESET + Color.GRAY + " - " + Color.RED + player.getArcadeClass().getDescription() + Color.GRAY + ".");
        }
    }
    
    private void handleServerTeleporter(Player player, boolean rightClicked) {
        if (!Arcade.getServer().bungeeCord()) {
            player.sendError("Serwer proxy w ustawieniach pluginu Arcade nie jest wlaczony.");
            return;
        }
        
        if (rightClicked) {
            this.picker.onCreate(player);
            ((org.bukkit.entity.Player) player.getPlayer()).openInventory((Inventory) this.picker.create());
        } else {
            if (!player.kickToLobby()) {
                player.sendError("Nie znaleziono serwera lobby.");
                Log.error("Nie znaleziono serwera lobby.");
            }
        }
    }
    
    private void handleTeamSelector(Player player, boolean rightClicked) {
        if (rightClicked) {
            this.selector.onCreate(player);
            ((org.bukkit.entity.Player) player.getPlayer()).openInventory((Inventory) this.selector.create());
        } else {
            player.sendMessage(Color.GRAY + "Obecnie znajdujesz sie w druzynie " + player.getTeam().getDisplayName() + Color.RESET + Color.GRAY + ".");
        }
    }
    
    private void handleTutorial(Player player, Tutorial tutorial, boolean rightClicked) {
        if (rightClicked && tutorial.isEmpty()) {
            player.sendError("Przepraszamy, na tej mapie poradnik nie jest dostepny.");
            player.sendError("Staramy sie go dodac jak najszybciej!");
        }
    }
}
