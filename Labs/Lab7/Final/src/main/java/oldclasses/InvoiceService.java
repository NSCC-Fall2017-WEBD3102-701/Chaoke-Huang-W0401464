package oldclasses;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name="invoiceService")
public class InvoiceService {
    public static final String URL = "http://localhost:9090/Invoices";
    public static final String INDEX_XHTML = "index.xhtml";
    private InvoiceDBUtil invoiceDBUtil;
    private ItemDBUtil itemBDUtil;
    private Logger logger = Logger.getLogger(getClass().getName());
    private List<Invoice> invoiceList = new ArrayList<>();
    private static Map<String,String> remoteItems;
    private static Invoice currentInvoice;

    public InvoiceService() throws Exception {
        super();
        remoteItems = new LinkedHashMap<String, String>();
        invoiceDBUtil = InvoiceDBUtil.getInstance();
        itemBDUtil = ItemDBUtil.getInstance();
        remoteItems = itemBDUtil.getItems();

    }

    public void setCurrentInvoice(Invoice currentInvoice) { // Cheating with static here. Replace with scope.
        InvoiceService.currentInvoice = currentInvoice;
    }

    public Invoice getCurrentInvoice() {
        return currentInvoice;
    }

    public String addAction(Invoice invoice) {
        try {
            invoiceDBUtil.addInvoice(invoice);


        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error adding invoice", exc);

            // add error message for JSF page
            addErrorMessage(exc);
        }

        return INDEX_XHTML;
    }

    public String deleteAction(Invoice invoice) {
        try {
            invoiceDBUtil.deleteInvoice(invoice.getId());

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error deleting invoice", exc);

            // add error message for JSF page
            addErrorMessage(exc);
        }
        return INDEX_XHTML;
    }

    public String updateRemoteAction(Invoice invoice) {
        try {
            invoiceDBUtil.updateInvoice(invoice);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error updating invoice", exc);

            // add error message for JSF page
            addErrorMessage(exc);
        }
        return INDEX_XHTML;
    }

    public String updateAction(Invoice invoice) {
        this.setCurrentInvoice(invoice);
        return "updateinvoice.xhtml";
    }

    public List<Invoice> getList() {

        logger.info("Loading invoice");

        invoiceList.clear();

        try {

            // get all actors from database
            invoiceList = invoiceDBUtil.getInvoices();

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading invoice", exc);

            // add error message for JSF page
            addErrorMessage(exc);
        }

        return invoiceList;
    }

    public Map<String,String> getRemoteItems() {
        return remoteItems;
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}