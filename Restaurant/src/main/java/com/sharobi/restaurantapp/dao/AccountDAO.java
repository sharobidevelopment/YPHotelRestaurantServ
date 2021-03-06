/**
 * 
 */
package com.sharobi.restaurantapp.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import com.sharobi.restaurantapp.dao.exception.DAOException;
import com.sharobi.restaurantapp.model.DailyExpenditure;
import com.sharobi.restaurantapp.model.DailyExpenditureType;

/**
 * @author admin
 *
 */
public interface AccountDAO {
	
  SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public int addDailyExpenditure(DailyExpenditure dailyExpenditure)
			throws DAOException;
	
	public List<DailyExpenditure> getDailyExpenditureByDate(String date,
			String storeId) throws DAOException;
	
	public List<DailyExpenditure> getDailyExpenditureByPeriod(String date,
			String toDate, String storeId) throws DAOException;
	
	public String deleteDailyExpenditure(DailyExpenditure dailyExpenditure) throws DAOException;

	public String updateDailyExpenditure(DailyExpenditure dailyExpenditure) throws DAOException;
	
	//added on 15.05.2018
	public List<DailyExpenditureType> getExpenditureTypes() throws DAOException;

}
