public class Vector {
    private float x;
    private float y;

    public Vector() {
        x = 0;
        y = 0;
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector normalize() {
        float length = (float) Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
        return this;
    }

    public static float getDistance(Vector a, Vector b) {
        float deltaX = a.getX() - b.getX();
        float deltaY = a.getY() - b.getY();
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public static Vector subtract(Vector a, Vector b) {
        float x = a.getX() - b.getX();
        float y = a.getY() - b.getY();
        return new Vector(x, y);
    }
}
