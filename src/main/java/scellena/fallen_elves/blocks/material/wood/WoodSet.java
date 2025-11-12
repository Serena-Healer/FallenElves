package scellena.fallen_elves.blocks.material.wood;

import net.minecraft.util.IStringSerializable;

public class WoodSet {
    public String name;

    public enum EnumWoodTypes implements IStringSerializable {
        MAGIC(0, "magic");

        final int id;
        final String name;

        EnumWoodTypes(int id, String name){
            this.id = id;
            this.name = name;
        }

        public int getId(){
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        public static EnumWoodTypes byMetadata(int meta)
        {
            for(EnumWoodTypes e : values()){
                if(e.getId() == meta % 2)return e;
            }
            return null;
        }
    }
}
