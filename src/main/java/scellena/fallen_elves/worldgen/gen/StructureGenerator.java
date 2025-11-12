package scellena.fallen_elves.worldgen.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import scellena.fallen_elves.FallenElves;

import java.util.Random;

public class StructureGenerator extends WorldGenerator {

    public String name;
    public static WorldServer SERVER;
    public static final PlacementSettings SETTINGS = new PlacementSettings().setChunk(null).setIgnoreEntities(false).setIgnoreStructureBlock(true).setMirror(Mirror.NONE).setRotation(Rotation.NONE);
    public int yDiff;

    /**
     * @param name structure ディレクトリからの相対パス
     * @param yDiff 深さ(ここに指定した正の整数マス分だけ地中に埋まります)
     */
    public StructureGenerator(String name, int yDiff){
        this.name = name;
        this.yDiff = yDiff;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        run(worldIn, position);
        return true;
    }

    public void run(World world, BlockPos pos){
        MinecraftServer server = world.getMinecraftServer();
        pos = pos.add(0, -yDiff, 0);
        SERVER = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
        TemplateManager manager = SERVER.getStructureTemplateManager();
        ResourceLocation file = new ResourceLocation(FallenElves.MOD_ID, name);
        Template template = manager.get(null, file);
        if(template != null){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, SETTINGS);
        }
    }

}
