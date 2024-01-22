package com.farsand.farsand.protocol.injector;

import java.io.DataInputStream;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

import com.farsand.farsand.protocol.events.PacketContainer;
import com.farsand.farsand.protocol.events.PacketEvent;
import org.apache.commons.lang3.ArrayUtils;


import net.minecraft.server.Packet;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class ReadPacketModifier implements MethodInterceptor {

    @SuppressWarnings("rawtypes")
    private static Class[] parameters = { DataInputStream.class };

    // Common for all packets of the same type
    private PacketInjector packetInjector;
    private int packetID;

    // Whether or not a packet has been cancelled
    private static WeakHashMap<Object, Object> override = new WeakHashMap<Object, Object>();

    public ReadPacketModifier(int packetID, PacketInjector packetInjector) {
        this.packetID = packetID;
        this.packetInjector = packetInjector;
    }

    @Override
    public Object intercept(Object thisObj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        Object returnValue = null;
        String methodName = method.getName();

        // We always pass these down (otherwise, we'll end up with a infinite loop)
        if (methodName.equals("hashCode") || methodName.equals("equals") || methodName.equals("toString")) {
            return proxy.invokeSuper(thisObj, args);
        }

        if (override.containsKey(thisObj)) {
            Object overridenObject = override.get(thisObj);

            // Cancel EVERYTHING, including "processPacket"
            if (overridenObject == null)
                return null;

            returnValue = proxy.invokeSuper(overridenObject, args);
        } else {
            returnValue = proxy.invokeSuper(thisObj, args);
        }

        // Is this a readPacketData method?
        if (returnValue == null &&
                ArrayUtils.isEquals(method.getParameterTypes(), parameters)) {

            // We need this in order to get the correct player
            DataInputStream input = (DataInputStream) args[0];

            // Let the people know
            PacketContainer container = new PacketContainer(packetID, (Packet) thisObj);
            PacketEvent event = packetInjector.packetRecieved(container, input);
            Packet result = event.getPacket().getHandle();

            // Handle override
            if (event != null) {
                if (event.isCancelled()) {
                    override.put(thisObj, null);
                } else if (!objectEquals(thisObj, result)) {
                    override.put(thisObj, result);
                }
            }
        }

        return returnValue;
    }

    private boolean objectEquals(Object a, Object b) {
        return System.identityHashCode(a) != System.identityHashCode(b);
    }
}
