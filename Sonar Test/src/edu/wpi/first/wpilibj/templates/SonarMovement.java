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
        double distance;
        double diagDist;
        double diagonalConversion = 1.414213562;
        final int maxDist = 8;
        
	public SonarMovement(Victor motorLeft, Victor motorRight, SonarStereo sonarStereo) {
		
                leftMotor = motorLeft;
		rightMotor = motorRight;
		this.sonarStereo = sonarStereo;
		
	}
	
	public void moveForward() {
            
            leftMotor.set(.265);
            rightMotor.set(-.25);
            System.out.println("Moving ahead captain");
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

//        public void turn() {
//            counter++;
//            
//            //turning right
//            if(sonarStereo.left.getFeet() > sonarStereo.right.getFeet()){
//                if (counter <= 10) {
//                    leftMotor.set(.42);
//                    rightMotor.set(-.21);
//                    System.out.println("turning right captain");
//                }
//            }
//            
//            //turning left
//            else if(sonarStereo.left.getFeet() < sonarStereo.right.getFeet()) {
//                if (counter <= 10 + delay)
//                    leftMotor.set(.21);
//                    rightMotor.set(-.42);
//                    System.out.println("turning left captain");
//          }
//            if (counter <= 11){
//                
//            leftMotor.set(.26);
//            rightMotor.set(-.25);
//      }
//  }
        public void diagDist() {
             
            System.out.println("Scanning enemy ships captain");
            
            //object is to the right
            if(sonarStereo.left.getFeet() > sonarStereo.right.getFeet()) {
                distance = sonarStereo.right.getFeet();
            }
            //object is to the left
            else if(sonarStereo.left.getFeet() < sonarStereo.right.getFeet()) {
                distance = sonarStereo.left.getFeet();
            }
            diagDist = distance * diagonalConversion;
        }
        
        public void turnLeft() { //ADD PEDOMETER STUFF FOR ACTUAL ROBOT
            
            gyro.reset();
            if(gyro.getAngle() <= 45 && gyro.getAngle() > 0) {
                leftMotor.set(.2);
                rightMotor.set(.5);
            }
        }
        
        public void turnRight() { //ADD PEDOMETER STUFF FOR ACTUAL ROBOT
            
            gyro.reset();
            if(gyro.getAngle() >= -45 && gyro.getAngle() < 0) {
                leftMotor.set(.5);
                rightMotor.set(.2);
            }
        }
        
        public void decide() {
            
            System.out.println("Enemy ships cloaked captain, scanning area");
            
            //object is to the right, turn left
            if(sonarStereo.left.getFeet() > sonarStereo.right.getFeet() && 
                    sonarStereo.left.getFeet() < maxDist && 
                    sonarStereo.left.getFeet() < maxDist) {
                turnLeft();
                System.out.println("Turning left captain");
            }
            //object is to the left, turn right
            else if(sonarStereo.left.getFeet() < sonarStereo.right.getFeet() &&
                        sonarStereo.left.getFeet() < maxDist && 
                        sonarStereo.left.getFeet() < maxDist) {             
                turnRight();
                System.out.println("Turning right captain");
            }
            //no object found within the maximum distance, go forwards
            else if(sonarStereo.left.getFeet() > 8 && sonarStereo.right.getFeet() >8) {
                moveForward();
                System.out.println("Enemy space clear captain, moving ahead");
            }
        }
        
        //DEMO FOR KIT OF PARTS CHASSIS
        public void demo() {
            
            decide();
            //method inside a method????? add stuff later lol n3rd5
        }
} 