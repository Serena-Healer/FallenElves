package scellena.fallen_elves.decay;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scellena.fallen_elves.blocks.BlockHandler;
import scellena.fallen_elves.entities.mobs.templetes.HostileMob;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockDecayHelper {

    public static final Map<Block, Block> decayingMap = new HashMap<>();

    public static void initMap(){
        decayingMap.put(Blocks.GRASS, BlockHandler.DECAY_GRASS);
        decayingMap.put(Blocks.DIRT, BlockHandler.DECAY_DIRT);
        decayingMap.put(Blocks.SAND, BlockHandler.DECAY_SAND);
    }

    public static boolean decay(World worldIn, BlockPos pos){
        if(decayingMap.containsKey(worldIn.getBlockState(pos).getBlock())){
            worldIn.setBlockState(pos, decayingMap.get(worldIn.getBlockState(pos).getBlock()).getDefaultState());
            worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 2);
            return true;
        }
        return false;
    }

    public static boolean canInflictDecay(Block block){
        return decayingMap.values().contains(block);
    }

    public static void onWorldTick(World worldIn){
        for(EntityLivingBase entity : worldIn.getEntities(EntityLivingBase.class, (e -> true))){
            Block block = entity.getEntityWorld().getBlockState(entity.getPosition().add(0, -0.5, 0)).getBlock();
            if(canInflictDecay(block) && !(entity instanceof EntityMob && !(entity instanceof HostileMob))){
                entity.attackEntityFrom(DamageSource.WITHER, 1);
            }

            /*
            //拡散
            for(int i=0; i<40; i++){
                {
                    Random random = new Random();
                    BlockPos p1 = entity.getPosition();
                    double r = Math.pow(Math.E, random.nextDouble() * Math.log(256));
                    double arg1 = random.nextDouble() * Math.PI * 2;
                    BlockPos p2 = p1.add(r * Math.sin(arg1), random.nextGaussian() * 10, r * Math.cos(arg1));
                    if(worldIn.isBlockLoaded(p2)) {
                        if (canInflictDecay(worldIn.getBlockState(p2).getBlock())) {
                            decay(worldIn, p2.add(random.nextInt(5) - 2, random.nextInt(5) - 2, random.nextInt(5) - 2));
                        }
                    }
                }
                {
                    Random random = new Random();
                    BlockPos p1 = entity.getPosition();
                    double r = Math.pow(random.nextDouble() * 128, 2);
                    double arg1 = random.nextDouble() * Math.PI * 2;
                    BlockPos p2 = p1.add(r * Math.sin(arg1), random.nextGaussian() * 10, r * Math.cos(arg1));
                    if(worldIn.isBlockLoaded(p2)) {
                        if (canInflictDecay(worldIn.getBlockState(p2).getBlock())) {
                            decay(worldIn, p2.add(random.nextInt(5) - 2, random.nextInt(5) - 2, random.nextInt(5) - 2));
                        }
                    }
                }
            }

            if(entity instanceof EntityPlayer){
                for(int i=0; i<10000; i++){
                    {
                        Random random = new Random();
                        BlockPos p1 = entity.getPosition();
                        double r = random.nextDouble() * 256;
                        double arg1 = random.nextDouble() * Math.PI * 2;
                        BlockPos p2 = p1.add(r * Math.sin(arg1), random.nextGaussian() * 10.0, r * Math.cos(arg1));
                        if(worldIn.isBlockLoaded(p2)) {
                            if (canInflictDecay(worldIn.getBlockState(p2).getBlock())) {
                                decay(worldIn, p2.add(random.nextInt(5) - 2, random.nextInt(5) - 2, random.nextInt(5) - 2));
                            }
                        }
                    }
                    {
                        Random random = new Random();
                        BlockPos p1 = entity.getPosition();
                        double r = Math.pow(random.nextDouble() * 256, 2);
                        double arg1 = random.nextDouble() * Math.PI * 2;
                        BlockPos p2 = p1.add(r * Math.sin(arg1), random.nextGaussian() * 10.0, r * Math.cos(arg1));
                        if(worldIn.isBlockLoaded(p2)) {
                            if (canInflictDecay(worldIn.getBlockState(p2).getBlock())) {
                                decay(worldIn, p2.add(random.nextInt(5) - 2, random.nextInt(5) - 2, random.nextInt(5) - 2));
                            }
                        }
                    }
                }
            }
             */
        }
    }

}
