package org.mocraft.Common;

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
import org.mocraft.Common.network.PacketManager;
import org.mocraft.Common.network.common.SyncIEEPMessage;
import org.mocraft.Utils.ICore;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/11.
 */
public class ClientCore implements IExtendedEntityProperties, ICore {

    @Override
    public UUID getLord() {
        return lord;
    }

    @Override
    public void setLord(UUID lord) {
        this.lord = lord;
    }

    @Override
    public int getLordLevel() { return this.lordLevel; }

    @Override
    public void setLordLevel(int level) { this.lordLevel = level; }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLevel() { return level; }

    @Override
    public void setLevel(int level) { this.level = level; }

    @Override
    public UUID getMembers(int i) { return members.get(i); }

    @Override
    public void addMembers(UUID player) { this.members.add(player); }

    @Override
    public void setX(int x) { this.x = x; }

    @Override
    public int getX() { return this.x; }

    @Override
    public void setY(int y) { this.y = y; }

    @Override
    public int getY() { return this.y; }

    @Override
    public void setZ(int z) { this.z = z; }

    @Override
    public int getZ() { return this.z; }

    public static final String PROP_NAME = AgeOfKingdom.MODID + "_ExtendedProperties";

    private UUID uuid;
    private UUID lord;
    private int lordLevel;
    private String name;
    private int level;
    private ArrayList<UUID> members = new ArrayList<UUID>();
    private int x, y, z;

    public ClientCore() {
        this.uuid = this.lord = new UUID(0L, 0L);
        this.lordLevel = 0;
        this.name = "null";
        this.level = -1;
        this.addMembers(new UUID(5L, 5L));
        this.x = this.y = this.z = 0;
    }

    public ClientCore(UUID uuid, UUID lord, String name, int level, UUID[] members, int x, int y, int z) {
        this.uuid = uuid;
        this.lord = lord;
        this.name = name;
        this.level = level;
        for(UUID id : members) {
            this.members.add(id);
        }
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ClientCore(NBTTagCompound compound) {
        loadNBTData(compound);
    }

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new Handler());
    }

    public static final void register(EntityPlayer player) { player.registerExtendedProperties(PROP_NAME, new ClientCore()); }
    public static final ClientCore get(EntityPlayer p) { return (ClientCore) p.getExtendedProperties(PROP_NAME); }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound tmp = new NBTTagCompound();

        tmp.setString("UUID", this.uuid.toString());
        tmp.setString("Lord", this.lord.toString());
        tmp.setInteger("LordLevel", this.lordLevel);
        tmp.setString("Name", this.name);
        tmp.setInteger("Level", this.level);
        NBTTagList memberList = new NBTTagList();
        for(UUID id : members) {
            memberList.appendTag(new NBTTagString(id.toString()));
        }
        tmp.setTag("Members", memberList);
        tmp.setInteger("X", this.x);
        tmp.setInteger("Y", this.y);
        tmp.setInteger("Z", this.z);

        compound.setTag(PROP_NAME, tmp);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if(compound.hasKey(PROP_NAME, Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound tmp = compound.getCompoundTag(PROP_NAME);

            this.uuid = UUID.fromString(tmp.getString("UUID"));
            this.lord = UUID.fromString(tmp.getString("Lord"));
            this.lordLevel = tmp.getInteger("LordLevel");
            this.name = tmp.getString("Name");
            this.level = tmp.getInteger("Level");
            NBTTagList memberList = tmp.getTagList("Members", Constants.NBT.TAG_LIST);
            for(int i = 0; i < memberList.tagCount(); ++i) {
                members.add(UUID.fromString(memberList.getStringTagAt(i)));
            }
            this.x = tmp.getInteger("X");
            this.y = tmp.getInteger("Y");
            this.z = tmp.getInteger("Z");
        }
    }

    @Override
    public void init(Entity entity, World world) {  }

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
                    ((ClientCore)e.entity.getExtendedProperties(ClientCore.PROP_NAME)).loadNBTData(AgeOfKingdom.serverProxy.getPlayerClientCore((EntityPlayer) e.entity));
                }
                PacketManager.sendTo(new SyncIEEPMessage((EntityPlayer)e.entity), (EntityPlayerMP) e.entity);
            }
        }

        @SubscribeEvent
        public void onLivingDeath(LivingDeathEvent e) {
            if(e.entity instanceof EntityPlayer) {
                NBTTagCompound compound = new NBTTagCompound();
                ((ClientCore) e.entity.getExtendedProperties(ClientCore.PROP_NAME)).saveNBTData(compound);
                AgeOfKingdom.serverProxy.setPlayerClientCore(((EntityPlayer)e.entity).getUniqueID(), compound);
            }
        }
    }
}
