package it.fmuia.example.struts;

import it.fmuia.example.hibernate.Customer;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HelloWorldAction extends Action
{
	@PersistenceUnit(name = "persistenceUnit", unitName = "persistenceUnit")
	private EntityManager em;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		this.em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
		Customer customer = new Customer(1, "Hello", "world", new Date());
		this.em.getTransaction().begin();
		this.em.persist(customer);
		this.em.getTransaction().commit();

		Customer myCustomer = em.find(Customer.class, 1L);
		HelloWorldForm helloWorldForm = (HelloWorldForm) form;
		helloWorldForm.setMessage("CUSTOMER IS  " + myCustomer.getName() + " " + myCustomer.getAddress());
		this.em.close();
		return mapping.findForward("success");
	}

}