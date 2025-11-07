package scellena.fallen_elves.events.handler;

import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import scellena.fallen_elves.Confiance5th;
import scellena.fallen_elves.data.entity.EntityCapabilityProvider;
import scellena.fallen_elves.data.entity.EntityDataHandler;
import scellena.fallen_elves.data.player.PlayerCapabilityProvider;
import scellena.fallen_elves.data.player.PlayerDataHandler;
import scellena.fallen_elves.data.world.WorldDataHandler;
import scellena.fallen_elves.entities.ICustomEntity;

import java.util.ArrayList;
import java.util.List;

public class CapabilityEventHandler {

    @SubscribeEvent
    public void initCapabilities(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if(entity != null){
            if(entity instanceof EntityPlayer) {
                event.addCapability(new ResourceLocation(Confiance5th.MOD_ID, "LightSpiritRPG_player"), new PlayerCapabilityProvider());
            }
            event.addCapability(new ResourceLocation(Confiance5th.MOD_ID, "LightSpiritRPG_entity"), new EntityCapabilityProvider());
        }
    }

    @SubscribeEvent
    public void reloadPlayerCapability(PlayerEvent.Clone event){
        PlayerDataHandler dataOld = PlayerCapabilityProvider.getPlayerData(event.getOriginal());
        PlayerDataHandler dataNew = PlayerCapabilityProvider.getPlayerData(event.getEntityPlayer());
        dataNew.readNBT(dataOld.getNBT());
        event.getEntityPlayer().heal(200000);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            PlayerDataHandler data = PlayerCapabilityProvider.getPlayerData(event.player);
            if (data != null) {
                data.onTick();
            }
        }
    }

    @SubscribeEvent
    public void onEntityTick(TickEvent.WorldTickEvent event){
        if(event.phase == TickEvent.Phase.END) {
            event.world.getEntities(Entity.class, Predicates.alwaysTrue()).forEach(e -> {
                EntityDataHandler data = EntityCapabilityProvider.getEntityData(e);
                if (data != null) {
                    data.onTick();
                }
            });
        }
    }

    @SubscribeEvent
    public void onJoin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event){
        WorldDataHandler.get(event.player.getEntityWorld()).markDirty();
    }

    @SubscribeEvent
    public void onMoveDimension(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event){
        WorldDataHandler.get(event.player.getEntityWorld()).markDirty();
    }

}
