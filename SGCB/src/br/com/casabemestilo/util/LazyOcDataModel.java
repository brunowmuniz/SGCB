package br.com.casabemestilo.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.casabemestilo.DAO.OcDAO;
import br.com.casabemestilo.model.Oc;



public class LazyOcDataModel extends LazyDataModel<Oc>{
	
	private List<Oc> listaLazyOc;
    
    public LazyOcDataModel() {
   
    }
    
    @Override
    public Oc getRowData(String idOc) {
    	Integer id = Integer.valueOf(idOc);
    	
        for(Oc oc : listaLazyOc) {
            if(oc.getId().equals(id))
                return oc;
        }
        
        return null;
    }

    @Override
    public Object getRowKey(Oc oc) {
        return oc.getId();
    }

    @Override
    public List<Oc> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
    	OcDAO ocDAO = new OcDAO();
    	listaLazyOc = ocDAO.listaLazy(first, pageSize);
    	if (getRowCount() <= 0) {  
            setRowCount(ocDAO.totalOc());  
        }  
        // set the page dize  
        setPageSize(pageSize);  
        return listaLazyOc;  
    }
}
