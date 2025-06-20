package com.emp.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WebConvertController {

	@GetMapping("/")
	public String home() {
		return "convert"; // Loads convert.html
	}

	@PostMapping("/convert-pdf")
	public String handleUpload(@RequestParam("file") MultipartFile file, Model model) {
		try {
			byte[] pdfBytes = convertDocToPdf(file.getBytes(), file.getOriginalFilename());

			// Save to temporary location
			String filename = UUID.randomUUID() + ".pdf";
			Path pdfPath = Paths.get(System.getProperty("java.io.tmpdir"), filename);
			Files.write(pdfPath, pdfBytes);

			model.addAttribute("downloadLink", "/download/" + filename);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "❌ Failed to convert file: " + e.getMessage());
		}
		return "convert";
	}

	@GetMapping("/download/{filename}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
		Path file = Paths.get(System.getProperty("java.io.tmpdir")).resolve(filename);
		Resource resource = new UrlResource(file.toUri());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=converted.pdf")
				.contentType(MediaType.APPLICATION_PDF).body(resource);
	}

	public byte[] convertDocToPdf(byte[] wordBytes, String fileName) throws IOException {
		String text;

		try (InputStream is = new ByteArrayInputStream(wordBytes)) {
			if (fileName.endsWith(".docx")) {
				XWPFDocument docx = new XWPFDocument(is);
				text = new XWPFWordExtractor(docx).getText();
			} else if (fileName.endsWith(".doc")) {
				HWPFDocument doc = new HWPFDocument(is);
				text = new WordExtractor(doc).getText();
			} else {
				throw new IllegalArgumentException("Unsupported file format: " + fileName);
			}
		}

		try (PDDocument pdfDoc = new PDDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			PDPage page = new PDPage();
			pdfDoc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.setLeading(14.5f);
			contentStream.newLineAtOffset(25, 700);

			for (String line : text.split("\n")) {
				String cleanLine = line.replaceAll("[\\r\\t\\u0000-\\u001F]", " ");
				contentStream.showText(cleanLine);
				contentStream.newLine();
			}

			contentStream.endText();
			contentStream.close();

			pdfDoc.save(out);
			return out.toByteArray();
		}
	}
}
