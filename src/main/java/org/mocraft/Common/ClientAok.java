package org.mocraft.Common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.network.PacketManager;
import org.mocraft.Common.network.server.SyncIEEPMessage;
import org.mocraft.Utils.BlockPos;

/**
 * Created by Clode on 2016/10/11.
 */
public class ClientAok implements IExtendedEntityProperties {

    public static final String PROP_NAME = AgeOfKingdom.MODID + "_ExtendedProperties";

    private EntityPlayer player;
    private int lordLevel = 0;
    private BlockPos landPos = new BlockPos(0, 0, 0);

    public ClientAok(EntityPlayer player) {
        this.player = player;
    }

    public ClientAok(NBTTagCompound compound, EntityPlayer player) {
        this.player = player;
        loadNBTData(compound);
    }

    public static void init() {
        Handler h = new Handler();
        MinecraftForge.EVENT_BUS.register(h);
        FMLCommonHandler.instance().bus().register(h);
    }

    public static final void register(EntityPlayer player) { player.registerExtendedProperties(PROP_NAME, new ClientAok(player)); }
    public static final ClientAok get(EntityPlayer p) { return (ClientAok) p.getExtendedProperties(PROP_NAME); }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound tmp = new NBTTagCompound();

        tmp.setInteger("LordLevel", this.lordLevel);
        this.landPos.saveNBTData(tmp);
        compound.setTag(PROP_NAME, tmp);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if(compound.hasKey(PROP_NAME, Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound tmp = compound.getCompoundTag(PROP_NAME);

            this.lordLevel = tmp.getInteger("LordLevel");
            this.landPos.loadNBTData(tmp);
        }
    }

    @Override
    public void init(Entity entity, World world) {  }

    public int getLordLevel() { return this.lordLevel; }

    public void setLordLevel(int level) { this.lordLevel = level; }

    public BlockPos getLandPos() { return this.landPos; }

    public void setLandPos(BlockPos landPos) { this.landPos = landPos; }

    public static class Handler {

        @SubscribeEvent
        public void onEntityConstruct(EntityEvent.EntityConstructing e) {
            if (e.entity instanceof EntityPlayerMP && e.entity.getExtendedProperties(PROP_NAME) == null) {
                register((EntityPlayer) e.entity);
            }
        }
        /**
        @SubscribeEvent
        public void onEntityJoinEvent(EntityJoinWorldEvent e) {
            if(e.entity instanceof EntityPlayer && !e.entity.worldObj.isRemote) {
                if(AgeOfKingdom.serverProxy.getPlayerClientCore((EntityPlayer) e.entity) != null) {
                    ((ClientAok)e.entity.getExtendedProperties(ClientAok.PROP_NAME)).loadNBTData(AgeOfKingdom.serverProxy.getPlayerClientCore((EntityPlayer) e.entity));
                }
                PacketManager.sendTo(new SyncIEEPMessage((EntityPlayer)e.entity), (EntityPlayerMP) e.entity);
            }
        }

        @SubscribeEvent
        public void onLivingDeath(LivingDeathEvent e) {
            if(e.entity instanceof EntityPlayer) {
                NBTTagCompound compound = new NBTTagCompound();
                ((ClientAok) e.entity.getExtendedProperties(ClientAok.PROP_NAME)).saveNBTData(compound);
                AgeOfKingdom.serverProxy.setPlayerClientCore(((EntityPlayer)e.entity).getUniqueID(), compound);
            }
        }
**/
    }
}
