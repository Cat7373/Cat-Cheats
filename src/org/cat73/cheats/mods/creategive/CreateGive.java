package org.cat73.cheats.mods.creategive;

import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModInfo;
import org.cat73.cheats.reference.Names;
import org.cat73.cheats.util.PlayerMessage;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.util.ResourceLocation;

@ModInfo(name="CreateGive")
public class CreateGive extends Mod {
    @Override
    public void onEnable() {
        Mod.minecraft.displayGuiScreen(new Gui_CreateGive());
        setEnabled(false);
    }

    public static void giveItem(final int itemId, int damage, final int slot, int count, final String NBT_Json) {
        final EntityPlayerSP player = Mod.minecraft.thePlayer;

        // 获取item
        final Item item = Item.getItemById(itemId);
        if(item == null) {
            PlayerMessage.error(I18n.format(Names.Mods.CreateGive.Error.ITEM_NOT_FOUND, itemId));
            return;
        }

        // 获取物品
        ItemStack stack = new ItemStack(item, count, damage);

        // 设置NBT
        NBTTagCompound nbtTagCompound;
        try {
            nbtTagCompound = JsonToNBT.func_180713_a(NBT_Json);
        } catch (NBTException e) {
            PlayerMessage.error(I18n.format(Names.Mods.CreateGive.Error.NBT_ERROR, NBT_Json));
            e.printStackTrace();
            return;
        }
        stack.setTagCompound(nbtTagCompound);

        // 将物品添加到物品栏
        player.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(slot == 0 ? -1 : 35 + slot, stack));
    }

    public static void giveItem(final String command, final int slot) {
        final String[] args = command.split(" ");
        if(args.length < 3) {
            PlayerMessage.error(I18n.format(Names.Mods.CreateGive.Error.MISSING_PARAM));
        }

        int itemId = 0, count = 0, damage = 0;
        
        // itemId
        try {
            itemId = getItemByText(args[2]);
        } catch(Exception e) {
            PlayerMessage.error(I18n.format(Names.Mods.CreateGive.Error.UNKNOW_ITEM_NAME, args[2]));
            e.printStackTrace();
            return;
        }
        
        // count
        try {
            count = args.length >= 4 ? Integer.parseInt(args[3]) : 1;
        } catch(Exception e) {
            PlayerMessage.error(I18n.format(Names.Mods.CreateGive.Error.COUNT_ERROR, args[3]));
            e.printStackTrace();
            return;
        }
        
        // damage
        try {
            damage = args.length >= 5 ? Integer.parseInt(args[4]) : 0;
        } catch(Exception e) {
            PlayerMessage.error(I18n.format(Names.Mods.CreateGive.Error.DAMAGE_ERROR, args[4]));
            e.printStackTrace();
            return;
        }
        
        // give
        try {
            String nbt_JSON = args.length >= 6 ? getNbtJSON(args, 5) : "{}";
            giveItem(itemId, damage, slot, count, nbt_JSON);
        } catch (Exception e) {
            PlayerMessage.error(I18n.format(Names.Mods.CreateGive.Error.UNKNOW_ERROR, command));
            e.printStackTrace();
        }
    }
    
    private static String getNbtJSON(String[] args, int i) {
        String result = "";
        while(true) {
            result += args[i];

            if(++i < args.length) {
                result += " ";
            } else {
                break;
            }
        }
        return result;
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
