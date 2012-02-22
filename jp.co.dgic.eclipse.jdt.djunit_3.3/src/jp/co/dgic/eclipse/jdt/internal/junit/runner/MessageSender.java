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
package jp.co.dgic.eclipse.jdt.internal.junit.runner;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender {

	/** The client socket */
	private Socket clientSocket;
	/** Print writer for sending messages */
	private PrintWriter writer;
	/** RemoteTestRunnerClient host */
	private String host = "";
	/** RemoteTestRunnerClient port */
	private int port = -1;

	public boolean connect() {
		if (port == -1) return false;
		for (int i = 1; i < 20; i++) {
			try {
				clientSocket = new Socket(host, port);
				writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
				return true;
			} catch (IOException e) {
				// continue
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// continue
			}
		}
		return false;
	}

	public void shutDown() {
		if (writer != null) {
			writer.close();
			writer = null;
		}

		try {
			if (clientSocket != null) {
				clientSocket.close();
				clientSocket = null;
			}
		} catch (IOException e) {
		}
	}

	public void sendMessage(final String msg) {
		if(writer == null) {
			return;
		}
		Thread t = new Thread(new Runnable() {
			public void run() {
				writer.println(msg);
			}
		});
		t.start();
	}

	public void initialize(String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].toLowerCase().equals("-host")) {
					host = args[i + 1];
				}
				if (args[i].toLowerCase().equals("-djunitport")) {
					port = Integer.parseInt(args[i + 1]);
				}
			}
		} catch (Exception e) {
			// return default value
		}
	}

}
