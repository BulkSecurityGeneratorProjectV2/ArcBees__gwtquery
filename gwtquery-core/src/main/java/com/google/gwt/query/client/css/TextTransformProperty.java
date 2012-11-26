/*
 * Copyright 2011, The gwtquery team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.query.client.css;

import com.google.gwt.dom.client.Style.HasCssName;

/**
 * The <i>text-transform</i> property controls capitalization effects of an
 * element's text.
 */
public class TextTransformProperty extends
    CssProperty<TextTransformProperty.TextTransform> {

  /**
   * Possible values for <i>text-transform</i> property.
   */
  public enum TextTransform implements HasCssName {
    /**
     * Transforms the first character of each word to uppercase
     */
    CAPITALIZE,
    /**
     * Transforms all characters to uppercase
     */
    UPPERCASE,
    /**
     * Transforms all characters to lowercase
     */
    LOWERCASE;

    public String getCssName() {
      return name().toLowerCase();
    };
  }

  private static final String CSS_PROPERTY = "textTransform";

  public static void init() {
    CSS.TEXT_TRANSFORM = new TextTransformProperty();
  }

  private TextTransformProperty() {
    super(CSS_PROPERTY);
  }

}
