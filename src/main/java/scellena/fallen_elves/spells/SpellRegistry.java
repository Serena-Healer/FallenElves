package scellena.fallen_elves.spells;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import scellena.fallen_elves.FallenElves;
import scellena.fallen_elves.spells.types.*;

public class SpellRegistry {
    public static IForgeRegistry<SpellRegistryEntry> SPELLS;

    public static void init(){

        SPELLS.register(new SpellRegistryEntry(BulletSpell.class, new ResourceLocation(FallenElves.MOD_ID, "bullet")));
        SPELLS.register(new SpellRegistryEntry(BulletSpell2.class, new ResourceLocation(FallenElves.MOD_ID, "bullet_2")));
        SPELLS.register(new SpellRegistryEntry(ExplodeSpell.class, new ResourceLocation(FallenElves.MOD_ID, "bullet_area")));
        SPELLS.register(new SpellRegistryEntry(ExplodeFromSelfSpell.class, new ResourceLocation(FallenElves.MOD_ID, "explode")));

        SPELLS.register(new SpellRegistryEntry(HealSpell.class, new ResourceLocation(FallenElves.MOD_ID, "heal")));
        SPELLS.register(new SpellRegistryEntry(HealingBulletSpell.class, new ResourceLocation(FallenElves.MOD_ID, "heal_bullet")));
        SPELLS.register(new SpellRegistryEntry(AreaHealSpell.class, new ResourceLocation(FallenElves.MOD_ID, "heal_area")));

        SPELLS.register(new SpellRegistryEntry(ShieldSpell.class, new ResourceLocation(FallenElves.MOD_ID, "shield")));

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
