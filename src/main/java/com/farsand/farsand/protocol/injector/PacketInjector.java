package com.farsand.farsand.protocol.injector;

import java.io.DataInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import com.farsand.farsand.protocol.events.PacketContainer;
import com.farsand.farsand.protocol.events.PacketEvent;
import com.farsand.farsand.protocol.reflect.FuzzyReflection;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.bukkit.entity.Player;

import net.minecraft.server.Packet;


/**
 * This class is responsible for adding or removing proxy objects that intercepts recieved packets.
 *
 * @author Kristian
 */
class PacketInjector {

    // The "put" method that associates a packet ID with a packet class
    private static Method putMethod;
    private static Object intHashMap;

    // The packet filter manager
    private PacketFilterManager manager;

    // Allows us to determine the sender
    private Map<DataInputStream, Player> playerLookup;

    // Class loader
    private ClassLoader classLoader;

    public PacketInjector(ClassLoader classLoader, PacketFilterManager manager,
                          Map<DataInputStream, Player> playerLookup) throws IllegalAccessException {

        this.classLoader = classLoader;
        this.manager = manager;
        this.playerLookup = playerLookup;
        initialize();
    }

    private void initialize() throws IllegalAccessException {
        if (intHashMap == null) {
            // Accessing the 'a' HashMap in Packet class
            Field intHashMapField;
            try {
                intHashMapField = Packet.class.getDeclaredField("a");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Field 'a' not found in Packet class.", e);
            }

            // Ensure the field is accessible
            intHashMapField.setAccessible(true);

            // Retrieve the 'a' HashMap value
            try {
                intHashMap = intHashMapField.get(null);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("Failed to retrieve the value of field 'a' in Packet class.", e);
            }

            // Now, find a suitable "put" method
            Method[] methods = intHashMap.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().equals("put") && method.getParameterCount() == 2) {
                    // Assuming 'put' method with two parameters is the correct one
                    putMethod = method;
                    break;
                }
            }

            // Check if a suitable 'put' method was found
            if (putMethod == null) {
                throw new RuntimeException("No suitable 'put' method found in IntHashMap class.");
            }
        }
    }


    @SuppressWarnings("rawtypes")
    public boolean addPacketHandler(int packetID) {
        if (hasPacketHandler(packetID))
            return false;

        Enhancer ex = new Enhancer();

        // Unfortunately, we can't easily distinguish between these two functions:
        //   * Object lookup(int par1)
        //   * Object removeObject(int par1)

        // So, we'll use the classMapToInt registry instead.
        Map<Integer, Class> overwritten = MinecraftRegistry.getOverwrittenPackets();
        Map<Integer, Class> previous = MinecraftRegistry.getPreviousPackets();
        Map<Class, Integer> registry = MinecraftRegistry.getPacketToID();
        Class old = MinecraftRegistry.getPacketClassFromID(packetID);

        // Check for previous injections
        if (!old.getName().startsWith("net.minecraft.")) {
            throw new IllegalStateException("Packet " + packetID + " has already been injected.");
        }

        // Subclass the specific packet class
        ex.setSuperclass(old);
        ex.setCallbackType(ReadPacketModifier.class);
        ex.setUseCache(false);
        ex.setClassLoader(classLoader);
        Class proxy = ex.createClass();

        // Add a static reference
        Enhancer.registerStaticCallbacks(proxy, new Callback[] {
                new ReadPacketModifier(packetID, this)
        });

        try {
            // Override values
            putMethod.invoke(intHashMap, packetID, proxy);
            previous.put(packetID, old);
            registry.put(proxy, packetID);
            overwritten.put(packetID, proxy);
            return true;

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Illegal argument.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access method.", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception occured in IntHashMap.put.", e);
        }
    }

    @SuppressWarnings("rawtypes")
    public boolean removePacketHandler(int packetID) {
        if (!hasPacketHandler(packetID))
            return false;

        Map<Class, Integer> registry = MinecraftRegistry.getPacketToID();
        Map<Integer, Class> previous = MinecraftRegistry.getPreviousPackets();
        Map<Integer, Class> overwritten = MinecraftRegistry.getOverwrittenPackets();

        // Use the old class definition
        try {
            Class old = previous.get(packetID);
            Class proxy = MinecraftRegistry.getPacketClassFromID(packetID);

            putMethod.invoke(intHashMap, packetID, old);
            previous.remove(packetID);
            registry.remove(proxy);
            overwritten.remove(packetID);
            return true;

            // Handle some problems
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access method.", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception occured in IntHashMap.put.", e);
        }
    }

    public boolean hasPacketHandler(int packetID) {
        return MinecraftRegistry.getPreviousPackets().containsKey(packetID);
    }

    public Set<Integer> getPacketHandlers() {
        return MinecraftRegistry.getPreviousPackets().keySet();
    }

    // Called from the ReadPacketModified monitor
    PacketEvent packetRecieved(PacketContainer packet, DataInputStream input) {

        Player client = playerLookup.get(input);
        PacketEvent event = PacketEvent.fromClient((Object) manager, packet, client);

        manager.invokePacketRecieving(event);
        return event;
    }

    @SuppressWarnings("rawtypes")
    public void cleanupAll() {
        Map<Integer, Class> overwritten = MinecraftRegistry.getOverwrittenPackets();
        Map<Integer, Class> previous = MinecraftRegistry.getPreviousPackets();

        // Remove every packet handler
        for (Integer id : previous.keySet()) {
            removePacketHandler(id);
        }

        overwritten.clear();
        previous.clear();
    }
}
