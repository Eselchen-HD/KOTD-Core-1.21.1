package de.eselgamerhd.kotd.common.init;

public interface IEnergyContainer {
    int receiveEnergy(int maxReceive, boolean simulate);
    int extractEnergy(int maxExtract, boolean simulate);
    int getEnergyStored();
    int getMaxEnergyStored();
}
