package org.mocraft.TileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/11.
 */
public class TileCore extends TileEntity {

    private boolean isUsing = false;
    private UUID uuid;
    private UUID lord;
    private int lordLevel;
    private String name;
    private int level;
    private ArrayList<UUID> members = new ArrayList<UUID>();

    public TileCore() {
        this.isUsing = false;
        this.lord = new UUID(0L, 0L);
        this.name = "null";
    }

    public void onCreate(UUID lord, String name) {
        this.uuid = UUID.randomUUID();
        this.lord = lord;
        this.lordLevel = this.level = 1;
        this.name = name;
        this.members.add(lord);
        updateEntity();
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
        compound.setInteger("LordLevel", lordLevel);
        compound.setString("Name", name);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.isUsing = compound.getBoolean("Using");
        this.lord = UUID.fromString(compound.getString("Lord"));
        this.lordLevel = compound.getInteger("LordLevel");
        this.name = compound.getString("Name");
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

    public int getLordLevel() { return this.lordLevel; }

    public void setLordLevel(int level) { this.lordLevel = level; }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) { this.uuid = uuid; }

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
}
