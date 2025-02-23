// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * NativeStyleServiceInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Mar 02, 2009 (07:08:06 PST) WSDL2Java emitter.
 */

package com.google.api.ads.admanager.axis.v202208;

public interface NativeStyleServiceInterface extends java.rmi.Remote {

    /**
     * Creates new {@link NativeStyle} objects.
     */
    public com.google.api.ads.admanager.axis.v202208.NativeStyle[] createNativeStyles(com.google.api.ads.admanager.axis.v202208.NativeStyle[] nativeStyles) throws java.rmi.RemoteException, com.google.api.ads.admanager.axis.v202208.ApiException;

    /**
     * Gets a {@link NativeStylePage NativeStylePage} of {@link NativeStyle}
     * objects that satisfy
     *         the given {@link Statement}. The following fields are supported
     * for filtering:
     *         
     *         <table>
     *         <tr>
     *         <th scope="col">PQL Property</th>
     *         <th scope="col">Object Property</th>
     *         </tr>
     *         <tr>
     *         <td>{@code id}</td>
     *         <td>{@link NativeStyle#id}</td>
     *         </tr>
     *         <tr>
     *         <td>{@code name}</td>
     *         <td>{@link NativeStyle#name}</td>
     *         </tr>
     *         </table>
     */
    public com.google.api.ads.admanager.axis.v202208.NativeStylePage getNativeStylesByStatement(com.google.api.ads.admanager.axis.v202208.Statement filterStatement) throws java.rmi.RemoteException, com.google.api.ads.admanager.axis.v202208.ApiException;

    /**
     * Performs actions on {@link NativeStyle native styles} that
     * match the given {@link Statement}.
     */
    public com.google.api.ads.admanager.axis.v202208.UpdateResult performNativeStyleAction(com.google.api.ads.admanager.axis.v202208.NativeStyleAction nativeStyleAction, com.google.api.ads.admanager.axis.v202208.Statement filterStatement) throws java.rmi.RemoteException, com.google.api.ads.admanager.axis.v202208.ApiException;

    /**
     * Updates the specified {@link NativeStyle} objects.
     */
    public com.google.api.ads.admanager.axis.v202208.NativeStyle[] updateNativeStyles(com.google.api.ads.admanager.axis.v202208.NativeStyle[] nativeStyles) throws java.rmi.RemoteException, com.google.api.ads.admanager.axis.v202208.ApiException;
}
