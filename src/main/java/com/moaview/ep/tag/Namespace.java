package com.moaview.ep.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.moaview.ep.exception.EpException;
import com.moaview.ep.util.EpUtils;

public class Namespace extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private String prefix;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter jw= pageContext.getOut();
		
		try {
			prefix = StringUtils.isBlank(prefix) ? "ep_" : prefix;
			
			Object namespace = pageContext.getAttribute("$namespace$",PageContext.PAGE_SCOPE);
			
			if(namespace==null) {
				String simpleName = pageContext.getPage().getClass().getSimpleName();
				namespace = EpUtils.nameUUIDFromBytes(simpleName);
				pageContext.setAttribute("$namespace$",namespace);
				
			}
		
			jw.write(String.format("%s%s",prefix, namespace));
		} catch (IOException e) {
			throw new EpException("tag error ",e); 
		}
		
		return SKIP_BODY;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
