package com.farsand.farsand.protocol.events;


import com.farsand.farsand.NullArgumentException;
import com.farsand.farsand.protocol.injector.StructureCache;
import com.farsand.farsand.protocol.reflect.EquivalentConverter;
import com.farsand.farsand.protocol.reflect.StructureModifier;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.Packet;

/**
 * Represents a Minecraft packet indirectly.
 *
 * @author Kristian
 */
public class PacketContainer {

    protected Packet handle;
    protected int id;

    // Current structure modifier
    protected StructureModifier<Object> structureModifier;

    /**
     * Creates a packet container for a new packet.
     * @param id - ID of the packet to create.
     */
    public PacketContainer(int id) {
        this(id, StructureCache.newPacket(id));
    }

    /**
     * Creates a packet container for an existing packet.
     * @param id - ID of the given packet.
     * @param handle - contained packet.
     */
    public PacketContainer(int id, Packet handle) {
        this(id, handle, StructureCache.getStructure(id).withTarget(handle));
    }

    /**
     * Creates a packet container for an existing packet.
     * @param id - ID of the given packet.
     * @param handle - contained packet.
     * @param structure - structure modifier.
     */
    public PacketContainer(int id, Packet handle, StructureModifier<Object> structure) {
        if (handle == null)
            throw new NullArgumentException("handle");

        this.id = id;
        this.handle = handle;
        this.structureModifier = structure;
    }

    /**
     * Retrieves the underlying Minecraft packet.
     * @return Underlying Minecraft packet.
     */
    public Packet getHandle() {
        return handle;
    }

    /**
     * Retrieves the generic structure modifier for this packet.
     * @return Structure modifier.
     */
    public StructureModifier<Object> getModifier() {
        return structureModifier;
    }

    public <T> StructureModifier<T> getPrimitiveModifier(Class<T> primitiveType) {
        return structureModifier.withType(primitiveType);
    }

    public StructureModifier<ItemStack> getItemModifier() {
        // Convert from and to the Bukkit wrapper
        return structureModifier.<ItemStack>withType(net.minecraft.server.ItemStack.class, new EquivalentConverter<ItemStack>() {
            public Object getGeneric(ItemStack specific) {
                //return ((CraftItemStack) specific).getHandle();
                return null;
            }

            @Override
            public ItemStack getSpecific(Object generic) {
                return new CraftItemStack((net.minecraft.server.ItemStack) generic);
            }
        });
    }

    public StructureModifier<ItemStack[]> getItemArrayModifier() {
        // Convert to and from the Bukkit wrapper
        return structureModifier.<ItemStack[]>withType(net.minecraft.server.ItemStack[].class, new EquivalentConverter<ItemStack[]>() {
            public Object getGeneric(ItemStack[] specific) {
                net.minecraft.server.ItemStack[] result = new net.minecraft.server.ItemStack[specific.length];

                // Unwrap every item
                for (int i = 0; i < result.length; i++) {
                    //result[i] = ((CraftItemStack) specific[i]).getHandle();
                }
                return result;
            }

            @Override
            public ItemStack[] getSpecific(Object generic) {
                net.minecraft.server.ItemStack[] input = (net.minecraft.server.ItemStack[]) generic;
                ItemStack[] result = new ItemStack[input.length];

                // Add the wrapper
                for (int i = 0; i < result.length; i++) {
                    result[i] = new CraftItemStack(input[i]);
                }
                return result;
            }
        });
    }

    /**
     * Retrieves the ID of this packet.
     * @return Packet ID.
     */
    public int getID() {
        return id;
    }
}
