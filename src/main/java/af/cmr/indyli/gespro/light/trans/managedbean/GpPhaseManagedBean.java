package af.cmr.indyli.gespro.light.trans.managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import af.cmr.indyli.gespro.light.business.entity.GpPhase;
import af.cmr.indyli.gespro.light.business.entity.GpProject;
import af.cmr.indyli.gespro.light.business.exception.GesproBusinessException;
import af.cmr.indyli.gespro.light.business.service.IGpPhaseService;
import af.cmr.indyli.gespro.light.business.service.IGpProjectService;
import af.cmr.indyli.gespro.light.business.service.impl.GpPhaseServiceImpl;
import af.cmr.indyli.gespro.light.business.service.impl.GpProjectServiceImpl;

@ManagedBean(name = "ctrPhaseBean")
@SessionScoped
public class GpPhaseManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7723786038820169772L;
	private GpPhase phaseDataBean = new GpPhase();
	private IGpPhaseService phaseService = new GpPhaseServiceImpl();
	private IGpProjectService projectService = new GpProjectServiceImpl();
	private List<GpPhase> phaseList = null;
	private int projectId;

	@ManagedProperty(value = "#{ctrProjetBean}")
	private GpProjectManagedBean projectManagedBean = new GpProjectManagedBean();

	public GpPhaseManagedBean() {
		this.phaseList = this.phaseService.findAll();
	}

	public GpPhase getPhaseDataBean() {
		return phaseDataBean;
	}

	public String savePhase() {
		GpProject gpProject = new GpProject();
		gpProject = this.projectService.findById(projectId);

		this.phaseDataBean.setGpProject(gpProject);
		try {
			this.phaseService.create(phaseDataBean);
		} catch (GesproBusinessException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
		
			return "errors";
		}

		GpPhaseServiceImpl phaseServiceImpl = new GpPhaseServiceImpl();
		List<GpPhase> phases = phaseServiceImpl.findByProjectId(projectId);
		projectManagedBean.setPhaseList(phases);

		return "success";
	}

	public String addPhase() {
		projectId = Integer.valueOf(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("projectId"));
		return "success";
	}

	public void validationDateDebut(FacesContext context, UIComponent toValidate, Date value)
			throws ValidatorException {

		GpProject p = this.projectService.findById(projectId);
		Date dateDebut = value;

		if (dateDebut.before(p.getCreationDate())) {
			FacesMessage message = new FacesMessage("Une phase ne peut etre créer avant le projet correspondant !");
			throw new ValidatorException(message);
		}
	}

	public void setPhaseDataBean(GpPhase phaseDataBean) {
		this.phaseDataBean = phaseDataBean;
	}

	public List<GpPhase> getPhaseList() {
		return phaseList;
	}

	public void setPhaseList(List<GpPhase> phaseList) {
		this.phaseList = phaseList;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getProjectId() {
		return projectId;
	}

	public GpProjectManagedBean getProjectManagedBean() {
		return projectManagedBean;
	}

	public void setProjectManagedBean(GpProjectManagedBean projectManagedBean) {
		this.projectManagedBean = projectManagedBean;
	}

}
