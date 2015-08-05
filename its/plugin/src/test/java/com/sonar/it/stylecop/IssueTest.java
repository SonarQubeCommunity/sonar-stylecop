/*
 * SonarSource :: StyleCop :: ITs :: Plugin
 * Copyright (C) 2014 SonarSource
 * sonarqube@googlegroups.com
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
package com.sonar.it.stylecop;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarRunner;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.sonar.wsclient.issue.Issue;
import org.sonar.wsclient.issue.IssueQuery;

import java.io.File;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class IssueTest {

  @ClassRule
  public static Orchestrator orchestrator = Tests.ORCHESTRATOR;

  @BeforeClass
  public static void init() {
    orchestrator.resetData();

    SonarRunner build = Tests.createSonarRunner()
      .setProjectDir(new File("projects/IssueTest/"))
      .setProjectKey("project")
      .setProjectName("project")
      .setProjectVersion("1.0")
      .setSourceDirs(".")
      .setProperty("sonar.modules", "Project1")
      .setProperty("sonar.stylecop.msBuildPath", orchestrator.getConfiguration().getString("sonar.stylecop.msBuildPath"))
      .setProperty("sonar.stylecop.styleCopDllPath", orchestrator.getConfiguration().getString("sonar.stylecop.styleCopDllPath"))
      .setProperty("sonar.stylecop.projectFilePath", new File("projects/IssueTest/Project1/Project1.csproj").getAbsolutePath())
      .setProperty("sonar.sourceEncoding", "UTF-8")
      .setProfile("profile");

    orchestrator.executeBuild(build);
  }

  @Test
  public void one_issue_for_rule_unused_variable() {
    List<Issue> issues = orchestrator.getServer().wsClient().issueClient().find(IssueQuery.create()).list();

    assertThat(issues).hasSize(1);
    assertThat(issues.get(0).ruleKey()).isEqualTo("stylecop:UseStringEmptyForEmptyStrings");
  }

}
