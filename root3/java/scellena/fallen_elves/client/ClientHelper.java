package scellena.fallen_elves.client;

import akka.japi.Pair;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientHelper {

    public static List<Pair<String, Long>> crosshair = new ArrayList<>();
    public static List<Pair<String, Long>> corner = new ArrayList<>();

    public static void addCrosshairString(String text){
        crosshair.add(new Pair<>(text, Minecraft.getMinecraft().world.getTotalWorldTime() + 20));
    }

    public static void addCornerString(String text){
        corner.add(new Pair<>(text, Minecraft.getMinecraft().world.getTotalWorldTime() + 80));
    }

}
