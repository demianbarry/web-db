//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
// Copyright (C) 1999 - 2002, Salmon LLC
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License version 2
// as published by the Free Software Foundation;
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
// ====================================================================
//
// The Apache Software License, Version 1.1
//
// Copyright (c) 1999 The Apache Software Foundation.  All rights
// reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
//
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in
//    the documentation and/or other materials provided with the
//    distribution.
//
// 3. The end-user documentation included with the redistribution, if
//    any, must include the following acknowlegement:
//       "This product includes software developed by the
//        Apache Software Foundation (http://www.apache.org/)."
//    Alternately, this acknowlegement may appear in the software itself,
//    if and wherever such third-party acknowlegements normally appear.
//
// 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
//    Foundation" must not be used to endorse or promote products derived
//    from this software without prior written permission. For written
//    permission, please contact apache@apache.org.
//
// 5. Products derived from this software may not be called "Apache"
//    nor may "Apache" appear in their names without prior written
//    permission of the Apache Group.
//
//  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
//  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
//  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
//  DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
//  ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
//  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
//  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
//  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
//  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
//  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
//  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
//  SUCH DAMAGE.
//  ====================================================================
//
//  This software consists of voluntary contributions made by many
//  individuals on behalf of the Apache Software Foundation.  For more
//  information on the Apache Software Foundation, please see
//  <http://www.apache.org/>.

package com.salmonllc.ideTools;

import com.salmonllc.util.MessageLog;


import java.lang.reflect.Method;


/**
 * Boostrap loader for Catalina.  This was copied from Catalina 4.03 source and modified to run from the IDETool
 */

public final class TomcatBootstrap  {

    public static int START = 1;
    public static int STOP = 2;
    public static int RESTART = 3;

    public static void execute(int process, String browserPath, String webPage, String serverType) {
          try {
            Class types[] = {Integer.TYPE, String.class,String.class};
            Object args[] = {new Integer(process),browserPath,webPage};
            String className = "com.salmonllc.ideTools.Tomcat40Bootstrap";
            if (serverType == IDETool.SERVER_TOMCAT41)
                className = "com.salmonllc.ideTools.Tomcat41Bootstrap";
            else if (serverType == IDETool.SERVER_TOMCAT50)
                className = "com.salmonllc.ideTools.Tomcat50Bootstrap";
            Class c = Class.forName(className);
            Method m = c.getMethod("execute",types);
            m.invoke(null,args);

          } catch (Exception e) {
              MessageLog.writeErrorMessage("Tomcat4Bootstrap.execute()",e,null);
          }
    }
}
