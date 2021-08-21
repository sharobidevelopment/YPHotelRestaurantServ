package com.sharobi.restaurantapp.commonUtil;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import com.sharobi.restaurantapp.dao.StoreAddressDAOImpl;
import com.sharobi.restaurantapp.model.StoreMaster;

import javawebparts.core.org.apache.commons.lang.WordUtils;

class BillPrintA4 implements Printable {

	SimpleDateFormat df = new SimpleDateFormat();
	String receiptDetailLine;
	public static final String pspace = "               ";// 15-spaces
	Object[] printData = null;
	int storeId;
	private BufferedImage image;
	StoreAddressDAOImpl addressDAOImpl = new StoreAddressDAOImpl();

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {

		try {
			
			System.out.println("Print awt...");
			df.applyPattern("dd/MM/yyyy HH:mm:ss");
			String strText = null;
			final String LF = "\n";// text string to output
			int lineStart; // start index of line in textarea
			int lineEnd; // end index of line in textarea
			int lineNumber;
			int lineCount;
			final String SPACE = "          ";// 10 spaces
			final String SPACES = "         ";// 9
			final String uline = "________________________________________";
			final String dline = "----------------------------------------";
			String greetings = "THANKS FOR YOUR VISIT";
			receiptDetailLine = "asdasdasda";
			Object[] dataToPrnt = null;

			Graphics2D g2d = (Graphics2D) graphics;
			Graphics2D g2dResTitle = (Graphics2D) graphics;
			Graphics2D g2dDateTime = (Graphics2D) graphics;
			Graphics2D g2dItemTitle = (Graphics2D) graphics;
			Graphics2D g2dItem = (Graphics2D) graphics;
			Graphics2D g2dTableNo = (Graphics2D) graphics;

			StoreMaster store = addressDAOImpl.getStoreByStoreId(storeId);
			int kotResTitlFont = store.getKotResTitleFont();
			int kotDateTimeFont = store.getKotDateTimeFont();
			int kotItemFont = store.getBillTextFont();
			int kotItemTitlFont = store.getBillHeaderFont();
			int kotTableFont = store.getKotTableFont();

			Font font = null;
			Font fontResTitle = null;
			Font fontDateTime = null;
			Font fontItemTitle = null;
			Font fontItemTitleTotal = null;
			Font fontItem = null;
			Font fontTableNo = null;
			Font fontInvoiceText = null;

			//if (store.getId() == 37 || store.getId() == 38) {
				font = new Font(store.getBillFontType(), Font.PLAIN, 10);
				fontResTitle = new Font(store.getBillFontType(), Font.BOLD, kotResTitlFont);
				fontDateTime = new Font(store.getBillFontType(), Font.PLAIN,
						kotDateTimeFont);
				fontItemTitle = new Font(store.getBillFontType(), Font.PLAIN,
						kotItemTitlFont);
				fontItemTitleTotal = new Font(store.getBillFontType(), Font.BOLD, 14);
				fontItem = new Font(store.getBillFontType(), Font.PLAIN, kotItemFont);
				fontTableNo = new Font(store.getBillFontType(), Font.PLAIN, kotTableFont);
				fontInvoiceText = new Font(store.getBillFontType(), Font.BOLD, 12);
			/*} else {
				font = new Font("MONOSPACED", Font.BOLD, 10);
				fontResTitle = new Font("MONOSPACED", Font.BOLD, kotResTitlFont);
				fontDateTime = new Font("MONOSPACED", Font.BOLD,
						kotDateTimeFont);
				fontItemTitle = new Font("MONOSPACED", Font.BOLD,
						kotItemTitlFont);
				fontItemTitleTotal = new Font("MONOSPACED", Font.BOLD, 16);
				fontItem = new Font("MONOSPACED", Font.BOLD, kotItemFont);
				fontTableNo = new Font("MONOSPACED", Font.BOLD, kotTableFont);
				fontInvoiceText = new Font("MONOSPACED", Font.BOLD, 12);
			}*/
			if (pageIndex < 0 || pageIndex >= 1) {
				return Printable.NO_SUCH_PAGE;
			}

			// Get Print Data
			dataToPrnt = getPrintData();

			// String kot = (String) dataToPrnt[0];
			String strName = (String) dataToPrnt[0];
			System.out.println("str name: " + strName);
			String address = (String) dataToPrnt[1] ;
			String emailId = (String) dataToPrnt[2];
			String phNumber = (String) dataToPrnt[3];

			String vatRegNo = (String) dataToPrnt[4];
			String serviceTaxNo = (String) dataToPrnt[5];
			String invoice = (String) dataToPrnt[6];
			String dateTable = (String) dataToPrnt[7];

			Map<Integer, List<String>> itemsMap = (Map<Integer, List<String>>) dataToPrnt[8];
			int length = itemsMap.size();
			System.out.println("map length is..." + length);
			Set<Integer> keys = itemsMap.keySet();
			List<String> nonVatItem = (List<String>) dataToPrnt[9];
			String nonVatItemText = nonVatItem.get(0);
			String nonVatItemAmt = nonVatItem.get(1);

			List<String> vatItem = (List<String>) dataToPrnt[10];
			String vatItemText = vatItem.get(0);
			String vatItemAmt = vatItem.get(1);

			List<String> totalAmt = (List<String>) dataToPrnt[11];
			String totalAmtTxt = totalAmt.get(0);
			String totalAmtVal = totalAmt.get(1);

			List<String> vatAmtLst = (List<String>) dataToPrnt[12];
			String vatAmtTxt = vatAmtLst.get(0);
			String vatAmtVal = vatAmtLst.get(1);

			List<String> dscntLst = (List<String>) dataToPrnt[14];
			String dscntTxt = dscntLst.get(0);
			String dscntVal = dscntLst.get(1);

			List<String> grossLst = (List<String>) dataToPrnt[15];
			String grossTxt = grossLst.get(0);
			String grossVal = grossLst.get(1);

			String tableNo = (String) dataToPrnt[16];

			List<String> paidAmtLst = (List<String>) dataToPrnt[17];
			String paidAmtTxt = paidAmtLst.get(0);
			String paidAmtVal = paidAmtLst.get(1);

			List<String> amtToPayLst = (List<String>) dataToPrnt[18];
			String amtToPayTxt = amtToPayLst.get(0);
			String amtToPayVal = amtToPayLst.get(1);

			List<String> custDiscLst = (List<String>) dataToPrnt[19];
			String custDiscTxt = custDiscLst.get(0);
			String custDisVal = custDiscLst.get(1);

			String userId = (String) dataToPrnt[20];

			List<String> tenderAmtLst = (List<String>) dataToPrnt[21];
			String tenderAmtTxt = tenderAmtLst.get(0);
			String tenderAmtVal = tenderAmtLst.get(1);

			List<String> paymntTypeLst = (List<String>) dataToPrnt[22];
			String paymntTypeTxt = paymntTypeLst.get(0);
			String paymntTypeVal = paymntTypeLst.get(1);

			List<String> refundAmtLst = (List<String>) dataToPrnt[23];
			String refundAmtTxt = refundAmtLst.get(0);
			String refundAmtVal = refundAmtLst.get(1);

			// for home delivery
			String custName = (String) dataToPrnt[24];
			String custAddress = (String) dataToPrnt[25];
			String custPhone = (String) dataToPrnt[26];
			String deliveryPersonName = (String) dataToPrnt[27];
			String tablNo = (String) dataToPrnt[28];
			String billFooterText = (String) dataToPrnt[29];
			String thanksLine1 = (String) dataToPrnt[30];
			String thanksLine2 = (String) dataToPrnt[31];
			double roundAdjVal = (double) dataToPrnt[32];
			String cashierString = (String) dataToPrnt[33];
			String serverString = (String) dataToPrnt[34];
			
			List<String> servcChargeLst = (List<String>) dataToPrnt[35];
			String servcChargeTxt = servcChargeLst.get(0);
			String servcChargeVal = servcChargeLst.get(1);
			String custVatRegNo = (String) dataToPrnt[36];
			String waiterName=(String)dataToPrnt[42];
			

			// int length=keys.size();
			// Print Table
			int space = 5;
			int y = 10;
			//File f = null;

			// draw image start

			// cH = cH + 10;

			try {
				int logoXPos=store.getLogoXCoordinate();
				image = ImageIO.read(new URL("http://localhost:8080/Restaurant/rest/image/store/get?name="
								+ store.getId()));
				//f = new File("D:\\2016-07-21\\upc.gif");
				//image = ImageIO.read(f);

				if (image != null) {
					int w = image.getWidth();
					int h = image.getHeight();
					BufferedImage img = new BufferedImage(w, h,
							BufferedImage.TYPE_INT_ARGB); // Graphics2D g2dImage
															// =
					img.createGraphics();
				
					g2d.drawImage(image, logoXPos, y, null);
					//
					y = y + h ;
				}
				// g2d.setPaint(Color.red);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// draw image end

			y = y + space;
			g2dResTitle.translate(pageFormat.getImageableX(),
					pageFormat.getImageableY());
			g2dResTitle.setFont(fontResTitle);

			String ad2 = WordUtils.wrap(strName, 24);
			String split2[] = ad2.split("\\n");
			
			int strLength=strName.length();
			int padding=(24-strLength)/2;
			if(padding<0){
				padding=0;
			}
			int charPixel=8;
			double threepone=3.1;
			double threepfour=3.4;
			int three=3;
			int x=(int)(90*threepone);
			int x1=(int)(90*threepfour);
			int x2=(int)(12*threepone);
			int x3=(int)(110*threepfour);
			for (int k = 0; k < split2.length; k++) {
				int length1=(split2[k]).length();
				System.out.println("name length:: "+length1);
				//if(store.getId()==59){
				g2dResTitle.drawString(split2[k], (x)-((length1*charPixel)/2), 10);
				//}
				//else {
					//g2dResTitle.drawString(split2[k], 0, y);
				//}
				//y = y + 12;
				
			}
			// g2dResTitle.drawString(strName, 0, y);

			double start = 0.0;
			//y = y + space ;
			g2d.translate(start, pageFormat.getImageableY());
			g2d.setFont(font);

			String ad = WordUtils.wrap(address, 60);
			String split[] = ad.split("\\n");
			int l1=(((split[0].length()-2)*charPixel)/2);
			int d1=x1-l1;
			g2d.drawString(split[0], d1, 19);
			
			for (int k = 1; k < split.length; k++) {
				y = y + 12;
				int length2=(split[k]).length();
				int l2=((length2-4)*charPixel)/2;
				int d2=x1-l2;
				g2d.drawString(split[k], (x1)-(l2+((d2-d1)/2)), y);	
				
			}

			if (!emailId.equalsIgnoreCase("") && emailId.length() > 0) {
        int length3=(emailId).length();
        //y = y + space;
        y = y + 12;
        g2d.translate(start, pageFormat.getImageableY());
        g2d.setFont(font);
        g2d.drawString(emailId, (x1)-((length3*charPixel)/2), y);
        //y = y + space + 3;
        y = y + 12;
				
			}

			//y = y + space + 3;
			g2d.translate(start, pageFormat.getImageableY());
			g2d.setFont(font);
			int length4=phNumber.length();
      g2d.drawString(phNumber, (x)-((length4*charPixel)/2),y);

			if (store.getInvoiceText() != null) {
				if (store.getInvoiceText().length() > 0
						&& !(store.getInvoiceText().equalsIgnoreCase(""))) {
					//y = y + space + 7;
					y = y + 24;
					g2d.translate(start, pageFormat.getImageableY());
					g2d.setFont(fontInvoiceText);
					int length5=store.getInvoiceText().length();
					g2d.drawString(store.getInvoiceText(), (x)-((length5*charPixel)/2), y);
				}
			}
			/*************************
			 * this two texts printed below for Red Onion req.
			 */
			/*if (tablNo.equalsIgnoreCase("0")) {
				if (store.getParcelVat().equalsIgnoreCase("Y")) {
					
					if (store.getVatRegNo() != null
							&& store.getVatRegNo().trim().length() > 1) {
						//y = y + space + 3;
						y = y + 3;
						g2d.translate(start, pageFormat.getImageableY());
						g2d.setFont(font);
						g2d.drawString(vatRegNo, x2, y);

					}
				}
				
				//y = y + space + 3;
				y = y + 3;
				if (store.getParcelServiceTax().equalsIgnoreCase("Y")) {
					if (store.getServiceTaxNo() != null
							&& store.getServiceTaxNo().trim().length() > 1) {
						g2d.translate(start, pageFormat.getImageableY());
						g2d.setFont(font);
						g2d.drawString(serviceTaxNo, x2, y);

					}
				}
				
			} else if (!tablNo.equalsIgnoreCase("0")) {
				
				if (store.getVatRegNo() != null
						&& store.getVatRegNo().trim().length() > 1) {
					//y = y + space + 3;
					y = y + 3;
					g2d.translate(start, pageFormat.getImageableY());
					g2d.setFont(font);
					g2d.drawString(vatRegNo, x2, y);

				}
				

				if (store.getServiceTaxNo() != null
						&& store.getServiceTaxNo().trim().length() > 1) {
					//y = y + space + 3;
					y = y + 3;
					g2d.translate(start, pageFormat.getImageableY());
					g2d.setFont(font);
					g2d.drawString(serviceTaxNo, x2, y);

				}
			

			}*/
			//y = y + space + 10;
			y = y + 12;

			g2dDateTime.translate(start, pageFormat.getImageableY());
			g2dDateTime.setFont(fontDateTime);
			g2dDateTime.drawString(invoice, x3, y);  //x2 replaced by x3 for Red Onion
			
			if (tablNo.equalsIgnoreCase("0")) { // for home delivery/parcel

				if (store.getParcelAddress().equalsIgnoreCase("Y")) {
					if (custName != null) {
						if (!custName.equalsIgnoreCase("")
								&& !custName.equalsIgnoreCase("null")
								&& custName.length() > 0) {
//							g2dDateTime.drawString("Cust. Name:"+custName, x3, y);//commented for red onion req
							g2dDateTime.drawString("Cust. Name:"+custName, x2, y);
						}
				}
						
					}
				}

			//y = y + space + 5;
			y = y + 12;
			g2dDateTime.translate(start, pageFormat.getImageableY());
			g2dDateTime.setFont(fontDateTime);
//			g2dDateTime.drawString(dateTable, x2, y);//commented for red onion req
			g2dDateTime.drawString(dateTable, x3, y);
			if (tablNo.equalsIgnoreCase("0")) { // for home delivery/parcel

				if (store.getParcelAddress().equalsIgnoreCase("Y")) {
					if (custPhone != null) {
						if (!custPhone.equalsIgnoreCase("")
								&& !custPhone.equalsIgnoreCase("null")
								&& custPhone.length() > 0) {
//							g2dDateTime.drawString("Ph. No:"+custPhone, x3, y);//commented for red onion req
							g2dDateTime.drawString("Ph. No:"+custPhone, x2, y);
							
						}
					}
						
					}
				}
			

			// print cashier name
			//y = y + space + 5;
			y = y +  12;
			g2dDateTime.translate(start, pageFormat.getImageableY());
			g2dDateTime.setFont(fontDateTime);
			//g2dDateTime.drawString(cashierString, x2, y);
			if (tablNo.equalsIgnoreCase("0")) { // for home delivery/parcel

				if (store.getParcelAddress().equalsIgnoreCase("Y")) {
					if (custAddress != null) {
						if (!custAddress.equalsIgnoreCase("")
								&& !custAddress.equalsIgnoreCase("null")
								&& custAddress.length() > 0) {
							
							if(custAddress.trim().length()>24){
								custAddress=custAddress.substring(0, 24);
							}
							String addr = "Addr: " + custAddress;
//							g2dDateTime.drawString(addr, x3, y);//commented for red onion req
							g2dDateTime.drawString(addr, x2, y);
						
						}
					}
						
					}
				}
			

			if (store.getId() == 37 || store.getId() == 38) {
				//y = y + space + 8;
				y = y +  8;
			} else {
				//y = y + space + 5;
				y = y +  5;
			}
			g2dDateTime.translate(start, pageFormat.getImageableY());
			g2dDateTime.setFont(fontTableNo);
			//g2dDateTime.drawString(tableNo, x2, y);
			if (tablNo.equalsIgnoreCase("0")) { // for home delivery/parcel

				if (store.getParcelAddress().equalsIgnoreCase("Y")) {
					if (custVatRegNo != null) {
						if (!custVatRegNo.equalsIgnoreCase("")
								&& !custVatRegNo.equalsIgnoreCase("null")
								&& custVatRegNo.length() > 0) {
//							g2dDateTime.drawString("Cust. VAT No.:"+custVatRegNo, x3, y);//commented for red onion req
							g2dDateTime.drawString("Cust. VAT No.:"+custVatRegNo, x2, y);
						}
					}
						
					}
				}
			
			
			
			
			y = y + space;//before table start
			g2d.drawString(
					"....................................................................................................................................................",
					x2, y);//before table start
			y = y + 3;
			g2dItemTitle.translate(start, pageFormat.getImageableY());
			g2dItemTitle.setFont(fontItemTitle);
			// g2dItemTitle.drawString(thrStrng, 0, y);

			/*
			 * String mod[][] = new String[][] { { "ITEM NAME", "QTY" }, {
			 * "Ceaser Salad", "1" }, { "MANCHOW SOUP", "10" }, {
			 * "Tomato Shorba", "4" }, { "Chicken Tikka W", "2" } };
			 */
			
			int cH = 0;
			int d3 = 0;
			int d33 = 0;
			int d4 = 0;
			int bx = 0;
		if(store.getRateInBill().equalsIgnoreCase("Y")){			//with rate
			d3=6;
			d33 = 4 * d3 + 10;
			d4 = 3 * d3;
//			bx = 170;
			bx = 165;
			
			String mod[][][];
			if (length == 1) {
				mod = new String[length + 1 + 2][length + 1 + 2][length + 1 + 2];
			} else {
				mod = new String[length + 2][length + 2][length + 2];
			}
			mod[0][0][0] = "Item Name/Description";
			mod[0][0][1] = "Qty";
			mod[0][0][2] = "Rate";
			mod[0][0][3] = "Amt";
			g2d.drawString(
					"....................................................................................................................................................",
					x2, y+18);
			int j = 1;
			Iterator<Integer> iterateKeys = keys.iterator();
			while (iterateKeys.hasNext()) {

				Integer itemId = (Integer) iterateKeys.next();
				System.out.println("item id is::  " + itemId);
				List<String> itemLst = itemsMap.get(itemId);
				String itemName = itemLst.get(0);
				String quantity = itemLst.get(1);
				System.out.println("item quantity is::  " + quantity);
				String totalPriceByItem = itemLst.get(2);
				String itemRate = itemLst.get(3);

				while (j < (mod.length - 1)) {
					mod[j][0][0] = itemName;
					mod[j][0][1] = quantity;
					mod[j][0][2] = itemRate;
					mod[j][0][3] = totalPriceByItem;
					break;
				}
				j = j + 1;
			}
			if (length > 1) {
				mod[j][0][0] = "";
				mod[j][0][1] = ".............";
				mod[j][0][2] = "";
				mod[j][0][3] = "";
			} else if (length == 1) {
				mod[j][0][0] = "";
				mod[j][0][1] = "";
				mod[j][0][2] = "";
				mod[j][0][3] = "";
			}
			
			y = y + space;

			for (int i = 0; i < 1; i++) {
				String itemname = mod[i][0][0];
				String quantity = mod[i][0][1];
				String itemRate = mod[i][0][2];
				String totalPriceByItem = mod[i][0][3];
				cH = (y) + (10 * i); // shifting drawing line
				if (i != (mod.length - 1)) {
					// g2dItemTitle.drawString(itemname, 00, cH);
					// g2dItemTitle.drawString(quantity, 121, cH);
					// g2dItemTitle.drawString(itemRate, 155, cH);
					// g2dItemTitle.drawString(totalPriceByItem, 165, cH);

					g2dItemTitle.drawString(itemname, x2, cH);
					g2dItemTitle.drawString(quantity, 100*three, cH);
					g2dItemTitle.drawString(itemRate, 129*three, cH);
					g2dItemTitle.drawString(totalPriceByItem, 168*three, cH);
				}

			}
			y = y + 5;

			g2dItem.translate(start, pageFormat.getImageableY());
			g2dItem.setFont(fontItem);

			
			g2d.setFont(font);
			for (int i = 1; i < mod.length; i++) { // items
				String itemname = mod[i][0][0];
				String quantity = mod[i][0][1];
				String itemRate = mod[i][0][2];
				String totalPriceByItem = mod[i][0][3];
				cH = (y) + (15 * i); // shifting drawing line
				if (i != (mod.length - 1)) {
					int length1 = itemname.length();
					if (length1 <= 48) {
						g2dItem.drawString(itemname, x2+00, cH);
						if (quantity.length() > 3) // d4 //rate
							g2dItem.drawString(quantity,97*three, cH);
						else if (quantity.length() > 2) // d4 //rate
							g2dItem.drawString(quantity,99*three, cH);
						else if (quantity.length() > 1) // d3
							g2dItem.drawString(quantity, 101*three, cH);
						else
							g2dItem.drawString(quantity, 103*three, cH);
						//g2dItem.drawString(quantity, 105*three, cH);

						if (itemRate.length() > 6) // d4 //rate
							g2dItem.drawString(itemRate,( bx - d33 - d4+8+d3)*three, cH);
						else if (itemRate.length() > 5) // d3
							g2dItem.drawString(itemRate, ((bx - d33 - d4+10+d3)*three), cH);
						else
							g2dItem.drawString(itemRate, (bx - d33 - d4 +12+d3)*three, cH);

						if (totalPriceByItem.length() > 8) // d4
							g2dItem.drawString(totalPriceByItem, (bx-4)*three, cH);
						else if (totalPriceByItem.length() > 7) // d4
							g2dItem.drawString(totalPriceByItem, (bx-2)*three, cH);
						else if (totalPriceByItem.length() > 6) // d4
							g2dItem.drawString(totalPriceByItem,( bx)*three, cH);
						else if (totalPriceByItem.length() > 5) // d3
							g2dItem.drawString(totalPriceByItem, (bx+2)*three, cH);
						else
							g2dItem.drawString(totalPriceByItem, (bx+4)*three, cH);

					} else {
						String temp = WordUtils.wrap(itemname, 48);
						String split1[] = temp.split("\\n");

						g2dItem.drawString(split1[0], x2+00, cH);
						for (int k = 1; k < split1.length; k++) {

							cH = (y + 10) + (15 * i);
							String name2 = split1[k];
							g2dItem.drawString(name2, x2+00, cH);
							
							y = y + 10;
						}
						if (quantity.length() > 3) // d4 //rate
							g2dItem.drawString(quantity,97*three, cH);
						else if (quantity.length() > 2) // d4 //rate
							g2dItem.drawString(quantity,99*three, cH);
						else if (quantity.length() > 1) // d3
							g2dItem.drawString(quantity, 101*three, cH);
						else
							g2dItem.drawString(quantity, 103*three, cH);
						
						// g2dItem.drawString(totalPriceByItem, 160, cH);

						if (itemRate.length() > 6) // d4 //rate
							g2dItem.drawString(itemRate, (bx - d33 - d4+8+d3)*three, cH);
						else if (itemRate.length() > 5) // d3
							g2dItem.drawString(itemRate, ((bx - d33 - d4+10+d3)*three), cH);
						else
							g2dItem.drawString(itemRate,(bx - d33 - d4 +12+d3)*three, cH);

						if (totalPriceByItem.length() > 8) // d4
							g2dItem.drawString(totalPriceByItem, (bx-4)*three, cH);
						else if (totalPriceByItem.length() > 7) // d4
							g2dItem.drawString(totalPriceByItem, (bx-2)*three, cH);
						else if (totalPriceByItem.length() > 6) // d4
							g2dItem.drawString(totalPriceByItem, (bx)*three, cH);
						else if (totalPriceByItem.length() > 5) // d3
							g2dItem.drawString(totalPriceByItem, (bx+2)*three, cH);
						else
							g2dItem.drawString(totalPriceByItem, (bx+4)*three, cH);


					}
				}
			}
		}
		else {		//without rate
			
			d3=6;
			//d33 = 4 * d3 + 10;
			d4 = 3 * d3;
//			bx = 170;
			bx = 165;
			
			String mod[][][];
			if (length == 1) {
				mod = new String[length + 1 + 2][length + 1 + 2][length + 1 + 2];
			} else {
				mod = new String[length + 2][length + 2][length + 2];
			}
			mod[0][0][0] = "Item Name";
			mod[0][0][1] = "Qty";
			mod[0][0][2] = "Amount";
			
			g2d.drawString(
					".......................................................................................................................................................",
					x2, y+20);
			int j = 1;
			Iterator<Integer> iterateKeys = keys.iterator();
			while (iterateKeys.hasNext()) {

				Integer itemId = (Integer) iterateKeys.next();
				System.out.println("item id is::  " + itemId);
				List<String> itemLst = itemsMap.get(itemId);
				String itemName = itemLst.get(0);
				String quantity = itemLst.get(1);
				System.out.println("item quantity is::  " + quantity);
				String totalPriceByItem = itemLst.get(2);
				

				while (j < (mod.length - 1)) {
					mod[j][0][0] = itemName;
					mod[j][0][1] = quantity;
					mod[j][0][2] = totalPriceByItem;
					
					break;
				}
				j = j + 1;
			}
			mod[j][0][0] = "";
			mod[j][0][1] = ".............";
			mod[j][0][2] = "";

		
			y = y + space;
	
			for (int i = 0; i < 1; i++) {
				String itemname = mod[i][0][0];
				String quantity = mod[i][0][1];
				String totalPriceByItem = mod[i][0][2];
				cH = (y) + (10 * i); // shifting drawing line
				if (i != (mod.length - 1)) {
					g2dItemTitle.drawString(itemname, x2+00, cH);
					g2dItemTitle.drawString(quantity, 121*three, cH);
					g2dItemTitle.drawString(totalPriceByItem, 155*three, cH);
				}

			}
			y = y + 5;

			g2dItem.translate(start, pageFormat.getImageableY());
			g2dItem.setFont(fontItem);

			d3 = 6;
			d4 = 2 * d3;

//			bx = 170;
			bx = 165;
			g2d.setFont(font);
			for (int i = 1; i < mod.length; i++) { // items
				String itemname = mod[i][0][0];
				String quantity = mod[i][0][1];
				String totalPriceByItem = mod[i][0][2];
				cH = (y) + (15 * i); // shifting drawing line
				if (i != (mod.length - 1)) {
					int length1 = itemname.length();
					if (length1 <= 20) {
						g2dItem.drawString(itemname, x2+00, cH);
						g2dItem.drawString(quantity, 131*three, cH);

						if (totalPriceByItem.length() > 6) // d4
							g2dItem.drawString(totalPriceByItem, (bx - d4)*three, cH);
						else if (totalPriceByItem.length() > 5) // d3
							g2dItem.drawString(totalPriceByItem, (bx - d3)*three, cH);
						else
							g2dItem.drawString(totalPriceByItem, bx*three, cH);

					} else {
						String temp = WordUtils.wrap(itemname, 20);
						String split1[] = temp.split("\\n");

						g2dItem.drawString(split1[0], x2+00, cH);
						g2dItem.drawString(quantity, 131*three, cH);
						// g2dItem.drawString(totalPriceByItem, 160, cH);

						if (totalPriceByItem.length() > 6) // d4
							g2dItem.drawString(totalPriceByItem, (bx - d4)*three, cH);
						else if (totalPriceByItem.length() > 5) // d3
							g2dItem.drawString(totalPriceByItem, (bx - d3)*three, cH);
						else
							g2dItem.drawString(totalPriceByItem, bx*three, cH);

						for (int k = 1; k < split1.length; k++) {

							cH = (y + 10) + (15 * i);
							String name2 = split1[k];
							g2dItem.drawString(name2, x2+00, cH);
							g2dItem.drawString("", 130*three, cH);
							g2dItem.drawString("", 165*three, cH);
							y = y + 10;
						}

					}
				}
			}
			
		}//else without rate
		

			g2d.translate(start, pageFormat.getImageableY());

			g2d.drawString(
					"....................................................................................................................................................................................",
					x2, cH);
			// g2d.drawString("", 170, cH);
			//bx = 165;

			if (store.getId() != 39) {
				if (store.getVatTaxText()!=null && store.getVatTaxText().trim().length() >0) {
					if (nonVatItemAmt != null) {
						double nonVatItemAmount = Double
								.parseDouble(nonVatItemAmt);
						if (nonVatItemAmount > 0.0) {
							cH = cH + 15; // non vat
							g2d.drawString(nonVatItemText.trim(), x2+00, cH);
							g2d.drawString(store.getCurrency().trim(), 129*three, cH);
							// g2d.drawString(nonVatItemAmt, bx, cH);
							if (nonVatItemAmt.length() > 8) // d4
								g2dItem.drawString(nonVatItemAmt, (bx-4)*three, cH);
							else if (nonVatItemAmt.length() > 7) // d4
								g2dItem.drawString(nonVatItemAmt, (bx-2)*three, cH);
							else
							if (nonVatItemAmt.length() > 6) // d4
								g2dItem.drawString(nonVatItemAmt, (bx)*three, cH);
							else if (nonVatItemAmt.length() > 5) // d3
								g2dItem.drawString(nonVatItemAmt,(bx+2)*three, cH);
							else
								g2dItem.drawString(nonVatItemAmt, (bx+4)*three, cH);
						}
					}
				}
			}
			//if (store.getId() != 37 && store.getId() != 38 && store.getId()!=59) {
				if (store.getVatTaxText().trim().length() > 0 && !store.getVatTaxText().trim().equalsIgnoreCase("GST")) {
					if (vatItemAmt != null) {
						double vatItemAmount = Double.parseDouble(vatItemAmt);
						if (vatItemAmount > 0.0) {
							cH = cH + 10; // vat
							g2d.drawString(vatItemText, x2+00, cH);
							g2d.drawString(store.getCurrency().trim(), 129*three, cH);
							// g2d.drawString(vatItemAmt, bx, cH);
							if (vatItemAmt.length() > 8) // d4
								g2dItem.drawString(vatItemAmt, (bx-4)*three, cH);
							else if (vatItemAmt.length() > 7) // d4
								g2dItem.drawString(vatItemAmt, (bx-2)*three, cH);
							else
							if (vatItemAmt.length() > 6) // d4
								g2dItem.drawString(vatItemAmt, (bx)*three, cH);
							else if (vatItemAmt.length() > 5) // d3
								g2dItem.drawString(vatItemAmt, (bx+2)*three, cH);
							else
								g2dItem.drawString(vatItemAmt, (bx+4)*three, cH);
						}
					}
				}
			//}

			if (store.getId() != 39) {//subtotal
				cH = cH + 10;
				g2d.drawString(totalAmtTxt, x2+00, cH);
				g2d.drawString(store.getCurrency().trim(), 129*three, cH);
				// g2d.drawString(totalAmtVal, bx, cH);
				if (totalAmtVal.length() > 8) // d4
				g2dItem.drawString(totalAmtVal, (bx-4)*three, cH);
			else if (totalAmtVal.length() > 7) // d4
				g2dItem.drawString(totalAmtVal, (bx-2)*three, cH);
			else
				if (totalAmtVal.length() > 6) // d4
					g2dItem.drawString(totalAmtVal, (bx)*three, cH);
				else if (totalAmtVal.length() > 5) // d3
					g2dItem.drawString(totalAmtVal, (bx +2)*three, cH);
				else
					g2dItem.drawString(totalAmtVal, (bx+4)*three, cH);
			}
			if (store.getVatTaxText() != null
					&& store.getVatTaxText().trim().length() > 1) {
				if (vatAmtVal != null) {
					double vatAmtValue = Double.parseDouble(vatAmtVal);
					if (vatAmtValue > 0.0) {
						cH = cH + 10;
						g2d.drawString(vatAmtTxt, x2+00, cH);
						g2d.drawString(store.getCurrency().trim(), 129*three, cH);
						// g2d.drawString(vatAmtVal, bx, cH);
						if (vatAmtVal.length() > 8) // d4
						g2dItem.drawString(vatAmtVal, (bx-4)*three, cH);
					else if (vatAmtVal.length() > 7) // d4
						g2dItem.drawString(vatAmtVal, (bx-2)*three, cH);
					else
						if (vatAmtVal.length() > 6) // d4
							g2dItem.drawString(vatAmtVal, (bx)*three, cH);
						else if (vatAmtVal.length() > 5) // d3
							g2dItem.drawString(vatAmtVal, (bx +2)*three, cH);
						else
							g2dItem.drawString(vatAmtVal, (bx+4)*three, cH);
						//cH = cH + 10;
					}
				}
			}
			cH=cH+10;
			if (store.getServiceTaxText() != null
					&& store.getServiceTaxText().trim().length() > 1) {
				List<String> serviceTaxLst = (List<String>) dataToPrnt[13];
				String servceTaxTxt = serviceTaxLst.get(0);
				String servceTaxVal = serviceTaxLst.get(1);

				g2d.drawString(servceTaxTxt, x2+00, cH);
				g2d.drawString(store.getCurrency().trim(), 129*three, cH);
				// g2d.drawString(servceTaxVal, bx, cH);
				if (servceTaxVal.length() > 8) // d4
				g2dItem.drawString(servceTaxVal, (bx-4)*three, cH);
			else if (servceTaxVal.length() > 7) // d4
				g2dItem.drawString(servceTaxVal, (bx-2)*three, cH);
			else
				if (servceTaxVal.length() > 6) // d4
					g2dItem.drawString(servceTaxVal,( bx )*three, cH);
				else if (servceTaxVal.length() > 5) // d3
					g2dItem.drawString(servceTaxVal, (bx +2)*three, cH);
				else
					g2dItem.drawString(servceTaxVal, (bx+4)*three, cH);
				cH = cH + 10;
			}
			if (store.getServiceChargeText() != null
					&& store.getServiceChargeText().trim().length() > 1) {
				//cH = cH + 10;

				g2d.drawString(servcChargeTxt, x2+00, cH);
				g2d.drawString(store.getCurrency().trim(), 129*three, cH);
				// g2d.drawString(servceTaxVal, bx, cH);
				if (servcChargeVal.length() > 8) // d4
				g2dItem.drawString(servcChargeVal, (bx-4)*three, cH);
			else if (servcChargeVal.length() > 7) // d4
				g2dItem.drawString(servcChargeVal, (bx-2)*three, cH);
			else
				if (servcChargeVal.length() > 6) // d4
					g2dItem.drawString(servcChargeVal, (bx)*three, cH);
				else if (servcChargeVal.length() > 5) // d3
					g2dItem.drawString(servcChargeVal, (bx+2)*three, cH);
				else
					g2dItem.drawString(servcChargeVal, (bx+4)*three, cH);
				cH=cH+10;
			}
			
			if (roundAdjVal != 0.00) {
				int bx1 = 160;
				String roundAdjStr = Double.toString(roundAdjVal);
				// double roundAdjValue = Double.parseDouble(roundAdjVal);
				// if (roundAdjValue > 0.0) {
				cH = cH + 2;
				g2d.drawString("Rounding Adj: ", x2+00, cH);
				g2d.drawString(store.getCurrency().trim(), 129*three, cH);
				// g2d.drawString(vatAmtVal, bx, cH);
				if (roundAdjVal < 0.00) {
					if (roundAdjStr.length() > 8) // d4
					g2dItem.drawString(roundAdjStr, (bx-4)*three, cH);
				else if (roundAdjStr.length() > 7) // d4
					g2dItem.drawString(roundAdjStr, (bx-2)*three, cH);
				else
					if (roundAdjStr.length() > 6) // d4
						g2dItem.drawString(roundAdjStr, (bx )*three, cH);
					else if (roundAdjStr.length() > 5) // d3
						g2dItem.drawString(roundAdjStr,( bx +2)*three, cH);
					else
						g2dItem.drawString(roundAdjStr, (bx+6)*three , cH);
				} else if (roundAdjVal > 0.00) {
					if (roundAdjStr.length() > 8) // d4
					g2dItem.drawString(roundAdjStr, (bx-4)*three, cH);
				else if (roundAdjStr.length() > 7) // d4
					g2dItem.drawString(roundAdjStr, (bx-2)*three, cH);
				else
					if (roundAdjStr.length() > 6) // d4
						g2dItem.drawString(roundAdjStr,( bx)*three, cH);
					else if (roundAdjStr.length() > 5) // d3
						g2dItem.drawString(roundAdjStr, ( bx +2)*three, cH);
					else
						g2dItem.drawString(roundAdjStr, (bx+6)*three, cH);
				}
				cH = cH + 10;
				// }
			}

			/*if (dscntVal != null) {
				double discount = Double.parseDouble(dscntVal);
				if (discount > 0.0) {
					g2d.drawString(dscntTxt, 00, cH);
					g2d.drawString(store.getCurrency().trim(), 124, cH);
					// g2d.drawString(dscntVal, bx, cH);
					if (dscntVal.length() > 6) // d4
						g2dItem.drawString(dscntVal, bx - d4, cH); // item
																	// discount
					else if (dscntVal.length() > 5) // d3
						g2dItem.drawString(dscntVal, bx - d3, cH);
					else
						g2dItem.drawString(dscntVal, bx, cH);
				}
			}

			if (custDisVal != null) {
				double custDisValue = Double.parseDouble(custDisVal);
				if (custDisValue > 0.0) {
					cH = cH + 10;
					g2d.drawString(custDiscTxt, 00, cH);
					g2d.drawString(store.getCurrency().trim(), 124, cH);
					if (custDisVal.length() > 6) // d4
						g2dItem.drawString(custDisVal, bx - d4, cH); // customer
																		// discount
					else if (custDisVal.length() > 5) // d3
						g2dItem.drawString(custDisVal, bx - d3, cH);
					else
						g2dItem.drawString(custDisVal, bx, cH);
				}
			}*/
			g2dItemTitle.translate(start, pageFormat.getImageableY());

			if (store.getId() == 37 || store.getId() == 38) {
				g2dItemTitle.setFont(fontItemTitleTotal);
			} else {
				fontItemTitle = new Font(store.getBillFontType(), Font.BOLD, 12);
				g2dItemTitle.setFont(fontItemTitle);
			}
			cH = cH + 10+5;
			
			g2dItemTitle.drawString(grossTxt, x2+00, cH);
			fontItemTitle = new Font(store.getBillFontType(), Font.BOLD, 11);
			g2dItemTitle.setFont(fontItemTitle);
			g2dItemTitle.drawString(store.getCurrency().trim(), 129*three, cH);
			// g2dItemTitle.drawString(grossVal, 168, cH);
			g2dItem.setFont(fontItemTitleTotal);
			if (grossVal.length() > 8) // d4
			g2dItem.drawString(grossVal, (bx-8)*three, cH);
		else if (grossVal.length() > 7) // d4
			g2dItem.drawString(grossVal, (bx-6)*three, cH);
		else
			if (grossVal.length() > 6) // d4
				g2dItem.drawString(grossVal,( bx-4)*three, cH);
			else if (grossVal.length() > 5) // d3
				g2dItem.drawString(grossVal, (bx-2 )*three, cH);
			else
				g2dItem.drawString(grossVal,( bx)*three, cH);
			
			/************changes made due to change in item and customer discount position
			 * 
			 */
			cH = cH + 10;
			g2dItem.setFont(fontItem);
			g2d.setFont(font);
			
			if (dscntVal != null) {
				double discount = Double.parseDouble(dscntVal);
				if (discount > 0.0) {
					g2d.drawString(dscntTxt, x2+00, cH);
					g2d.drawString(store.getCurrency().trim(), 129*three, cH);
					// g2d.drawString(dscntVal, bx, cH);
					if (dscntVal.length() > 8) // d4
					g2dItem.drawString(dscntVal, (bx-4)*three, cH);
				else if (dscntVal.length() > 7) // d4
					g2dItem.drawString(dscntVal, (bx-2)*three, cH);
				else
					if (dscntVal.length() > 6) // d4
						g2dItem.drawString(dscntVal, (bx)*three, cH); // item
																	// discount
					else if (dscntVal.length() > 5) // d3
						g2dItem.drawString(dscntVal, (bx+2 )*three, cH);
					else
						g2dItem.drawString(dscntVal, (bx+4)*three, cH);
				}
			}

			if (custDisVal != null) {
				double custDisValue = Double.parseDouble(custDisVal);
				if (custDisValue > 0.0) {
					cH = cH + 10;
					g2d.drawString(custDiscTxt, x2+00, cH);
					g2d.drawString(store.getCurrency().trim(), 129*three, cH);
					if (custDisVal.length() > 8) // d4
					g2dItem.drawString(custDisVal, (bx-4)*three, cH);
				else if (custDisVal.length() > 7) // d4
					g2dItem.drawString(custDisVal, (bx-2)*three, cH);
				else
					if (custDisVal.length() > 6) // d4
						g2dItem.drawString(custDisVal,( bx )*three, cH); // customer
																		// discount
					else if (custDisVal.length() > 5) // d3
						g2dItem.drawString(custDisVal, (bx+2)*three, cH);
					else
						g2dItem.drawString(custDisVal, (bx+4)*three, cH);
				}
			}
			
			/************changes made due to change in item and customer discount position
			 * 
			 */

			cH = cH + 10;
			g2d.drawString(
					"....................................................................................................................................................................................",
					x2, cH);
			fontItemTitle = new Font(store.getBillFontType(), Font.PLAIN, 12);
			g2dItemTitle.setFont(fontItemTitle);

			if (paidAmtVal.length() > 0) {
				double paidAmt = Double.parseDouble(paidAmtVal);
				if (paidAmt > 0.0) {
					cH = cH + 15; // print paid amount
					g2dItemTitle.drawString(paidAmtTxt, x2+00, cH);
					g2dItemTitle.drawString(store.getCurrency().trim(), 129*three, cH);
					if (paidAmtVal.length() > 8) // d4
					g2dItem.drawString(paidAmtVal, (bx-4)*three, cH);
				else if (paidAmtVal.length() > 7) // d4
					g2dItem.drawString(paidAmtVal, (bx-2)*three, cH);
				else
					if (paidAmtVal.length() > 6) // d4
						g2dItemTitle.drawString(paidAmtVal,( bx)*three, cH);
					else if (paidAmtVal.length() > 5) // d3
						g2dItemTitle.drawString(paidAmtVal, (bx+2 )*three, cH);
					else
						g2dItemTitle.drawString(paidAmtVal,( bx +4)*three, cH);
				}
			}
			if (amtToPayVal.length() > 0) {
				double amtToPay = Double.parseDouble(amtToPayVal);
				if (amtToPay > 0.0) {
					//amtToPayTxt = amtToPayTxt + "(" + store.getCurrency() + ")";
					cH = cH + 15; // print amount to pay
					g2dItemTitle.drawString(amtToPayTxt, x2+00, cH);
					g2dItemTitle.drawString(store.getCurrency().trim(), 129*three, cH);
					if (amtToPayVal.length() > 8) // d4
					g2dItem.drawString(amtToPayVal, (bx-4)*three, cH);
				else if (amtToPayVal.length() > 7) // d4
					g2dItem.drawString(amtToPayVal, (bx-2)*three, cH);
				else
					if (amtToPayVal.length() > 6) // d4
						g2dItemTitle.drawString(amtToPayVal, (bx )*three, cH);
					else if (amtToPayVal.length() > 5) // d3
						g2dItemTitle.drawString(amtToPayVal, (bx +2)*three, cH);
					else
						g2dItemTitle.drawString(amtToPayVal, (bx + 4)*three, cH);
				}
			}
			if (tenderAmtVal.length() > 0) {
				double tenderAmt = Double.parseDouble(tenderAmtVal);
				if (tenderAmt > 0.0) {
					cH = cH + 15; // print tender amount
					g2dItemTitle.drawString(tenderAmtTxt, x2+00, cH);
					g2dItemTitle.drawString(store.getCurrency().trim(), 129*three, cH);
					if (tenderAmtVal.length() > 8) // d4
					g2dItem.drawString(tenderAmtVal, (bx-4)*three, cH);
				else if (tenderAmtVal.length() > 7) // d4
					g2dItem.drawString(tenderAmtVal, (bx-2)*three, cH);
				else
					if (tenderAmtVal.length() > 6) // d4
						g2dItemTitle.drawString(tenderAmtVal, (bx )*three, cH);
					else if (tenderAmtVal.length() > 5) // d3
						g2dItemTitle.drawString(tenderAmtVal, (bx+2)*three, cH);
					else
						g2dItemTitle.drawString(tenderAmtVal,( bx+ 4)*three, cH);
				}
			}
			cH = cH + 15; // print payment type
			g2dItemTitle.drawString(paymntTypeVal, 00+x2, cH);

			if (refundAmtVal.length() > 0) {
				double refundAmt = Double.parseDouble(refundAmtVal);
				if (refundAmt > 0.0) {
					cH = cH + 15; // print refund amount
					g2dItemTitle.drawString(refundAmtTxt, x2+00, cH);
					g2dItemTitle.drawString(store.getCurrency().trim(), 129*three, cH);
					if (refundAmtVal.length() > 8) // d4
					g2dItem.drawString(refundAmtVal, (bx-4)*three, cH);
				else if (refundAmtVal.length() > 7) // d4
					g2dItem.drawString(refundAmtVal, (bx-2)*three, cH);
				else
					if (refundAmtVal.length() > 6) // d4
						g2dItemTitle.drawString(refundAmtVal, (bx)*three, cH);
					else if (refundAmtVal.length() > 5) // d3
						g2dItemTitle.drawString(refundAmtVal, (bx+2 )*three, cH);
					else
						g2dItemTitle.drawString(refundAmtVal, (bx +4)*three, cH);
				}
			}
			
			//print waiter name
			String waiter="waiter";
			if(waiterName!=null){
				if(!waiterName.equalsIgnoreCase("") && waiterName.length()>0){
					cH = cH + 10; 
					g2dItemTitle.drawString(waiter+" :"+waiterName, 00, cH);
					
				}
			}
			
			if (store.getId() == 37 || store.getId() == 38) {
				g2d.setFont(fontTableNo);
			} else {
				g2d.setFont(fontItem);
			}

			// print server name for saravanna
			if (store.getId() == 37 || store.getId() == 38) {
				cH = cH + 10;
				g2d.setFont(fontItem);
				g2d.drawString(serverString, x2+00, cH);
			}
			
			if (tablNo.equalsIgnoreCase("0")) {
				if (store.getParcelVat().equalsIgnoreCase("Y")) {
					
					if (store.getVatRegNo() != null
							&& store.getVatRegNo().trim().length() > 1) {
						//y = y + space + 3;
						//y = y + 3;
						cH = cH + 3;
						g2d.translate(start, pageFormat.getImageableY());
						g2d.setFont(font);
						g2d.drawString(vatRegNo, x2, cH);

					}
				}
				
				//y = y + space + 3;
				//y = y + 3;
				cH = cH + 5;
				if (store.getParcelServiceTax().equalsIgnoreCase("Y")) {
					if (store.getServiceTaxNo() != null
							&& store.getServiceTaxNo().trim().length() > 1) {
						g2d.translate(start, pageFormat.getImageableY());
						g2d.setFont(font);
						g2d.drawString(serviceTaxNo, x2, cH);

					}
				}
				
			} else if (!tablNo.equalsIgnoreCase("0")) {
				
				if (store.getVatRegNo() != null
						&& store.getVatRegNo().trim().length() > 1) {
					//y = y + space + 3;
					//y = y + 3;
					cH = cH + 3;
					g2d.translate(start, pageFormat.getImageableY());
					g2d.setFont(font);
					g2d.drawString(vatRegNo, x2, cH);

				}
				

				if (store.getServiceTaxNo() != null
						&& store.getServiceTaxNo().trim().length() > 1) {
					//y = y + space + 3;
					//y = y + 3;
					cH = cH + 3;
					g2d.translate(start, pageFormat.getImageableY());
					g2d.setFont(font);
					g2d.drawString(serviceTaxNo, x2, cH);

				}
			

			}

			
			if (billFooterText != null && !billFooterText.equalsIgnoreCase("")
					&& billFooterText.length() > 0) {
				cH = cH + 15;
				g2d.setFont(fontItem);
				String bilFootrTxt = WordUtils.wrap(billFooterText, 40);
				String splitFooter[] = bilFootrTxt.split("\\n");

				for (int k = 0; k < splitFooter.length; k++) {
					g2d.drawString(splitFooter[k], x2+0, cH);
					cH = cH + 12;
				}
			}

			if (thanksLine1 != null && !thanksLine1.equalsIgnoreCase("")
					&& thanksLine1.length() > 0) {
				cH = cH + 15+10;
				g2d.setFont(fontItem);
				g2d.drawString(thanksLine1, (75*3)+00, cH);
			}

			if (thanksLine2 != null && !thanksLine2.equalsIgnoreCase("")
					&& thanksLine2.length() > 0) {
				cH = cH + 15+10;
				g2d.setFont(fontItem);
				g2d.drawString(thanksLine2, (75*3)+30, cH);
			}

			if(storeId == 37 || storeId == 38)return Printable.PAGE_EXISTS;
				
			Calendar cal = Calendar.getInstance();
			cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
			String currentTime = sdf.format(cal.getTime());

			cH = cH + 10;
			g2d.setFont(font);
			g2d.drawString(currentTime + "  " + "(" + userId + ")", (75*3)+00, cH);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Printable.PAGE_EXISTS;
	}

	public void setPrintData(Object[] data, int storeId) {

		this.printData = data;
		this.storeId = storeId;

	}

	public Object[] getPrintData() {

		return printData;

	}
}
