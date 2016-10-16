package org.mocraft.Block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.mocraft.Common.ICore;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Clode on 2016/10/11.
 */
public class TileCore extends TileEntity implements ICore {

    private boolean isUsing = false;
    private UUID uuid;
    private UUID lord;
    private String name;
    private int level;
    private ArrayList<UUID> members = new ArrayList<UUID>();
    private int x, y, z;

    public TileCore() {
        this.isUsing = false;
        this.lord = new UUID(0L, 0L);
        this.name = "null";
        this.x = this.y = this.z = 0;
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
        compound.setInteger("X", x);
        compound.setInteger("Y", y);
        compound.setInteger("Z", z);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.isUsing = compound.getBoolean("Using");
        this.lord = UUID.fromString(compound.getString("Lord"));
        this.name = compound.getString("Name");
        this.x = compound.getInteger("X");
        this.x = compound.getInteger("Y");
        this.x = compound.getInteger("Z");
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

    @Override
    public UUID getLord() {
        return lord;
    }

    @Override
    public void setLord(UUID lord) {
        this.lord = lord;
    }

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
    public void setX(int x) {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public void setY(int y) {

    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setZ(int z) {

    }

    @Override
    public int getZ() {
        return 0;
    }
}
