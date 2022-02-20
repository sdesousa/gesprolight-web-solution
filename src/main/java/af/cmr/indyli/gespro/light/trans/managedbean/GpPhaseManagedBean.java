package af.cmr.indyli.gespro.light.trans.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import af.cmr.indyli.gespro.light.business.entity.GpPhase;
import af.cmr.indyli.gespro.light.business.exception.GesproBusinessException;
import af.cmr.indyli.gespro.light.business.service.IGpPhaseService;
import af.cmr.indyli.gespro.light.business.service.impl.GpPhaseServiceImpl;

@ManagedBean(name = "ctrPhaseBean")
@SessionScoped
public class GpPhaseManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7723786038820169772L;
	private GpPhase phaseDataBean = new GpPhase();
	private IGpPhaseService phaseService = new GpPhaseServiceImpl();
	private List<GpPhase> phaseList = null;

	// constructeur
	public GpPhaseManagedBean() {
		this.phaseList = this.phaseService.findAll();
	}

	// recupere l'id du project et renvoie vers form add phase
	public String addPhase() {
		Integer projectId = (Integer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentProjectId");
		phaseDataBean.getGpProject().setId(projectId);
		return "success";
	}

	// appelle create phase
	public String savePhase() {
		try {
			this.phaseService.create(phaseDataBean);
		} catch (GesproBusinessException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);

			return null;
		}
		Integer projectId = (Integer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentProjectId");
		this.phaseList = phaseService.findByProjectId(projectId);
		return "success";
	}

//getters and setters
	public void setPhaseDataBean(GpPhase phaseDataBean) {
		this.phaseDataBean = phaseDataBean;
	}

	public GpPhase getPhaseDataBean() {
		return phaseDataBean;
	}

	public List<GpPhase> getPhaseList() {
		return phaseList;
	}

	public void setPhaseList(List<GpPhase> phaseList) {
		this.phaseList = phaseList;
	}
}
