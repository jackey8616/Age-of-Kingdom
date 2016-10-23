package org.mocraft.Common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Network.PacketManager;
import org.mocraft.Network.client.SyncIEEPMessage;
import org.mocraft.Utils.BlockPos;

import java.util.ArrayList;

/**
 * Created by Clode on 2016/10/11.
 */
public class ClientAok implements IExtendedEntityProperties {

    public static final String PROP_NAME = AgeOfKingdom.MODID + "_ExtendedProperties";

    // Personal Information
    private EntityPlayer player;
    private int lordLevel = 0;
    private BlockPos landPos = new BlockPos(0, 0, 0);
    // Global Data From Server
    private String lordName = "null";
    private String aokName = "null";
    private int aokLevel = 0;
    private ArrayList<String> members = new ArrayList<String>();

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

    public static final ClientAok get(EntityPlayer player) {
        if(player.getExtendedProperties(PROP_NAME) == null) {
            player.registerExtendedProperties(PROP_NAME, new ClientAok(player));
        }
        return (ClientAok) player.getExtendedProperties(PROP_NAME);
    }

    public void clearAok() {
        this.landPos = new BlockPos(0, 0, 0);
        this.lordName = "null";
        this.aokName = "null";
        this.aokLevel = 0;
        this.members.clear();
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound tmp = new NBTTagCompound();

        tmp.setInteger("LordLevel", this.lordLevel);
        this.landPos.saveNBTData(tmp);
        tmp.setString("LordName", this.lordName);
        tmp.setString("AokName", this.aokName);
        tmp.setInteger("AokLevel", this.aokLevel);
        NBTTagList list = new NBTTagList();
        for(String member : this.members) {
            list.appendTag(new NBTTagString(member));
        }
        tmp.setTag("Members", list);
        compound.setTag(PROP_NAME, tmp);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if(compound.hasKey(PROP_NAME, Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound tmp = compound.getCompoundTag(PROP_NAME);

            this.lordLevel = tmp.getInteger("LordLevel");
            this.landPos.loadNBTData(tmp);
            this.lordName = tmp.getString("LordName");
            this.aokName = tmp.getString("AokName");
            this.aokLevel = tmp.getInteger("AokLevel");
            NBTTagList list = (NBTTagList) tmp.getTag("Members");
            ArrayList<String> tmpList = new ArrayList<String>();
            for(int i = 0; i < list.tagCount(); ++i) {
                    tmpList.add(list.getStringTagAt(i));
            }
            this.members = tmpList;
        }
    }

    @Override
    public void init(Entity entity, World world) {  }

    public int getLordLevel() { return this.lordLevel; }

    public void setLordLevel(int level) { this.lordLevel = level; }

    public BlockPos getLandPos() { return this.landPos; }

    public void setLandPos(BlockPos landPos) { this.landPos = landPos; }

    public EntityPlayer getPlayer() { return player; }

    public void setPlayer(EntityPlayer player) { this.player = player; }

    public String getLordName() { return lordName; }

    public void setLordName(String lordName) { this.lordName = lordName; }

    public String getAokName() { return aokName; }

    public void setAokName(String aokName) { this.aokName = aokName; }

    public int getAokLevel() { return aokLevel; }

    public void setAokLevel(int aokLevel) { this.aokLevel = aokLevel; }

    public ArrayList<String> getMembers() { return members; }

    public void setMembers(ArrayList<String> members) { this.members = members; }

    public void addMember(String playerName) {
        for(String search : members) {
            if(search.equals(playerName)) { return; }
        }
        members.add(playerName);
    }

    public static class Handler {

        @SubscribeEvent
        public void onEntityConstruct(EntityEvent.EntityConstructing e) {
            if (e.entity instanceof EntityPlayerMP && e.entity.getExtendedProperties(PROP_NAME) == null) {
                register((EntityPlayer) e.entity);
            }
        }

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
    }
}
