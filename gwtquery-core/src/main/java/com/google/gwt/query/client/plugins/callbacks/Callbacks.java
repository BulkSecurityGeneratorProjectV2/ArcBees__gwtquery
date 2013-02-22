/*
 * Copyright 2013, The gwtquery team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.query.client.plugins.callbacks;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.builders.JsonBuilder;
import com.google.gwt.query.client.js.JsObjectArray;

/**
 * Implementation of jQuery.Callbacks for gwtquery.
 */
public class Callbacks {
  
  /**
   * Iterface used for callbacks which could cancel the execution 
   * when returning false;
   *
   */
  public static interface Callback {
    /**
     * Return false to avoid executing the rest of functions 
     */
    boolean f(Object ...objects);
  }
  
  /**
   * Interface representing the options of a Callbacks collection.
   * 
   * To create an implementation of this interface just call: Callbacks.createOptions()
   */
  public static interface CallbackOptions extends JsonBuilder {
    boolean getMemory();
    boolean getOnce();
    boolean getStopOnFalse();
    boolean getUnique();
    CallbackOptions setMemory();
    CallbackOptions setOnce();
    CallbackOptions setStopOnFalse();
    CallbackOptions setUnique();
  }
  
  public static CallbackOptions createOptions() {
    return GWT.create(CallbackOptions.class);
  }
  
  private JsObjectArray<Object> callbacks = JsObjectArray.create();
  
  private boolean done = false;
    
  private JsObjectArray<Object> memory = JsObjectArray.create();
  
  public final CallbackOptions opts;
  
  /**
   * Create a new Callbacks object with default options
   */
  public Callbacks() {
    opts = createOptions();
  }

  /**
   * Create a new Callbacks object with given options
   */
  public Callbacks(CallbackOptions options) {
    opts = options;
  }

  /**
   * Create a new Callbacks object with options given as a space delimited string.
   * 
   * Valid options are:
   * 
   * once, memory, unique, stopOnFalse
   */
  public Callbacks(String options) {
    this();
    opts.load(Properties.create(options.replaceAll("[^\\S]+|$", ":1,")));
  }
  
  /**
   * Add a Callback or a collection of callbacks to a callback list.
   * 
   */
  public Callbacks add(Callback... c) {
    addAll((Object[])c);
    return this;
  }
  
  /**
   * Add a Callback or a collection of callbacks to a callback list.
   */
  public Callbacks add(com.google.gwt.core.client.Callback<?, ?>... c) {
    addAll((Object[])c);
    return this;
  }

  /**
   * Add a Function or a collection of Function to a callback list.
   */
  public Callbacks add(Function... f) {
    addAll((Object[])f);
    return this;
  }
  
  /**
   * Disable a callback list from doing anything more.
   */
  public Callbacks disable() {
    done = true;
    return this;
  }

  /**
   * Call all of the callbacks with the given arguments.
   */
  public Callbacks fire(Object... o) {
    run(opts.getMemory() ? memory.add(o).elements() : o);
    return this;
  }
  
  /**
   * Remove a callback or a collection of callbacks from a callback list.
   */
  public Callbacks remove(Object... o) {
    callbacks.remove(o);
    return this;
  }
  
  @SuppressWarnings({"unchecked", "rawtypes"})
  private void run(Object... o) {
    if (!done) {
      done = opts.getOnce();
      for (Object c : callbacks.elements()) {
        if (c instanceof Callback) {
          boolean r = ((Callback)c).f(o);
          if (opts.getStopOnFalse() && !r) break;
        } else if (c instanceof Function) {
          ((Function)c).f(o);
        } else if (c instanceof com.google.gwt.core.client.Callback) {
          ((com.google.gwt.core.client.Callback)c).onSuccess(o);
        }
      }
    }
  }
  
  private void addAll(Object...o) {
    for (Object i : o) {
      if (!opts.getUnique() || !callbacks.contains(i)) {
        callbacks.add(i);
      }
    }
  }
}
