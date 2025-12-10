package scellena.fallen_elves.spells.types;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.decay.BlockDecayHelper;
import scellena.fallen_elves.potions.PotionsHandler;
import scellena.fallen_elves.spells.SpellBase;

public class TheLastSpell extends SpellBase {

    int tick = 10000;
    BlockPos pos;

    @Override
    public boolean onRightClick() {
        tick = 0;
        pos = getOwner().getPosition();
        return true;
    }

    @Override
    public void onTick() {
        super.onTick();
        tick++;
        if(tick < 40){
            for(int i=0; i<8; i++){
                double angle = Math.PI * ((double)i) / 4.0;
                for(int j=0; j<4; j++){
                    double r = tick * 12 + j * 3;
                    BlockPos pos2 = new BlockPos(pos.getX() + r * Math.sin(angle), 0, pos.getZ() + r * Math.cos(angle));
                    for(int x=0; x<3; x++){
                        for(int z=0; z<3; z++){
                            BlockPos pos3 = pos2.add(x - 1, 0, z - 1);
                            for(int k=0; k<256; k++){
                                BlockPos pos4 = pos3.add(0, k, 0);
                                BlockDecayHelper.decay(getOwner().world, pos4);
                            }
                        }
                    }
                }
            }
        }
        if(tick == 100){
            getOwner().sendMessage(new TextComponentTranslation("spell.fallen_elves:the_last.msg1"));
        }
        if(tick == 200){
            getOwner().sendMessage(new TextComponentTranslation("spell.fallen_elves:the_last.msg2"));
        }
        if(tick == 300){
            getOwner().sendMessage(new TextComponentTranslation("spell.fallen_elves:the_last.msg3"));
        }
        if(tick == 400){
            getOwner().sendMessage(new TextComponentTranslation("spell.fallen_elves:the_last.msg4"));
            getOwner().sendMessage(new TextComponentTranslation("spell.fallen_elves:the_last.msg5"));
            getOwner().sendMessage(new TextComponentTranslation("spell.fallen_elves:the_last.msg6"));
            getOwner().sendMessage(new TextComponentTranslation("spell.fallen_elves:the_last.msg7"));
            getOwner().sendMessage(new TextComponentTranslation("spell.fallen_elves:the_last.msg8"));
            if(getOwner() instanceof EntityLivingBase){
                ((EntityLivingBase) getOwner()).addPotionEffect(new PotionEffect(PotionsHandler.END, 20 * 10, 0));
            }
        }
    }

    @Override
    public double getManaCost() {
        return 1000;
    }
}
