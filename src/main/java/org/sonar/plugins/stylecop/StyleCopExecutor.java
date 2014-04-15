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
import org.sonar.api.utils.SonarException;
import org.sonar.api.utils.command.Command;
import org.sonar.api.utils.command.CommandException;
import org.sonar.api.utils.command.CommandExecutor;
import org.sonar.api.utils.command.StreamConsumer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class StyleCopExecutor {

  private static final IssuesFilteringConsumer ISSUES_FILTERING_CONSUMER = new IssuesFilteringConsumer();

  public void execute(String executable, String msBuildFile, int timeoutMinutes, String timeoutExceptionMessage) {
    try {
      CommandExecutor.create().execute(
        Command.create(executable)
          .addArgument(msBuildFile),
        ISSUES_FILTERING_CONSUMER, ISSUES_FILTERING_CONSUMER, TimeUnit.MINUTES.toMillis(timeoutMinutes));
    } catch (CommandException e) {
      if (isTimeout(e)) {
        throw new SonarException(timeoutExceptionMessage, e);
      }
      throw e;
    }
  }

  private static boolean isTimeout(CommandException e) {
    return e.getCause() instanceof TimeoutException;
  }

  private static class IssuesFilteringConsumer implements StreamConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(IssuesFilteringConsumer.class);

    @Override
    public void consumeLine(String line) {
      if (isIssue(line)) {
        LOG.debug(line);
      } else {
        LOG.info(line);
      }
    }

    private static boolean isIssue(String line) {
      return line.contains(": warning : SA");
    }

  }

}
