/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Synopsys, Inc. - ARC GNU Toolchain support
 *******************************************************************************/

package com.arc.embeddedcdt.common;

public enum ArcGdbServer {
    JTAG_OPENOCD("JTAG via OpenOCD"),
    JTAG_ASHLING_OPELLAXD("JTAG via Ashling Opella-XD"),
    JTAG_ASHLING_VITRAXS("JTAG via Ashling Vitra-XS"),
    NSIM("nSIM"),
    GENERIC_GDBSERVER("Connect to running GDB server"),
    CUSTOM_GDBSERVER("Custom GDB server");

    private final String string;
    public static final ArcGdbServer DEFAULT_GDB_SERVER = JTAG_OPENOCD;

    private ArcGdbServer(final String text) {
        this.string = text;
    }

    @Override
    public String toString() {
        return string;
    }

    public static ArcGdbServer fromString(final String string) {
        for (ArcGdbServer server : ArcGdbServer.values()) {
            if (server.toString().equals(string))
                return server;
        }
        throw new IllegalArgumentException("String does not correspond to any ARC GDB Server.");
    }
}
