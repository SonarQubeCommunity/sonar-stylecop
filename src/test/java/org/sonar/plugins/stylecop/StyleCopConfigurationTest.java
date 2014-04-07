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

    when(settings.getString("sonar.stylecop.msBuildPath")).thenReturn("c:/MSBuild.exe");
    assertThat(conf.msBuildPath()).isEqualTo("c:/MSBuild.exe");
  }

  @Test
  public void msBuildPath_deprecated_properties() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    when(settings.getString("sonar.dotnet.version")).thenReturn("4.0");
    when(settings.getString("sonar.dotnet.4.0.sdk.directory")).thenReturn("c:/.NET_4.0");
    assertThat(conf.msBuildPath()).isEqualTo("c:/.NET_4.0/MSBuild.exe");

    when(settings.getString("sonar.dotnet.version")).thenReturn("3.5");
    when(settings.getString("sonar.dotnet.3.5.sdk.directory")).thenReturn("c:/.NET_3.5");
    assertThat(conf.msBuildPath()).isEqualTo("c:/.NET_3.5/MSBuild.exe");

    when(settings.getString("sonar.dotnet.3.5.sdk.directory")).thenReturn("c:/.NET_3.5/");
    assertThat(conf.msBuildPath()).isEqualTo("c:/.NET_3.5/MSBuild.exe");

    when(settings.getString("sonar.dotnet.3.5.sdk.directory")).thenReturn("c:\\.NET_3.5\\");
    assertThat(conf.msBuildPath()).isEqualTo("c:\\.NET_3.5\\MSBuild.exe");
  }

  @Test
  public void styleCopDllPath() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    when(settings.getString("sonar.stylecop.styleCopDllPath")).thenReturn("c:/StyleCop.dll");
    assertThat(conf.styleCopDllPath()).isEqualTo("c:/StyleCop.dll");
  }

  @Test
  public void styleCopDllPath_deprecated_properties() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    when(settings.getString("sonar.stylecop.installDirectory")).thenReturn("c:/StyleCop");
    assertThat(conf.styleCopDllPath()).isEqualTo("c:/StyleCop/StyleCop.dll");

    when(settings.getString("sonar.stylecop.installDirectory")).thenReturn("c:/StyleCop/");
    assertThat(conf.styleCopDllPath()).isEqualTo("c:/StyleCop/StyleCop.dll");

    when(settings.getString("sonar.stylecop.installDirectory")).thenReturn("c:\\StyleCop\\");
    assertThat(conf.styleCopDllPath()).isEqualTo("c:\\StyleCop\\StyleCop.dll");
  }

  @Test
  public void projectFilePath() {
    Settings settings = mock(Settings.class);
    StyleCopConfiguration conf = new StyleCopConfiguration(settings);

    when(settings.getString("sonar.stylecop.projectFilePath")).thenReturn("c:/Solution/Project/project.csproj");
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

    when(settings.getInt("sonar.stylecop.timeoutMinutes")).thenReturn(42);
    assertThat(conf.timeoutMinutes()).isEqualTo(42);
  }

}
