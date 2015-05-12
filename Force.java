package Kinematics-master;

public class Force {
		private Vector gravity;
		private Vector airResist;
		private Vector spring;
		private Vector tension;
		private Vector magnetic;
		
		private boolean hasTension;
		
		private double gForce;
		private double airResistConst;
		private double springConst;
		private double stringLength;
		
		
		/**
		 * @param g the gravitational field strength (if it doesn't apply, set to 0)
		 * @param C the air resistance constant (if it doesn't apply, set to 0)
		 * @param k the spring constant (if it doesn't apply, set to 0)
		 */
		public Force(double gForce, double airResistConst, double springConst)
		{
			gravity = new Vector(0,0,0);
			airResist = new Vector(0,0,0);
			spring = new Vector(0,0,0);
			tension = new Vector(0,0,0);
			magnetic = new Vector(0,0,0);
			
			this.gForce = gForce;
			this.airResistConst = airResistConst;
			this.springConst = springConst;	
		}
		
		public double getGForce()
		{
			return gForce;
		}
	
		public double getAirResistConst()
		{
			return airResistConst;
		}
		
		public double getSpringConst()
		{
			return springConst;
		}
		
		public void setGravity(double mass, Vector direction)
		{
			gravity = direction.unitVector().scale(mass*gForce);
		}
		
		public Vector getGravity()
		{
			return gravity;
		}
		
		
		public void setAirResistance(Vector velocity)
		{
			airResist = velocity.scale(-airResistConst*Math.abs(velocity.magnitude()));
		}
		
		public Vector getAirResistance()
		{
			return airResist;
		}
		
		public void setSpring(Vector position)
		{
			spring = position.scale(-springConst);
		}
		
		public Vector getSpring()
		{
			return spring;
		}
		
		public void setTension(Vector projPosition, double stringLength, Vector velocity, double mass, double increment)
		{
			hasTension = true;
			this.stringLength = stringLength;
			// This is when the string is not pulled tight.
			if (projPosition.magnitude()< stringLength)
			{
				tension = new Vector(0,0,0);
			}
			// This is when the string has just become tight but the velocity is not yet tangential to the radius.
			if (projPosition.magnitude() > stringLength &&
					Math.abs(velocity.dot(projPosition)) >= 0.1)
			{
				tension = projPosition.unitVector().scale(
						(-mass/increment) * velocity.dot(projPosition.unitVector()) - gravity.dot(projPosition.unitVector()));
			}
			// This is when the velocity is tangential to the radius.
			if (projPosition.magnitude() > stringLength  && 
					Math.abs(velocity.dot(projPosition)) < 0.1)
			{
				tension = projPosition.unitVector().scale(-(gravity.dot(projPosition)));
			}
		}
		
		public Vector getTension()
		{
			return tension;
		}
		
		public double getStringLength()
		{
			return stringLength;
		}
		
		public boolean hasTension()
		{
			return hasTension;
		}

		public void setMagnetic(double charge, Vector velocity, Vector BField)
		{
			magnetic = velocity.cross(BField).scale(charge);
		}
		public Vector addForces()
		{
			return gravity.add(spring).add(airResist).add(tension).add(magnetic);
		}
	}
