package com.ciii.bobmu.ogllearn.utils;

public class Geometry {
    public static class Point {
        public final float x, y, z;

        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point translateY(float distance) {
            return new Point(x, y + distance, z);
        }

        public Point translate(Vector vector) {
            return new Point(
                    x + vector.x,
                    y + vector.y,
                    y + vector.z
            );
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    public static class Circle {
        public final Point center;
        public final float radius;

        public Circle(Point center, float radius) {
            this.center = center;
            this.radius = radius;
        }

        public Circle scale(float scale) {
            return new Circle(center, radius * scale);
        }

        @Override
        public String toString() {
            return "Circle{" +
                    "center=" + center +
                    ", radius=" + radius +
                    '}';
        }
    }

    public static class Cylinder {
        public final Point center;
        public final float radius;
        public final float height;

        public Cylinder(Point center, float radius, float height) {
            this.center = center;
            this.radius = radius;
            this.height = height;
        }

        @Override
        public String toString() {
            return "Cylinder{" +
                    "center=" + center +
                    ", radius=" + radius +
                    ", height=" + height +
                    '}';
        }
    }

    public static class Vector {
        public final float x, y, z;

        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "Vector{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }

        public float length() {
            return (float) Math.sqrt(x * x + y * y + z * z);
        }

        /**
         * 计算两个向量的交叉乘积
         * @param other
         * @return
         */
        public Vector crossProduct(Vector other) {
            return new Vector(
                    (y * other.z) - (z * other.y),
                    (z * other.x) - (x * other.z),
                    (x * other.y) - (y * other.x)
            );
        }

        /**
         * 计算两个向量的点积
         * @param other
         * @return
         */
        public float dotProduct(Vector other) {
            return x * other.x + y * other.y + z * other.z;
        }

        /**
         * 使用缩放量均匀缩放向量的每个分量
         * @param f 缩放量
         * @return
         */
        public Vector scale(float f) {
            return new Vector(
                    x * f,
                    y * f,
                    z * f
            );
        }
    }

    public static class Ray {
        public final Point point;
        public final Vector vector;

        public Ray(Point point, Vector vector) {
            this.point = point;
            this.vector = vector;
        }

        @Override
        public String toString() {
            return "Ray{" +
                    "point=" + point +
                    ", vector=" + vector +
                    '}';
        }
    }

    public static class Sphere {
        public final Point center;
        public final float radius;

        public Sphere(Point center, float radius) {
            this.center = center;
            this.radius = radius;
        }

        @Override
        public String toString() {
            return "Sphere{" +
                    "center=" + center +
                    ", radius=" + radius +
                    '}';
        }
    }

    public static class Plane {
        public final Point point;
        public final Vector normal;

        public Plane(Point point, Vector normal) {
            this.point = point;
            this.normal = normal;
        }

        @Override
        public String toString() {
            return "Plane{" +
                    "point=" + point +
                    ", normal=" + normal +
                    '}';
        }
    }

    /**
     * 根据两点确定一条向量
     * @param from
     * @param to
     * @return
     */
    public static Vector vectorBetween(Point from, Point to) {
        return new Vector(to.x - from.x, to.y - from.y, to.z - from.z);
    }

    /**
     * 射线与圆的相交测试
     *
     * @param sphere
     * @param ray
     * @return
     */
    public static boolean intersects(Sphere sphere, Ray ray) {
        return distanceBetween(sphere.center, ray) < sphere.radius;
    }

    /**
     * 射线和平面的相交测试
     * @param ray
     * @param plane
     * @return 交点
     */
    public static Point intersectionPoint(Ray ray, Plane plane) {
        Vector rayToPlaneVector = vectorBetween(ray.point, plane.point);
        float scaleFactor = rayToPlaneVector.dotProduct(plane.normal) / ray.vector.dotProduct(plane.normal);
        Point intersectionPoint = ray.point.translate(ray.vector.scale(scaleFactor));
        return intersectionPoint;
    }

    private static float distanceBetween(Point point, Ray ray) {
        Vector p1ToPoint = vectorBetween(ray.point, point);
        Vector p2ToPoint = vectorBetween(ray.point.translate(ray.vector), point);
        float areaOfTriangleTimesTwo = p1ToPoint.crossProduct(p2ToPoint).length();
        float lengthOfBase = ray.vector.length();
        float distanceFromPointToRay = areaOfTriangleTimesTwo / lengthOfBase;
        return distanceFromPointToRay;
    }


}
