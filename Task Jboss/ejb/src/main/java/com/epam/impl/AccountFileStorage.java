package com.epam.impl;

import java.io.File;
import java.io.IOException;

import javax.ejb.Stateful;

import com.epam.model.Account;

@Stateful(name = "accountFileStorage")
public class AccountFileStorage extends AbstractFileStorage<Account, Long> {

	private String filePath;
	
	public AccountFileStorage() {
		File file = new File("account.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		this.filePath = file.getAbsolutePath();
	}

	@Override
	protected String getFilePath() {
		return filePath;
	}

}
