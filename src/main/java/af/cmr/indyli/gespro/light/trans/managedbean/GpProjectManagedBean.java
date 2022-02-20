package af.cmr.indyli.gespro.light.trans.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

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
@RequestScoped
public class GpProjectManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -901050335633154583L;
	private GpProject projectDataBean = new GpProject();
	private IGpProjectService projetService = new GpProjectServiceImpl();
	private IGpPhaseService phaseService = new GpPhaseServiceImpl();
	private IGpProjectManagerService<GpProjectManager> pmService = new GpProjectManagerServiceImpl();
	private IGpOrganizationService orgService = new GpOrganizationServiceImpl();

	private List<GpProject> projectList = null;
	private List<GpPhase> phaseList = null;
	private List<GpOrganization> organizations;
	private List<GpProjectManager> projectManagers;
	

	private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

	public GpProjectManagedBean() {
		this.projectList = this.projetService.findAll();
		this.organizations = this.orgService.findAll();
		this.projectManagers = this.pmService.findAll();
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public String saveProject() {
		try {
			//Si l'ID n'est pas renseigné alors c'est une creation
			if (Objects.isNull(this.projectDataBean.getId())) {
				this.projetService.create(this.projectDataBean);
			}else {
				//S'il est renseigné alors c'est une mise à jour
				this.projetService.update(this.projectDataBean);
			}
		} catch (GesproBusinessException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
		this.projectList = this.projetService.findAll();
		return "success";
	}

	public String addProject() {
		this.projectDataBean = new GpProject();
		return "success";
	}

	// Recharger le formulaire update
	public String selectProjectById() throws GesproBusinessException {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		this.projectDataBean = this.projetService.findById(Integer.valueOf(id));
		return "succcess";
	}


	public String getProjectPhase() {
		Integer projectId = Integer
				.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

		//On sette l'identifiant du projet en cours, afin de pouvoir y adosser les phases ulterieurement
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentProjectId", projectId);
		this.phaseList = this.phaseService.findByProjectId(projectId);
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
}
