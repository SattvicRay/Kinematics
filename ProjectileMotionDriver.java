package kinematics_v2;

import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;

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
	
	
	public static void main (String[] args)
	{
		//Level1(Math.PI/4);
		//Level2(Math.PI/4);
		//Level3(Air.NOAIR);
		//Level3(Air.AIR);
		//Challenge();
		//TestQuest2();
		TestQuest6();
	}
	
	public enum Air{AIR, NOAIR};
	
	public static void Level1(double angleWithHoriz)
	{
		double initVelocity = 10;
		projectile = new Projectile(new Vector(0,0,0), 
				new Vector(initVelocity*Math.cos(angleWithHoriz),0,initVelocity*Math.sin(angleWithHoriz)), 
				new Vector(0,0,0), mass);
		force = new Force(gForce,0,0);
		force.setGravity(projectile.getMass(), new Vector(0, 0, -1));
		world = new World(projectile, force, totalTime, increment);
		world.extrapolate(World.Print.PRINT, World.Terminate.ABOVEGROUND);
	}
	
	public static void Level2(double angleWithHoriz)
	{
		double initVelocity = 10;
		projectile = new Projectile(new Vector(0,0,0), 
				new Vector(initVelocity*Math.cos(angleWithHoriz),0,initVelocity*Math.sin(angleWithHoriz)), 
				new Vector(0,0,0), mass);
		force = new Force(gForce,airResistConst,0);
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