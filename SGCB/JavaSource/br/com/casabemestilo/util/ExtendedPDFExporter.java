package br.com.casabemestilo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;

import org.primefaces.component.celleditor.CellEditor;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.PDFExporter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

public class ExtendedPDFExporter extends PDFExporter {

	public ExtendedPDFExporter() {}

	@Override
    protected String exportValue(FacesContext context, UIComponent component) {
        if (component instanceof CellEditor) {
            return exportValue(context, ((CellEditor) component).getFacet("output"));
        }
        else if (component instanceof HtmlGraphicImage) {
            return (String) component.getAttributes().get("alt");
        }
        else {
            return super.exportValue(context, component);
        }
    }
	
	@Override
	public void export(FacesContext context, DataTable table, String filename, boolean pageOnly, boolean selectionOnly, String encodingType, MethodExpression preProcessor, MethodExpression postProcessor) throws IOException { 
		try {
	        Document document = new Document();
	        document.setPageSize(PageSize.A4.rotate());
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);
	        
	        if(preProcessor != null) {
	    		preProcessor.invoke(context.getELContext(), new Object[]{document});
	    	}

            if(!document.isOpen()) {
                document.open();
            }
	        
	    	document.add(super.exportPDFTable(context, table, pageOnly, selectionOnly, encodingType));
	    	
	    	if(postProcessor != null) {
	    		postProcessor.invoke(context.getELContext(), new Object[]{document});
	    	}
	    	
	        document.close();
	    	
	        writePDFToResponse(context.getExternalContext(), baos, filename);
	        
		} catch(DocumentException e) {
			throw new IOException(e.getMessage());
		}
	}
		
}
