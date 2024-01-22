package com.farsand.farsand.protocol;


import com.google.common.collect.ImmutableSet;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import com.farsand.farsand.protocol.events.PacketContainer;
import com.farsand.farsand.protocol.events.PacketListener;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;


public interface ProtocolManager  {

    /**
     * Retrieves a list of every registered packet listener.
     * @return Every registered packet listener.
     */
    public abstract ImmutableSet<PacketListener> getPacketListeners();

    /**
     * Adds a packet listener.
     * @param listener - new packet listener.
     */
    public abstract void addPacketListener(PacketListener listener);

    /**
     * Removes a given packet listener.
     * @param listener - the packet listener to remove.
     */
    public abstract void removePacketListener(PacketListener listener);

    /**
     * Send a packet to the given player.
     * @param reciever - the reciever.
     * @param packet - packet to send.
     * @throws InvocationTargetException - if an error occured when sending the packet.
     */
    public void sendServerPacket(Player reciever, PacketContainer packet)
            throws InvocationTargetException;

    /**
     * Send a packet to the given player.
     * @param reciever - the reciever.
     * @param packet - packet to send.
     * @param filters - whether or not to invoke any packet filters.
     * @throws InvocationTargetException - if an error occured when sending the packet.
     */
    public void sendServerPacket(Player reciever, PacketContainer packet, boolean filters)
            throws InvocationTargetException;

    /**
     * Simulate recieving a certain packet from a given player.
     * @param sender - the sender.
     * @param packet - the packet that was sent.
     * @throws InvocationTargetException If the reflection machinery failed.
     * @throws IllegalAccessException If the underlying method caused an error.
     */
    public void recieveClientPacket(Player sender, PacketContainer packet)
            throws IllegalAccessException, InvocationTargetException;

    /**
     * Simulate recieving a certain packet from a given player.
     * @param sender - the sender.
     * @param packet - the packet that was sent.
     * @param filters - whether or not to invoke any packet filters.
     * @throws InvocationTargetException If the reflection machinery failed.
     * @throws IllegalAccessException If the underlying method caused an error.
     */
    public void recieveClientPacket(Player sender, PacketContainer packet, boolean filters)
            throws IllegalAccessException, InvocationTargetException;

    /**
     * Constructs a new encapsulated Minecraft packet with the given ID.
     * @param id - packet ID.
     * @return New encapsulated Minecraft packet.
     */
    public PacketContainer createPacket(int id);

    /**
     * Retieves a set of every enabled packet.
     * @return Every packet filter.
     */
    public Set<Integer> getPacketFilters();

    /**
     * Determines whether or not is protocol mananger has been disabled.
     * @return TRUE if it has, FALSE otherwise.
     */
    public boolean isClosed();
}