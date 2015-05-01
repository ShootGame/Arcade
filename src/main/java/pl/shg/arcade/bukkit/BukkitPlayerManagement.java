/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.inventory.Enchantment;
import pl.shg.arcade.api.inventory.Item;
import pl.shg.arcade.api.map.ArcadeClass;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.map.team.Team;
import pl.shg.arcade.api.map.team.kit.Kit;
import pl.shg.arcade.api.map.team.kit.KitItem;
import pl.shg.arcade.api.map.team.kit.KitType;
import pl.shg.arcade.api.map.team.kit.Option;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class BukkitPlayerManagement implements PlayerManagement {
    private final ObserverKit obsKit = new ObserverKit();
    private final Random random = new Random();
    private final Server server;
    
    public BukkitPlayerManagement(Server server) {
        Validate.notNull(server, "server can not ben null");
        this.server = server;
    }
    
    @Override
    public boolean isGhost(pl.shg.arcade.api.human.Player player) {
        Validate.notNull(player, "player can not be null");
        return ((Player) player.getPlayer()).hasPotionEffect(PotionEffectType.INVISIBILITY);
    }
    
    @Override
    public void playSound(pl.shg.arcade.api.human.Player player, pl.shg.arcade.api.Sound sound) {
        Validate.notNull(player, "player can not be null");
        Validate.notNull(sound, "sound can not be null");
        
        Sound result = null;
        switch (sound) {
            case BEGINING: result = Sound.ORB_PICKUP; break;
            case BEGINS: result = Sound.ANVIL_LAND; break;
            case ELIMINATION: result = Sound.IRONGOLEM_DEATH; break;
            case ENEMY_LOST: result = Sound.WITHER_DEATH; break;
            case ENEMY_WON: result = Sound.WITHER_SPAWN; break;
            case MENTION: result = Sound.NOTE_PIANO; break;
        }
        if (result != null) {
            Player bukkitPlayer = (Player) player.getPlayer();
            bukkitPlayer.playSound(bukkitPlayer.getLocation(), result, 10F, 1F);
        }
    }
    
    @Override
    public void refreshHiderForAll() {
        PlayerHider.refreshAll();
    }
    
    @Override
    public void setAsObserver(pl.shg.arcade.api.human.Player player, boolean fullKit, boolean hider) {
        Validate.notNull(player, "player can not be null");
        Player bukkitPlayer = (Player) player.getPlayer();
        
        player.getPermissions().removeFromTeams(false);
        player.getPermissions().addToGroup(Arcade.getPermissions().getObservers());
        player.reloadPermissions();
        
        bukkitPlayer.setCollidesWithEntities(false);
        
        bukkitPlayer.setFoodLevel(20);
        bukkitPlayer.setMaxHealth(20.0);
        if (!player.isDead()) {
            bukkitPlayer.setHealth(20.0);
        }
        bukkitPlayer.setGameMode(GameMode.CREATIVE);
        if (hider) {
            PlayerHider.refresh(player);
        }
        for (PotionEffect potion : bukkitPlayer.getActivePotionEffects()) {
            bukkitPlayer.removePotionEffect(potion.getType());
        }
        
        bukkitPlayer.setFlySpeed(0.20F);
        bukkitPlayer.setWalkSpeed(0.20F);
        
        bukkitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
        
        this.obsKit.set(bukkitPlayer, fullKit);
    }
    
    @Override
    public void setAsPlayer(pl.shg.arcade.api.human.Player player, KitType kit, boolean hider, boolean sendTitle) {
        Validate.notNull(player, "player can not be null");
        Player bukkitPlayer = (Player) player.getPlayer();
        Team team = player.getTeam();
        
        player.getPermissions().removeFromTeams(false);
        player.getPermissions().addToGroup(Arcade.getPermissions().getPlayable());
        player.reloadPermissions();
        
        bukkitPlayer.setCollidesWithEntities(true);
        
        bukkitPlayer.getInventory().clear();
        bukkitPlayer.getInventory().setArmorContents(null);
        bukkitPlayer.setFoodLevel(20);
        bukkitPlayer.setMaxHealth(20.0);
        bukkitPlayer.setHealth(20.0);
        bukkitPlayer.setGameMode(GameMode.SURVIVAL);
        if (hider) {
            PlayerHider.refresh(player);
        }
        for (PotionEffect potion : bukkitPlayer.getActivePotionEffects()) {
            bukkitPlayer.removePotionEffect(potion.getType());
        }
        
        bukkitPlayer.setFlySpeed(0.20F);
        bukkitPlayer.setWalkSpeed(0.20F);
        
        // Set kits of the team
        if (team.getKits().containsKey(kit)) {
            this.setKit(player, bukkitPlayer, team.getKits().get(kit));
        }
        
        // Set kits of the class
        ArcadeClass clazz = player.getArcadeClass();
        if (clazz != null && clazz.hasKits()) {
            this.setKit(player, bukkitPlayer, clazz.getKits().get(kit));
        }
        
        player.teleport(team.getSpawns().get(this.random.nextInt(team.getSpawns().size())));
        
        if (sendTitle) {
            player.sendTitle(Color.GREEN + "Powodzenia!");
        }
    }
    
    @Override
    public void setGhost(pl.shg.arcade.api.human.Player player, boolean ghost) {
        Validate.notNull(player, "player can not be null");
        if (ghost) {
            ((Player) player.getPlayer()).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
        } else {
            ((Player) player.getPlayer()).removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }
    
    private void setKit(pl.shg.arcade.api.human.Player player, Player bukkitPlayer, List<Kit> kits) {
        for (Kit kitObj : kits) {
            if (kitObj == null) {
                continue;
            }
            // Items
            if (kitObj.hasItems()) {
                for (KitItem item : kitObj.getItems()) {
                    ItemStack stack = itemToStack(item);
                    if (stack == null) {
                    } else if (item.hasSlot()) {
                        bukkitPlayer.getInventory().setItem(item.getSlot(), stack);
                    } else {
                        bukkitPlayer.getInventory().addItem(stack);
                    }
                }
            }
            // Options
            if (kitObj.hasOptions()) {
                for (Option option : kitObj.getOptions()) {
                    option.perform(player);
                }
            }
        }
    }
    
    /**
     * Convert Arcade API's Item to the Bukkit's ItemStack
     * @param item Item object to convert
     * @return converted Item to ItemStack
     */
    public static ItemStack itemToStack(Item item) {
        Material material = Material.getMaterial(item.getType().getID());
        if (material == null) {
            return null;
        }
        ItemStack stack = new ItemStack(material);
        
        stack.setAmount(item.getAmount());
        if (item.getType().hasSubID()) {
            stack.setDurability((byte) item.getType().getSubID());
        }
        if (item.hasEnchantments()) {
            for (Enchantment enchantment : item.getEnchantments()) {
                stack.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment
                        .getById(enchantment.getEID()), enchantment.getLevel());
            }
        }
        
        ItemMeta meta = stack.getItemMeta();
        if (item.hasName()) {
            meta.setDisplayName(Color.translate(item.getName()));
        }
        if (item.hasDescription()) {
            List<String> lore = new ArrayList<>();
            for (String line : item.getDescription()) {
                lore.add(Color.translate(line));
            }
            meta.setLore(lore);
        }
        
        stack.setItemMeta(meta);
        return stack;
    }
    
    private class ObserverKit {
        public void set(Player player, boolean allItems) {
            PlayerInventory inventory = player.getInventory();
            if (allItems) {
                this.clear(player);
                
                inventory.setItem(2, this.teamSelector());
                
                int classSelector = 0;
                if (Arcade.getMaps().getCurrentMap().hasClasses()) {
                    classSelector++;
                    inventory.setItem(3, this.classSelector());
                }   
                
                Tutorial tutorial = Arcade.getMaps().getCurrentMap().getTutorial();
                if (tutorial.isEmpty()) {
                    inventory.setItem(3 + classSelector, this.bookTutorialNull(tutorial));
                } else {
                    inventory.setItem(3 + classSelector, this.bookTutorial(tutorial));
                }
                
                if (Arcade.getServer().bungeeCord()) {
                    inventory.setItem(8, this.serverTeleporter());
                }
            }
            inventory.setItem(0, this.compass());
            player.updateInventory();
        }
        
        private ItemStack classSelector() {
            ItemStack item = new ItemStack(Material.SKULL_ITEM);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Color.GOLD + "Zmien klase");
            itemMeta.setLore(Arrays.asList(
                    Color.RED + "LPM" + Color.DARK_PURPLE + " - pokaz klase jaka teraz grasz",
                    Color.RED + "PPM" + Color.DARK_PURPLE + " - otworz okno zmiany klasy"
            ));
            item.setItemMeta(itemMeta);
            return item;
        }
        
        private void clear(Player player) {
            player.getInventory().clear();
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
        }
        
        private ItemStack compass() {
            ItemStack item = new ItemStack(Material.COMPASS);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Color.GOLD + "Szybki teleport");
            itemMeta.setLore(Arrays.asList(
                    Color.RED + "LPM" + Color.DARK_PURPLE + " - teleportuj sie na najwyzszy blok danego miejsca",
                    Color.RED + "PPM" + Color.DARK_PURPLE + " - teleportuj sie do wybranego miejsca"
            ));
            item.setItemMeta(itemMeta);
            return item;
        }
        
        private ItemStack teamSelector() {
            ItemStack item = new ItemStack(Material.NETHER_STAR);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Color.GOLD + "Wybierz druzyne");
            itemMeta.setLore(Arrays.asList(
                    Color.RED + "LPM" + Color.DARK_PURPLE + " - pokaz druzyne w której sie znajdujesz",
                    Color.RED + "PPM" + Color.DARK_PURPLE + " - otwórz okno wyboru druzyny"
            ));
            item.setItemMeta(itemMeta);
            return item;
        }
        
        private ItemStack bookTutorial(Tutorial tutorial) {
            Map map = Arcade.getMaps().getCurrentMap();
            ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
            
            BookMeta itemMeta = (BookMeta) item.getItemMeta();
            itemMeta.setAuthor(map.getAuthorsString(Color.DARK_PURPLE, Color.GRAY));
            itemMeta.setDisplayName(tutorial.getName());
            itemMeta.setLore(Arrays.asList(
                    Color.RED + "PPM" + Color.DARK_PURPLE + " - pokaz poradnik gry do tej mapy"
            ));
            itemMeta.setPages(map.getTutorial().toList());
            itemMeta.setTitle(Color.RED + map.getDisplayName());
            
            item.setItemMeta(itemMeta);
            return item;
        }
        
        private ItemStack bookTutorialNull(Tutorial tutorial) {
            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(tutorial.getName());
            itemMeta.setLore(Arrays.asList(
                    Color.GRAY + Color.ITALIC + "PPM - pokaz poradnik gry do tej mapy"
            ));
            item.setItemMeta(itemMeta);
            return item;
        }
        
        private ItemStack serverTeleporter() {
            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Color.GOLD + "Teleport serwerowy");
            itemMeta.setLore(Arrays.asList(
                    Color.RED + "LPM" + Color.DARK_PURPLE + " - teleportuj sie na serwer lobby",
                    Color.RED + "PPM" + Color.DARK_PURPLE + " - otwórz okno wyboru serwera"
            ));
            item.setItemMeta(itemMeta);
            return item;
        }
    }
}
