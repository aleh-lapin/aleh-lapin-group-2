package com.epam.impl;

import java.io.File;
import java.io.IOException;

import javax.ejb.Stateful;

import com.epam.model.CurrencyRate;

@Stateful(name = "currencyRateStorage")
public class CurrencyRateStorage extends
		AbstractFileStorage<CurrencyRate, Long> {

	private String filePath;
	
	public CurrencyRateStorage() {
		File file = new File("currencyrate.txt");
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
