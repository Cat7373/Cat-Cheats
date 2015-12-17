package org.cat73.xray.mods.creategive;

import org.cat73.xray.mods.Mod;
import org.cat73.xray.mods.ModInfo;
import org.cat73.xray.util.PlayerMessage;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

@ModInfo(name="CreateGive")
public class CreateGive extends Mod {
    public void onEnable() {
        Mod.minecraft.displayGuiScreen(new Gui_CreateGive());
        setEnabled(false);
    }
    
 // TODO 增强健壮性 避免非法输入时崩溃
    public static void giveItem(final int itemId, final int damage, int slot, final int count, final String NBT_Json) {
        final EntityPlayerSP player = Mod.minecraft.thePlayer;

        final ItemStack stack = new ItemStack(Item.getItemById(itemId), count, damage);

        NBTTagCompound nbtTagCompound;
        try {
            nbtTagCompound = JsonToNBT.func_180713_a(NBT_Json);
        } catch (NBTException e) {
            PlayerMessage.warn("非法的NBT_Json: " + NBT_Json);
            e.printStackTrace();
            return;
        }
        stack.setTagCompound(nbtTagCompound);
        
        final ItemStack[] inventory = slot <= 35 ? player.inventory.mainInventory : player.inventory.armorInventory;
        if(slot >= 36) {
            slot -= 36;
        }
        inventory[slot] = stack;
    }
    
    // TODO 支持更少的参数
    // TODO NBT_Json中含有空格时不会出错
    public static void giveItem(final String command, final int slot) {
        final String[] args = command.split(" ");
        try {
            final int itemId = getItemByText(args[2]);
            final int damage = Integer.parseInt(args[4]);
            final int count = Integer.parseInt(args[3]);
            giveItem(itemId, damage, slot, count, args[5]);
        } catch (Exception e) {
            PlayerMessage.warn("非法的Command: " + command);
            e.printStackTrace();
        }
    }
    
    public static int getItemByText(final String text) {
        final ResourceLocation resourcelocation = new ResourceLocation(text);
        final Item item = (Item)Item.itemRegistry.getObject(resourcelocation);
        
        if(item == null) {
            return Integer.parseInt(text);
        } else {
            return Item.getIdFromItem(item);
        }
    }
}
