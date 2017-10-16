package br.com.conpag.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public class RestEasyMultipartFormParser {

	private MultipartFormDataInput input;
	
	private InputStream arquivo;
	private String nomeArquivo;
	private MediaType mimetype;
	
	private String model;

	public void init( MultipartFormDataInput input ) throws IOException{
		this.input = input;
		this.parse();
	}
	
	
	private void parse() throws IOException{
		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		List<InputPart> inPart = formParts.get("file");

		for (InputPart inputPart : inPart) {
			MultivaluedMap<String, String> headers = inputPart.getHeaders();
			this.nomeArquivo = this.parseFileName(headers);
			
			InputStream arquivo = inputPart.getBody(InputStream.class,null);
			this.arquivo = arquivo;
			
			this.mimetype = inputPart.getMediaType();
		}
	}
	
	public String getFormData( String name ) throws IOException {
		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		List<InputPart> inPartModel = formParts.get(name);
		for (InputPart inputPart : inPartModel) {
			return inputPart.getBodyAsString();
		}
		return null;
	}
	

	public InputStream getFile() {
		return this.arquivo;
	}
	
	public String getNomeArquivo(){
		return this.nomeArquivo;
	}

	public byte [] getFileAsBytes() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = this.arquivo.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		return buffer.toByteArray();
	}

	// Parse Content-Disposition header to get the original file name
	private String parseFileName(MultivaluedMap<String, String> headers) {
		String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
		for (String name : contentDispositionHeader) {
			if ((name.trim().startsWith("filename"))) {
				String[] tmp = name.split("=");
				String fileName = tmp[1].trim().replaceAll("\"","");
				return fileName;
			}
		}
		return null;
	}

	public MediaType getMimetype() {
		return mimetype;
	}

	public String getModel() {
		return model;
	}

	
	
}
