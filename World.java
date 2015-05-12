package kinematics_v2;
import java.awt.Dimension;
import java.text.DecimalFormat;

/**
 * The World class performs Euler's method for calculating the trajectory of a projectile under different forces.
 * @author Sattvic Ray
 *
 */
public class World 
{
	private Projectile projectile;
	private Force force;
	private double totalTime;
	private double increment;
	private DecimalFormat df;
	private Canvas canvasPos;
	private Canvas canvasVel;
	
	/**
	 * 
	 * @param projectile the projectile undergoing the motion
	 * @param force the net force that the projectile is subjected to
	 * @param totalTime the total time for extrapolating the projectile's trajectory
	 * @param increment the increment of time between which the trajectory is updated
	 */
	public World(Projectile projectile, Force force, double totalTime, double increment)
	{
		canvasPos = new Canvas("z-position vs x-position");
		canvasPos.setVisible(true);
		canvasVel = new Canvas("velocity vs time");
		canvasVel.setVisible(true);
		this.projectile = projectile;
		this.force = force;
		this.totalTime = totalTime;
		this.increment = increment;
		df = new DecimalFormat("#.00");
	}
	
	public enum Print{PRINT, NOPRINT};
	public enum Terminate{ABOVEGROUND, ANYWHERE};
	
	/**
	 * Calls the projectile's Increment method and adjusts the forces accordingly. 
	 * @param print whether or not to print the output
	 */
	public void extrapolate(Print print, Terminate above)
	{
		Dimension d = canvasPos.getSize();
		double zMax = d.getHeight();
		
		double elapsedTime = 0;
		if (print == Print.PRINT)
		{
			System.out.println("time \t x \t y \t z \t\t vx \t vy \t vz \t speed \t\t ax \t ay \t az \t m_acc");
		}
		while (elapsedTime<=totalTime)
		{
			force.setGravity(projectile.getMass(), force.getGravity());
			Vector totalForce = force.addForces();
			projectile.setAccel(totalForce.scale(1/projectile.getMass()));
			
			projectile.increment(increment);
			elapsedTime += increment;
			
			force.setAirResistance(projectile.getVelocity());
			force.setSpring(projectile.getPosition());
			force.setMagnetic(10, projectile.getVelocity(), new Vector(0,0.0,0));
			
			
			canvasPos.drawLine((int)(projectile.getPosition().getX()*75), (int)(zMax-projectile.getPosition().getZ()*75), 
					(int)(projectile.getPosition().getX()*75+1), (int)(zMax-projectile.getPosition().getZ()*75+1));
			
			canvasVel.drawLine((int)(elapsedTime*75), (int)(zMax-projectile.getVelocity().getZ()*75), 
					(int)(elapsedTime*75+1), (int)(zMax-projectile.getVelocity().getZ()*75+1));

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (force.hasTension())
			{
				force.setTension(projectile.getPosition(), force.getStringLength(), 
						projectile.getVelocity(), projectile.getMass(), increment);
			}
			if (print == Print.PRINT)
			{
				System.out.println(df.format(elapsedTime) + "\t" + projectile.toString());
			}
			if (above == Terminate.ABOVEGROUND && projectile.getPosition().getZ() < 0)
			{
				break;
			}
		}
	}
}
