package Kinematics;

import java.text.DecimalFormat;

/**
 * The Projectile class creates a projectile and keeps track of its position, velocity, acceleration, and mass.
 * @author Sattvic Ray
 *
 */
public class Projectile 
{
	private static Vector position;
	private static Vector velocity;
	private static Vector acceleration;
	private static double mass; 
	
	/**
	 * 
	 * @param position the initial position
	 * @param velocity the initial velocity
	 * @param acceleration the initial acceleration
	 * @param mass the mass
	 */
	public Projectile(Vector position, Vector velocity, Vector acceleration, double mass)
	{
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;
	}


	public void increment(double time)
	{
		velocity = velocity.add(acceleration.scale(time));
		position = position.add(velocity.scale(time));
	}
	

	public Vector getPosition()
	{
		return position;
	}
	
	public Vector getVelocity()
	{
		return velocity;
	}
	
	public Vector getAccel()
	{
		return acceleration;
	}
	
	public void setAccel(Vector accel)
	{
		acceleration = accel;
	}
	
	public double getMass()
	{
		return mass;
	}
	
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("#.00");
		return (position.toString() + "\t" 
		+ velocity.toString() + df.format(velocity.magnitude()) + "\t" 
		+ "\t" + acceleration.toString() + df.format(acceleration.magnitude()));
	}
}
