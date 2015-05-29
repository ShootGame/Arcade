/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.item;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class Enchantments {
    private static final List<EnchantmentType> enchantments = new ArrayList<>();
    
    // Armor
    public static final EnchantmentType PROTECTION =
            new EnchantmentType(0, 4, "protection");
    public static final EnchantmentType FIRE_PROTECTION =
            new EnchantmentType(1, 4, "fire protection");
    public static final EnchantmentType FEATHER_FALLING =
            new EnchantmentType(2, 4, "feather falling");
    public static final EnchantmentType BLAST_PROTECTION =
            new EnchantmentType(3, 4, "blast protection");
    public static final EnchantmentType PROJECTILE_PROTECTION =
            new EnchantmentType(4, 4, "projectile protection");
    public static final EnchantmentType RESPIRATION =
            new EnchantmentType(5, 3, "respiration");
    public static final EnchantmentType AQUA_AFFINITY =
            new EnchantmentType(6, 1, "aqua affinity");
    public static final EnchantmentType THORNS =
            new EnchantmentType(7, 3, "thorns");
    public static final EnchantmentType DEPTH_STRIDER =
            new EnchantmentType(8, 3, "depth strider");
    
    // Swords
    public static final EnchantmentType SHARPNESS =
            new EnchantmentType(16, 5, "sharpness");
    public static final EnchantmentType SMITE =
            new EnchantmentType(17, 5, "smite");
    public static final EnchantmentType BANE_OF_ARTHROPODS =
            new EnchantmentType(18, 5, "bane of arthropods");
    public static final EnchantmentType KNOCKBACK =
            new EnchantmentType(19, 2, "knockback");
    public static final EnchantmentType FIRE_ASPECT =
            new EnchantmentType(20, 2, "fire aspect");
    public static final EnchantmentType LOOTING =
            new EnchantmentType(21, 3, "looting");
    
    // Tools
    public static final EnchantmentType EFFICIENCY =
            new EnchantmentType(32, 5, "efficiency");
    public static final EnchantmentType SILK_TOUCH =
            new EnchantmentType(33, 1, "silk touch");
    public static final EnchantmentType UNBREAKING =
            new EnchantmentType(34, 3, "unbreaking");
    public static final EnchantmentType FORTUNE =
            new EnchantmentType(35, 3, "fortune");
    
    // Bows
    public static final EnchantmentType POWER =
            new EnchantmentType(48, 5, "power");
    public static final EnchantmentType PUNCH =
            new EnchantmentType(49, 2, "punch");
    public static final EnchantmentType FLAME =
            new EnchantmentType(50, 1, "flame");
    public static final EnchantmentType INFINITY =
            new EnchantmentType(51, 1, "infinity");
    
    // Fishing rods
    public static final EnchantmentType LUCK_OF_THE_SEA =
            new EnchantmentType(61, 3, "luck of the sea");
    public static final EnchantmentType LURE =
            new EnchantmentType(62, 3, "lure");
    
    static {
        registerDefaultEnchantments();
    }
    
    public static EnchantmentType getEnchantment(int eid) {
        for (EnchantmentType enchantment : enchantments) {
            if (enchantment.getEID() == eid) {
                return enchantment;
            }
        }
        return null;
    }
    
    public static EnchantmentType getEnchantment(String name) {
        Validate.notNull(name, "name can not be null");
        name = name.toLowerCase().replace("_", " ");
        for (EnchantmentType enchantment : enchantments) {
            if (enchantment.getName().toLowerCase().equals(name)) {
                return enchantment;
            }
        }
        return null;
    }
    
    public static List<EnchantmentType> getRegisteredEnchantments() {
        return enchantments;
    }
    
    public static void registerEnchantment(EnchantmentType enchantment) {
        Validate.notNull(enchantment, "enchantment can not be null");
        enchantments.add(enchantment);
    }
    
    public static void registerDefaultEnchantments() {
        // Armor
        registerEnchantment(Enchantments.PROTECTION);
        registerEnchantment(Enchantments.FIRE_PROTECTION);
        registerEnchantment(Enchantments.FEATHER_FALLING);
        registerEnchantment(Enchantments.BLAST_PROTECTION);
        registerEnchantment(Enchantments.PROJECTILE_PROTECTION);
        registerEnchantment(Enchantments.RESPIRATION);
        registerEnchantment(Enchantments.AQUA_AFFINITY);
        registerEnchantment(Enchantments.THORNS);
        registerEnchantment(Enchantments.DEPTH_STRIDER);
        
        // Swords
        registerEnchantment(Enchantments.SHARPNESS);
        registerEnchantment(Enchantments.SMITE);
        registerEnchantment(Enchantments.BANE_OF_ARTHROPODS);
        registerEnchantment(Enchantments.KNOCKBACK);
        registerEnchantment(Enchantments.FIRE_ASPECT);
        registerEnchantment(Enchantments.LOOTING);
        
        // Tools
        registerEnchantment(Enchantments.EFFICIENCY);
        registerEnchantment(Enchantments.SILK_TOUCH);
        registerEnchantment(Enchantments.UNBREAKING);
        registerEnchantment(Enchantments.FORTUNE);
        
        // Bows
        registerEnchantment(Enchantments.POWER);
        registerEnchantment(Enchantments.PUNCH);
        registerEnchantment(Enchantments.FLAME);
        registerEnchantment(Enchantments.INFINITY);
        
        // Fishing rods
        registerEnchantment(Enchantments.LUCK_OF_THE_SEA);
        registerEnchantment(Enchantments.LURE);
    }
}
