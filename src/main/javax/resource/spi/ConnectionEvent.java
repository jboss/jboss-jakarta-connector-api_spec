/*
* JBoss, Home of Professional Open Source
* Copyright 2008, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/

package javax.resource.spi;

import javax.resource.ResourceException;
import java.util.EventObject;

/** The ConnectionEvent class provides information about the source of 
 *  a connection related event.A ConnectionEvent instance contains the 
 *  following information: 
 *  <UL>
 *    <LI>Type of the connection event
 *    <LI>ManagedConnection instance that generated the connection event.
 *        A ManagedConnection instance is returned from the method
 *        ConnectionEvent.getSource.
 *    <LI>Connection handle associated with the ManagedConnection instance; 
 *        required for the CONNECTION_CLOSED event and optional for the 
 *        other event types.
 *    <LI>Optionally, an exception indicating the connection related error. 
 *        Note that exception is used for CONNECTION_ERROR_OCCURRED.
 *  </UL>
 * 
 *  <p>This class defines following types of event notifications:
 *  <UL>
 *     <LI>CONNECTION_CLOSED
 *     <LI>LOCAL_TRANSACTION_STARTED
 *     <LI>LOCAL_TRANSACTION_COMMITTED
 *     <LI>LOCAL_TRANSACTION_ROLLEDBACK
 *     <LI>CONNECTION_ERROR_OCCURRED
 *  </UL>
 *
 *  @version     0.5
 *  @author      Rahul Sharma
 *  @see         javax.resource.spi.ConnectionEventListener
 */

public class ConnectionEvent extends java.util.EventObject {

  /** Event notification that an application component has closed the 
   *  connection
  **/
  public static final int CONNECTION_CLOSED = 1;

  /** Event notification that a Resource Manager Local Transaction was
   *  started on the connection
  **/
  public static final int LOCAL_TRANSACTION_STARTED = 2;

  /** Event notification that a Resource Manager Local Transaction was
   *  committed on the connection
  **/
  public static final int LOCAL_TRANSACTION_COMMITTED = 3;

  /** Event notification that a Resource Manager Local Transaction was
   *  rolled back on the connection
  **/
  public static final int LOCAL_TRANSACTION_ROLLEDBACK = 4;

  /** Event notification that an error occurred on the connection. 
   *  This event indicates that the ManagedConnection instance is
   *  now invalid and unusable.
  **/
  public static final int CONNECTION_ERROR_OCCURRED = 5;


  /** Exception associated with the <code>ConnectionEvent</code>
   *  instance.
   *
   *  @serial
  **/
  private Exception exception;

  /** Type of the event
  **/
  protected int id;

  private Object connectionHandle;    
  
  /**
   * Construct a ConnectionEvent object. Exception defaults to null.
   *
   * @param    source      ManagedConnection that is the
   *                       source of the event
   * @param    eid         type of the Connection event
   */
  public ConnectionEvent(ManagedConnection source, int eid) {
    super(source);         
    this.id = eid;
  }

  /**
   * Construct a ConnectionEvent object.
   *
   * @param    source      ManagedConnection that is the
   *                       source of the event
   * @param    exception   exception about to be thrown to the application
   * @param    eid         type of the Connection event
   */
  public ConnectionEvent(ManagedConnection source, int eid,
			 Exception exception) {
    super(source);  
    this.exception = exception;
    this.id = eid;
  }

  /**Get the connection handle associated with the Managed
   * Connection instance. Used for CONNECTION_CLOSED event.
   *
   * @return the connection handle. May be null
   */
  public Object getConnectionHandle() {
    return connectionHandle;
  }

  /**
   * Set the connection handle. Used for CONNECTION_CLOSED event
   */
  public void setConnectionHandle(Object connectionHandle) {
    this.connectionHandle = connectionHandle;
  }

 
  /**
   * Get the exception. May be null.
   *
   * @return the exception about to be thrown.
   */
  public Exception getException() {
    return exception;
  }

  /**
   * Get the type of event
   */
  public
  int getId() {
    return id;
  }
} 





