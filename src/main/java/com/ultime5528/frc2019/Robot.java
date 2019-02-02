/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import com.ultime5528.frc2019.subsystems.RouleauCargo;
import com.ultime5528.frc2019.commands.LancerBallon;
import com.ultime5528.frc2019.subsystems.BasePilotable;
import com.ultime5528.frc2019.subsystems.Hatch;
import com.ultime5528.frc2019.subsystems.Elevateur;
import com.ultime5528.frc2019.subsystems.Lanceur;
import com.ultime5528.frc2019.subsystems.Grimpeur;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import badlog.lib.BadLog;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI oi;
  public static BasePilotable basePilotable;
  public static RouleauCargo rouleauCargo;
  public static Lanceur lanceur;
  public static Hatch hatch;
  public static PowerDistributionPanel pdp;
  public static Elevateur elevateur;
  public static Grimpeur grimpeur;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  private BadLog log;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    log = BadLog.init("media/sda1/BadLog/test.bag");
    
    BadLog.createValue("Match number", "" + DriverStation.getInstance().getMatchNumber());
   
    basePilotable = new BasePilotable();
    rouleauCargo = new RouleauCargo();
    lanceur = new Lanceur();
    hatch = new Hatch();
    elevateur = new Elevateur();
    grimpeur = new Grimpeur();

    SmartDashboard.putData("Auto mode", m_chooser);
    pdp = new PowerDistributionPanel();
    

    oi = new OI();

    log.finishInitialization();
  }

  @Override
  public void robotPeriodic() {
    log.updateTopics();
    log.log();
  }


  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }


  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public static void afficherErreur(String erreur) {
    DriverStation.reportError("**********" + erreur.toUpperCase() + "**********", false);


  }

}

