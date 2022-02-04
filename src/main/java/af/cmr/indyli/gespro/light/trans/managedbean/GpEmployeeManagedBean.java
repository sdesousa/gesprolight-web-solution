package af.cmr.indyli.gespro.light.trans.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.routines.EmailValidator;

import af.cmr.indyli.gespro.light.business.dao.IGpEmployeeDAO;
import af.cmr.indyli.gespro.light.business.dao.impl.GpEmployeeDAOImpl;
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

	public GpEmployeeManagedBean() {
		this.empList = this.empService.findAll();
	}

	public String saveEmployee() throws GesproBusinessException {
		this.empService.create(this.empDataBean);
		this.empList = this.empService.findAll();
		return "success";
	}

	public String updateEmpById() {
		String editEmpId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("empId");
		this.empDataBean = this.empService.findById(Integer.valueOf(editEmpId));
		return "success";
	}

	public String updateEmployee() throws GesproBusinessException {
		this.empService.update(this.empDataBean);
		this.empList = this.empService.findAll();
		return "success";
	}

	public String deleteEmpById() {
		String delEmpId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("empId");
		this.empService.deleteById(Integer.valueOf(delEmpId));
		this.empList = this.empService.findAll();
		return "success";
	}

	// fonction validation
	public void validateEmail(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String eMail = (String) value;
		IGpEmployeeDAO<GpEmployee> dao = new GpEmployeeDAOImpl();

		if (!EmailValidator.getInstance().isValid(eMail)) {
			FacesMessage message = new FacesMessage("Adresse Email invalide !");
			throw new ValidatorException(message);
		} else {
			if (dao.ifEmpExistByFileNumberOrEmail("", eMail, "")) {

				FacesMessage message = new FacesMessage(
						String.format("Un employee existe deja avec ce cet email[%s]", eMail));
				throw new ValidatorException(message);
			}
		}
	}

	public void validateFileNumber(FacesContext context, UIComponent toValidate, Object value)
			throws ValidatorException {
		String fileNumber = (String) value;
		IGpEmployeeDAO<GpEmployee> dao = new GpEmployeeDAOImpl();

		if (dao.ifEmpExistByFileNumberOrEmail(fileNumber, "", "")) {

			FacesMessage message = new FacesMessage(
					String.format("Un employee existe deja avec ce matricule[%s]", fileNumber));
			throw new ValidatorException(message);
		}

	}

	public void validateLogin(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String login = (String) value;
		IGpEmployeeDAO<GpEmployee> dao = new GpEmployeeDAOImpl();

		if (dao.ifEmpExistByFileNumberOrEmail("", "", login)) {

			FacesMessage message = new FacesMessage(String.format("Un employee existe deja avec ce login[%s]", login));
			throw new ValidatorException(message);
		}

	}

	// fin fonctions validation
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
