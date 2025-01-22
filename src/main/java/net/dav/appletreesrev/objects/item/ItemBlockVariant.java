package net.dav.appletreesrev.objects.item;

import net.dav.appletreesrev.objects.block.BlockAppleSapling;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockVariant extends ItemBlock {
    public ItemBlockVariant(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack) + "_" + BlockAppleSapling.TreeType.byMetadata(stack.getMetadata()).getName();
    }
}
