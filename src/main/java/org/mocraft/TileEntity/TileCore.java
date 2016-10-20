package org.mocraft.TileEntity;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import org.mocraft.AgeOfKingdom;
import org.mocraft.Common.ClientAok;
import org.mocraft.Utils.BlockPos;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/11.
 */
public class TileCore extends TileEntity {

    private boolean isUsing = false;
    private UUID lord;
    private String name;
    private int aokLevel;
    private ArrayList<UUID> members = new ArrayList<UUID>();

    public TileCore() {
        this.isUsing = false;
        this.lord = new UUID(0L, 0L);
        this.name = "null";
        this.aokLevel = 0;
    }

    public void insertToClientAok(ClientAok clientAok) {
        clientAok.setLandPos(new BlockPos(xCoord, yCoord, zCoord));
        clientAok.setLordName(AgeOfKingdom.serverProxy.getPlayerByUuid(this.lord).getDisplayName());
        clientAok.setAokName(this.name);
        clientAok.setAokLevel(this.aokLevel);
        clientAok.setMembers(this.members);
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
        compound.setString("Name", name);
        compound.setInteger("AokLevel", aokLevel);
        NBTTagList memberList = new NBTTagList();
        for(UUID member : members) {
            memberList.appendTag(new NBTTagString(member.toString()));
        }
        compound.setTag("Members", memberList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.isUsing = compound.getBoolean("Using");
        this.lord = UUID.fromString(compound.getString("Lord"));
        this.name = compound.getString("Name");
        this.aokLevel = compound.getInteger("AokLevel");
        NBTTagList memberList = compound.getTagList("Members", Constants.NBT.TAG_LIST);
        for(int i = 0; i < memberList.tagCount(); ++i) {
            this.members.add(UUID.fromString(memberList.getStringTagAt(i)));
        }
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
        return isUsing;
    }

    public void setUsing(boolean using) {
        isUsing = using;
        updateEntity();
    }

    public UUID getLord() {
        return lord;
    }

    public void setLord(UUID lord) {
        this.lord = lord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAokLevel() { return this.aokLevel; }

    public void setAokLevel(int aokLevel) { this.aokLevel = aokLevel; }

    public ArrayList<UUID> getMembers() { return this.members; }

    public UUID getMembersAtIndex(int i) { return this.members.get(i); }

    public void addMembers(UUID player) { this.members.add(player); }
}
