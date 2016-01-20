package com.tut;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class EmployeeDao {

	private static SessionFactory factory;

	public List<Employee> getEmployees() {

		List<Employee> employees = null;
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			employees = castList(Employee.class,session.createQuery("FROM Employee")
					.list());
			
			for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
				Employee employee = (Employee) iterator.next();
				System.out.print("First Name: " + employee.getFirstName());
				System.out.print("  Last Name: " + employee.getLastName());
				System.out.println("  Salary: " + employee.getSalary());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return employees;
	}

	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> list = new ArrayList<T>(c.size());
		for (Object o : c)
			list.add(clazz.cast(o));

		return list;

	}

	public  ByteArrayOutputStream getEmployeesOutputStream(List<Employee> emps) {
		Document document = new Document();
		ByteArrayOutputStream baosPdf = new ByteArrayOutputStream();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					baosPdf);//new FileOutputStream("Employees.pdf"));
			document.open();

			PdfPTable table = new PdfPTable(3); // 3 columns.
			table.setWidthPercentage(100); // Width 100%
			table.setSpacingBefore(10f); // Space before table
			table.setSpacingAfter(10f); // Space after table

			// Set Column widths
			float[] columnWidths = { 1f, 1f, 1f };
			table.setWidths(columnWidths);

			for (int i = 0; i < emps.size(); i++) {
				PdfPCell cell1 = new PdfPCell(new Paragraph(emps.get(i)
						.getFirstName()));
				cell1.setBorderColor(BaseColor.BLUE);
				cell1.setPaddingLeft(10);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell1);

				PdfPCell cell2 = new PdfPCell(new Paragraph(emps.get(i)
						.getLastName()));
				cell1.setBorderColor(BaseColor.BLUE);
				cell1.setPaddingLeft(10);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell2);

				PdfPCell cell3 = new PdfPCell(new Paragraph(String.valueOf(emps.get(i)
						.getSalary())));
				cell1.setBorderColor(BaseColor.BLUE);
				cell1.setPaddingLeft(10);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell3);

			}
			document.add(table);

			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baosPdf;
	}
	
	public  void writeToPdfFile(List<Employee> emps) {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("Employees.pdf"));
			document.open();

			PdfPTable table = new PdfPTable(3); // 3 columns.
			table.setWidthPercentage(100); // Width 100%
			table.setSpacingBefore(10f); // Space before table
			table.setSpacingAfter(10f); // Space after table

			// Set Column widths
			float[] columnWidths = { 1f, 1f, 1f };
			table.setWidths(columnWidths);

			for (int i = 0; i < emps.size(); i++) {
				PdfPCell cell1 = new PdfPCell(new Paragraph(emps.get(i)
						.getFirstName()));
				cell1.setBorderColor(BaseColor.BLUE);
				cell1.setPaddingLeft(10);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell1);

				PdfPCell cell2 = new PdfPCell(new Paragraph(emps.get(i)
						.getLastName()));
				cell1.setBorderColor(BaseColor.BLUE);
				cell1.setPaddingLeft(10);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell2);

				PdfPCell cell3 = new PdfPCell(new Paragraph(String.valueOf(emps.get(i)
						.getSalary())));
				cell1.setBorderColor(BaseColor.BLUE);
				cell1.setPaddingLeft(10);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell3);

			}
			document.add(table);

			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
