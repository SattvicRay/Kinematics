package Kinematics;

import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;
import java.util.Scanner;

public class ProjectileMotionDriver 
{
	private static Projectile projectile;
	private static Force force;
	private static World world;
	
	private static double mass = 20; //kg
	
	private static double gForce = 9.8; // m/s^2
	
	private static double totalTime = 50; // s
	private static double increment = 0.01; //s 
	
	private static Scanner scan;
	
	public static void main (String[] args)
	{
		Simulation();
	}
	
	public enum Air{AIR, NOAIR};
	
	public static void Simulation()
	{
		System.out.println("Welcome to the kinematics simulation. Kinematics is simulated in 3 dimensions with gravity and air resistance");
		Scanner scan = new Scanner(System.in);
		double theta = 0;
		double initVelocity = 0;
		double airRes = 0;
		System.out.println("initial velocity? (m/s) A good value is 10");
		initVelocity = Double.parseDouble(scan.nextLine());
		System.out.println("angle with ground? (radians) A good value is 0.75");
		theta = Double.parseDouble(scan.nextLine());
		System.out.println("air resistance constant? (kg/m) A good value is 5");
		airRes = Double.parseDouble(scan.nextLine());
		
		projectile = new Projectile(new Vector(0,0,0), 
				new Vector(initVelocity*Math.cos(theta),0,initVelocity*Math.sin(theta)), 
				new Vector(0,0,0), mass);
		force = new Force(gForce,airRes,0);
		force.setGravity(projectile.getMass(), new Vector(0, 0, -1));
		world = new World(projectile, force, totalTime, increment);
		world.extrapolate(World.Print.PRINT, World.Terminate.ABOVEGROUND);
	}
}
