package Kinematics;
import java.text.DecimalFormat;

/**
 * The Vector class creates vector objects and deals with their operations.
 * @author Sattvic Ray
 *
 */
public class Vector 
{
	private double x;
	private double y;
	private double z;
	
	/**
	 * 
	 * @param x x-component
	 * @param y y-component
	 * @param z z-component
	 */
	public Vector(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	

	public Vector scale(double t)
	{
		double xScale = x*t;
		double yScale = y*t;
		double zScale = z*t;
		return new Vector (xScale, yScale, zScale);
	}
	

	public Vector add(Vector v)
	{
		return new Vector(x+v.getX(), y+v.getY(), z+v.getZ());
	}
	

	public Vector subtract(Vector v)
	{
		return new Vector(x-v.getX(), y-v.getY(), z-v.getZ());
	}
	

	public double dot(Vector v)
	{
		return x * v.getX() + y * v.getY() + z * v.getZ();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public double magnitude()
	{
		return Math.pow(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2), 0.5);
	}
	
	public Vector unitVector()
	{
		return scale(1/magnitude());
	}
	
	public Vector cross(Vector v)
	{
		return new Vector(y*v.getZ() - z*v.getY(), z*v.getX() - x*v.getZ(), x*v.getY() - y*v.getX());
	}
	
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("#.00");
		return (df.format(getX()) + "\t" 
		+ df.format(getY()) + "\t"
		+ df.format(getZ()) + "\t");
	}

}
