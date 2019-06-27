/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

@Autonomous(name="test val of IMU", group="Test stuff")
public class IMUtest extends LinearOpMode {

    /* Declare OpMode members. */

    private DcMotor fld = null;
    private DcMotor frd = null;
    private DcMotor bld = null;
    private DcMotor brd = null;
    private double acc[] = new double[3];

    BNO055IMU imu;

    Orientation angles;
    Acceleration gravity;

    @Override
    public void runOpMode() {
        frd = hardwareMap.get(DcMotor.class, "frd");
        fld = hardwareMap.get(DcMotor.class, "fld");
        brd = hardwareMap.get(DcMotor.class, "brd");
        bld = hardwareMap.get(DcMotor.class, "bld");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        fld.setDirection(DcMotor.Direction.FORWARD);
        frd.setDirection(DcMotor.Direction.REVERSE);
        bld.setDirection(DcMotor.Direction.FORWARD);
        brd.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "calibrating");
        telemetry.update();

        //stop and get ready for epicness
        fld.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bld.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //run using encoders
        fld.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bld.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "done calibrating");
        telemetry.update();

        waitForStart();
        telemetry.addData("Status", "Starting");
        telemetry.update();
        doYourSTUFF();
        telemetry.addData("Status", "Complete");
        telemetry.update();
    }
    public void doYourSTUFF() {
        while (opModeIsActive()) {
            imu.startAccelerationIntegration(new Position(), new Velocity(), 250);
            angles = imu.getAngularOrientation();
            gravity = imu.getGravity();
            telemetry.addLine()
            .addData("acc", gravity);
            telemetry.addLine()
            .addData("rot", angles);
            telemetry.update();
            imu.stopAccelerationIntegration();
        }
    }
}