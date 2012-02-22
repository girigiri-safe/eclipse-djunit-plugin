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
package jp.co.dgic.eclipse.jdt.internal.junit.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.eclipse.jdt.internal.junit.runner.MessageIds;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorActionBarContributor;

public class DJUnitTestRunnerClient {

	/**
	 * The server socket
	 */
	private ServerSocket fServerSocket;
	private Socket fSocket;
	private int fPort = -1;
	private PrintWriter fWriter;
	private BufferedReader fBufferedReader;

	private boolean fDebug = false;

	/**
	 * Reads the message stream from the RemoteTestRunner
	 */
	private class ServerConnection extends Thread {
		int fPort;

		public ServerConnection(int port) {
			super("ServerConnection"); //$NON-NLS-1$
			fPort = port;
		}

		public void run() {
			try {
				if (fDebug) {
					System.out.println("Creating server socket " + fPort); //$NON-NLS-1$
				}
				fServerSocket = new ServerSocket(fPort);
				fSocket = fServerSocket.accept();
				fBufferedReader = new BufferedReader(new InputStreamReader(fSocket.getInputStream(), "UTF-8"));
				fWriter = new PrintWriter(fSocket.getOutputStream(), true);
				String message;

				while (fBufferedReader != null && (message = readMessage(fBufferedReader)) != null) {
					receiveMessage(message);
				}

			} catch (SocketException e) {
			} catch (IOException e) {
				// fall through
			}
			shutDown();
		}
	}

	/**
	 * Start listening to a test run. Start a server connection that
	 * the RemoteTestRunner can connect to.
	 */
	public synchronized void startListening(int port) {
		fPort = port;
		ServerConnection connection = new ServerConnection(port);
		connection.start();
	}

	/**
	 * Requests to stop the remote test run.
	 */
	public synchronized void stopTest() {
		if (isRunning()) {
			fWriter.println(MessageIds.TEST_STOP);
			fWriter.flush();
		}
	}

	public synchronized void shutDown() {

		clearInfo();

		if (fDebug)
			System.out.println("shutdown " + fPort); //$NON-NLS-1$

		if (fWriter != null) {
			fWriter.close();
			fWriter = null;
		}
		try {
			if (fBufferedReader != null) {
				fBufferedReader.close();
				fBufferedReader = null;
			}
		} catch (IOException e) {
		}
		try {
			if (fSocket != null) {
				fSocket.close();
				fSocket = null;
			}
		} catch (IOException e) {
		}
		try {
			if (fServerSocket != null) {
				fServerSocket.close();
				fServerSocket = null;
			}
		} catch (IOException e) {
		}
	}

	public boolean isRunning() {
		return fSocket != null;
	}

	private String readMessage(BufferedReader in) throws IOException {
		return in.readLine();
	}

	private void receiveMessage(String message) {
		postInfo(message);
	}

	private void clearInfo() {
		postAsyncRunnable(new Runnable() {
			public void run() {
				IStatusLineManager statusLine = getStatusLine();
				if (statusLine == null)
					return;
				statusLine.setErrorMessage(null);
				statusLine.setMessage(null);
			}
		});
	}

	private void postInfo(final String message) {
		postAsyncRunnable(new Runnable() {
			public void run() {
				IStatusLineManager statusLine = getStatusLine();
				if (statusLine == null) {
					return;
				}
				statusLine.setErrorMessage(null);
				statusLine.setMessage(message);
			}
		});
	}

	private void postAsyncRunnable(Runnable r) {
		getDisplay().syncExec(r);
	}

	private IStatusLineManager getStatusLine() {

		IWorkbenchPart activePart = getWorkbenchWindow().getActivePage().getActivePart();

		if (activePart instanceof IViewPart) {
			IViewPart activeViewPart = (IViewPart) activePart;
			IViewSite activeViewSite = activeViewPart.getViewSite();
			return activeViewSite.getActionBars().getStatusLineManager();
		}

		if (activePart instanceof IEditorPart) {
			IEditorPart activeEditorPart = (IEditorPart) activePart;
			IEditorActionBarContributor contributor = activeEditorPart.getEditorSite().getActionBarContributor();
			if (contributor instanceof EditorActionBarContributor)
				return ((EditorActionBarContributor) contributor).getActionBars().getStatusLineManager();
		}
		// no active part
		return null;
	}

	private IWorkbenchWindow getWorkbenchWindow() {
		return PlatformUI.getWorkbench().getWorkbenchWindows()[0];
	}

	private Display getDisplay() {
		return getWorkbenchWindow().getShell().getDisplay();
	}

}
