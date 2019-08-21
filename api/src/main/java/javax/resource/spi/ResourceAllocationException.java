/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javax.resource.spi;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A <code>ResourceAllocationException</code> can be thrown by an 
 * application server or
 * resource adapter to indicate any failure to allocate system resources 
 * (example: threads, physical connections). An example is error condition 
 * when an upper bound is reached on the maximum number of physical 
 * connections that can be managed by an application server specific 
 * connection pool.
 *
 * @version 1.0
 * @author Rahul Sharma
 * @author Ram Jeyaraman
 */

public class ResourceAllocationException 
        extends javax.resource.ResourceException {

    /**
     * Serial version uid
     */
    private static final long serialVersionUID;

    static {
        Boolean legacy = (Boolean) AccessController.doPrivileged(new PrivilegedAction() {
            public Boolean run() {
                try {
                    if (System.getProperty("org.jboss.j2ee.LegacySerialization") != null)
                        return Boolean.TRUE;
                } catch (Throwable ignored) {
                    // Ignore
                }
                return Boolean.FALSE;
            }
        });

        if (Boolean.TRUE.equals(legacy)) {
            serialVersionUID = -2680085755660844424L;
        } else {
            serialVersionUID = -9036793565852998502L;
        }
    }

    /**
     * Constructs a new instance with null as its detail message.
     */
    public ResourceAllocationException() { super(); }

    /**
     * Constructs a new instance with the specified detail message.
     *
     * @param message the detail message.
     */
    public ResourceAllocationException(String message) {
	super(message);
    }

    /**
     * Constructs a new throwable with the specified cause.
     *
     * @param cause a chained exception of type <code>Throwable</code>.
     */
    public ResourceAllocationException(Throwable cause) {
	super(cause);
    }

    /**
     * Constructs a new throwable with the specified detail message and cause.
     *
     * @param message the detail message.
     *
     * @param cause a chained exception of type <code>Throwable</code>.
     */
    public ResourceAllocationException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Constructs a new throwable with the specified detail message and
     * an error code.
     *
     * @param message a description of the exception.
     * @param errorCode a string specifying the vendor specific error code.
     */
    public ResourceAllocationException(String message, String errorCode) {
	super(message, errorCode);
    }
}
