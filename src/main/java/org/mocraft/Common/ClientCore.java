package org.mocraft.Common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
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

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/11.
 */
public class ClientCore implements IExtendedEntityProperties, ICore {

    public static final String PROP_NAME = AgeOfKingdom.MODID + "_ExtendedProperties";

    public UUID getLord() {
        return lord;
    }

    public void setLord(UUID lord) {
        this.lord = lord;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public UUID getMembers(int i) { return members.get(i); }

    public void addMembers(UUID player) { this.members.add(player); }

    private UUID uuid;
    private UUID lord;
    private String name;
    private int level;
    private ArrayList<UUID> members = new ArrayList<UUID>();

    public ClientCore() {
        this.uuid = this.lord = new UUID(0L, 0L);
        this.name = "null";
        this.level = 0;
        this.addMembers(new UUID(5L, 5L));
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
        tmp.setString("Name", this.name);
        tmp.setInteger("Level", this.level);
        NBTTagList memberList = new NBTTagList();
        for(UUID id : members) {
            memberList.appendTag(new NBTTagString(id.toString()));
        }
        tmp.setTag("Members", memberList);

        compound.setTag(PROP_NAME, tmp);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if(compound.hasKey(PROP_NAME, Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound tmp = compound.getCompoundTag(PROP_NAME);

            this.uuid = UUID.fromString(tmp.getString("UUID"));
            this.lord = UUID.fromString(tmp.getString("Lord"));
            this.name = tmp.getString("Name");
            this.level = tmp.getInteger("Level");
            NBTTagList memberList = tmp.getTagList("Members", Constants.NBT.TAG_LIST);
            for(int i = 0; i < memberList.tagCount(); ++i) {
                members.add(UUID.fromString(memberList.getStringTagAt(i)));
            }
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
