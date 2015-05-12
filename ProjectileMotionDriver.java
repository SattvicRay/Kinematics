package kinematics_v2;

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
	
	private static double gForce = 8.8; // m/s^2
	private static double airResistConst = 3.1; // kg/m
	private static double stringLength = 4; // m
	
	private static double charge = 10; // C
	private static Vector BField = new Vector(0,0.0,0);
	
	private static double totalTime = 50; // s
	private static double increment = 0.01; //s 
	
	private static Scanner scan;
	
	public static void main (String[] args)
	{
		//Level1(Math.PI/4);
		Level2(Math.PI/4);
		//Level3(Air.NOAIR);
		//Level3(Air.AIR);
		//Challenge();
		//TestQuest2();
		//TestQuest6();
	}
	
	public enum Air{AIR, NOAIR};
	
	public static void Level1(double angleWithHoriz)
	{
		Scanner scan = new Scanner(System.in);
		double theta = 0;
		double initVelocity = 10;
		System.out.println("initial velocity? (m/s)");
		initVelocity = Double.parseDouble(scan.nextLine());
		System.out.println("angle with ground? (radians)");
		theta = Double.parseDouble(scan.nextLine());
		projectile = new Projectile(new Vector(0,0,0), 
				new Vector(initVelocity*Math.cos(theta),0,initVelocity*Math.sin(theta)), 
				new Vector(0,0,0), mass);
		force = new Force(gForce,0,0);
		force.setGravity(projectile.getMass(), new Vector(0, 0, -1));
		world = new World(projectile, force, totalTime, increment);
		world.extrapolate(World.Print.PRINT, World.Terminate.ABOVEGROUND);
	}
	
	public static void Level2(double angleWithHoriz)
	{
		System.out.println("Welcome to the kinematics simulation. Kinematics is simulated in 3 dimensions with gravity and air resistance");
		Scanner scan = new Scanner(System.in);
		double theta = 0;
		double initVelocity = 10;
		double airRes = airResistConst;
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

	public static void Level3(Air type)
	{
		ArrayList<Double> angles = new ArrayList<Double>();
		ArrayList<Double> distances = new ArrayList<Double>();
		double angleWithHoriz = 0;
		for (int i=0; i<= 90; i++)
		{
			angleWithHoriz = i*Math.PI / 180;
			if(type == Air.AIR)
			{
				Level1(angleWithHoriz);
			}
			else if (type == Air.NOAIR)
			{
				Level2(angleWithHoriz);
			}
			angles.add(angleWithHoriz);
			distances.add(projectile.getPosition().getX());
		}
		DecimalFormat df = new DecimalFormat("#.00");		
		System.out.println("The angle for largest horizontal distance is " 
							+ df.format(angles.get(distances.indexOf(Collections.max(distances)))*180/Math.PI)
							+ " degrees.");
	}
	
	public static void Challenge()
	{
		projectile = new Projectile(new Vector(2,0,0), 
				new Vector(0,0,0), 
				new Vector(0,0,0), mass);
		force = new Force(gForce,airResistConst,0);
		force.setGravity(projectile.getMass(), new Vector(0, 0, -1));
		force.setTension(projectile.getPosition(), stringLength, projectile.getVelocity(), 
				projectile.getMass(), increment);
		world = new World(projectile, force, totalTime, increment);
		world.extrapolate(World.Print.PRINT, World.Terminate.ANYWHERE);
	}
	
	public static void TestQuest2()
	{
		projectile = new Projectile(new Vector(0,0,0), 
				new Vector(10*Math.cos(20*Math.PI/180), 0, 10*Math.sin(20*Math.PI/180)), 
				new Vector(0,0,0), mass);
		force = new Force(gForce, airResistConst, 0);
		force.setGravity(projectile.getMass(), new Vector(0,0,-1));
		world = new World(projectile, force, totalTime, increment);
		world.extrapolate(World.Print.PRINT, World.Terminate.ABOVEGROUND);
	}
	
	public static void TestQuest6()
	{
		projectile = new Projectile(new Vector(0,0,0), 
				new Vector(10*Math.cos(20*Math.PI/180), 0, 10*Math.sin(20*Math.PI/180)), 
				new Vector(0,0,0), mass);
		force = new Force(gForce, airResistConst, 0);
		force.setGravity(projectile.getMass(), new Vector(0,0,-1));
		force.setMagnetic(charge, projectile.getVelocity(), BField);
		world = new World(projectile, force, totalTime, increment);
		world.extrapolate(World.Print.PRINT, World.Terminate.ABOVEGROUND);
	}
}
