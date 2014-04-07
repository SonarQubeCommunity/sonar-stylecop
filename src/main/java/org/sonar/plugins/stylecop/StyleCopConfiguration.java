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
    if (settings.hasKey(StyleCopPlugin.STYLECOP_OLD_DOTNET_VERSION_PROPERTY_KEY)) {
      String netFrameworkPropertyKey = StyleCopPlugin.STYLECOP_OLD_DOTNET_FRAMEWORK_PROPERTY_KEY_PART_1 +
        settings.getString(StyleCopPlugin.STYLECOP_OLD_DOTNET_VERSION_PROPERTY_KEY) +
        StyleCopPlugin.STYLECOP_OLD_DOTNET_FRAMEWORK_PROPERTY_KEY_PART_2;

      if (settings.hasKey(netFrameworkPropertyKey)) {
        LOG.warn("Use the new property \"" + StyleCopPlugin.STYLECOP_MSBUILD_PATH_PROPERTY_KEY + "\" instead of the deprecated \""
          + StyleCopPlugin.STYLECOP_OLD_DOTNET_VERSION_PROPERTY_KEY + "\" and \""
          + netFrameworkPropertyKey + "\".");
        return settings.getString(netFrameworkPropertyKey) + "MSBuild.exe";
      }
    }

    return settings.getString(StyleCopPlugin.STYLECOP_MSBUILD_PATH_PROPERTY_KEY);
  }

  public String styleCopDllPath() {
    if (settings.hasKey(StyleCopPlugin.STYLECOP_OLD_INSTALL_DIRECTORY_PROPERTY_KEY)) {
      LOG.warn("Use the new property \"" + StyleCopPlugin.STYLECOP_DLL_PATH_PROPERTY_KEY + "\" instead of the deprecated \""
        + StyleCopPlugin.STYLECOP_OLD_INSTALL_DIRECTORY_PROPERTY_KEY + "\".");
      return settings.getString(StyleCopPlugin.STYLECOP_OLD_INSTALL_DIRECTORY_PROPERTY_KEY) + "StyleCop.dll";
    }
    return settings.getString(StyleCopPlugin.STYLECOP_DLL_PATH_PROPERTY_KEY);
  }

  public String projectFilePath() {
    return settings.getString("sonar.stylecop.projectFilePath");
  }

  public int timeoutMinutes() {
    return settings.getInt(StyleCopPlugin.STYLECOP_TIMEOUT_MINUTES_PROPERTY_KEY);
  }

}
