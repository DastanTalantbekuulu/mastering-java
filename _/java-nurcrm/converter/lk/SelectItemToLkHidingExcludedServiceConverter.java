package kg.nurtelecom.util.converter.lk;

import kg.nurtelecom.crm.entity.lk.LkHidingExcludedService;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.List;
import java.util.function.Predicate;

@FacesConverter(value = "SelectItemToLkHidingExcludedServiceConverter")
public class SelectItemToLkHidingExcludedServiceConverter implements Converter {

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
            s = ((LkHidingExcludedService) value).getLkServiceId().toString();
        }
        return s;
    }

    private LkHidingExcludedService getSelectedItemAsEntity(UIComponent comp, String value) {
        LkHidingExcludedService item = null;

        List<LkHidingExcludedService> selectItems = null;
        for (UIComponent uic : comp.getChildren()) {
                Long itemId = Long.valueOf(value);
                selectItems = (List<LkHidingExcludedService>) ((UISelectItems) uic).getValue();

                if (itemId != null && selectItems != null && !selectItems.isEmpty()) {
                    Predicate<LkHidingExcludedService> predicate = i -> i.getLkServiceId().equals(itemId);
                    item = selectItems.stream().filter(predicate).findFirst().orElse(null);
                }

        }

        return item;
    }
}
