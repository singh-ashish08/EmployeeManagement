package com.emp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/convert")
public class DocToPdfController {

	/*
	 * @PostMapping(value = "/doc-to-pdf", consumes =
	 * MediaType.MULTIPART_FORM_DATA_VALUE) public void
	 * convertDocToPdf(@RequestParam("file") MultipartFile file, HttpServletResponse
	 * response) throws IOException { if (file.isEmpty()) { throw new
	 * RuntimeException("Uploaded file is empty."); }
	 * 
	 * String text = extractTextFromWord(file); if (text == null || text.isBlank())
	 * { throw new RuntimeException("Failed to extract text from Word file."); }
	 * 
	 * // Create PDF using PDFBox try (PDDocument pdfDoc = new PDDocument()) {
	 * PDPage page = new PDPage(); pdfDoc.addPage(page);
	 * 
	 * PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, page);
	 * contentStream.beginText(); contentStream.setFont(PDType1Font.HELVETICA, 12);
	 * contentStream.setLeading(14.5f); contentStream.newLineAtOffset(25, 700);
	 * 
	 * String[] lines = text.split("\n"); for (String line : lines) { // Remove or
	 * replace tab characters String cleanLine = line.replace("\t", "    "); // or
	 * just "" contentStream.showText(cleanLine); contentStream.newLine(); }
	 * 
	 * 
	 * contentStream.endText(); contentStream.close();
	 * 
	 * // Set response headers response.setContentType("application/pdf");
	 * response.setHeader("Content-Disposition",
	 * "attachment; filename=converted.pdf");
	 * 
	 * pdfDoc.save(response.getOutputStream()); } }
	 * 
	 * private String extractTextFromWord(MultipartFile file) { String fileName =
	 * file.getOriginalFilename(); try (InputStream is = file.getInputStream()) { if
	 * (fileName.endsWith(".docx")) { XWPFDocument docx = new XWPFDocument(is);
	 * XWPFWordExtractor extractor = new XWPFWordExtractor(docx); return
	 * extractor.getText(); } else if (fileName.endsWith(".doc")) { HWPFDocument doc
	 * = new HWPFDocument(is); WordExtractor extractor = new WordExtractor(doc);
	 * return extractor.getText(); } else { throw new
	 * IllegalArgumentException("Only .doc and .docx files are supported."); } }
	 * catch (IOException e) { e.printStackTrace(); return null; }
	 */
	// }

	@PostMapping(value = "/doc-to-pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void convertDocToPdf(@RequestParam("file") MultipartFile file, HttpServletResponse response)
			throws IOException {
		if (file.isEmpty()) {
			throw new RuntimeException("Uploaded file is empty.");
		}

		String text = extractTextFromWord(file);
		if (text == null || text.isBlank()) {
			throw new RuntimeException("Failed to extract text from Word file.");
		}

		// Create PDF using PDFBox
		try (PDDocument pdfDoc = new PDDocument()) {
			PDPage page = new PDPage();
			pdfDoc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.setLeading(14.5f);
			contentStream.newLineAtOffset(25, 700);

			String[] lines = text.split("\n");
			for (String line : lines) {
				String cleanLine = line.replace("\t", "    ") // replace tabs with spaces
						.replaceAll("[\\r\\u0000-\\u001F]", " "); // replace all control characters
				contentStream.showText(cleanLine);
				contentStream.newLine();
			}

			contentStream.endText();
			contentStream.close();

			// Set headers to force download
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=converted.pdf");

			// Stream to browser
			pdfDoc.save(response.getOutputStream());
		}
	}// byte array resource

	private String extractTextFromWord(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		try (InputStream is = file.getInputStream()) {
			if (fileName.endsWith(".docx")) {
				XWPFDocument docx = new XWPFDocument(is);
				XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
				return extractor.getText();
			} else if (fileName.endsWith(".txt")) {
				return new String(is.readAllBytes(), StandardCharsets.UTF_8);
			} else if (fileName.endsWith(".doc")) {
				HWPFDocument doc = new HWPFDocument(is);
				WordExtractor extractor = new WordExtractor(doc);
				return extractor.getText();
			}

			else {
				throw new IllegalArgumentException("Only .doc and .docx files are supported.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
