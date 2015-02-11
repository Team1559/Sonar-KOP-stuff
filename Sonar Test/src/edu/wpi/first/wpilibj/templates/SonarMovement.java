package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;

public class SonarMovement {
    
    Victor leftMotor;
    Victor rightMotor;
    int counter = 0;
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
    int timer;
    
    public SonarMovement(Victor motorLeft, Victor motorRight, SonarStereo sonarStereo, Gyro gyro) {	
       
        leftMotor = motorLeft;
	rightMotor = motorRight;
	this.sonarStereo = sonarStereo;
        this.gyro = gyro;		
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

    public void diagDist() {
         
        //System.out.println("Scanning enemy ships captain");
        
        //object is to the right
        if(sonarStereo.left.getFeet() > sonarStereo.right.getFeet())
        distance = sonarStereo.right.getFeet();
        //object is to the left
        else if(sonarStereo.left.getFeet() < sonarStereo.right.getFeet())
        distance = sonarStereo.left.getFeet();
        diagDist = distance * diagonalConversion;
    }
 
    //for use when returns LEFT
    public void leftSequence() {
      if (decide = true) {
        if(decide() == LEFT) {
        switch(counter) {
        case 0:
            decide = false;
            turnLeft(leftMotor, rightMotor, gyro, 45);
            if(turnLeft(leftMotor, rightMotor, gyro, 45) == true)
                counter =1;
            break;
        case 1:
            decide = false;
            turnRight(leftMotor, rightMotor, gyro, 90);
            if(turnRight(leftMotor, rightMotor, gyro, 90) == true)
                counter = 2;
            break;
        case 2:
            moveForward();
            timer++;
            if (timer == 75)
                counter = 3;
            break;
        case 3:
            decide = false;
            turnLeft(leftMotor, rightMotor, gyro, 45);
            if(turnLeft(leftMotor, rightMotor, gyro, 45) == true)
                counter = 4;
            break;
        case 4:
            demo();
            break;
        default:
            break;
        }
          }
        }
    }
    
    //for use when returns RIGHT
    public void rightSequence() {
        if(decide = true) {
         if(decide() == RIGHT) {
        switch(counter) {
        case 0:
            decide = false;
            turnRight(leftMotor, rightMotor, gyro, 45);
            if(turnRight(leftMotor, rightMotor, gyro, 45) == true)
                counter = 1;
            break;
        case 1:
            decide = false;
            turnLeft(leftMotor, rightMotor, gyro, 90);
            if(turnLeft(leftMotor, rightMotor, gyro, 90) == true)
                counter = 2;
            break;
        case 2:
            moveForward();
            timer++;
            if(timer == 75)
                counter = 3;
             
            break;
        case 3:
            decide = false;
            turnRight(leftMotor, rightMotor, gyro, 45);
            if(turnRight(leftMotor, rightMotor, gyro, 45) == true)
                counter = 4;
            break;
        case 4:
            demo();
            break;
        default:
            break;
        }
        System.out.println("Counter: " + counter);
        }
      }
    }
    
    public boolean turnLeft(Victor left, Victor right, Gyro g, int angle) {
        g.reset();
        left.set(-.4);
        right.set(-.6);
        return g.getAngle() <= -angle;
    }
    
    public boolean turnRight(Victor left, Victor right, Gyro g, int angle) {
        g.reset();
        left.set(.4);
        right.set(.6);
        return g.getAngle() >= angle;
    }

    //for use when returns CLEAR
    public void clear() {
        if(decide() == CLEAR) {
        moveForward();
        }
    }
    
    public int decide() {
        
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
           System.out.println("STRANGER DANGER");
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
                leftSequence();
                rightSequence();
                counter2 = 0;
                clear();
            }
            break;
        }
    }
}