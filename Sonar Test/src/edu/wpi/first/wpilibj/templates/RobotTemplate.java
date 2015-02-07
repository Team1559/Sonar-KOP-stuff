package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Victor;

public class RobotTemplate extends IterativeRobot {

	SonarStereo ssonar = new SonarStereo(1, 2, 1);
	Victor left;
        Victor right;
	SonarMovement move;

	public void robotInit() {
            left = new Victor(2);
            right = new Victor(3);
            move = new SonarMovement(left, right, ssonar);
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
