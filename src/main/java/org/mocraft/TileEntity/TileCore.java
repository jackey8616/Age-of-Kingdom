package org.mocraft.TileEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.ClientAok;
import org.mocraft.Network.client.SyncIEEPMessage;
import org.mocraft.ProxyServer;
import org.mocraft.Utils.BlockPos;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/11.
 */
public class TileCore extends TileEntity {

    private boolean isUsing = false;
    private UUID lord;
    private String lordName;
    private int lordLevel;
    private String aokName;
    private int aokLevel;
    private ArrayList<UUID> members = new ArrayList<UUID>();

    public TileCore() {
        this.isUsing = false;
        this.lord = new UUID(0L, 0L);
        this.lordName = "null";
        this.lordLevel = 0;
        this.aokName = "null";
        this.aokLevel = 0;
    }

    @SideOnly(Side.SERVER)
    public void syncToAll() {
        for(UUID member : members) {
            EntityPlayer player = AgeOfKingdom.serverProxy.getPlayerByUuid(member);
            if(player != null) {
                this.insertToClientAok(ClientAok.get(player));
                AgeOfKingdom.channel.sendTo(new SyncIEEPMessage(player), (EntityPlayerMP) player);
            }
        }
    }

    public boolean hasPlayer(UUID uuid) {
        for(UUID member : members) {
            if(member.equals(uuid)) return true;
        }
        return false;
    }

    @SideOnly(Side.SERVER)
    public void insertToClientAok(ClientAok clientAok) {
        clientAok.setLandPos(new BlockPos(xCoord, yCoord, zCoord));
        clientAok.setLordName(getLordName());
        clientAok.setLordLevel(this.lordLevel);
        clientAok.setAokName(this.aokName);
        clientAok.setAokLevel(this.aokLevel);
        clientAok.setMembers(toStringArrayList());
    }

    @Override
    public void updateEntity() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setBoolean("Using", isUsing);
        compound.setString("Lord", lord.toString());
        compound.setString("LordName", lordName);
        compound.setInteger("LordLevel", lordLevel);
        compound.setString("AokName", aokName);
        compound.setInteger("AokLevel", aokLevel);
        NBTTagList memberList = new NBTTagList();
        for (UUID member : members) {
            memberList.appendTag(new NBTTagString(member.toString()));
        }
        compound.setTag("Members", memberList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.isUsing = compound.getBoolean("Using");
        this.lord = UUID.fromString(compound.getString("Lord"));
        this.lordName = compound.getString("LordName");
        this.lordLevel = compound.getInteger("LordLevel");
        this.aokName = compound.getString("AokName");
        this.aokLevel = compound.getInteger("AokLevel");
        NBTTagList memberList = (NBTTagList) compound.getTag("Members");
        ArrayList<UUID> tmpMember = new ArrayList<UUID>();
        for(int i = 0; i < memberList.tagCount(); ++i) {
            tmpMember.add(UUID.fromString(memberList.getStringTagAt(i)));
        }
        this.members = tmpMember;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    public boolean isUsing() {
        return this.isUsing;
    }

    public void setUsing(boolean using) {
        this.isUsing = using;
        updateEntity();
    }

    public String getLordName() { return this.lordName; }

    public UUID getLord() {
        return lord;
    }

    public void setLord(UUID lord) {
        this.lord = lord;
        this.lordName = AgeOfKingdom.serverProxy.getPlayerByUuid(lord).getDisplayName();
    }

    public String getAokName() { return this.aokName; }

    public void setAokName(String name) {
        this.aokName = name;
    }

    public int getAokLevel() { return this.aokLevel; }

    public void setAokLevel(int aokLevel) { this.aokLevel = aokLevel; }

    public ArrayList<UUID> getMembers() { return this.members; }

    public void setMembers(ArrayList<UUID> members) { this.members = members; }

    public UUID getMemberUuidAtIndex(int i) { return this.members.get(i); }

    public String getMemberNameAtIndex(int i) { return AgeOfKingdom.serverProxy.getPlayerByUuid(this.members.get(i)).getDisplayName(); }

    public void addMember(UUID player) {
        for(UUID uuid : members) {
            if(uuid.equals(player)) { return; }
        }
        this.members.add(player);
    }

    public void removeMember(UUID player) {
        if(members.contains(player)) {
            members.remove(player);
        }
    }

    public ArrayList<String> toStringArrayList() {
        ArrayList<String> memberName = new ArrayList<String>();
        for(UUID member : members) {
            EntityPlayer player = AgeOfKingdom.serverProxy.getPlayerByUuid(member);
            if(player != null){
                memberName.add(player.getDisplayName());
            }
        }
        return memberName;
    }

    public int getLordLevel() { return lordLevel; }

    public void setLordLevel(int lordLevel) { this.lordLevel = lordLevel; }

}
