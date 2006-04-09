package se.idega.idegaweb.commune.adulteducation.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import se.idega.idegaweb.commune.business.CommuneUserBusiness;
import se.idega.idegaweb.commune.presentation.CommuneBlock;
import com.idega.block.school.data.SchoolClass;
import com.idega.block.school.data.SchoolClassMember;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.core.contact.data.Phone;
import com.idega.core.location.data.Address;
import com.idega.core.location.data.PostalCode;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.idegaweb.IWUserContext;
import com.idega.io.MediaWritable;
import com.idega.io.MemoryFileBuffer;
import com.idega.io.MemoryInputStream;
import com.idega.io.MemoryOutputStream;
import com.idega.presentation.IWContext;
import com.idega.user.data.User;
import com.idega.util.IWTimestamp;
import com.idega.util.PersonalIDFormatter;
import com.idega.util.text.Name;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      idega multimedia
 * @author       <a href="mailto:aron@idega.is">aron@idega.is</a>
 * @version 1.0
 */
public class GroupFileWriter implements MediaWritable {

	private MemoryFileBuffer buffer = null;
	private Locale locale;
	private CommuneUserBusiness userBusiness;
	private SchoolClass schoolClass;
	private IWResourceBundle iwrb;
	
	public final static String prmPrintType = "print_type";
	public final static String XLS = "xls";
	public final static String PDF = "pdf";
	
	public GroupFileWriter() {
	}
	
