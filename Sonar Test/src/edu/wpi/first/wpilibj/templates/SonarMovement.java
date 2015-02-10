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
        double diagonalConversion = Math.sqrt(2); // 1.414213562
        final int maxDist = 5;
        final int CLEAR = 0;
        final int LEFT = 1;
        final int RIGHT = 2;
        final int NOTHING = 3;
        int counter2 = 0;
        boolean decide;
        
	public SonarMovement(Victor motorLeft, Victor motorRight, SonarStereo sonarStereo, Gyro gy) {
		
                leftMotor = motorLeft;
		rightMotor = motorRight;
		this.sonarStereo = sonarStereo;
                gyro = gy;
		
	}
	
	public void moveForward() {
            
            leftMotor.set(.265);
            rightMotor.set(-.25);
            //System.out.println("Moving ahead captain");
	}
	public void stop() {
		
            leftMotor.set(0);
            rightMotor.set(0);
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
             
            //System.out.println("Scanning enemy ships captain");
            
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
        
//        public void turnLeft() { //ADD PEDOMETER STUFF FOR ACTUAL ROBOT
//            
//            gyro.reset();
//            if(gyro.getAngle() >= 315) {
//                leftMotor.set(.15);
//                rightMotor.set(-.5);
//                System.out.println("Turning left m8");
//            }
//            else if(gyro.getAngle() <= 315) {
//                moveForward();
//            }
//        }
//        
//        public void turnRight() { //ADD PEDOMETER STUFF FOR ACTUAL ROBOT
//            
//            gyro.reset();
//            if(gyro.getAngle() <= 45) {
//                leftMotor.set(.5);
//                rightMotor.set(-.15);
//                System.out.println("Turning right m8");
//            }
//            else if(gyro.getAngle() <= 45) {
//                moveForward();
//            }
//        }
        
        public void turnLeft() {
            
          if (decide = true) {
            if(decide() == LEFT) {
                switch(counter) {
                case 0:
                    decide = false;
                    System.out.println("case 0");
                    gyro.reset();
                    counter = 1;
                    break;
                case 1:
                    decide = false;
                    System.out.println("case 1");
                    leftMotor.set(.05);
                    rightMotor.set(-.5);
                    if(gyro.getAngle() <= -90) {
                        counter = 2;
                    }
                    break;
                case 2:
                    decide = false;
                    System.out.println("case 2");
                    leftMotor.set(.5);
                    rightMotor.set(-.05);
                    if(gyro.getAngle() >= 45) {
                        counter = 3;
                    }
                    break;
                case 3:
                    decide = false;
                    leftMotor.set(.05);
                    rightMotor.set(-.5);
                    if(gyro.getAngle() >= 0) {
                        demo();
                    }
                    break;
//                case 4:
//                    moveForward();
//                    break;
//                case 400:
//                    stop();
//                    break;
                }
              }
            }
        }
        
        public void turnRight() {
            
            if(decide = true) {
             if(decide() == RIGHT) {
                switch(counter) {
                case 0:
                    decide = false;
                    System.out.println("case 0");
                    sonarStereo.stop();
                    gyro.reset();
                    counter = 1;
                    System.out.println(counter);
                    break;
                case 1:
                    decide = false;
                    leftMotor.set(.5);
                    rightMotor.set(-.05);
                    System.out.println("case 1");
                    if(gyro.getAngle() >= 45) {
                        counter = 2;
                    }
                    System.out.println(counter);
                    break;
                case 2:
                    decide = false;
                    leftMotor.set(.05);
                    rightMotor.set(-.5);
                    System.out.println("case 2");
                    if(gyro.getAngle() <= -45) {
                        counter = 3;
                    }
                    System.out.println(counter);
                    break;
                case 3:
                    decide = false;
                    leftMotor.set(.5);
                    rightMotor.set(-.05);
                    if(gyro.getAngle() >= 0) {
                        demo();
                    }
                    System.out.println(counter);
                    break;
//                case 4:
//                    moveForward();
//                    break;
//                case 400:
//                    stop();
//                    break;
                }
              }
            }
        }
        
        //for use when returns CLEAR
        public void clear() {
            
            if(decide() == CLEAR) {
                moveForward();
            }
        }
        
        public int decide() {
            
            //System.out.println("Enemy ships cloaked captain, scanning area");
            
             //no object found within the maximum distance, go forwards
            if(sonarStereo.left.getFeet() > maxDist && sonarStereo.right.getFeet() > maxDist) {
                System.out.println("CLEAR");
                return CLEAR;
                //System.out.println("Enemy space clear captain, moving ahead");
            }
            
            //object is to the right, turn left
            if(sonarStereo.left.getFeet() > sonarStereo.right.getFeet() &&
                    sonarStereo.right.getFeet() < maxDist) {
                System.out.println("LEFT");
                decide = true;
                return LEFT;
                //System.out.println("Turning left captain");
            }
            //object is to the left, turn right
            else if(sonarStereo.left.getFeet() < sonarStereo.right.getFeet() &&
                        sonarStereo.left.getFeet() < maxDist) {  
                System.out.println("RIGHT");
                decide = true;
                return RIGHT;
                //System.out.println("Turning right captain");
            }
            else {
                System.out.println("Decision failed");
                return NOTHING;
            }
        }
        
        //DEMO FOR KIT OF PARTS CHASSIS
        public void demo() {
            
            switch (counter2) {
            case 0:
                moveForward();
                decide();
                counter2 = 1;
                break;
            case 1:
                if(decide() == LEFT || decide() == RIGHT || decide() == CLEAR) {
                    turnLeft(); //Added counter2 = 0 in method turnLeft
                    turnRight(); //Added counter2 = 0 in method turnRight
                    clear();
                }
                break;
            }
        }
} 