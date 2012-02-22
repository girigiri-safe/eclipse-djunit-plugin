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
package jp.co.dgic.eclipse.jdt.internal.coverage.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import jp.co.dgic.eclipse.jdt.internal.coverage.report.ClickableFieldLabel;
import jp.co.dgic.eclipse.jdt.internal.coverage.report.FilesCanvas;
import jp.co.dgic.eclipse.jdt.internal.coverage.report.OverallCanvas;
import jp.co.dgic.eclipse.jdt.internal.coverage.report.PackagesCanvas;
import jp.co.dgic.eclipse.jdt.internal.coverage.report.PageNavigation;
import jp.co.dgic.eclipse.jdt.internal.coverage.report.ReportLabel;
import jp.co.dgic.eclipse.jdt.internal.coverage.report.html.HtmlReportExportProcess;
import jp.co.dgic.eclipse.jdt.internal.coverage.util.CoveragePluginUtil;
import jp.co.dgic.eclipse.jdt.internal.coverage.util.CoverageUtil;
import jp.co.dgic.eclipse.jdt.internal.coverage.util.MarkerUtil;
import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitMessages;
import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitPlugin;
import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitProjectPropertyPage;
import jp.co.dgic.testing.common.coverage.CoverageEntry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

public class CoverageReportView extends ViewPart {

	private static final int LINE_HEIGHT = 20;

	private static final FontData FONT_CONTENTS = new FontData("Courier New", 10, SWT.BOLD);

	private static final FontData FONT_TITLE = new FontData("Franklin Gothic Medium", 16, SWT.BOLD);

	private static final RGB RGB_TITLE = new RGB(0, 0, 0);
	private static final RGB RGB_BACKGROUND = new RGB(255, 255, 255);
	private static final RGB RGB_HEADER_BG = new RGB(0, 128, 0);
	private static final RGB RGB_HEADER_FG = new RGB(255, 255, 255);

	public static final String NAME = "jp.co.dgic.eclipse.jdt.coverage.CoverageReportView";

	private static final int LINES_PER_PAGE = 50;

	private ScrolledComposite scrolledComposite;

	private Canvas reportCanvas;

	private ClickableFieldLabel summaryLabel;

	private IProject currentProject;

	private Action exportHtmlAction;

	private Action refreshAction;

	private Action clearAction;

	private Action deleteMarkerAction;

	private Action createMarkerAction;

	private String packageNameFilter;

	private int currentPage = 1;
	private int maxPage = 1;

	private static String reportOutputDirectory;

	public void createPartControl(Composite parent) {

		createMenu();
		createButtons();

		scrolledComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setBackground(new Color(scrolledComposite.getDisplay(), RGB_BACKGROUND));
		scrolledComposite.getVerticalBar().setIncrement(10);
		scrolledComposite.getHorizontalBar().setIncrement(10);

		buildReportView();

	}

