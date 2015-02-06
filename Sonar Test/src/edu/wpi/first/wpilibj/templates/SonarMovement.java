package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;

public class SonarMovement {
        
	Victor leftMotor;
	Victor rightMotor;
	int counter = 0;
	final int delay = 5;
	SonarStereo sonarStereo;
        Gyro gyro;
        
	public SonarMovement(Victor motorLeft, Victor motorRight, SonarStereo sonarStereo) {
		leftMotor = motorLeft;
		rightMotor = motorRight;
		this.sonarStereo = sonarStereo;
		
	}
	
	public void moveForward() {
            leftMotor.set(.265);
            rightMotor.set(-.25);
	}
	public void stop() {
		
		if(sonarStereo.left.getFeet() < 5 && sonarStereo.right.getFeet() < 5) {
			leftMotor.set(0);
			rightMotor.set(0);
		} else {
			moveForward();
		}
	}
	
	public void stopSonar() {
	
		if(sonarStereo.left.getFeet() <= 5 && sonarStereo.right.getFeet() <= 5) {
			leftMotor.set(0);
			rightMotor.set(0);
		} else {
			moveForward();
                        System.out.println("l: " + sonarStereo.left.getFeet());
                        System.out.println("r: " + sonarStereo.right.getFeet());
		}
	}

        public void turn() {
            counter++;
            
            //turning right
            if(sonarStereo.left.getFeet() > sonarStereo.right.getFeet()){
                if (counter <= 10) {
                    leftMotor.set(.42);
                    rightMotor.set(-.21);
                    System.out.println("turning right captain");
                }
            }
            
            //turning left
            else if(sonarStereo.left.getFeet() < sonarStereo.right.getFeet()) {
                if (counter <= 10 + delay)
                    leftMotor.set(.21);
                    rightMotor.set(-.42);
                    System.out.println("turning left captain");
        }
            if (counter <= 11){
                
            leftMotor.set(.26);
            rightMotor.set(-.25);
    }
  }
}
