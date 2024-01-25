import java.io.Serializable;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Mobile implements Serializable {

    private static final long serialVersionUID = -7250065889869767422L;

    // @NotNull(message="Please enter the model number")
    private String mno;

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public void validateModelNo(FacesContext context, UIComponent comp,
                                Object value) {


        System.out.println("inside validate method");

        String mno = value.toString();
        Double big = (Double) value;
        System.out.println(mno);


        if (big >= 5 || big <=-5.0) {
            ((UIInput) comp).setValid(false);

            FacesMessage message = new FacesMessage(
                    "Интервал (-5;5)");
            context.addMessage(comp.getClientId(context), message);
        }
    }

}