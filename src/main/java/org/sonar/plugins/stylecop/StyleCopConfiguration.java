/*
 * SonarQube StyleCop Plugin
 * Copyright (C) 2014 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.stylecop;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;

public class StyleCopConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(StyleCopConfiguration.class);

  private final Settings settings;

  public StyleCopConfiguration(Settings settings) {
    this.settings = settings;
  }

  public String msBuildPath() {
    String result;

    if (settings.hasKey(StyleCopPlugin.STYLECOP_OLD_DOTNET_VERSION_PROPERTY_KEY)) {
      String netFrameworkPropertyKey = StyleCopPlugin.STYLECOP_OLD_DOTNET_FRAMEWORK_PROPERTY_KEY_PART_1 +
        settings.getString(StyleCopPlugin.STYLECOP_OLD_DOTNET_VERSION_PROPERTY_KEY) +
        StyleCopPlugin.STYLECOP_OLD_DOTNET_FRAMEWORK_PROPERTY_KEY_PART_2;

      LOG.warn("Use the new property \"" + StyleCopPlugin.STYLECOP_MSBUILD_PATH_PROPERTY_KEY + "\" instead of the deprecated \""
        + StyleCopPlugin.STYLECOP_OLD_DOTNET_VERSION_PROPERTY_KEY + "\" and \""
        + netFrameworkPropertyKey + "\".");

      result = mergePathAndFile(mandatoryStringProperty(netFrameworkPropertyKey), "MSBuild.exe");
    } else {
      result = mandatoryStringProperty(StyleCopPlugin.STYLECOP_MSBUILD_PATH_PROPERTY_KEY);
    }

    return result;
  }

  public String styleCopDllPath() {
    String result;

    if (settings.hasKey(StyleCopPlugin.STYLECOP_OLD_INSTALL_DIRECTORY_PROPERTY_KEY)) {
      LOG.warn("Use the new property \"" + StyleCopPlugin.STYLECOP_DLL_PATH_PROPERTY_KEY + "\" instead of the deprecated \""
        + StyleCopPlugin.STYLECOP_OLD_INSTALL_DIRECTORY_PROPERTY_KEY + "\".");

      result = mergePathAndFile(settings.getString(StyleCopPlugin.STYLECOP_OLD_INSTALL_DIRECTORY_PROPERTY_KEY), "StyleCop.dll");
    } else {
      result = mandatoryStringProperty(StyleCopPlugin.STYLECOP_DLL_PATH_PROPERTY_KEY);
    }

    return result;
  }

  public String projectFilePath() {
    return mandatoryStringProperty("sonar.stylecop.projectFilePath");
  }

  public int timeoutMinutes() {
    return mandatoryIntProperty(StyleCopPlugin.STYLECOP_TIMEOUT_MINUTES_PROPERTY_KEY);
  }

  private String mandatoryStringProperty(String propertyKey) {
    checkMandatoryProperty(propertyKey);
    return settings.getString(propertyKey);
  }

  private int mandatoryIntProperty(String propertyKey) {
    checkMandatoryProperty(propertyKey);
    return settings.getInt(propertyKey);
  }

  private void checkMandatoryProperty(String propertyKey) {
    Preconditions.checkArgument(settings.hasKey(propertyKey), "Missing the mandatory property \"" + propertyKey + "\".");
  }

  private static String mergePathAndFile(String s1, String s2) {
    return s1.endsWith("/") || s1.endsWith("\\") ? s1 + s2 : s1 + "/" + s2;
  }

}
