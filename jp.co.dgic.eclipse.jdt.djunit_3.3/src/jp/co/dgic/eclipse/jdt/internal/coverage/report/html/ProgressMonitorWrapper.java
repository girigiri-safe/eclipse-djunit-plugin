/**
 * Copyright (C)2004 dGIC Corporation.
 *
 * This file is part of djUnit plugin.
 *
 * djUnit plugin is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * djUnit plugin is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with djUnit plugin; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 */
package jp.co.dgic.eclipse.jdt.internal.coverage.report.html;

import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitMessages;

import org.eclipse.core.runtime.IProgressMonitor;

public class ProgressMonitorWrapper implements IDJUnitProgressMonitor {
    
    private IProgressMonitor monitor;
    
    public ProgressMonitorWrapper(IProgressMonitor monitor) {
        this.monitor = monitor;
    }

    public void beginTask(String name, int totalWork) {
        String message = DJUnitMessages.getString("CoverageReportView.message.export.making.message") + " " + name;
        monitor.beginTask(message, totalWork);
    }

    public void done() {
        monitor.done();
    }

    public void internalWorked(double work) {
        monitor.internalWorked(work);
    }

    public boolean isCanceled() {
        return monitor.isCanceled();
    }

    public void setCanceled(boolean value) {
        monitor.setCanceled(value);
    }

    public void setTaskName(String name) {
        String message = DJUnitMessages.getString("CoverageReportView.message.export.making.message") + " " + name;
        monitor.setTaskName(message);
    }

    public void subTask(String name) {
        String message = DJUnitMessages.getString("CoverageReportView.message.export.exporting.message") + " " + name;
        monitor.subTask(message);
    }

    public void worked(int work) {
        monitor.worked(work);
    }

}
