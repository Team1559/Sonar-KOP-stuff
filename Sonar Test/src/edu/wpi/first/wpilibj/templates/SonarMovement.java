package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;

public class SonarMovement {
    
    Victor leftMotor, rightMotor;
    final int DELAY = 5;
    final int MAXDISTANCE = 4;
    final int NO = 0, LEFT = 1, RIGHT = 2;
    SonarStereo sonarStereo;
    Gyro gyro;
    double leftSave;
    double rightSave;
    int turnStage;
    int timer;
    boolean decisionMade = false;
    
    public SonarMovement(Victor motorLeft, Victor motorRight, SonarStereo sonarStereo, Gyro gyro) {
        leftMotor = motorLeft;
        rightMotor = motorRight;
        this.gyro = gyro;
        this.sonarStereo = sonarStereo;
        turnStage = 0;
        timer = 0;
    }
	
    public void moveForward() {
        leftMotor.set(.265);
        rightMotor.set(-.25);
    }
    
    public void gyroReading() {
        System.out.println(gyro.getAngle());
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

    public boolean isDetecting() {
        return sonarStereo.right.getFeet() < MAXDISTANCE && sonarStereo.left.getFeet() < MAXDISTANCE;
    }
    
    public int decide() {
        if (isDetecting()) {
            if (sonarStereo.right.getFeet() < sonarStereo.left.getFeet()) {
                System.out.println("Left!");
//                sonarStereo.stop();
                decisionMade = true;
                return LEFT;
            } else {
                System.out.println("Right!");
//                sonarStereo.stop();
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
            rightSequence();
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
        switch(turnStage) {
            case 0:
                System.out.println("Stage 0: Begin turn");
                leftMotor.set(-.25);
                rightMotor.set(-.25);
                if(gyro.getAngle() <= -30) {
                    turnStage = 1;
                }
                break;
            case 1:
                System.out.println("Stage 1: Forward after turn");
                moveForward();
                timer++;
                System.out.println(timer);
                // was 40
                if(timer >= 75) {
                    turnStage = 2;
                    gyro.reset();
                    timer = 0;
                }
                break;
            case 2:
                System.out.println("Stage 2: turn back");
                leftMotor.set(.25);
                rightMotor.set(.25);
                if(gyro.getAngle() >= 75)
                    turnStage = 3;
                    timer = 0;
                break;
            case 3:
                System.out.println("Stage 3: Forward again");
                timer++;
                //was 30 V
                if(timer >= 75) {
                    moveForward();
                }
                else {
                    turnStage = 4;
                    gyro.reset();
                    timer = 0;
                }
                break;
            case 4:
                System.out.println("Stage 4: Re-adjust");
                leftMotor.set(-.25);
                rightMotor.set(-.25);
                if(gyro.getAngle() <= -30) {
                    turnStage = 1;
                }
                break;
//            case 5:
//                System.out.println("You spin me right round baby!");
//                leftMotor.set(.6);
//                rightMotor.set(.6);
//                break;
            default:
//                turnStage = 0;
//                timer = 0;
                break;
        }
    }
    
    
    
    
    public void rightSequence() {
        switch(turnStage) {
            case 0:
                System.out.println("Stage 0: Turn right");
                leftMotor.set(.25);
                rightMotor.set(.25);
                if(gyro.getAngle() >= 30) {
                    turnStage = 1;
                }
                break;
            case 1:
                System.out.println("Stage 1: Forward");
                timer++;
                System.out.println(timer);
                if(timer <= 75) {
                    moveForward();
                }
                else{
                    turnStage = 2;
                    gyro.reset();
                    timer = 0;
                }
                break;
            case 2:
                System.out.println("Stage 2: Turn back");
                leftMotor.set(-.30);
                rightMotor.set(-.25);
                if(gyro.getAngle() <= -75)
                    turnStage = 3;
                break;
            case 3:
                System.out.println("Stage 3: Forward again");
                timer++;
                if(timer <= 75) {
                    moveForward();
                }
                else {
                    turnStage = 4;
                    gyro.reset();
                    timer = 0;
                }
                break;
            case 4:
                System.out.println("Stage 4: Re-adjust");
                if(gyro.getAngle() <= -30)
                    turnStage = 5;
                break;
//            case 5:
//                System.out.println("You spin me right round baby!");
//                leftMotor.set(.6);
//                rightMotor.set(.6);
//                break;
            default:
//                turnStage = 0;
//                timer = 0;
                break;
        }
    }
}
//    public void turnLeft() {
//        moveForward();
//        gyroSave = gyro.getAngle();
//        if (isDetecting()) {
//        if (sonarStereo.right.getFeet() < sonarStereo.left.getFeet()) {
//            //Object is closer to right
//            leftSave = sonarStereo.right.getFeet();
//            rightSave = sonarStereo.left.getFeet();
//            if (rightSave > leftSave) {
//            if (gyroSave < 45) {
//                leftMotor.set(.20);
//                rightMotor.set(.25);
//                gyroSave = gyro.getAngle();
//                System.out.println("Gyro: " + gyro.getAngle());
//                System.out.println("Big Wheel keeps on turning!");
//            } else {
//                leftMotor.set(0);
//                rightMotor.set(0);
//                System.out.println("STOP. DROP.");
//            }
//            }  
//        }
//        if (gyroSave < 55){
//            turnStage = 0;
//            turnStage++;
//            if (turnStage <= 2) {
//            leftMotor.set(.3);
//            rightMotor.set(-.3);
//            System.out.println("Basic SLIDE!");
//            } else {
//            leftMotor.set(0);
//            rightMotor.set(0);
//            System.out.println("STOP. DROP.");
//            }
//        }
//        turnStage = 10;
//        gyro.reset();
//        if (turnStage == 10) {
//            gyro.getAngle();
//            if(gyro.getAngle() > -35) {
//            leftMotor.set(-.30);
//            rightMotor.set(-.3);
//            System.out.println("Slide to the right!");
//            } else {
//            leftMotor.set(0);
//            rightMotor.set(0);
//            }
//        }
//        }
//    }
//        public void arrow (int turnStage) {
//        switch (turnStage){
//            case 0:
//                gyro.reset();
//                gyroSave = gyro.getAngle();
//                turnStage++;
//                break;
//            case 1:
//                if (gyroSave >= -45){
//                    leftMotor.set(-.3);
//                    rightMotor.set(-.3);
//                    System.out.println("Back to the basics");
//                }
//                else {
//                    leftMotor.set(0);
//                    rightMotor.set(0);
//                    System.out.println("STOP. DROP.");
//                    turnStage++;
//                }
//                break;
//            default:
//                break;
//        }
//    }
//}
//           leftMotor.set(.36);
//           rightMotor.set(-.35);
//           turnStage++;
//               if (turnStage == 15)
//               {
//                   leftMotor.set(0);
//                   rightMotor.set(0);
//               }
//          
//           gyro.reset();
//           turnStage = 0;
//           if (gyro.getAngle() >= -45) {
//           leftMotor.set(0);
//           rightMotor.set(-.75); 
//           }
//           leftMotor.set(.36);
//           rightMotor.set(-.35);
//        turnStage++; 
//            if (turnStage == 15)
//            {
//            leftMotor.set(0);
//            rightMotor.set(0);
//            gyro.reset();
//            }
//            }
//            }
//            }
//            }
//          if (gyro.getAngle() <= 45) {
//          leftMotor.set(.75);
//          rightMotor.set(0);
//          }
//          leftMotor.set(.51);
//          rightMotor.set(-.5);