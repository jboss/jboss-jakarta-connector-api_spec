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

package javax.resource.spi.work;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.lang.Object;
import java.lang.Runnable;
import java.lang.Exception;
import java.lang.Throwable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.EventObject;

/**
 * This class models the various events that occur during the processing of
 * a <code>Work</code> instance.
 *
 * @version 1.0
 * @author  Ram Jeyaraman
 */
public class WorkEvent extends EventObject {
    /** Serial version uid */
    private static final long serialVersionUID;

    /** Persistence fields information */
    private static final ObjectStreamField[] serialPersistentFields;
    private static final int TYPE_IDX = 0;
    private static final int WORK_IDX = 1;
    private static final int EXCPEPTION_IDX = 2;
    private static final int DURATION_IDX = 2;

    /**
     * Indicates <code>Work</code> instance has been accepted.
     */
    public static final int WORK_ACCEPTED = 1;

    /**
     * Indicates <code>Work</code> instance has been rejected.
     */
    public static final int WORK_REJECTED = 2;

    /**
     * Indicates <code>Work</code> instance has started execution.
     */
    public static final int WORK_STARTED = 3;

    /**
     * Indicates <code>Work</code> instance has completed execution.
     */
    public static final int WORK_COMPLETED = 4;

    /**
     * The event type.
     */
    private int type;

    /**
     * The <code>Work</code> object on which the event occured.
     */
    private Work work;

    /**
     * The exception that occured during <code>Work</code> processing.
     */
    private WorkException exc;

    /**
     * The start delay duration (in milliseconds).
     */
    private long startDuration = WorkManager.UNKNOWN;

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
            serialVersionUID = 6971276136970053051L;
            serialPersistentFields = new ObjectStreamField[]{
                    /** @serialField type int */
                    new ObjectStreamField("type", int.class),
                    /** @serialField work Work */
                    new ObjectStreamField("work", Work.class),
                    /** @serialField exception WorkException */
                    new ObjectStreamField("e", WorkException.class),
                    /** @serialField startDuration long */
                    new ObjectStreamField("startDuration", long.class)
            };
        } else {
            serialVersionUID = -3063612635015047218L;
            serialPersistentFields = new ObjectStreamField[]{
                    /** @serialField type int */
                    new ObjectStreamField("type", int.class),
                    /** @serialField work Work */
                    new ObjectStreamField("work", Work.class),
                    /** @serialField exception WorkException */
                    new ObjectStreamField("exception", WorkException.class),
                    /** @serialField startDuration long */
                    new ObjectStreamField("startDuration", long.class)
            };
        }
    }


    /**
     * Constructor.
     *
     * @param source The object on which the event initially 
     * occurred.
     *
     * @param type The event type.
     *
     * @param work The <code>Work</code> object on which 
     * the event occured.
     *
     * @param exc The exception that occured during 
     * <code>Work</code> processing.

    */
    public WorkEvent(Object source, int type, Work work, WorkException exc) {
	super(source);
	this.type = type;
	this.work =  work;
	this.exc = exc;
    }

    /**
     * Constructor.
     *
     * @param source The object on which the event initially 
     * occurred.
     *
     * @param type The event type.
     *
     * @param work The <code>Work</code> object on which 
     * the event occured.
     *
     * @param exc The exception that occured during 
     * <code>Work</code> processing.
     *
     * @param startDuration The start delay duration 
     * (in milliseconds).
     */
    public WorkEvent(Object source, int type, Work work, WorkException exc,
            long startDuration) {
	this(source, type, work, exc);
	this.startDuration = startDuration;
    }

    /**
     * Return the type of this event.
     *
     * @return the event type.
     */
    public int getType() { return this.type; }

    /**
     * Return the <code>Work</code> instance which is the cause of the event.
     *
     * @return the <code>Work</code> instance.
     */
    public Work getWork() { return this.work; }

    /**
     * Return the start interval duration.
     *
     * @return the time elapsed (in milliseconds) since the <code>Work</code>
     * was accepted, until the <code>Work</code> execution started. Note, 
     * this does not offer real-time guarantees. It is valid to return -1, if
     * the actual start interval duration is unknown.
     */
    public long getStartDuration() { return this.startDuration; }

    /**
     * Return the <code>WorkException</code>. The actual 
     * <code>WorkException</code> subtype returned depends on the type of the
     * event.
     *
     * @return a <code>WorkRejectedException</code> or a 
     * <code>WorkCompletedException</code>, if any.
     */
    public WorkException getException() { return this.exc; }

    /**
     * Read object
     *
     * @param ois The object input stream
     * @throws ClassNotFoundException If a class can not be found
     * @throws IOException            Thrown if an error occurs
     */
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ObjectInputStream.GetField fields = ois.readFields();
        String name = serialPersistentFields[TYPE_IDX].getName();
        this.type = fields.get(name, 0);
        name = serialPersistentFields[WORK_IDX].getName();
        this.work = (Work) fields.get(name, null);
        name = serialPersistentFields[EXCPEPTION_IDX].getName();
        this.exc = (WorkException) fields.get(name, null);
        name = serialPersistentFields[DURATION_IDX].getName();
        this.startDuration = fields.get(name, 0L);
    }

    /**
     * Write object
     *
     * @param oos The object output stream
     * @throws IOException Thrown if an error occurs
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        ObjectOutputStream.PutField fields = oos.putFields();
        String name = serialPersistentFields[TYPE_IDX].getName();
        fields.put(name, type);
        name = serialPersistentFields[WORK_IDX].getName();
        fields.put(name, work);
        name = serialPersistentFields[EXCPEPTION_IDX].getName();
        fields.put(name, exc);
        name = serialPersistentFields[DURATION_IDX].getName();
        fields.put(name, startDuration);
        oos.writeFields();
    }
}