	public void buildReportView() {

		Control oldCanvas = scrolledComposite.getContent();
		scrolledComposite.setContent(null);
		if (oldCanvas != null) {
			disposeOldReport(oldCanvas);
		}
		
		reportCanvas = new Canvas(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(reportCanvas);
		GridLayout grid = new GridLayout();
		grid.numColumns = 1;
		reportCanvas.setLayout(grid);
		reportCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				draw(e.gc);
			}
		});

		reportCanvas.setBackground(new Color(reportCanvas.getDisplay(), RGB_BACKGROUND));

		setEnableMenuButtons();

		// Project Name
		if (currentProject == null) {
			Label message = new ReportLabel(reportCanvas, SWT.NONE);
			message.setText(DJUnitMessages.getString("CoverageReportView.message.noproject"));
			message.setBackground(new Color(reportCanvas.getDisplay(), RGB_BACKGROUND));
			message.setFont(new Font(reportCanvas.getDisplay(), FONT_TITLE));
			createEmptyLine();
			resizeReportCanvas();
			return;
		}

		Label projectNameLabel = new ReportLabel(reportCanvas, SWT.LEFT);
		projectNameLabel.setBackground(new Color(reportCanvas.getDisplay(), RGB_BACKGROUND));
		projectNameLabel.setForeground(new Color(reportCanvas.getDisplay(), RGB_TITLE));
		projectNameLabel.setFont(new Font(reportCanvas.getDisplay(), FONT_TITLE));
		projectNameLabel.setText(currentProject.getName());

		// [summary] label
		summaryLabel = new ClickableFieldLabel(reportCanvas, SWT.NONE);
		summaryLabel.setText("coverage summary");
		summaryLabel.setFont(new Font(reportCanvas.getDisplay(), FONT_CONTENTS));
		summaryLabel.setBackground(new Color(reportCanvas.getDisplay(), RGB_HEADER_BG));
		summaryLabel.setForeground(new Color(reportCanvas.getDisplay(), RGB_HEADER_FG));
		summaryLabel.setSize(400, LINE_HEIGHT);

		summaryLabel.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				setPackageNameFilter(null);
				setCurrentPage(1);
				buildReportView();
			}
		});

		createEmptyLine();

		CoveragePluginUtil.setCoverageWorkingDirectory(currentProject);
		CoverageUtil.prepareCoverageEntry();

		// [Coverage Report] label
		Label title = new ReportLabel(reportCanvas, SWT.LEFT);
		title.setBackground(new Color(reportCanvas.getDisplay(), RGB_BACKGROUND));
		title.setForeground(new Color(reportCanvas.getDisplay(), RGB_TITLE));
		title.setFont(new Font(reportCanvas.getDisplay(), FONT_TITLE));
		title.setText("Coverage Report");

		createEmptyLine();

		if (packageNameFilter == null) {
			// Overall Coverage
			new OverallCanvas(this);

			createEmptyLine();

		}

		// [Packages] label
		Label packagesTitle = new ReportLabel(reportCanvas, SWT.LEFT);
		packagesTitle.setBackground(new Color(reportCanvas.getDisplay(), RGB_BACKGROUND));
		packagesTitle.setForeground(new Color(reportCanvas.getDisplay(), RGB_TITLE));
		packagesTitle.setFont(new Font(reportCanvas.getDisplay(), FONT_TITLE));
		packagesTitle.setText("Packages");

		createEmptyLine();

		// Packages Coverage
		new PackagesCanvas(this);

		createEmptyLine();

		// pages
		maxPage = calcMaxPage(calcFilteredMaxLineCount());

		// [AllFiles] label
		Label filesTitle = new ReportLabel(reportCanvas, SWT.LEFT);
		filesTitle.setBackground(new Color(reportCanvas.getDisplay(), RGB_BACKGROUND));
		filesTitle.setForeground(new Color(reportCanvas.getDisplay(), RGB_TITLE));
		filesTitle.setFont(new Font(reportCanvas.getDisplay(), FONT_TITLE));
		filesTitle.setText("All files (" + currentPage + "/" + maxPage + " page)");

		// Page navigation
		if (maxPage > 1) {
			new PageNavigation(this, currentPage, maxPage);
		}

		// Files Coverage
		new FilesCanvas(this, calcMinLineNumber(), calcMaxLineNumber());

		createEmptyLine();

		resizeReportCanvas();
	}

	public void movePage(int pageNumber) {
		setCurrentPage(pageNumber);
		buildReportView();
	}

	public void prevPage() {
		if (currentPage <= 0) return;
		currentPage--;
		buildReportView();
	}

	public void nextPage() {
		if (currentPage >= maxPage) return;
		currentPage++;
		buildReportView();
	}

	public void setCurrentPage(int pageNumber) {
		this.currentPage = pageNumber;
	}

	private int calcMinLineNumber() {
		return (currentPage - 1) * LINES_PER_PAGE;
	}

	private int calcMaxLineNumber() {
		return currentPage * LINES_PER_PAGE;
	}

	private int calcMaxPage(long lineCount) {
		return (int) (Math.ceil((double) lineCount / (double) LINES_PER_PAGE));
	}

	private int calcFilteredMaxLineCount() {
		int count = 0;
		Map entries = CoverageUtil.getFileCoverage();
		for (Iterator it = entries.entrySet().iterator(); it.hasNext();) {

			Map.Entry me = (Map.Entry) it.next();
			CoverageEntry entry = (CoverageEntry) me.getValue();

			if (isFilteredPackage(entry)) continue;

			count++;
		}
		return count;
	}

	private boolean isFilteredPackage(CoverageEntry entry) {

		String packageName = getPackageNameFilter();
		if (packageName == null) {
			return false;
		}

		if (packageName.equalsIgnoreCase("default")) {
			if (entry.getName().indexOf('.') >= 0) {
				return true;
			}
			return false;
		}

		return !entry.getName().startsWith(packageName + ".");
	}

	private void resizeReportCanvas() {
		reportCanvas.setSize(reportCanvas.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private void createEmptyLine() {
		new ReportLabel(reportCanvas, SWT.NONE);
	}

	public Canvas getReportCanvas() {
		return reportCanvas;
	}

	public void setFocus() {
		reportCanvas.setFocus();
	}

	private void draw(GC gc) {

		// resize [summary] label
		if (summaryLabel != null) {
			gc.fillRectangle(reportCanvas.getClientArea());
			summaryLabel.setSize(reportCanvas.getClientArea().width, 20);
		}
	}

	private void createMenu() {
		IMenuManager mgr = getViewSite().getActionBars().getMenuManager();

		IProject[] projects = getAllProjects();
		if (projects == null) {
			return;
		}
		for (int idx = 0; idx < projects.length; idx++) {
			if (!projects[idx].isOpen()) {
				continue;
			}
			mgr.add(createAction(projects[idx]));
		}
	}

	private void createButtons() {
		IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();

		// export html button
		exportHtmlAction = new Action() {
			public void run() {
				exportHtmlReport();
			}
		};
		exportHtmlAction.setImageDescriptor(getImageDescriptor("full/djunit/export_html.gif"));
		exportHtmlAction.setDisabledImageDescriptor(getImageDescriptor("full/djunit/export_html_g.gif"));
		exportHtmlAction.setToolTipText("Export");

		exportHtmlAction.setEnabled((currentProject != null));

		// refresh button
		refreshAction = new Action() {
			public void run() {
				packageNameFilter = null;
				setCurrentPage(1);
				buildReportView();
				MarkerUtil.clearCoverageMarker(currentProject);
				updateCoverageMarker();
			}
		};
		refreshAction.setImageDescriptor(getImageDescriptor("full/djunit/refresh.gif"));
		refreshAction.setDisabledImageDescriptor(getImageDescriptor("full/djunit/refresh_g.gif"));
		refreshAction.setToolTipText("Refresh");

		refreshAction.setEnabled((currentProject != null));

		// clear button
		clearAction = new Action() {
			public void run() {
				packageNameFilter = null;
				setCurrentPage(1);
				if (currentProject == null) {
					return;
				}
				clearCoverage();
			}
		};
		clearAction.setImageDescriptor(getImageDescriptor("full/djunit/removeall.gif"));
		clearAction.setDisabledImageDescriptor(getImageDescriptor("full/djunit/removeall_g.gif"));
		clearAction.setToolTipText("Clear");

		clearAction.setEnabled((currentProject != null));

		// deleteMarker button
		deleteMarkerAction = new Action() {
			public void run() {
				MarkerUtil.clearCoverageMarker(currentProject);
			}
		};
		deleteMarkerAction.setImageDescriptor(getImageDescriptor("full/djunit/deletemarker.gif"));
		deleteMarkerAction.setDisabledImageDescriptor(getImageDescriptor("full/djunit/deletemarker_g.gif"));
		deleteMarkerAction.setToolTipText("Delete marker");

		deleteMarkerAction.setEnabled((currentProject != null));

		// createMarker button
		createMarkerAction = new Action() {
			public void run() {
				MarkerUtil.clearCoverageMarker(currentProject);
				updateCoverageMarker();
			}
		};
		createMarkerAction.setImageDescriptor(getImageDescriptor("full/djunit/createmarker.gif"));
		createMarkerAction.setDisabledImageDescriptor(getImageDescriptor("full/djunit/createmarker_g.gif"));
		createMarkerAction.setToolTipText("Create marker");

		createMarkerAction.setEnabled((currentProject != null));

		mgr.add(exportHtmlAction);
		mgr.add(refreshAction);
		mgr.add(clearAction);
		mgr.add(deleteMarkerAction);
		mgr.add(createMarkerAction);
	}

	private Action createAction(IProject project) {
		return new CoverageAction(project) {
			public void run() {
				currentProject = this.project;
				packageNameFilter = null;

				setEnableMenuButtons();

				buildReportView();
			}
		};
	}

	private void setEnableMenuButtons() {

		if (exportHtmlAction != null) {
			exportHtmlAction.setEnabled((currentProject != null));
		}
		if (refreshAction != null) {
			refreshAction.setEnabled((currentProject != null));
		}
		if (clearAction != null) {
			clearAction.setEnabled((currentProject != null));
		}
		if (deleteMarkerAction != null) {
			deleteMarkerAction.setEnabled((currentProject != null));
		}
		if (createMarkerAction != null) {
			createMarkerAction.setEnabled((currentProject != null));
		}
	}
	private IProject[] getAllProjects() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getProjects();
	}

	public IProject getProject() {
		return currentProject;
	}

	public void setProject(IProject project) {
		currentProject = project;
	}

	public IJavaProject getJavaProject() {
		try {
			return (IJavaProject) (currentProject.getNature(JavaCore.NATURE_ID));
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPackageNameFilter() {
		return packageNameFilter;
	}

	public void setPackageNameFilter(String packageName) {
		packageNameFilter = packageName;
	}

	public void asyncBuildView() {
		getViewSite().getShell().getDisplay().asyncExec(new ViewUpdateThread());
	}

	public void clearCoverage() {
		CoveragePluginUtil.deleteCoverageResults(currentProject);
		MarkerUtil.clearCoverageMarker(currentProject);
		packageNameFilter = null;
		buildReportView();
	}

	private class CoverageAction extends Action {

		protected IProject project;

		public CoverageAction(IProject project) {
			super(project.getName());
			this.project = project;
		}
	}

	private class ViewUpdateThread implements Runnable {
		public void run() {
			buildReportView();
		}
	}

	private ImageDescriptor getImageDescriptor(String relativePath) {
		String iconPath = "icons/";
		try {
			//			DJUnitPlugin plugin = DJUnitPlugin.getDefault();
			//			URL installURL = plugin.getDescriptor().getInstallURL();
			URL installURL = Platform.getBundle(DJUnitPlugin.PLUGIN_ID).getEntry("/");
			URL url = new URL(installURL, iconPath + relativePath);
			return ImageDescriptor.createFromURL(url);
		} catch (MalformedURLException e) {
			// should not happen
			DJUnitPlugin.log(e);
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	public void updateCoverageMarker() {
		try {
			CoverageMarkingProcess markingProcess = new CoverageMarkingProcess(getJavaProject());
			markingProcess.setUser(true);
			markingProcess.schedule();
		} catch (Throwable t) {
			DJUnitPlugin.log(t);
		}
	}

	private void exportHtmlReport() {
		try {
			String outputDirectory = getReportOutputDirectory();

			if (outputDirectory == null) return;

			reportOutputDirectory = outputDirectory;
			
			String reportCharset = DJUnitProjectPropertyPage.readCoverageReportCharset(currentProject);
			System.setProperty("djunit.html.charset", reportCharset);
			System.setProperty("djunit.src.file.encoding", currentProject.getDefaultCharset());

			String[] args = new String[6];
			args[0] = "-i";
			args[1] = getCoverageWorkingDirectory() + "/jcoverage.ser";
			args[2] = "-s";
			args[3] = getSourceDirectories();
			args[4] = "-o";
			args[5] = outputDirectory;

			HtmlReportExportProcess exportProcess = new HtmlReportExportProcess(args);
			Shell shell = new Shell(Display.getDefault());
			ProgressMonitorDialog monitorDialog = new ProgressMonitorDialog(shell);

			monitorDialog.run(true, true, exportProcess);

		} catch (Throwable t) {
			DJUnitPlugin.log(t);
			String message = t.getMessage();
			if (t.getMessage() == null) {
				message = t.toString();
			}
			MessageBox md = new MessageBox(new Shell(Display.getDefault()), SWT.OK | SWT.ICON_ERROR);
			md.setText("Error");
			md.setMessage(message);
			md.open();
		}
	}

	private String getSourceDirectories() throws CoreException {
		StringBuffer sb = new StringBuffer();

		IPackageFragmentRoot[] roots;
		try {
			roots = getJavaProject().getAllPackageFragmentRoots();
			for (int idx = 0; idx < roots.length; idx++) {
				int kind = roots[idx].getKind();
				if (kind != IPackageFragmentRoot.K_SOURCE)
					continue;
				sb.append(roots[idx].getResource().getLocation());
				sb.append(";");
			}
		} catch (JavaModelException e) {
			throw new CoreException(e.getStatus());
		}

		return sb.toString();
	}

	private String getCoverageWorkingDirectory() {

		String directoryName = DJUnitProjectPropertyPage.readWorkingDirectory(currentProject);

		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		IResource directory = null;
		if (directoryName != null) {
			directory = workspaceRoot.findMember(currentProject.getProject().getName() + directoryName);
		}

		return directory.getLocation().toString();
	}

	public boolean getUseCoverageMarker() {
		return DJUnitProjectPropertyPage.readUseMarker(currentProject);
	}

	private String getReportOutputDirectory() {

		DirectoryDialog dd = new DirectoryDialog(getSite().getShell());
		dd.setText(DJUnitMessages.getString("CoverageReportView.message.export.output.title"));
		dd.setMessage(DJUnitMessages.getString("CoverageReportView.message.export.output.message"));

		if (reportOutputDirectory == null) {
			reportOutputDirectory = currentProject.getLocation().toString();
		}
		dd.setFilterPath(reportOutputDirectory);

		return dd.open();

	}

	protected void disposeOldReport(Control control) {
		if (control instanceof Composite) {
			Control[] children = ((Composite) control).getChildren();
			for (int i = 0; i < children.length; i++) {
				disposeOldReport(children[i]);
			}
		}
		control.dispose();
	}
}