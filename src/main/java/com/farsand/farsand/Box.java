package com.farsand.farsand;

public class Box {
    private Vector3 pos1;
    private Vector3 pos2;

    public Box(Vector3 pos1, Vector3 pos2) {
        this.pos1 = new Vector3(pos1.x, pos1.y, pos1.z);
        this.pos2 = new Vector3(pos2.x, pos2.y, pos2.z);
    }

    public boolean isInside(Vector3 point) {
        double minX = Math.min(pos1.x, pos2.x);
        double minY = Math.min(pos1.y, pos2.y);
        double minZ = Math.min(pos1.z, pos2.z);

        double maxX = Math.max(pos1.x, pos2.x);
        double maxY = Math.max(pos1.y, pos2.y);
        double maxZ = Math.max(pos1.z, pos2.z);

        return point.x >= minX && point.x <= maxX &&
                point.y >= minY && point.y <= maxY &&
                point.z >= minZ && point.z <= maxZ;
    }
}
