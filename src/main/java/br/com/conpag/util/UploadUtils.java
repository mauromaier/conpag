package br.com.conpag.util;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class UploadUtils {
	
	
	private HttpServletRequest request;
	
	private Map<String,String> itens;
	private InputStream file;
	
//	public UploadUtils( HttpServletRequest request ){
//		this.request = request;
//		boolean isMultipart = ServletFileUpload.isMultipartContent( request );  
//		if (isMultipart) {
//			this.itens = new HashMap <String,String>();
//			this.parseRequest();
//		}
//	}
	
	public InputStream getUploadedFile(){
		return this.file;
	}
	
	public String getFormItem(String item){
		return itens.get( item ); 
	}
//	
//	private void parseRequest(){
//		try{
//			// Create a factory for disk-based file items
//			FileItemFactory factory = new DiskFileItemFactory();
//			// Create a new file upload handler
//			ServletFileUpload upload = new ServletFileUpload(factory);
//			// Parse the request   
//			List<FileItem> items = upload.parseRequest( request );
//			Iterator it = items.iterator();
//			Iterator itForm = items.iterator();
//			
//			//itera pelo form buscando o texto
//			while ( itForm.hasNext() ) { 
//			    FileItem fitem = (FileItem) itForm.next(); 
//			    	if ( fitem.isFormField() ) {
//			    		this.itens.put( fitem.getFieldName() , fitem.getString() );
//			    	}
//			    	else {
//			    		//salva em disco
//			    		//File temp = new File( this.context.getRealPath("/") + fitem.getName() );
//			    		//fitem.write( temp );
//			    		//deleta o arquivo temporario
//			        	 //if ( temp.exists() ) {
//			        	 //	temp.delete();
//			        	 //}
//			    		
//			    		//mantem em stream
//			    		this.file = fitem.getInputStream();
//			    	}
//			 }
//		}
//		catch ( Exception e ){
//			e.printStackTrace();
//		}
//	}
}
