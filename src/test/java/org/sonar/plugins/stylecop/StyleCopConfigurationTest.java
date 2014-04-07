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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.api.config.Settings;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StyleCopConfigurationTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void msBuildPath() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    mockSetProperty(settings, "sonar.stylecop.msBuildPath", "c:/MSBuild.exe");
    assertThat(conf.msBuildPath()).isEqualTo("c:/MSBuild.exe");
  }

  @Test
  public void msBuildPath_missing() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing the mandatory property \"sonar.stylecop.msBuildPath\".");

    new StyleCopConfiguration(mock(Settings.class)).msBuildPath();
  }

  @Test
  public void msBuildPath_deprecated_properties() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    mockSetProperty(settings, "sonar.dotnet.version", "4.0");
    mockSetProperty(settings, "sonar.dotnet.4.0.sdk.directory", "c:/.NET_4.0");
    assertThat(conf.msBuildPath()).isEqualTo("c:/.NET_4.0/MSBuild.exe");

    mockSetProperty(settings, "sonar.dotnet.version", "3.5");
    mockSetProperty(settings, "sonar.dotnet.3.5.sdk.directory", "c:/.NET_3.5");
    assertThat(conf.msBuildPath()).isEqualTo("c:/.NET_3.5/MSBuild.exe");

    mockSetProperty(settings, "sonar.dotnet.3.5.sdk.directory", "c:/.NET_3.5/");
    assertThat(conf.msBuildPath()).isEqualTo("c:/.NET_3.5/MSBuild.exe");

    mockSetProperty(settings, "sonar.dotnet.3.5.sdk.directory", "c:\\.NET_3.5\\");
    assertThat(conf.msBuildPath()).isEqualTo("c:\\.NET_3.5\\MSBuild.exe");
  }

  @Test
  public void styleCopDllPath() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    mockSetProperty(settings, "sonar.stylecop.styleCopDllPath", "c:/StyleCop.dll");
    assertThat(conf.styleCopDllPath()).isEqualTo("c:/StyleCop.dll");
  }

  @Test
  public void styleCopDllPath_missing() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing the mandatory property \"sonar.stylecop.styleCopDllPath\".");

    new StyleCopConfiguration(mock(Settings.class)).styleCopDllPath();
  }

  @Test
  public void styleCopDllPath_deprecated_properties() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    mockSetProperty(settings, "sonar.stylecop.installDirectory", "c:/StyleCop");
    assertThat(conf.styleCopDllPath()).isEqualTo("c:/StyleCop/StyleCop.dll");

    mockSetProperty(settings, "sonar.stylecop.installDirectory", "c:/StyleCop/");
    assertThat(conf.styleCopDllPath()).isEqualTo("c:/StyleCop/StyleCop.dll");

    mockSetProperty(settings, "sonar.stylecop.installDirectory", "c:\\StyleCop\\");
    assertThat(conf.styleCopDllPath()).isEqualTo("c:\\StyleCop\\StyleCop.dll");
  }

  @Test
  public void projectFilePath() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    mockSetProperty(settings, "sonar.stylecop.projectFilePath", "c:/Solution/Project/project.csproj");
    assertThat(conf.projectFilePath()).isEqualTo("c:/Solution/Project/project.csproj");
  }

  @Test
  public void projectFilePath_missing() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing the mandatory property \"sonar.stylecop.projectFilePath\".");

    new StyleCopConfiguration(mock(Settings.class)).projectFilePath();
  }

  @Test
  public void timeoutMinutes() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    mockSetProperty(settings, "sonar.stylecop.timeoutMinutes", 42);
    assertThat(conf.timeoutMinutes()).isEqualTo(42);
  }

  @Test
  public void timeoutMinutes_missing() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Missing the mandatory property \"sonar.stylecop.timeoutMinutes\".");

    new StyleCopConfiguration(mock(Settings.class)).timeoutMinutes();
  }

  private static void mockSetProperty(Settings settings, String key, String value) {
    when(settings.hasKey(key)).thenReturn(true);
    when(settings.getString(key)).thenReturn(value);
  }

  private static void mockSetProperty(Settings settings, String key, int value) {
    when(settings.hasKey(key)).thenReturn(true);
    when(settings.getInt(key)).thenReturn(value);
  }

}