	public void init(HttpServletRequest req, IWContext iwc) {
		try {
			this.locale = iwc.getApplicationSettings().getApplicationLocale();
			this.iwrb = iwc.getIWMainApplication().getBundle(CommuneBlock.IW_BUNDLE_IDENTIFIER).getResourceBundle(this.locale);
			this.schoolClass = getSession(iwc).getSchoolClass();
			this.userBusiness = getUserBusiness(iwc);
			
			String type = req.getParameter(prmPrintType);
			if (type.equals(PDF)) {
				this.buffer = writePDF(iwc, this.schoolClass);
			}
			else if (type.equals(XLS)) {
				this.buffer = writeXLS(iwc, this.schoolClass);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getMimeType() {
		if (this.buffer != null) {
			return this.buffer.getMimeType();
		}
		return "application/pdf";
	}
	
	public void writeTo(OutputStream out) throws IOException {
		if (this.buffer != null) {
			MemoryInputStream mis = new MemoryInputStream(this.buffer);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (mis.available() > 0) {
				baos.write(mis.read());
			}
			baos.writeTo(out);
		}
		else {
			System.err.println("buffer is null");
		}
	}
	
	public MemoryFileBuffer writeXLS(IWContext iwc, SchoolClass schoolClass) throws Exception {
		MemoryFileBuffer buffer = new MemoryFileBuffer();
		MemoryOutputStream mos = new MemoryOutputStream(buffer);
		Collection students = getBusiness(iwc).getSchoolBusiness().getSchoolClassMemberHome().findAllBySchoolClass(schoolClass);

		if (!students.isEmpty()) {
	    HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFSheet sheet = wb.createSheet(schoolClass.getName());
	    sheet.setColumnWidth((short)0, (short) (30 * 256));
	    sheet.setColumnWidth((short)1, (short) (14 * 256));
	    sheet.setColumnWidth((short)2, (short) (30 * 256));
	    sheet.setColumnWidth((short)3, (short) (14 * 256));
			sheet.setColumnWidth((short)4, (short) (14 * 256));
			sheet.setColumnWidth((short)5, (short) (14 * 256));
			sheet.setColumnWidth((short)6, (short) (14 * 256));
	    HSSFFont font = wb.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    font.setFontHeightInPoints((short)12);
	    HSSFCellStyle style = wb.createCellStyle();
	    style.setFont(font);

			int cellRow = 0;
			HSSFRow row = sheet.createRow(cellRow++);
			HSSFCell cell = row.createCell((short)0);
	    cell.setCellValue(this.iwrb.getLocalizedString("name","Name"));
	    cell.setCellStyle(style);
	    cell = row.createCell((short)1);
	    cell.setCellValue(this.iwrb.getLocalizedString("personal_id","Personal ID"));
	    cell.setCellStyle(style);
	    cell = row.createCell((short)2);
	    cell.setCellValue(this.iwrb.getLocalizedString("address","Address"));
	    cell.setCellStyle(style);
			cell = row.createCell((short)3);
			cell.setCellValue(this.iwrb.getLocalizedString("postal_code","Postal code"));
			cell.setCellStyle(style);
	    cell = row.createCell((short)4);
	    cell.setCellValue(this.iwrb.getLocalizedString("phone","Phone"));
	    cell.setCellStyle(style);
			cell = row.createCell((short)5);
			cell.setCellValue(this.iwrb.getLocalizedString("start_date","Start date"));
			cell.setCellStyle(style);
	    cell = row.createCell((short)6);
	    cell.setCellValue(this.iwrb.getLocalizedString("end_date","End date"));
	    cell.setCellStyle(style);

			User student;
			Address address;
			PostalCode postalCode = null;
			Phone phone;
			SchoolClassMember studentMember;
			
			Iterator iter = students.iterator();
			while (iter.hasNext()) {
				row = sheet.createRow(cellRow++);
				studentMember = (SchoolClassMember) iter.next();
				student = studentMember.getStudent();
				address = this.userBusiness.getUsersMainAddress(student);
				if (address != null) {
					postalCode = address.getPostalCode();
				}
				phone = this.userBusiness.getChildHomePhone(student);
				IWTimestamp start = new IWTimestamp(studentMember.getRegisterDate());
				IWTimestamp end = studentMember.getRemovedDate() != null ? new IWTimestamp(studentMember.getRemovedDate()) : null;

				Name name = new Name(student.getFirstName(), student.getMiddleName(), student.getLastName());
		    row.createCell((short)0).setCellValue(name.getName(this.locale, true));
		    row.createCell((short)1).setCellValue(PersonalIDFormatter.format(student.getPersonalID(), this.locale));
		    if (address != null) {
			    row.createCell((short)2).setCellValue(address.getStreetAddress());
			    if (postalCode != null) {
						row.createCell((short)3).setCellValue(postalCode.getPostalAddress());
					}
		    }
			  if (phone != null) {
					row.createCell((short)4).setCellValue(phone.getNumber());
				}
			  row.createCell((short)5).setCellValue(start.getLocaleDate(this.locale, IWTimestamp.SHORT));
			  if (end != null) {
					row.createCell((short)6).setCellValue(end.getLocaleDate(this.locale, IWTimestamp.SHORT));
				}
			}
			wb.write(mos);
		}
		buffer.setMimeType("application/x-msexcel");
		return buffer;
	}
	
	public MemoryFileBuffer writePDF(IWContext iwc, SchoolClass schoolClass) throws Exception {
		MemoryFileBuffer buffer = new MemoryFileBuffer();
		MemoryOutputStream mos = new MemoryOutputStream(buffer);
		Collection students = getBusiness(iwc).getSchoolBusiness().getSchoolClassMemberHome().findAllBySchoolClass(schoolClass);

		if (!students.isEmpty()) {
			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			PdfWriter writer = PdfWriter.getInstance(document, mos);
			document.addTitle(schoolClass.getName());
			document.addAuthor("Idega Reports");
			document.addSubject(schoolClass.getName());
			document.open();
			
			User student;
			Address address;
			PostalCode postalCode = null;
			Phone phone;
			SchoolClassMember studentMember;
			Cell cell;
			
			String[] headers = {this.iwrb.getLocalizedString("name","Name"), this.iwrb.getLocalizedString("personal_id","Personal ID"), this.iwrb.getLocalizedString("address","Address"), this.iwrb.getLocalizedString("postal_code","Postal code"), this.iwrb.getLocalizedString("phone","Phone")};
			int[] sizes = { 30, 14, 24, 20, 12 };

			Table datatable = getTable(headers, sizes);
			Iterator iter = students.iterator();
			while (iter.hasNext()) {
				studentMember = (SchoolClassMember) iter.next();
				student = studentMember.getStudent();
				address = this.userBusiness.getUsersMainAddress(student);
				if (address != null) {
					postalCode = address.getPostalCode();
				}
				phone = this.userBusiness.getChildHomePhone(student);

				Name name = new Name(student.getFirstName(), student.getMiddleName(), student.getLastName());
				cell = new Cell(new Phrase(name.getName(this.locale, true), new Font(Font.HELVETICA, 9, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				datatable.addCell(cell);

				cell = new Cell(new Phrase(PersonalIDFormatter.format(student.getPersonalID(), this.locale), new Font(Font.HELVETICA, 9, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				datatable.addCell(cell);

				String streetAddress = "";
				if (address != null) {
					streetAddress = address.getStreetAddress();
				}
				cell = new Cell(new Phrase(streetAddress, new Font(Font.HELVETICA, 9, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				datatable.addCell(cell);

				String postalAddress = "";
				if (address != null && postalCode != null) {
					postalAddress = postalCode.getPostalAddress();
				}
				cell = new Cell(new Phrase(postalAddress, new Font(Font.HELVETICA, 9, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				datatable.addCell(cell);

				String phoneNumber = "";
				if (phone != null) {
					phoneNumber = phone.getNumber();
				}
				cell = new Cell(new Phrase(phoneNumber, new Font(Font.HELVETICA, 9, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				datatable.addCell(cell);

				if (!writer.fitsPage(datatable)) {
					datatable.deleteLastRow();
					document.add(datatable);
					document.newPage();
					datatable = getTable(headers, sizes);
				}
			}
			document.add(datatable);
			document.close();
			writer.setPdfVersion(PdfWriter.VERSION_1_2);
		}
		
		buffer.setMimeType("application/pdf");
		return buffer;
	}
	
	private Table getTable(String[] headers, int[] sizes) throws BadElementException, DocumentException {
		Table datatable = new Table(headers.length);
		datatable.setPadding(0.0f);
		datatable.setSpacing(0.0f);
		datatable.setBorder(Rectangle.NO_BORDER);
		datatable.setWidth(100);
		if (sizes != null) {
			datatable.setWidths(sizes);
		}
		for (int i = 0; i < headers.length; i++) {
			Cell cell = new Cell(new Phrase(headers[i], new Font(Font.HELVETICA, 12, Font.BOLD)));
			cell.setBorder(Rectangle.BOTTOM);
			datatable.addCell(cell);
		}
		datatable.setDefaultCellBorderWidth(0);
		datatable.setDefaultCellBorder(Rectangle.NO_BORDER);
		datatable.setDefaultRowspan(1);
		return datatable;
	}

	private AdultEducationBusiness getBusiness(IWApplicationContext iwac) {
		try {
			return (AdultEducationBusiness) IBOLookup.getServiceInstance(iwac, AdultEducationBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private CommuneUserBusiness getUserBusiness(IWApplicationContext iwac) {
		try {
			return (CommuneUserBusiness) IBOLookup.getServiceInstance(iwac, CommuneUserBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

	private AdultEducationSession getSession(IWUserContext iwuc) {
		try {
			return (AdultEducationSession) IBOLookup.getSessionInstance(iwuc, AdultEducationSession.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}
}