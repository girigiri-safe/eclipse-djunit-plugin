/**
 * www.jcoverage.com
 * Copyright (C)2003 jcoverage ltd.
 *
 * This file is part of jcoverage.
 *
 * jcoverage is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * jcoverage is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jcoverage; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 */
package com.jcoverage.coverage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class InstrumentationPersistence implements HasBeenInstrumented {

  final Map instrumentation=new HashMap();

  protected InstrumentationPersistence() {
  }

  protected Map loadInstrumentation() {
    File directory=getDirectory();
    
    try {
      return loadInstrumentation(new FileInputStream(new File(directory,Instrumentation.FILE_NAME)));
    } catch(FileNotFoundException ex) {
      return Collections.EMPTY_MAP;
    }
  }

  protected Map loadInstrumentation(InputStream is) {
    ObjectInputStream objects=null;
    try {
      objects=new ObjectInputStream(is);
      Map m=(Map)objects.readObject();
      return m;
    } catch(ClassNotFoundException ex) {
      return Collections.EMPTY_MAP;
    } catch(IOException ex) {
      return Collections.EMPTY_MAP;
    } finally {
      if(objects!=null) {
        try {
          objects.close();
        } catch(IOException ex) {
        }
      }

      if(is!=null) {
        try {
          is.close();
        } catch(IOException ex) {
        }
      }
    }
  }

  protected void merge(Map m) {
    Iterator i=m.entrySet().iterator();
    while(i.hasNext()) {
      Map.Entry entry=(Map.Entry)i.next();
      if(instrumentation.containsKey(entry.getKey())) {
        getInstrumentation(entry.getKey()).merge((Instrumentation)entry.getValue());
      } else {
        instrumentation.put(entry.getKey(),entry.getValue());
      }
    }
  }
  
  private File getDirectory() {
    if (System.getProperty("com.jcoverage.rawcoverage.dir")!=null) {
      return new File(System.getProperty("com.jcoverage.rawcoverage.dir"));
    } else {
      return new File(System.getProperty("user.dir"));
    }
  }

  protected void saveInstrumentation() {
    File directory=getDirectory();
    

    saveInstrumentation(directory);
  }

  protected void saveInstrumentation(File destDir) {
    FileOutputStream os=null;
    ObjectOutputStream objects=null;

    try {
      os=new FileOutputStream(new File(destDir,Instrumentation.FILE_NAME));
      objects=new ObjectOutputStream(os);
      objects.writeObject(instrumentation);
    } catch(IOException ex) {
    } finally {
      if(objects!=null) {
        try {
          objects.close();
        } catch(IOException ex) {
        }
      }

      if(os!=null) {
        try {
          os.close();
        } catch(IOException ex) {
        }
      }
    }
  }

  protected Instrumentation getInstrumentation(Object key) {
    return (Instrumentation)instrumentation.get(key);
  }

  protected Set keySet() {
    return Collections.unmodifiableSet(instrumentation.keySet());
  }

}
