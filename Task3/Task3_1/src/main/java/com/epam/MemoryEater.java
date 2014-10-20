package com.epam;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class MemoryEater {
	private static final Logger logger = Logger.getLogger(MemoryEater.class);

	public static void main(String[] args) {
		List<byte[]> v = new ArrayList<byte[]>();

		while (true) {
			Runtime rt = Runtime.getRuntime();
			byte b[] = new byte[10 * 1024 * 1024];
			if (rt.freeMemory() / rt.totalMemory() < rt.totalMemory() * 0.1) {
				v.clear();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
			v.add(b);
			logger.info("free memory: " + rt.freeMemory());
			logger.info("total memory: " + rt.totalMemory() + "\n");
		}
	}
}
