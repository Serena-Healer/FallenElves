package scellena.fallen_elves.spells;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.spells.types.HealSpell;
import scellena.fallen_elves.spells.types.BulletSpell;
import scellena.fallen_elves.spells.types.TheLastSpell;

public class SpellRegistry {
    public static IForgeRegistry<SpellRegistryEntry> SPELLS;

    public static void init(){

        SPELLS.register(new SpellRegistryEntry(BulletSpell.class, new ResourceLocation(FallenElves.MOD_ID, "test")));
        SPELLS.register(new SpellRegistryEntry(HealSpell.class, new ResourceLocation(FallenElves.MOD_ID, "heal")));
        SPELLS.register(new SpellRegistryEntry(TheLastSpell.class, new ResourceLocation(FallenElves.MOD_ID, "the_last")));

    }

    public static class SpellRegistryEntry extends IForgeRegistryEntry.Impl<SpellRegistryEntry> {

        Class<? extends SpellBase> clazz;

        public SpellRegistryEntry(Class<? extends SpellBase> clazz, ResourceLocation name){
            this.clazz = clazz;
            setRegistryName(name);
        }

        public SpellBase getInstance(){
            try {
                SpellBase ans = clazz.newInstance();
                ans.setRegistryName(getRegistryName());
                return ans;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        public Class<? extends SpellBase> getSpell(){
            return clazz;
        }
    }
}
