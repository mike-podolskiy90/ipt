/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gbif.ipt.model;

import java.util.Objects;
import java.util.Properties;
import java.util.StringJoiner;

public class IptColorScheme {

  public static final String PRIMARY_COLOR_PROPERTY = "primaryColor";
  public static final String PRIMARY_COLOR_DEFAULT_VALUE = "#61a861";
  public static final String NAVBAR_COLOR_PROPERTY = "navbarColor";
  public static final String NAVBAR_COLOR_DEFAULT_VALUE = "#78b578";
  public static final String NAVBAR_LINK_COLOR_PROPERTY = "navbarLinkColor";
  public static final String NAVBAR_LINK_COLOR_DEFAULT_VALUE = "#ffffff";
  public static final String LINK_COLOR_PROPERTY = "linkColor";
  public static final String LINK_COLOR_DEFAULT_VALUE = "#4ba2ce";

  private String primaryColor = "#61a861";
  private String navbarColor = "#78b578";
  private String navbarLinkColor = "#ffffff";
  private String linkColor = "#4ba2ce";

  public IptColorScheme() {
  }

  public IptColorScheme(Properties props) {
    setPrimaryColor(props.getProperty(PRIMARY_COLOR_PROPERTY));
    setNavbarColor(props.getProperty(NAVBAR_COLOR_PROPERTY));
    setNavbarLinkColor(props.getProperty(NAVBAR_LINK_COLOR_PROPERTY));
    setLinkColor(props.getProperty(LINK_COLOR_PROPERTY));
  }

  public Properties toProperties() {
    Properties props = new Properties();
    props.setProperty(PRIMARY_COLOR_PROPERTY, primaryColor);
    props.setProperty(NAVBAR_COLOR_PROPERTY, navbarColor);
    props.setProperty(NAVBAR_LINK_COLOR_PROPERTY, navbarLinkColor);
    props.setProperty(LINK_COLOR_PROPERTY, linkColor);
    return props;
  }

  public String getPrimaryColor() {
    return primaryColor;
  }

  public void setPrimaryColor(String primaryColor) {
    this.primaryColor = primaryColor;
  }

  public String getNavbarColor() {
    return navbarColor;
  }

  public void setNavbarColor(String navbarColor) {
    this.navbarColor = navbarColor;
  }

  public String getNavbarLinkColor() {
    return navbarLinkColor;
  }

  public void setNavbarLinkColor(String navbarLinkColor) {
    this.navbarLinkColor = navbarLinkColor;
  }

  public String getLinkColor() {
    return linkColor;
  }

  public void setLinkColor(String linkColor) {
    this.linkColor = linkColor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IptColorScheme that = (IptColorScheme) o;
    return Objects.equals(primaryColor, that.primaryColor)
        && Objects.equals(navbarColor, that.navbarColor)
        && Objects.equals(navbarLinkColor, that.navbarLinkColor)
        && Objects.equals(linkColor, that.linkColor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(primaryColor, navbarColor, navbarLinkColor, linkColor);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", IptColorScheme.class.getSimpleName() + "[", "]")
        .add("primaryColor='" + primaryColor + "'")
        .add("navbarColor='" + navbarColor + "'")
        .add("navbarLinkColor='" + navbarLinkColor + "'")
        .add("linkColor='" + linkColor + "'")
        .toString();
  }
}
