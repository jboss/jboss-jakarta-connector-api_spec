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

package javax.resource;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A <code>NotSupportedException</code> is thrown to indicate that 
 * callee (resource adapter
 * or application server for system contracts) cannot execute an operation 
 * because the operation is not a supported feature. For example, if the 
 * transaction support level for a resource adapter is 
 * <code>NO_TRANSACTION</code>, an invocation of <code>getXAResource</code> 
 * method on a <code>ManagedConnection</code> object should throw a
 * <code>NotSupportedException</code> exception.
 * 
 * @version 1.0
 * @author Rahul Sharma
 * @author Ram Jeyaraman
 */

public class NotSupportedException extends javax.resource.ResourceException {

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
            serialVersionUID = 1510031578141681454L;
        } else {
            serialVersionUID = -5433838894743521833L;
        }
    }

    /**
     * Constructs a new instance with null as its detail message.
     */
    public NotSupportedException() { super(); }

    /**
     * Constructs a new instance with the specified detail message.
     *
     * @param message the detail message.
     */
    public NotSupportedException(String message) {
	super(message);
    }

    /**
     * Constructs a new throwable with the specified cause.
     *
     * @param cause a chained exception of type <code>Throwable</code>.
     */
    public NotSupportedException(Throwable cause) {
	super(cause);
    }

    /**
     * Constructs a new throwable with the specified detail message and cause.
     *
     * @param message the detail message.
     *
     * @param cause a chained exception of type <code>Throwable</code>.
     */
    public NotSupportedException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Constructs a new throwable with the specified detail message and
     * error code.
     *
     * @param message a description of the exception.
     * @param errorCode a string specifying the vendor specific error code.
     */
    public NotSupportedException(String message, String errorCode) {
	super(message, errorCode);
    }
}
