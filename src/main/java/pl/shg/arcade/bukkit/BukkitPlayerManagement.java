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
import org.apache.commons.lang3.Validate;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.classes.ArcadeClass;
import pl.shg.arcade.api.human.VisibilityFilter;
import pl.shg.arcade.api.item.Enchantment;
import pl.shg.arcade.api.item.Item;
import pl.shg.arcade.api.kit.Kit;
import pl.shg.arcade.api.kit.KitItem;
import pl.shg.arcade.api.kit.KitType;
import pl.shg.arcade.api.kit.LeatherColorData;
import pl.shg.arcade.api.kit.Option;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class BukkitPlayerManagement implements PlayerManagement {
    private final ObserverKit obsKit = new ObserverKit();
    private final Random random = new Random();
    private final Server server;
    private VisibilityFilter visibility = new VisibilityFilter();
    
    public BukkitPlayerManagement(Server server) {
        Validate.notNull(server, "server can not ben null");
        this.server = server;
    }
    
    @Override
    public void addPotion(pl.shg.arcade.api.human.Player player, String id, int level, int time) {
        Validate.notNull(player, "player can not be null");
        Validate.notNull(id, "id can not be null");
        Validate.isTrue(level >= 0);
        Validate.isTrue(time >= 0);
        ((Player) player.getPlayer()).addPotionEffect(new PotionEffect(PotionEffectType.getByName(id), time, level), true);
    }
    
    @Override
    public VisibilityFilter getVisibility() {
        return this.visibility;
    }
    
    @Override
    public boolean isGhost(pl.shg.arcade.api.human.Player player) {
        Validate.notNull(player, "player can not be null");
        return ((Player) player.getPlayer()).hasPotionEffect(PotionEffectType.INVISIBILITY);
    }
    
    @Override
    public void playSound(pl.shg.arcade.api.human.Player player, pl.shg.arcade.api.Sound sound) {
        this.playSound(player, sound, 5F, 1F);
    }
    
    @Override
    public void playSound(pl.shg.arcade.api.human.Player player, pl.shg.arcade.api.Sound sound, float volume, float pitch) {
        Validate.notNull(player, "player can not be null");
        Validate.notNull(sound, "sound can not be null");
        
        Sound result = null;
        switch (sound) {
            case BEGINING: result = Sound.ORB_PICKUP; break;
            case BEGINS: result = Sound.ANVIL_LAND; break;
            case ELIMINATION: result = Sound.IRONGOLEM_DEATH; break;
            case ENEMY_LOST: result = Sound.WITHER_DEATH; break;
            case ENEMY_WON: result = Sound.WITHER_SPAWN; break;
            case MENTION: result = Sound.CHICKEN_EGG_POP; break; // NOTE_PIANO
            case OBJECTIVE: result = Sound.WITHER_IDLE; break;
            case OBJECTIVE_LOST: result = Sound.BLAZE_DEATH; break;
            case OBJECTIVE_SCORED: result = Sound.FIREWORK_TWINKLE2; break;
            case TICK: result = Sound.CLICK; break;
            case TIME_OUT: result = Sound.PORTAL_TRIGGER; break;
        }
        
        if (result != null) {
            Player bukkitPlayer = (Player) player.getPlayer();
            bukkitPlayer.playSound(bukkitPlayer.getLocation(), result, volume, pitch);
        }
    }
    
    @Override
    public void refreshHiderForAll() {
        for (pl.shg.arcade.api.human.Player online : Arcade.getServer().getConnectedPlayers()) {
            online.updateVisibility();
        }
    }
    
    @Override
    public void setAsObserver(pl.shg.arcade.api.human.Player player, boolean fullKit, boolean hider, boolean perms) {
        Validate.notNull(player, "player can not be null");
        Player bukkitPlayer = (Player) player.getPlayer();
        
        if (perms) {
            player.getPermissions().removeFromTeams(false);
            player.getPermissions().addToGroup(Arcade.getPermissions().getObservers());
            player.reloadPermissions();
        }
        
        bukkitPlayer.setAffectsSpawning(false);
        bukkitPlayer.setCollidesWithEntities(false);
        
        bukkitPlayer.setArrowsStuck(0);
        bukkitPlayer.setFoodLevel(20);
        bukkitPlayer.setMaxHealth(20.0);
        if (!player.isDead()) {
            bukkitPlayer.setHealth(20.0);
        }
        bukkitPlayer.setGameMode(GameMode.CREATIVE);
        if (hider) {
            player.updateVisibility();
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
    public void setAsPlayer(pl.shg.arcade.api.human.Player player, KitType kit, boolean hider, boolean sendTitle, boolean perms) {
        Validate.notNull(player, "player can not be null");
        Player bukkitPlayer = (Player) player.getPlayer();
        Team team = player.getTeam();
        
        if (perms) {
            player.getPermissions().removeFromTeams(false);
            player.getPermissions().addToGroup(Arcade.getPermissions().getPlayable());
            player.reloadPermissions();
        }
        
        bukkitPlayer.setAffectsSpawning(true);
        bukkitPlayer.setCollidesWithEntities(true);
        
        bukkitPlayer.setArrowsStuck(0);
        bukkitPlayer.getInventory().clear();
        bukkitPlayer.getInventory().setArmorContents(null);
        bukkitPlayer.setFoodLevel(20);
        bukkitPlayer.setMaxHealth(20.0);
        bukkitPlayer.setHealth(20.0);
        bukkitPlayer.setGameMode(GameMode.SURVIVAL);
        if (hider) {
            player.updateVisibility();
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
    
    @Override
    public void setVisibility(VisibilityFilter visibility) {
        if (visibility == null) {
            visibility = new VisibilityFilter();
        }
        
        this.visibility = visibility;
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
                        continue;
                    }
                    
                    if (item.hasData()) {
                        ItemMeta meta = stack.getItemMeta();
                        if (item.getData() instanceof LeatherColorData) {
                            LeatherColorData leather = (LeatherColorData) item.getData();
                            ((LeatherArmorMeta) meta).setColor(org.bukkit.Color.fromRGB(
                                    leather.getColor().getRGB()[0],
                                    leather.getColor().getRGB()[1],
                                    leather.getColor().getRGB()[2]));
                        }
                        stack.setItemMeta(meta);
                    }
                    
                    if (item.hasSlot()) {
                        bukkitPlayer.getInventory().setItem(item.getSlot(), stack);
                    } else {
                        bukkitPlayer.getInventory().addItem(stack);
                    }
                }
            }
            
            // Options
            if (kitObj.hasOptions()) {
                for (Option option : kitObj.getOptions()) {
                    option.apply(player);
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
        
        if (item.isUnbreakable()) {
            meta.setUnbreakable(true);
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
