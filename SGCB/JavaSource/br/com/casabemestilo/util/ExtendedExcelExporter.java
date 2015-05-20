package br.com.casabemestilo.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

import org.primefaces.component.celleditor.CellEditor;
import org.primefaces.component.columns.Columns;
import org.primefaces.component.export.ExcelExporter;
import org.primefaces.component.outputlabel.OutputLabel;

import com.sun.faces.facelets.component.UIRepeat;

public class ExtendedExcelExporter extends ExcelExporter {

	@Override
    protected String exportValue(FacesContext context, UIComponent component) {
        if (component instanceof CellEditor) {
            return exportValue(context, ((CellEditor) component).getFacet("output"));
        }
        else if (component instanceof HtmlGraphicImage) {
            return (String) component.getAttributes().get("alt");
        }else if (component instanceof UIRepeat) {
        	List<UIComponent> filhos = new ArrayList<UIComponent>();
        	String filho = ""; 
        	filhos = ((UIRepeat) component).getChildren();
        	
        	for (UIComponent componenteFilho : filhos) {        		
        		if(componenteFilho instanceof HtmlOutputText){
        			HtmlOutputText value = (HtmlOutputText) componenteFilho;
        			
        			if(value.getValue() != null){        				
        				if(value.getValue() instanceof Float){
    						DecimalFormat decFormat = new DecimalFormat("0.00");
    						value.setValue(decFormat.format(value.getValue()));       						
    					}            			
        				if(filho.equals("")){
                			filho = value.getValue().toString();
                		}else{
                			filho += " :: " + value.getValue().toString();
                		}
        			}        			        			
        		}else{
        			//filho = "[UIRepeat] Um ou mais componentes filhos não suportado! " + componenteFilho.getRendererType();
        		}        		        	
			}        	
        	return filho;
        }else if (component instanceof HtmlPanelGrid){
        	List<UIComponent> filhos = new ArrayList<UIComponent>();
        	String filho = ""; 
        	filhos = ((HtmlPanelGrid) component).getChildren();
        	
        	for (UIComponent componenteFilho : filhos) {
        		if(componenteFilho instanceof HtmlOutputText){
        			HtmlOutputText value = (HtmlOutputText) componenteFilho;
        			if(value.getValue() != null){
        				if(value.getValue() instanceof Date){
        					DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("pt", "BR"));
        					value.setValue(df.format(value.getValue()));
        				}
        				if(filho.equals("")){        			
                			filho = value.getValue().toString();
                		}else{
                			filho += " - " + value.getValue().toString();
                		}
        			}        			
        		}else{
        			filho = "[HtmlPanelGrid] Um ou mais componentes filhos não suportado!";
        		}        		        	
			}        	
        	return filho;
        }
        else {
            return super.exportValue(context, component);
        }
    }
}
