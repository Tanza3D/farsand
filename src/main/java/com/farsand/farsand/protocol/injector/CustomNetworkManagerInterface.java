package com.farsand.farsand.protocol.injector;

import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

public interface CustomNetworkManagerInterface {
    void a(NetHandler paramNetHandler);

    void a(Packet paramPacket);

    // Add other methods from NetworkManager as needed
}

