/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Synopsys, Inc. - ARC GNU Toolchain support
 *******************************************************************************/

package com.arc.embeddedcdt.dsf.gdb.server;

import java.io.File;

import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.arc.embeddedcdt.common.ArcGdbServer;
import com.arc.embeddedcdt.dsf.GdbServerBackend;
import com.arc.embeddedcdt.dsf.utils.ConfigurationReader;

public class AshlingBackend extends GdbServerBackend {

    private String commandLineTemplate = "%s"
            + " --jtag-frequency %s"
            + " --device %s"
            + " --gdb-port %s";

    public AshlingBackend(DsfSession session, ILaunchConfiguration launchConfiguration) {
        super(session, launchConfiguration);
    }

    @Override
    public String getCommandLine() {

        ConfigurationReader cfgReader = new ConfigurationReader(launchConfiguration);
        String ashlingPath = cfgReader.getAshlingPath();
        String gdbServerPort = cfgReader.getGdbServerPort();
        String jtagFrequency = cfgReader.getAshlingJtagFrequency();
        String device = cfgReader.getAshlingDevice();
        if (device == null || device.isEmpty()) {
            device = "arc";
        }
        String probeSerialNumber = cfgReader.getAshlingProbeSerialNumber();
        String gdbServerArgs = cfgReader.getAshlingGdbServerArgs();

        String commandLine = String.format(commandLineTemplate, ashlingPath, jtagFrequency,
                device, gdbServerPort);
        if (cfgReader.getGdbServer() == ArcGdbServer.JTAG_ASHLING_OPELLAXD) {
            commandLine += " --probe-type opella-xd";
        } else if (cfgReader.getGdbServer() == ArcGdbServer.JTAG_ASHLING_VITRAXS) {
            commandLine += " --probe-type vitra-xs";
        }
        if (probeSerialNumber != null && !probeSerialNumber.isEmpty()) {
            commandLine += " --instance " + probeSerialNumber;
        }
        if (gdbServerArgs != null && !gdbServerArgs.isEmpty()) {
            commandLine += " " + gdbServerArgs;
        }
        return commandLine;
    }

    @Override
    public String getProcessLabel() {
        return "Ashling GDBserver";
    }

    @Override
    public File getWorkingDirectory() {
        ConfigurationReader cfgReader = new ConfigurationReader(launchConfiguration);
        return new File(cfgReader.getAshlingPath()).getParentFile();
    }

}
