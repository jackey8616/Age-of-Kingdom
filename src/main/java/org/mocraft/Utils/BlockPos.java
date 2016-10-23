package org.mocraft.Utils;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Clode on 2016/10/17.
 */
public class BlockPos {

    private int x, y, z;

    public BlockPos(NBTTagCompound compound) {
        this.x = this.y = this.z = 0;
        loadNBTData(compound);
    }

    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean compareSquareRange(BlockPos pos, int range) {
        return Math.abs(pos.getX() - this.x) < range && Math.abs(pos.getZ() - this.z) < range;
    }

    public boolean equals(BlockPos compare) {
        if(this.x == compare.getX() && this.y == compare.getY() && this.z == compare.getZ()) {
            return true;
        }
        return false;
    }

    public String toString() { return "X= " + this.x + ", Y= " + this.y + ", Z=" + this.z; }

    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound tmp = new NBTTagCompound();
        tmp.setInteger("X", this.x);
        tmp.setInteger("Y", this.y);
        tmp.setInteger("Z", this.z);

        compound.setTag("BlockPos", tmp);
    }

    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound tmp = compound.getCompoundTag("BlockPos");

        this.x = tmp.getInteger("X");
        this.y = tmp.getInteger("Y");
        this.z = tmp.getInteger("Z");
    }

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public int getZ() { return z; }

    public void setZ(int z) { this.z = z; }

}
