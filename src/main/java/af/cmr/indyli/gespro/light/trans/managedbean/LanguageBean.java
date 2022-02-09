package af.cmr.indyli.gespro.light.trans.managedbean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "language")
@SessionScoped
public class LanguageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String localeCode;

	private static Map<String, Object> countries;
	// private String localeCode;
	private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

	static {
		countries = new LinkedHashMap<String, Object>();
		countries.put("FR", Locale.FRANCE);
		countries.put("EN", Locale.ENGLISH); // label, value

	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	// value change event listener
	public void countryLocaleCodeChanged(ValueChangeEvent e) {

		String newLocaleValue = e.getNewValue().toString();

		// loop country map to compare the locale code
		for (Map.Entry<String, Object> entry : countries.entrySet()) {

			if (entry.getValue().toString().equals(newLocaleValue)) {

				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());

			}
		}
	}
}