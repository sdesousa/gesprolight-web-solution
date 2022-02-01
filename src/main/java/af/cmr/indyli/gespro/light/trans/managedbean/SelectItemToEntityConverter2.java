package af.cmr.indyli.gespro.light.trans.managedbean;

import java.util.List;
import java.util.function.Predicate;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import af.cmr.indyli.gespro.light.business.entity.GpProjectManager;

@FacesConverter(value = "SelectItemToEntityConverter2")
public class SelectItemToEntityConverter2 implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		Object o = null;
		if (!(value == null || value.isEmpty())) {
			o = this.getSelectedItemAsEntity(comp, value);
		}
		return o;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
		String s = "";
		if (value != null) {
			s = ((GpProjectManager) value).getId().toString();
		}
		return s;
	}

	private GpProjectManager getSelectedItemAsEntity(UIComponent comp, String value) {
		GpProjectManager item = null;

		List<GpProjectManager> selectItems = null;
		for (UIComponent uic : comp.getChildren()) {
			if (uic instanceof UISelectItems) {
				Long itemId = Long.valueOf(value);
				selectItems = (List<GpProjectManager>) ((UISelectItems) uic).getValue();

				if (itemId != null && selectItems != null && !selectItems.isEmpty()) {
					Predicate<GpProjectManager> predicate = i -> i.getId().equals(itemId);
					item = selectItems.stream().filter(predicate).findFirst().orElse(null);
				}
			}
		}

		return item;
	}
}