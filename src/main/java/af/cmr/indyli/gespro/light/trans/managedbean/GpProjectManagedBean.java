package af.cmr.indyli.gespro.light.trans.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import af.cmr.indyli.gespro.light.business.dao.IGpProjectDAO;
import af.cmr.indyli.gespro.light.business.dao.impl.GpProjectDAOImpl;
import af.cmr.indyli.gespro.light.business.entity.GpOrganization;
import af.cmr.indyli.gespro.light.business.entity.GpPhase;
import af.cmr.indyli.gespro.light.business.entity.GpProject;
import af.cmr.indyli.gespro.light.business.entity.GpProjectManager;
import af.cmr.indyli.gespro.light.business.exception.GesproBusinessException;
import af.cmr.indyli.gespro.light.business.service.IGpOrganizationService;
import af.cmr.indyli.gespro.light.business.service.IGpPhaseService;
import af.cmr.indyli.gespro.light.business.service.IGpProjectManagerService;
import af.cmr.indyli.gespro.light.business.service.IGpProjectService;
import af.cmr.indyli.gespro.light.business.service.impl.GpOrganizationServiceImpl;
import af.cmr.indyli.gespro.light.business.service.impl.GpPhaseServiceImpl;
import af.cmr.indyli.gespro.light.business.service.impl.GpProjectManagerServiceImpl;
import af.cmr.indyli.gespro.light.business.service.impl.GpProjectServiceImpl;

@ManagedBean(name = "ctrProjetBean")
@SessionScoped
public class GpProjectManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -901050335633154583L;
	private GpProject projectDataBean = new GpProject();
	private GpOrganization organizationDataBean = new GpOrganization();
	private GpProjectManager gpProjectManagerDataBean = new GpProjectManager();
	private IGpProjectService projetService = new GpProjectServiceImpl();
	private IGpPhaseService phaseService = new GpPhaseServiceImpl();
	private IGpProjectManagerService<GpProjectManager> pmService = new GpProjectManagerServiceImpl();
	private IGpOrganizationService orgService = new GpOrganizationServiceImpl();

	private List<GpProject> projectList = null;
	private List<GpPhase> phaseList = null;
	private List<GpOrganization> organizations;
	private List<GpProjectManager> projectManagers;
	private GpOrganization org;

	private Integer idOrg;
	private Integer idPm;
	private int projectId;

	public GpProjectManagedBean() {
		this.org = new GpOrganization();
		this.projectList = this.projetService.findAll();
		this.organizations = this.orgService.findAll();
		this.projectManagers = this.pmService.findAll();
	}

	public String saveProject() throws ValidatorException {

		GpOrganization organization = (GpOrganization) orgService.findById(idOrg);
		this.projectDataBean.setGpOrganization(organization);

		GpProjectManager manager = (GpProjectManager) this.pmService.findById(idPm);
		this.projectDataBean.setGpChefProjet(manager);

		try {
			this.projetService.create(this.projectDataBean);
		} catch (GesproBusinessException e) {

			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;

		}
		this.projectList = this.projetService.findAll();

		return "success";
	}

	public String addProject() {
		this.idOrg = null;
		this.idPm = null;
		this.projectDataBean = new GpProject();
		return "success";
	}

	public String updateProject() throws GesproBusinessException {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		this.projectDataBean = this.projetService.findById(Integer.valueOf(id));
		this.idOrg = this.projectDataBean.getGpOrganization().getId();
		this.idPm = this.projectDataBean.getGpChefProjet().getId();
		return "succcess";
	}

	public String updateProjectById() throws GesproBusinessException {

		GpOrganization organization = (GpOrganization) orgService.findById(idOrg);
		this.projectDataBean.setGpOrganization(organization);

		GpProjectManager manager = (GpProjectManager) this.pmService.findById(idPm);
		this.projectDataBean.setGpChefProjet(manager);

		this.projetService.update(this.projectDataBean);
		this.projectList = this.projetService.findAll();

		return "success";
	}

	// Debut validation

	public void validateCode(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
		String code = (String) value;
		IGpProjectDAO dao = new GpProjectDAOImpl();

		if (dao.ifProjectByCode(code)) {
			FacesMessage message = new FacesMessage("Un projet ayant ce code existe !");
			throw new ValidatorException(message);
		}
	}

	public void validateEndDate(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {

		Object startDate = ((UIInput) context.getViewRoot().findComponent("addproject:startDate")).getSubmittedValue();
		Object endDate = ((UIInput) context.getViewRoot().findComponent("addproject:endDate")).getSubmittedValue();

		System.out.println(startDate.toString() + " DDD " + endDate);
	}

	// Fin validation

	public Integer getIdPm() {
		return idPm;
	}

	public void setIdPm(Integer idPm) {
		this.idPm = idPm;
	}

	public String getProjectPhase() {
		this.projectId = Integer
				.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

		this.phaseList = this.phaseService.findByProjectId(Integer.valueOf(projectId));
		return "success";
	}

	public String update() {
		return "success";
	}

	public GpProject getProjectDataBean() {
		return projectDataBean;
	}

	public void setProjectDataBean(GpProject projectDataBean) {
		this.projectDataBean = projectDataBean;
	}

	public GpOrganization getOrganizationDataBean() {
		return organizationDataBean;
	}

	public GpProjectManager getGpProjectManagerDataBean() {
		return gpProjectManagerDataBean;
	}

	public void setGpProjectManagerDataBean(GpProjectManager gpProjectManagerDataBean) {
		this.gpProjectManagerDataBean = gpProjectManagerDataBean;
	}

	public void setOrganizationDataBean(GpOrganization organizationDataBean) {
		this.organizationDataBean = organizationDataBean;
	}

	public GpOrganization getOrg() {
		return org;
	}

	public void setOrg(GpOrganization org) {
		this.org = org;
	}

	public List<GpProject> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<GpProject> projectList) {
		this.projectList = projectList;
	}

	public List<GpPhase> getPhaseList() {
		return phaseList;
	}

	public void setPhaseList(List<GpPhase> phaseList) {
		this.phaseList = phaseList;
	}

	public List<GpProjectManager> getProjectManagers() {
		return projectManagers;
	}

	public void setProjectManagers(List<GpProjectManager> projectManagers) {
		this.projectManagers = projectManagers;
	}

	public List<GpOrganization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<GpOrganization> organizations) {
		this.organizations = organizations;
	}

	public Integer getIdOrg() {
		return idOrg;
	}

	public void setIdOrg(Integer idOrg) {
		this.idOrg = idOrg;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

}
