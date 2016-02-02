package org.cat73.cheats.util;

import net.minecraft.item.ItemStack;

public class ItemUnit {
    public static boolean equalsItem(final ItemStack item1, final ItemStack item2) {
        if ((item1 != null) && (item2 != null) && (item1.getItem() == item2.getItem())) {
            if (!item1.getHasSubtypes() || ((item1.getItemDamage() == item2.getItemDamage()) && ItemStack.areItemStackTagsEqual(item1, item2))) {
                return true;
            }
        }
        return false;
    }

    public static int findFreeItem(final ItemStack[] inventory, final ItemStack item) {
        for (int i = 0; i < inventory.length; i++) {
            if (ItemUnit.equalsItem(inventory[i], item) && (inventory[i].stackSize < item.getMaxStackSize())) {
                return i;
            }
        }

        return -1;
    }

    public static int findItem(final ItemStack[] inventory, final ItemStack item) {
        for (int i = 0; i < inventory.length; i++) {
            if (ItemUnit.equalsItem(inventory[i], item)) {
                return i;
            }
        }

        return -1;
    }
}
