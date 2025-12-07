package scellena.fallen_elves.entities.mobs.mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.entities.ICustomEntity;
import scellena.fallen_elves.entities.LootTableHandler;
import scellena.fallen_elves.entities.mobs.templetes.FriendlyMob;
import scellena.fallen_elves.entities.mobs.templetes.HostileMob;
import scellena.fallen_elves.items.ItemHandler;
import scellena.fallen_elves.spells.SpellAttributes;

import javax.annotation.Nullable;

public class ElfHostile extends HostileMob implements ICustomEntity{

    public ElfHostile(World worldIn) {
        super(worldIn);
    }

    @Override
    public float getSpeed() {
        return 0.25F;
    }

    @Override
    public double getEffectiveHealth() {
        return 50;
    }

    @Override
    public double getAttackDamage() {
        return 2;
    }

    int tick = 0;

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(getAttackTarget() != null){
            tick--;
            if(tick <= 0){
                tick = rand.nextInt(200) + 140;
                EntityCapabilityProvider.getEntityData(this).getSpellObjects().get(new ResourceLocation(FallenElves.MOD_ID, "bullet_light")).onRightClick();
            }
        }
    }


    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ItemHandler.WAND_ENEMY));
        setDropChance(EntityEquipmentSlot.MAINHAND, 0.1F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        SpellAttributes.init(this);
        this.getEntityAttribute(SpellAttributes.MAGICAL_MIGHT).setBaseValue(0);
        this.getEntityAttribute(SpellAttributes.MAGICAL_MENDING).setBaseValue(0);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableHandler.ELF_HOSTILE;
    }
}
