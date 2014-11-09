package com.epam.impl;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.epam.ifaces.IStorage;

public abstract class AbstractFileStorage<T extends Serializable, ID extends Serializable>
		implements IStorage<T, ID> {

	protected abstract String getFilePath();

	public T find(ID id) {
		try {
			Map<ID, T> records = read();
			if (records != null) {
				return records.get(id);
			}
			return null;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public void addOrUpdate(ID id, T value) {
		try {
			Map<ID, T> records = read();
			if (records == null) {
				records = new HashMap<ID, T>();
			}
			records.put(id, value);
			write(records);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<ID, T> read() throws IOException {
		Map<ID, T> records = null;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(getFilePath()));
			records = (Map<ID, T>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return records;
	}

	private void write(Map<ID, T> records) throws IOException {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(getFilePath()));
			out.writeObject(records);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
