package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Victor;

public class RobotTemplate extends IterativeRobot {

	SonarStereo ssonar = new SonarStereo(1, 2, 3);
	Victor left;
        Victor right;
	SonarMovement move;
        Gyro gyro;

	public void robotInit() {
            left = new Victor(2);
            right = new Victor(3);
            gyro = new Gyro(1);
            move = new SonarMovement(left, right, ssonar, gyro);
	}

	public void autonomousInit() {
            
	}

	public void autonomousPeriodic() {
            ssonar.periodic();
            move.demo();
        }

	public void teleopInit() {

	}

	public void teleopPeriodic() {
            ssonar.periodic();
        }

	public void testPeriodic() {

	}

	public void disabledInit() {
            ssonar.stop();
	}

	public void disabledPeriodic() {

	}

}
