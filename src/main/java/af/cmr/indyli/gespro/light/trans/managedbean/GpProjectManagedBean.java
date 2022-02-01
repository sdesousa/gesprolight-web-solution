package af.cmr.indyli.gespro.light.trans.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import af.cmr.indyli.gespro.light.business.entity.GpOrganization;
import af.cmr.indyli.gespro.light.business.entity.GpPhase;
import af.cmr.indyli.gespro.light.business.entity.GpProject;
import af.cmr.indyli.gespro.light.business.entity.GpProjectManager;
import af.cmr.indyli.gespro.light.business.exception.GesproBusinessException;
import af.cmr.indyli.gespro.light.business.service.IGpPhaseService;
import af.cmr.indyli.gespro.light.business.service.IGpProjectService;
import af.cmr.indyli.gespro.light.business.service.impl.GpOrganizationServiceImpl;
import af.cmr.indyli.gespro.light.business.service.impl.GpPhaseServiceImpl;
import af.cmr.indyli.gespro.light.business.service.impl.GpProjectManagerServiceImpl;
import af.cmr.indyli.gespro.light.business.service.impl.GpProjectServiceImpl;

@ManagedBean(name = "ctrProjetBean")
@RequestScoped
public class GpProjectManagedBean implements Serializable {

	private GpProject projectDataBean = new GpProject();
	private GpOrganization organizationDataBean = new GpOrganization();
	private GpProjectManager gpProjectManagerDataBean = new GpProjectManager();
	private IGpProjectService projetService = new GpProjectServiceImpl();
	private IGpPhaseService phaseService = new GpPhaseServiceImpl();
	private GpProjectManagerServiceImpl empService = new GpProjectManagerServiceImpl();
	private GpOrganizationServiceImpl orgService = new GpOrganizationServiceImpl();

	private List<GpProject> projectList = null;
	private List<GpPhase> phaseList = null;
	private List<GpOrganization> organizations;
	private List<GpProjectManager> projectManagers;
	private GpOrganization org;

	private String idOrg;
	private String idEmp;
	private int projectId;
	
	public GpProjectManagedBean() {
		this.projectList = this.projetService.findAll();
	}

	public String saveProject() throws GesproBusinessException {
		this.projetService.create(this.projectDataBean);
		this.projectList = this.projetService.findAll();

//		System.out.println( "ID ORG :" + this.organizationDataBean.getId() + "   ID EMP : " + gpProjectManagerDataBean.getId());
		System.out.println(this.idOrg);
		return "success";
	}

	public String addProject() {
		this.organizations = this.orgService.findAll();
		this.projectManagers = this.empService.findAll();

		return "success";
	}

	public String updateProject() {
		return "succcess";
	}

	public String getProjectPhase() {
		this.projectId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

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

	public String getIdOrg() {
		return idOrg;
	}

	public void setIdOrg(String idOrg) {
		this.idOrg = idOrg;
	}

	public String getIdEmp() {
		return idEmp;
	}

	public void setIdEmp(String idEmp) {
		this.idEmp = idEmp;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

}
