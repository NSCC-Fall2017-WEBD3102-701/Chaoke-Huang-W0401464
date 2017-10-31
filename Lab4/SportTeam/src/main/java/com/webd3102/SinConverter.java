package com.webd3102;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import static java.util.regex.Pattern.matches;


@FacesConverter("com.webd3102.SportTeam.SinConverter")
public class SinConverter implements Converter {


    public Object getAsObject(FacesContext facesContext, UIComponent component, String value){
        String regex = "\\d\\d\\d-?\\d\\d\\d-?\\d\\d\\d";
        if(matches(regex,value)){
        StringBuilder sb = new StringBuilder( value.replaceAll("-",""));
        sb.insert(3,"-");
        sb.insert(7,"-");
        SinData sinData = new SinData(sb.toString());
        return sinData;
        }
        else{
            FacesMessage msg = new FacesMessage("SIN should be like:123-123-123");
            throw new ConverterException(msg);
        }


    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value){

        return value.toString();

    }



}
