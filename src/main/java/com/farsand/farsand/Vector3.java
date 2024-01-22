package com.farsand.farsand;

import org.bukkit.Location;
import org.bukkit.World;

public class Vector3 {

    public double x;
    public double y;
    public double z; // package-private variables; nice encapsulation if you place this in a maths package of something

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public Vector3 add(Vector3 vector) {
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this; // method chaining would be very useful
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }
}