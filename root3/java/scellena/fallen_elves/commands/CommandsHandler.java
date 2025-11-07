package scellena.fallen_elves.commands;

import net.minecraft.command.CommandBase;

import java.util.ArrayList;
import java.util.List;

public class CommandsHandler {
    public static List<CommandBase> allCommands = new ArrayList<>();
    public static void init(){
    }

    public static void register(CommandBase command){
        allCommands.add(command);
    }
}
