package af.cmr.indyli.gespro.light.trans.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import af.cmr.indyli.gespro.light.business.entity.GpEmployee;
import af.cmr.indyli.gespro.light.business.exception.GesproBusinessException;
import af.cmr.indyli.gespro.light.business.service.IGpEmployeeService;
import af.cmr.indyli.gespro.light.business.service.impl.GpEmployeeServiceImpl;

@ManagedBean(name = "ctrEmployeeBean")
@RequestScoped
public class GpEmployeeManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GpEmployee empDataBean = new GpEmployee();
	private IGpEmployeeService<GpEmployee> empService = new GpEmployeeServiceImpl();

	private List<GpEmployee> empList = null;

	// Contructeur

	public GpEmployeeManagedBean() {
		this.empList = this.empService.findAll();
	}

	// appel create employee
	public String saveEmployee() {
		try {
			this.empService.create(this.empDataBean);
		} catch (GesproBusinessException e) {
			FacesMessage message = new FacesMessage(e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
		this.empList = this.empService.findAll();
		return "success";
	}

	// recupere l'id employee to update et renvoie sur le form update
	public String updateEmpById() {
		String editEmpId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("empId");
		this.empDataBean = this.empService.findById(Integer.valueOf(editEmpId));
		return "success";
	}

	// appel update employee
	public String updateEmployee() throws GesproBusinessException {
		this.empService.update(this.empDataBean);
		this.empList = this.empService.findAll();
		return "success";
	}

	// recupee l'id employee to delete et appelle delete employee
	public String deleteEmpById() {
		String delEmpId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("empId");
		this.empService.deleteById(Integer.valueOf(delEmpId));
		this.empList = this.empService.findAll();
		return "success";
	}

	// getters and setters
	public GpEmployee getEmpDataBean() {
		return empDataBean;
	}

	public void setEmpDataBean(GpEmployee empDataBean) {
		this.empDataBean = empDataBean;
	}

	public List<GpEmployee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<GpEmployee> empList) {
		this.empList = empList;
	}
}
