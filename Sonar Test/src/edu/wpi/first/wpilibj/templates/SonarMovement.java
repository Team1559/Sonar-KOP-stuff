package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;

public class SonarMovement {
    
    Victor leftMotor, rightMotor;
    final int MAXDISTANCE = 4;
    final int NO = 0, LEFT = 1, RIGHT = 2;
    SonarStereo sonarStereo;
    Gyro gyro;
    int turnStage;
    int timer;
    final int TIME_LIMIT = 100;
    boolean decisionMade = false;
    
    public SonarMovement(Victor left, Victor right, SonarStereo sonarStereo, Gyro gyro) {
        leftMotor = left;
        rightMotor = right;
        this.gyro = gyro;
        this.sonarStereo = sonarStereo;
        turnStage = 0;
        timer = 0;
    }

    public double gyroReading() {
        return gyro.getAngle();
    }

//    public void stopSonar() {
//        if(sonarStereo.left.getFeet() <= 5 && sonarStereo.right.getFeet() <= 5) {
//            leftMotor.set(0);
//            rightMotor.set(0);
//        } else {
//            moveForward();
//            System.out.println("l: " + sonarStereo.left.getFeet());
//            System.out.println("r: " + sonarStereo.right.getFeet());
//        }
//    }

    public boolean isDetecting() {
        return sonarStereo.right.getFeet() < MAXDISTANCE || sonarStereo.left.getFeet() < MAXDISTANCE;
    }
    
    public int decide() {
        if (isDetecting()) {
            if (sonarStereo.right.getFeet() < sonarStereo.left.getFeet()) {
                System.out.println("Left!");
                decisionMade = true;
                return LEFT;
            } else {
                System.out.println("Right!");
                decisionMade = true;
                return RIGHT;
            }
        } else {
            return NO;
        }
    }
    
    public void react(int decision) {
        switch (decision) {
        case NO:
            moveForward();
            break;
        case LEFT:
            leftSequence();
            break;
        case RIGHT:
            rightSequence();
            break;
        default:
            moveForward();
            break;
        }
    }
    
    public void leftSequence() {
        // change this when right is working
        rightSequence();
    }
    
    public void rightSequence() {
        System.out.println(gyroReading());
        switch(turnStage) {
            case 0:
                System.out.println("Stage 0: Turn outward"); // Gyro-based
                turnRight();
                if(gyro.getAngle() >= 35) // KOP robot is a n3rd. Change to 45 in actual code!
                    turnStage = 1;
                break;
            case 1:
                System.out.println("Stage 1: Forward"); // Time-based
                timer++;
//              System.out.println("Timer: " + timer);
                if(timer <= TIME_LIMIT) {
                    moveForward();
                } else {
                    turnStage = 2;
                    timer = 0;
                }
                break;
            case 2:
                System.out.println("Stage 2: Turn back"); // Gyro-based
                turnLeft();
                if(gyro.getAngle() <= -30) // Change to 90
                    turnStage = 3;
                break;
            case 3:
                System.out.println("Stage 3: Forward again"); // Time-based
                timer++;
                if(timer <= TIME_LIMIT) {
                    moveForward();
                } else {
                    turnStage = 4;
                    timer = 0;
                }
                break;
            case 4:
                System.out.println("Stage 4: Re-adjust"); // Gyro-based
                turnRight();
                if(gyro.getAngle() >= 0) // Change to 45
                    turnStage = 5;
                break;
            case 5:
                System.out.println("Stage 5: Preparing for another obstacle");
                timer = 0;
                decisionMade = false;
                decide();
            default:
                break;
        }
    }
    
    public void moveForward() {
        setLeft(.265);
        setRight(.25);
    }

    public void turnLeft() {
        setLeft(-.30);
        setRight(.40);
    }
    
    public void turnRight() {
        setLeft(.40);
        setRight(-.20);
    }
    
    public void setLeft(double speed) {
        leftMotor.set(speed);
    }
    
    public void setRight(double speed) {
        rightMotor.set(-speed);
    }
}